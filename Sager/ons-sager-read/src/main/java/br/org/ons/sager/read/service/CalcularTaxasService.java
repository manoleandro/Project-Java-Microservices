package br.org.ons.sager.read.service;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.querydsl.collections.CollQuery;
import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.taxa.HorasIndisponibilidade;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Participacao;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
import br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida;
import br.org.ons.geracao.evento.taxa.TipoTaxa;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.EquipamentoUGParam;
import br.org.ons.sager.read.domain.InstalacaoTaxas;
import br.org.ons.sager.read.domain.Interligacoes;
import br.org.ons.sager.read.domain.MCEquipamentoParametro;
import br.org.ons.sager.read.domain.Parametro;
import br.org.ons.sager.read.domain.ParametroUGParam;
import br.org.ons.sager.read.domain.QInstalacaoTaxas;
import br.org.ons.sager.read.domain.QMCEquipamentoParametro;
import br.org.ons.sager.read.domain.TaxaEventosUGParam;
import br.org.ons.sager.read.domain.UGParam;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.domain.Valores;
import br.org.ons.sager.read.repository.InstalacaoTaxasRepository;
import br.org.ons.sager.read.repository.InterligacoesRepository;
import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;
import br.org.ons.sager.read.repository.UsinasRepository;
import br.org.ons.sager.read.web.rest.util.JavaUtil;

@Service
public class CalcularTaxasService {
	
		private final Logger log = LoggerFactory.getLogger(CalcularTaxasService.class);
		 
		private static final String SCENARIO_PRINCIPAL = "Principal";
		
		private static final String SCENARIO_NAME = "scenarioName"; 
		 
		private static final String INSTALACAO_TAXAS = "instalacaoTaxas";
		
		private static final String INDISPONIBILIDADE = "indisponibilidade";
		 		
		private final HorasIndisponibilidade qHorasIndisponibilidade = alias(HorasIndisponibilidade.class, "horasIndisponibilidade");
		
		private final ParametroTaxa qParametroTaxa = alias(ParametroTaxa.class,"parametroTaxa");
		
		private final Taxa qTaxa = alias(Taxa.class,"taxa");
		
		private final br.org.ons.sager.read.domain.Taxa qTaxaRead = alias(br.org.ons.sager.read.domain.Taxa.class,"taxa");

	    private UsinasRepository usinasRepository;

	    private InterligacoesRepository interligacoesRepository;

		private InstalacaoTaxasRepository instalacaoTaxasRepository;

		private MCEquipamentoParametroRepository mcEquipParamRepository;		

		private int versaoTaxa;
		
		private int versaoCenario;
		
		protected SimpleDateFormat sdfAnoMes = new SimpleDateFormat("yyyyMM");

		@Inject
		public CalcularTaxasService(UsinasRepository usinasRepository, InterligacoesRepository interligacoesRepository,
				InstalacaoTaxasRepository instalacaoTaxasRepository,
				MCEquipamentoParametroRepository mcEquipParamRepository) {
			super();
			this.usinasRepository = usinasRepository;
			this.interligacoesRepository = interligacoesRepository;
			this.instalacaoTaxasRepository = instalacaoTaxasRepository;
			this.mcEquipParamRepository = mcEquipParamRepository;
		}

		@SuppressWarnings("unchecked")
		@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event", 
		containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
		public void handleTaxasAcumuladasCalculadasV1Event(EventMessage<TaxasAcumuladasCalculadasV1Event> eventMessage) throws JMSException {
			
			EventMetadata metadata = null;
			TaxasAcumuladasCalculadasV1Event event = null;
			
			try{
				event = eventMessage.getEvent();
				metadata = eventMessage.getMetadata();
				
				/**
				 * Salva a Projeção InstalacaoTaxas (tela: consulta-taxas)
				 */
				//Adiciona apenas as TaxasMensais do Período
		        List<Taxa> taxas = from($(qTaxa),event.getTaxas()).
		        		where($(qTaxa.getPeriodo().getDataInicio()).eq(event.getDataInicioApuracao()))
		        .fetchResults().getResults();
		        taxas.addAll((List<Taxa>) (List<?>) event.getTaxasAcumuladas());
				this.saveInstalacaoTaxas(taxas,event.getDataInicioApuracao(), metadata);

				/**
				 * Salva a Projeção Memoria de Calculo ( Taxa Mensal)
				 */
				this.saveMemoriaCalculoMensal(taxas,new ArrayList<ParametroTaxa>(event.getParametros()), 
						event.getDataInicioApuracao(), metadata);
				
				/**
				 * Salva a projeção UGParam(3,2,1) (Tela: memoria-calculo acumulada)
				 */
				this.saveMemoriaCalculoAcumuladas((List<Taxa>) (List<?>) event.getTaxasAcumuladas(),
						new ArrayList<ParametroTaxa>(event.getParametros()),event.getDataInicioApuracao(), metadata);
				
			}catch(Exception e){
				log.error("handleTaxasAcumuladasCalculadasV1Event",e);
			}
		}		

		
		@SuppressWarnings("unchecked")
		@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event", 
		containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
		public void handleTaxasAcumuladasCalculadasV2Event(EventMessage<TaxasAcumuladasCalculadasV2Event> eventMessage) throws JMSException {
			
			EventMetadata metadata = null;
			TaxasAcumuladasCalculadasV2Event event;
			
			try{
				 event = eventMessage.getEvent();
				 metadata = eventMessage.getMetadata();
				 
				/**
				 * Salva a Projeção InstalacaoTaxas (tela: consulta-taxas)
				 */
				this.saveInstalacaoTaxas((List<Taxa>) (List<?>) event.getTaxas(),event.getDataInicioApuracao(), metadata);
				
				/**
				 * Salva a projeção UGParam(3,2,1) (Tela: memoria-calculo acumulada)
				 */
				List<ParametroTaxa> parametros = new ArrayList<ParametroTaxa>(event.getParametrosV1());
				parametros.addAll(new ArrayList<ParametroTaxa>(event.getParametrosV2()));
				this.saveMemoriaCalculoAcumuladas((List<Taxa>) (List<?>) event.getTaxas(),
						parametros,event.getDataInicioApuracao(), metadata);
								
			}catch(Exception e){
				log.error("handleTaxasAcumuladasCalculadasV2Event",e);
			}
		}
		
		public void saveInstalacaoTaxas(List<Taxa> taxas,Date datainicioApuracao,EventMetadata metadata){
			log.debug("saveInstalacaoTaxasAcumuladas: ["+datainicioApuracao+"]["+metadata+"]");
			
			InstalacaoTaxas novaInstalacaoTaxas = new InstalacaoTaxas();
			
			novaInstalacaoTaxas.setInstalacao(metadata.getAggregateId());
			String nomeInstalacao;
			Usinas usina = usinasRepository.findOne(metadata.getAggregateId());
			if(usina != null){
				nomeInstalacao = usina.getNome();
			}else{
				Interligacoes interligacao = interligacoesRepository.findOne(metadata.getAggregateId());
				nomeInstalacao = interligacao.getNome();
			}

			br.org.ons.sager.read.domain.Taxa taxaRead = new br.org.ons.sager.read.domain.Taxa();
			Calendar dataapuracao = Calendar.getInstance();
			dataapuracao.setTime(datainicioApuracao);
			taxaRead.setMajorVersion(metadata.getMajorVersion());
			taxaRead.setIdInstalacao(metadata.getAggregateId());
			taxaRead.setNomeInstalacao(nomeInstalacao);
			taxaRead.setJobId(metadata.getCorrelationId());
			taxaRead.setId(metadata.getAggregateId());
			taxaRead.setAno(dataapuracao.get(Calendar.YEAR));
			taxaRead.setMes(dataapuracao.get(Calendar.MONTH)+1);
			taxaRead.setDataApuracao(dataapuracao.getTime());
			taxaRead.setNomeCenario(metadata.getProperties().get(SCENARIO_NAME).toString());
			taxaRead.setDataCalculo(Date.from(metadata.getCreationDate().toInstant()));
			taxaRead.setRegulamentacao(this.getRegulamentacao(datainicioApuracao));
			if(taxas != null){
				for(Taxa taxa : taxas){
					Valores valores = new Valores();
					valores.setComentario(this.getAllComentarios(taxa.getComentarios()));
					valores.setNome(this.getNomeTaxaTipoInstalacao(taxa.getCodigo(), taxa.getTipo()));
					valores.setTipo(taxa.getTipo().toString());
					valores.setValor(taxa.getValor());
					taxaRead.addValor(valores);
				}
			}		
			taxaRead.setOutraVersaoCenario("n");
			taxaRead.setOutraVersaoTaxa("n");
			versaoTaxa = 1;
			versaoCenario = 1;
			
			/**
			 * Consulta no XS se existe uma Instalação para: InstalacaoID, Ano, Mes, Cenario 
			 */
			QInstalacaoTaxas qInstalacaoTaxas = new QInstalacaoTaxas(INSTALACAO_TAXAS);
			List<InstalacaoTaxas> listByInstalacao = IterableConverter.toList(
					instalacaoTaxasRepository.findAll(
							qInstalacaoTaxas.instalacao.equalsIgnoreCase(metadata.getAggregateId())));
			List<InstalacaoTaxas> listInstalacaoTaxa = from(qInstalacaoTaxas,listByInstalacao).
			        where(qInstalacaoTaxas.taxas.any().ano.eq(taxaRead.getAno()).
			        		and(qInstalacaoTaxas.taxas.any().mes.eq(taxaRead.getMes())
			        		)).fetchResults().getResults();	

			InstalacaoTaxas instalacaoTaxa = null;
			if(listInstalacaoTaxa != null && !listInstalacaoTaxa.isEmpty() ){
				instalacaoTaxa = listInstalacaoTaxa.get(0);
			}
			
			/**
			 * Se instalacaoTaxa != null. Adiciona as taxas nos Valores caso essas taxas não existam, 
			 * caso contrario, versiona a taxa.
			 */
			if(instalacaoTaxa != null){
				
				br.org.ons.sager.read.domain.Taxa taxa = from($(qTaxaRead),instalacaoTaxa.getTaxas()).
						where($(qTaxaRead.getAno()).eq(taxaRead.getAno()).
								and($(qTaxaRead.getMes()).eq(taxaRead.getMes())).
								and($(qTaxaRead.getNomeCenario()).eq(taxaRead.getNomeCenario()))).
						orderBy($(qTaxaRead.getVersaoTaxa()).desc())
						.fetchFirst();
				
				
				
				if(taxa != null){
					//Se não existe as taxas entao adiciona no mesmo container,
					if( taxa.getValores().containsAll(taxaRead.getValores())){
						
						versaoTaxa = taxa.getVersaoTaxa() + 1;
						taxaRead.setOutraVersaoTaxa("s");
						
						// altera o ident para o passado = 2.
						if(taxa.getIdent() == 1){
							taxa.setIdent(2);
						}
					}else{
						// Não contem, adicionar
						taxa.addValores(taxaRead.getValores());
						return;
					}
						
					if(!SCENARIO_PRINCIPAL.equalsIgnoreCase(taxa.getNomeCenario())){
						taxaRead.setOutraVersaoTaxa("s");
						taxa.setIdent(2);
						
						//Pega a Versao do ultimo cenario
						versaoCenario  = from($(qTaxaRead),instalacaoTaxa.getTaxas()).
								where($(qTaxaRead.getNomeCenario()).eq(taxaRead.getNomeCenario())).
								orderBy($(qTaxaRead.getVersaoCenario()).desc()).fetchFirst().getVersaoCenario();
						
					}
				}else{
					
					taxaRead.setOutraVersaoCenario("s");
					br.org.ons.sager.read.domain.Taxa taxaPrincipal = from($(qTaxaRead),instalacaoTaxa.getTaxas()).
							where($(qTaxaRead.getNomeCenario()).eq(SCENARIO_PRINCIPAL).
								and($(qTaxaRead.getIdent()).eq(1))).fetchOne();
					if(taxaPrincipal != null){
						taxaPrincipal.setOutraVersaoCenario("s");
					}
					
					//Pega a Versao do ultimo cenario
					versaoCenario  = from($(qTaxaRead),instalacaoTaxa.getTaxas()).
							orderBy($(qTaxaRead.getVersaoCenario()).desc()).fetchFirst().getVersaoCenario() + 1;
				}
			}
				
			taxaRead.setVersaoTaxa(versaoTaxa);
			taxaRead.setVersaoCenario(versaoCenario);
			// Se Cenário principal = 1 , Se não_principal = 3.
			if(SCENARIO_PRINCIPAL.equalsIgnoreCase(taxaRead.getNomeCenario())){
				taxaRead.setIdent(1);
			}else{
				taxaRead.setIdent(3);
			}
			
			this.saveInstalacaoTaxasRepository(listInstalacaoTaxa,taxaRead,instalacaoTaxa,novaInstalacaoTaxas);
			
	
		}
		
		private void saveInstalacaoTaxasRepository(List<InstalacaoTaxas> listInstalacaoTaxa,
				br.org.ons.sager.read.domain.Taxa taxaRead,
				InstalacaoTaxas instalacaoTaxa,
				InstalacaoTaxas novaInstalacaoTaxas){
			if(!listInstalacaoTaxa.isEmpty()){
				instalacaoTaxa.addTaxa(taxaRead);
				log.debug("taxa: "+taxaRead);
				log.debug("instalacaoTaxa: "+instalacaoTaxa);
				instalacaoTaxasRepository.save(instalacaoTaxa);				
			}else if(listInstalacaoTaxa.isEmpty()){
				novaInstalacaoTaxas.setTaxas(new ArrayList<>(Arrays.asList(taxaRead)));
				log.debug("novaInstalacaoTaxas: "+novaInstalacaoTaxas);
				instalacaoTaxasRepository.save(novaInstalacaoTaxas);		
			}else{
				log.debug("taxa: "+taxaRead);
				log.debug("instalacaoTaxa: "+instalacaoTaxa);
				instalacaoTaxasRepository.save(instalacaoTaxa);
			}
		}
		
		protected void saveMemoriaCalculoMensal(List<Taxa> taxas,List<ParametroTaxa> listParametroTaxa,Date dataInicioApuracao,EventMetadata metadata){
			
				for(Taxa taxa : JavaUtil.emptyIfNull(taxas)){
					
					//Se não foi calculada memoria de calculo, então nao deveremos salvar a memoria de calculo.
					if(	(taxa.getValor() == null && 
						 taxa.getComentarios() != null &&
						!taxa.getComentarios().isEmpty()) ||
						 !taxa.getTipo().equals(TipoTaxa.MENSAL)
						){
						continue; //vai para a proxima taxa acumulada.
					}
					
					String nomeTaxa = this.getNomeTaxa(taxa.getCodigo(), taxa.getTipo());
					
					/**
					 * Se o MCEquipamentoParametro já existe, então adiciona a Memoria de Calculo,
					 * senão cria.
					 * Mesmos: instalacao,dataApuracao,taxa
					 */
					QMCEquipamentoParametro qMCequipParam = new QMCEquipamentoParametro("mCEquipamentoParametro");
					MCEquipamentoParametro mcEquipParam = mcEquipParamRepository.findOne(
							qMCequipParam.instalacao.eq(metadata.getAggregateId()).and(
							qMCequipParam.dataApuracao.eq(dataInicioApuracao).and(
							qMCequipParam.taxaMemoriaCalculo.eq(nomeTaxa).and(
							qMCequipParam.versaoTaxa.eq(versaoTaxa).and(
							qMCequipParam.versaoCenario.eq(versaoCenario))))));	
					if( mcEquipParam != null){
						log.error("Memória de Calculo já existente: {}",mcEquipParam);
						mcEquipParam.setMemoriaCalculo(new ArrayList<UGParam>());
					}else{
						mcEquipParam = new MCEquipamentoParametro();
					}
					
					/**
					 * INST 1 - Equipamento da Taxa - ATUAL
					 */
					TaxaEventosUGParam txEvtUGParam = new TaxaEventosUGParam();
					txEvtUGParam.setData(dataInicioApuracao);
					txEvtUGParam.setIdent(1);
					txEvtUGParam.setTaxa(nomeTaxa);
					txEvtUGParam.setValor(taxa.getValor());
					txEvtUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
					mcEquipParam.addMemoriaCalculo(txEvtUGParam);
					log.debug("txEvtUGParam: "+txEvtUGParam);
						
					/**
					 * para cada equipamento, pegar o valor do ParametroEquipamento.
					 */
					
					List<Participacao> listParticipacaoOrdenada = 
							ordenarListaParticipante(taxa.getParticipacoesEquipamentos());
						for(Participacao participacao : JavaUtil.emptyIfNull(listParticipacaoOrdenada)){
							
							/**
							 * INST 2
							 */
							Equipamento equip = new Equipamento();
							equip.setId(participacao.getCodigoONS());
							equip.setNome(participacao.getIdParticipante());
							if(participacao.getValor() != null){
								equip.setParticipacao(participacao.getValor().multiply(new BigDecimal(100)));
							}
							equip.setValorPotencia(participacao.getPotencia());
							
							EquipamentoUGParam equipUGParam = new EquipamentoUGParam();
							equipUGParam.setData(taxa.getPeriodo().getDataInicio());
							equipUGParam.setIdent(2);
							equipUGParam.setTaxa(txEvtUGParam.getTaxa());
							equipUGParam.setTipoTaxa(txEvtUGParam.getTipoTaxa());
							equipUGParam.setEquipamento(equip);
							equipUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
							mcEquipParam.addMemoriaCalculo(equipUGParam);

							/**
							 * Para Esse EquipamentoAtual, pega a lista de Todos os Equipamentos/Parametros por UnidadeGeradora (60M)
							 * e monta o Ident 2. e 3
							 */				
							carregarParametrosV1(taxa,listParametroTaxa,participacao,txEvtUGParam,mcEquipParam,equip);
						}
						
						log.debug(mcEquipParam.getMemoriaCalculo().toString());
						
						/**
						 * Salva a Memória de Calculo.
						 */
						mcEquipParam.setDataApuracao(dataInicioApuracao);
						mcEquipParam.setInstalacao(metadata.getAggregateId());
						mcEquipParam.setTaxaMemoriaCalculo(nomeTaxa);
						mcEquipParam.setVersaoTaxa(versaoTaxa);
						mcEquipParam.setVersaoCenario(versaoCenario);
						mcEquipParam.setCorrelationId(metadata.getCorrelationId());
						mcEquipParamRepository.save(mcEquipParam);
				}
		}
		
		public void saveMemoriaCalculoAcumuladas(List<Taxa> taxasAcumuladas,List<ParametroTaxa> listParametroTaxa,
				Date dataInicioApuracao, EventMetadata metadata){
				
			/**
			 * Para cada taxa acumulada - Ordenar as Taxas pelo Dia
			 */
			List<Taxa> taxasAcumuladasOrdenadas = from($(qTaxa),taxasAcumuladas).
					orderBy($(qTaxa.getPeriodo().getDataInicio()).desc())
	        .fetchResults().getResults();
			for(br.org.ons.geracao.evento.taxa.Taxa taxaAcumulada : JavaUtil.emptyIfNull(taxasAcumuladasOrdenadas)){
				
				//Se não foi calculada memoria de calculo, então nao deveremos salvar a memoria de calculo.
				//sair também na Indisponibilidade, pois a MM é a soma do TEIP_ii e TEIFa_ii
				if(	(taxaAcumulada.getValor() == null && 
					taxaAcumulada.getComentarios() != null &&
					!taxaAcumulada.getComentarios().isEmpty()) ||
					taxaAcumulada.getCodigo().toLowerCase().contains("indisponibilidade")
					){
					continue; //vai para a proxima taxa acumulada.
				}
				
				String nomeTaxaAcumulada = this.getNomeTaxa(taxaAcumulada.getCodigo(), taxaAcumulada.getTipo());
				
				/**
				 * Se o QMCEquipamentoParametro já existe, então adiciona a Memoria de Calculo,
				 * senão cria.
				 * Mesmos: instalacao,dataApuracao,taxa
				 */
				QMCEquipamentoParametro qMCequipParam = new QMCEquipamentoParametro("mCEquipamentoParametro");
				MCEquipamentoParametro mcEquipParam = mcEquipParamRepository.findOne(
						qMCequipParam.instalacao.eq(metadata.getAggregateId()).and(
						qMCequipParam.dataApuracao.eq(dataInicioApuracao).and(
						qMCequipParam.taxaMemoriaCalculo.eq(nomeTaxaAcumulada).and(
						qMCequipParam.versaoTaxa.eq(versaoTaxa).and(
						qMCequipParam.versaoCenario.eq(versaoCenario))))));
				
				if( mcEquipParam == null){
					mcEquipParam = new MCEquipamentoParametro();
				}
				
				/**
				 * Para cada taxa, verificar a regulamentacao,
				 * caso antes  = memoriacalculoIds = lista de taxas (Usar o objeto taxasPassadas
				 * caso depois = memoriacalculoIds = lista de parametros
				 * Pega essa lista e a partir dela cria a memoriadecalculo
				 */
				if(1412132400000l > dataInicioApuracao.toInstant().toEpochMilli()){
					saveMemoriaCalculoTaxaAcumuladaV1((TaxaAcumulada) taxaAcumulada,listParametroTaxa, mcEquipParam);
				}else{
					saveMemoriaCalculoTaxasAcumuladasV2((TaxaAcumuladaEstendida) taxaAcumulada,listParametroTaxa,
							dataInicioApuracao,nomeTaxaAcumulada,mcEquipParam);
				}
				
				log.debug(mcEquipParam.getMemoriaCalculo().toString());
				
				/**
				 * Salva a Memoria de Calculo.
				 */
				mcEquipParam.setDataApuracao(dataInicioApuracao);
				mcEquipParam.setInstalacao(metadata.getAggregateId());
				mcEquipParam.setTaxaMemoriaCalculo(nomeTaxaAcumulada);
				mcEquipParam.setVersaoTaxa(versaoTaxa);
				mcEquipParam.setVersaoCenario(versaoCenario);
				mcEquipParam.setCorrelationId(metadata.getCorrelationId());
				mcEquipParamRepository.save(mcEquipParam);
				
			}
		}
		
		public void saveMemoriaCalculoTaxaAcumuladaV1(TaxaAcumulada taxaAcumuladaV1,List<ParametroTaxa> listParametroTaxa,
				MCEquipamentoParametro mcEquipParam){

			//memoriacalculoids = lista de taxas ( Usar o MM2 )
			
			/**
			 * Para cada TAXA, carrega a lista de todas as TAXAS utilizadosdos 60 Meses Anteriores de todas os Equipamentos
			 * 
			 */
				List<Taxa> taxasAcumuladasOrdenadas = from($(qTaxa),taxaAcumuladaV1.getTaxasPassadas()).
						orderBy($(qTaxa.getPeriodo().getDataInicio()).desc())
		        .fetchResults().getResults();
				for(Taxa taxa : JavaUtil.emptyIfNull(taxasAcumuladasOrdenadas)){
					
					String nomeTaxa = this.getNomeTaxa(taxa.getCodigo(),taxa.getTipo());
					
					/**
					 * Se for taxa de refencia nao tem equipamento
					 */
					/**
					 * INST 1 - Equipamento da Taxa - ATUAL
					 */
					TaxaEventosUGParam txEvtUGParam = new TaxaEventosUGParam();
					txEvtUGParam.setData(taxa.getPeriodo().getDataInicio());
					txEvtUGParam.setIdent(1);
					txEvtUGParam.setIdTaxa(taxa.getId());
					txEvtUGParam.setTaxa(nomeTaxa);
					txEvtUGParam.setTipoTaxa(taxa.getTipo().toString());
					txEvtUGParam.setValor(taxa.getValor());
					txEvtUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
					mcEquipParam.addMemoriaCalculo(txEvtUGParam);
					
						
					/** 
					 * Loop por Parametros, é feito com base no ParametrosTaxas, pois não existe relacionamento entre
					 * parametros e equipamentos nas TaxasMensais.
					 * OBS: Isso só se aplica a TaxasMensais, se for taxa Ajustada ou de Referencia a memoria de calculo é nulo.
					 */
						
					/**
					 * para cada equipamento, pegar o valor do ParametroEquipamento.
					 */
					List<Participacao> listParticipacaoOrdenada = 
							ordenarListaParticipante(taxa.getParticipacoesEquipamentos());
					for(Participacao participacao : JavaUtil.emptyIfNull(listParticipacaoOrdenada)){
						
						/**
						 * INST 2
						 */
						Equipamento equip = new Equipamento();
						equip.setId(participacao.getCodigoONS());
						equip.setNome(participacao.getIdParticipante());
						if(participacao.getValor() != null){
							equip.setParticipacao(participacao.getValor().multiply(new BigDecimal(100)));
						}
						equip.setValorPotencia(participacao.getPotencia());
						
						EquipamentoUGParam equipUGParam = new EquipamentoUGParam();
						equipUGParam.setData(taxa.getPeriodo().getDataInicio());
						equipUGParam.setIdent(2);
						equipUGParam.setTaxa(txEvtUGParam.getTaxa());
						equipUGParam.setTipoTaxa(txEvtUGParam.getTipoTaxa());
						equipUGParam.setEquipamento(equip);
						equipUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
						mcEquipParam.addMemoriaCalculo(equipUGParam);
						
						carregarParametrosV1(taxa, listParametroTaxa, participacao,txEvtUGParam,mcEquipParam,equip);
						
					}
				}
		}
		
		public void carregarParametrosV1(Taxa taxaAcumuladaV1, List<ParametroTaxa> listParametroTaxa,
				Participacao participacao, TaxaEventosUGParam txEvtUGParam, MCEquipamentoParametro mcEquipParam,Equipamento equip){
			/**
			 * Para Esse EquipamentoAtual, pega a lista de Todos os Equipamentos/Parametros por UnidadeGeradora (60M)
			 * e monta o Ident 2. e 3
			 */				
			
			List<String> listMemoriaCalculo = new ArrayList<>();
			if(taxaAcumuladaV1.getMemoriaCalculo() != null &&
			   !taxaAcumuladaV1.getMemoriaCalculo().isEmpty() &&
			   taxaAcumuladaV1.getMemoriaCalculo().get(0) != null){
				listMemoriaCalculo = taxaAcumuladaV1.getMemoriaCalculo();
			}
			
	        List<ParametroTaxa> listParamTxCalcByUG = from($(qParametroTaxa),listParametroTaxa).
	        		where($(qParametroTaxa.getId()).in(listMemoriaCalculo).
	        				and($(qParametroTaxa.getIdEquipamento()).eq(participacao.getIdParticipante())
	        )).fetchResults().getResults();
	        										
	        for(ParametroTaxa paramTxcalc : JavaUtil.emptyIfNull(listParamTxCalcByUG)){
					
					/**
					 * INST 3
					 */
					Parametro param = new Parametro();
					param.setNomeParametro(paramTxcalc.getCodigo());
					param.setParamEventosId(new HashSet<String>(paramTxcalc.getIdsEventos()));
					param.setValorParametro(paramTxcalc.getValor());
					
					Date dataParametro = this.getData(paramTxcalc.getMes(), paramTxcalc.getAno());
					
					ParametroUGParam paramUGParam = new ParametroUGParam();
					paramUGParam.setData(dataParametro);
					paramUGParam.setIdent(3);
					paramUGParam.setTaxa(txEvtUGParam.getTaxa());
					paramUGParam.setTipoTaxa(txEvtUGParam.getTipoTaxa());
					paramUGParam.setEquipamentoNome(participacao.getIdParticipante());
					paramUGParam.setParam(param);
					paramUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
					mcEquipParam.addMemoriaCalculo(paramUGParam);
					
					equip.addEquipamentosEventosIds(new HashSet<String>(paramTxcalc.getIdsEventos()));
					txEvtUGParam.addTaxaEventosIds(new HashSet<String>(paramTxcalc.getIdsEventos()));
					
					
				}
		}
		
		public void saveMemoriaCalculoTaxasAcumuladasV2(TaxaAcumuladaEstendida taxaAcumuladaV2,
				List<ParametroTaxa> listParametroTaxa,
				Date dataInicioApuracao,
				String nomeTaxaAcumulada,
				MCEquipamentoParametro mcEquipParam){
			//horasindisponibilidade: memoriacalculosids = lista de parametros. ( Usar o MC3 )
			
			/**
			 * Para cada ParticipacaoEquipamento da Taxa (Carregar Ident 1 )
			 */
			
			List<Participacao> listParticipacaoOrdenada = 
					ordenarListaParticipante(taxaAcumuladaV2.getParticipacoesEquipamentos());
			for(Participacao equipamentoAtual : JavaUtil.emptyIfNull(listParticipacaoOrdenada)){
			
				/**
				 * INST 1 - Equipamento da Taxa - ATUAL
				 */
				Equipamento equipAtual = new Equipamento();
				equipAtual.setId(equipamentoAtual.getCodigoONS());
				equipAtual.setNome(equipamentoAtual.getIdParticipante());
				equipAtual.setParticipacao(equipamentoAtual.getValor().multiply(new BigDecimal(100)));
				equipAtual.setValorPotencia(equipamentoAtual.getPotencia()); //o que vem do ODM já está carregado o valor
				
				EquipamentoUGParam equipIdent1 = new EquipamentoUGParam();
				equipIdent1.setData(dataInicioApuracao);
				equipIdent1.setIdent(1);
				equipIdent1.setTaxa(nomeTaxaAcumulada);
				if("Indice de Indisponibilidade".equalsIgnoreCase(nomeTaxaAcumulada)){
					equipIdent1.setIdTaxa(taxaAcumuladaV2.getCodigo());
				}
				equipIdent1.setTipoTaxa(taxaAcumuladaV2.getTipo().toString());
				equipIdent1.setEquipamento(equipAtual);
				equipIdent1.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());						
				mcEquipParam.addMemoriaCalculo(equipIdent1);							
				
				
				/**
				 * Pega apenas as horasIndisponibilidade para um dado equipamento
				 */
		        List<HorasIndisponibilidade> horasIndisponibilidade = from($(qHorasIndisponibilidade),taxaAcumuladaV2.getHorasIndisponibilidade()).
		        		where($(qHorasIndisponibilidade.getIdEquipamento()).eq(equipamentoAtual.getIdParticipante())
		        ).fetchResults().getResults();
				
				/**
				 * Para cada HoraIndisponibilidade que compoe a taxa
				 */
					for(HorasIndisponibilidade horaIndisponibildiade : JavaUtil.emptyIfNull(horasIndisponibilidade)){
						
						/**
						 * INST 2 ( Equipamentos de Meses anteriores (60meses)
						 */
		        		Equipamento equipMesAnterior = new Equipamento();
		        		equipMesAnterior.setId(horaIndisponibildiade.getIdEquipamento());
		        		equipMesAnterior.setNome(horaIndisponibildiade.getIdEquipamento());
		        		equipMesAnterior.setParticipacao(equipamentoAtual.getValor());
		        		if(horaIndisponibildiade.getPotencia() != null){
		        			equipMesAnterior.setValorPotencia(new BigDecimal(horaIndisponibildiade.getPotencia()));
		        		}
						
						EquipamentoUGParam equipUGParam = new EquipamentoUGParam();
						//Transforma Mes/Ano em Data
						Calendar dataMesAno = Calendar.getInstance();
						dataMesAno.set(horaIndisponibildiade.getAno(), horaIndisponibildiade.getMes()-1, 1,0,0,0);
						dataMesAno.set(Calendar.MILLISECOND, 0);
						equipUGParam.setData(dataMesAno.getTime());
						equipUGParam.setIdent(2);
						equipUGParam.setTaxa(taxaAcumuladaV2.getCodigo());
						equipUGParam.setCodigo(horaIndisponibildiade.getCodigo());
						equipUGParam.setTipoTaxa(taxaAcumuladaV2.getTipo().toString());
						equipUGParam.setEquipamento(equipMesAnterior);
						equipUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());										
						mcEquipParam.addMemoriaCalculo(equipUGParam);
						
						/**
				         * Salva o Ident 2 (PK: Equipamento, Mes, Ano)
				         * Salva o Ident 3 (Todas as informações abaixo do Ident 2)
				         */
						/**
						 * Para cada TAXA, carrega a lista de todos Parametros utilizadosdos 60 Meses Anteriores de todas os Equipamentos 
						 * 
						 * CODIGO = PARAMETRO
						 * 
						 */
						carregarParametrosTaxasAcumuladasV2(listParametroTaxa,
								horaIndisponibildiade,
								equipUGParam,
								equipIdent1,
								mcEquipParam);
					}
			}
		}
		
		 private void carregarParametrosTaxasAcumuladasV2(List<ParametroTaxa> listParametroTaxa,
				 HorasIndisponibilidade horaIndisponibildiade, 
				 EquipamentoUGParam equipUGParam,
				 EquipamentoUGParam equipIdent1,
				 MCEquipamentoParametro mcEquipParam){
			 
		   if(HorasIndisponibilidade.CodigoHorasIndisponibilidade.PARAMETRO.name().equalsIgnoreCase(
	        				horaIndisponibildiade.getCodigo()) &&
				   horaIndisponibildiade.getMemoriaCalculo() != null &&
				   !horaIndisponibildiade.getMemoriaCalculo().isEmpty()
				   ){
			   
				/**
				 * Para Esse EquipamentoAtual, pega a lista de Todos os Equipamentos/Parametros por UnidadeGeradora (60M)
				 * e monta o Ident 2. e 3
				 */
				OrderSpecifier<String> sortEquipamento = $(qParametroTaxa.getIdEquipamento()).asc();
				OrderSpecifier<Integer> sortAno = $(qParametroTaxa.getAno()).desc();
				OrderSpecifier<Integer> sortMes = $(qParametroTaxa.getMes()).desc();
		        List<ParametroTaxa> listParamTxCalcByUG = from($(qParametroTaxa),JavaUtil.emptyIfNull(listParametroTaxa)).
		        		where($(qParametroTaxa.getId()).in(horaIndisponibildiade.getMemoriaCalculo()))
		        		.orderBy(sortEquipamento,sortAno,sortMes)
		        		.fetchResults().getResults();
			   
		        	for(ParametroTaxa paramTxCalcMesesAnteriores : JavaUtil.emptyIfNull(listParamTxCalcByUG )){
		        		
		        		Date dataParametro = this.getData(paramTxCalcMesesAnteriores.getMes(), 
		        				paramTxCalcMesesAnteriores.getAno());
						
						/**
						 * Loop por Parametros, é feito com base no ParametrosTaxas, pois não existe relacionamento entre
						 * parametros e equipamentos nas TaxasMensais.
						 */
						Parametro param = new Parametro();
						param.setNomeParametro(paramTxCalcMesesAnteriores.getCodigo());
						param.setParamEventosId(new HashSet<String>(paramTxCalcMesesAnteriores.getIdsEventos()));
						param.setValorParametro(paramTxCalcMesesAnteriores.getValor());
						
						/**
						 * INST 3
						 */
						ParametroUGParam paramUGParam = new ParametroUGParam();
						paramUGParam.setData(dataParametro);
						paramUGParam.setIdent(3);
						paramUGParam.setTaxa(paramTxCalcMesesAnteriores.getCodigo());
						paramUGParam.setEquipamentoNome(equipUGParam.getEquipamento().getId());
						paramUGParam.setParam(param);
						paramUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());												
						equipUGParam.getEquipamento().addEquipamentosEventosIds(new HashSet<String>(paramTxCalcMesesAnteriores.getIdsEventos()));
						equipIdent1.getEquipamento().addEquipamentosEventosIds(new HashSet<String>(paramTxCalcMesesAnteriores.getIdsEventos()));
						mcEquipParam.addMemoriaCalculo(paramUGParam);
	
		        	}
		        }else if(!HorasIndisponibilidade.CodigoHorasIndisponibilidade.PARAMETRO.name().equalsIgnoreCase(
	        				horaIndisponibildiade.getCodigo())){
		        	/**
		        	 * CASO o CODIGO = REFERENCIA ou AJUSTADA
		        	 */
		        	
		        	//NUMERADOR
		        	Parametro param = new Parametro();
					param.setNomeParametro(horaIndisponibildiade.getDescricao());
					param.setValorParametro(horaIndisponibildiade.getNumerador());
					
					/**
					 * INST 3
					 */
					ParametroUGParam paramUGParam = new ParametroUGParam();
					paramUGParam.setData(equipUGParam.getData());
					paramUGParam.setIdent(3);
					paramUGParam.setTaxa(horaIndisponibildiade.getDescricao());
					paramUGParam.setEquipamentoNome(horaIndisponibildiade.getIdEquipamento());
					paramUGParam.setParam(param);
					paramUGParam.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
					mcEquipParam.addMemoriaCalculo(paramUGParam);	
					
					//DENOMINADOR
		        	Parametro denominador = new Parametro();
		        	denominador.setNomeParametro(horaIndisponibildiade.getDescricaoAuxiliar());
					denominador.setValorParametro(horaIndisponibildiade.getDenominador());
					
					/**
					 * INST 3
					 */
					ParametroUGParam paramUGParamDenominador = new ParametroUGParam();
					paramUGParamDenominador.setData(equipUGParam.getData());
					paramUGParamDenominador.setIdent(3);
					paramUGParamDenominador.setTaxa(horaIndisponibildiade.getDescricaoAuxiliar());
					paramUGParamDenominador.setEquipamentoNome(horaIndisponibildiade.getIdEquipamento());
					paramUGParamDenominador.setParam(denominador);
					paramUGParamDenominador.setIdOrdenacao(mcEquipParam.getMemoriaCalculo().size());
					mcEquipParam.addMemoriaCalculo(paramUGParamDenominador);						        	
	
		        }
		}
		
		private String getRegulamentacao(Date dataAupracao){
			
			long resolucaoOutubro2014 = 1412132400000l;
			
			if(resolucaoOutubro2014 > dataAupracao.getTime()){
				return "1";
			}else{
				return "2";
			}
		}		
		
		private String getAllComentarios(List<Comentario> listComentarios){
			StringBuilder comentarios = new StringBuilder();
			if(listComentarios != null)
				for(Comentario comentario : listComentarios){
					comentarios.append(comentario.getDescricao() + ". ");
				}
			return comentarios.toString();
		}
		
		public List<Participacao> ordenarListaParticipante(List<Participacao> listParticipacao){
			
			
			Collections.sort(listParticipacao, new Comparator<Participacao>() {
    			@Override
    			public int compare(Participacao t1, Participacao t2) {
    				if(t1 == null || t1.getCodigoONS() == null)
    					return -1;
    				return CalcularTaxasService.getOrdenarNomeEquipamento(t1.getCodigoONS()).compareTo(
    						CalcularTaxasService.getOrdenarNomeEquipamento(t2.getCodigoONS()));
    			}
    		});
			
			return listParticipacao;
			
		}
		
		public static String getOrdenarNomeEquipamento(String nomeEquipamento){
			
			String numEquipFormatado;
			String nomeEquipamentoSemNumero;
			
			try{
				if(nomeEquipamento== null)
					return "";
				
				int index = nomeEquipamento.indexOf("UG");
				
				//Interligacoes Internacionais.
				if( index < 1)
					return nomeEquipamento;
				
				//Acrescenta a posicao das 2 letras(UG) ao index
				index = index +2;
				int numeroEquip = Integer.parseInt(nomeEquipamento.substring(index));
				//Formata para minimo de 2 casas.
				numEquipFormatado = String.format("%02d", numeroEquip);
				nomeEquipamentoSemNumero = nomeEquipamento.substring(0, index);
				
			}catch(Exception e){
				//Não conseguiu converter devolve o mesmo nome.
				return nomeEquipamento;
			}
			
			return nomeEquipamentoSemNumero+numEquipFormatado;
		}
		
		public String getNomeTaxa(String taxa, TipoTaxa tipoTaxa){
			String nomeTaxa;
			String tipo = tipoTaxa.toString().toLowerCase();
			if(tipoTaxa == TipoTaxa.ACUMULADA && 
					 (taxa.toLowerCase().contains("teip_ii") ||
					  taxa.toLowerCase().contains("teifa_ii"))){
				//Se for TEIP_ii ou TEIFa_ii > essas duas taxas deverão ser mescladas e serão a memoria de calculo do Indice de Indisponibilidade
				nomeTaxa = "Indice de Indisponibilidade";
			}else if(tipoTaxa == TipoTaxa.ACUMULADA && taxa.toLowerCase().contains(INDISPONIBILIDADE)){
				nomeTaxa = taxa;
			}else{
				nomeTaxa = taxa+ " "+tipo;
			}
			return nomeTaxa;
		}
		
		private String getNomeTaxaTipoInstalacao(String taxa, TipoTaxa tipoTaxa){
			String nomeTaxa;
			String tipo = tipoTaxa.toString().toLowerCase();
			if(tipoTaxa == TipoTaxa.ACUMULADA && taxa.toLowerCase().contains(INDISPONIBILIDADE)){
				nomeTaxa = taxa;
			}else{
				nomeTaxa = taxa+ " "+tipo;
			}
			return nomeTaxa;
		}		
		
	    public List<InstalacaoTaxas> findById(String[] ids, Pageable pageable) {
	        log.debug("Request to get the InstalacaoTaxas by id Instalação");
	        
	        QInstalacaoTaxas pred = new QInstalacaoTaxas("instalacaoTaxas");
	        if(ids == null){
	        	return IterableConverter.toList(instalacaoTaxasRepository.findAll(null,pageable));
	        }
	        return IterableConverter.toList(instalacaoTaxasRepository.findAll(pred.instalacao.in(ids), pageable));
	    }
	    
	    private Date getData(int mes, int ano){
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.set(ano,mes-1,1,0,0,0);
	    	cal.set(Calendar.MILLISECOND, 0);
	    	
	    	return cal.getTime();
	    }

}
