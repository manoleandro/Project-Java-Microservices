package br.org.ons.sager.read.service;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Participacao;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
import br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida;
import br.org.ons.geracao.evento.taxa.TipoTaxa;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.EquipamentoUGParam;
import br.org.ons.sager.read.domain.MCEquipamentoParametro;
import br.org.ons.sager.read.domain.QMCEquipamentoParametro;
import br.org.ons.sager.read.domain.Reprodutibilidade;
import br.org.ons.sager.read.domain.TaxaEventosUGParam;
import br.org.ons.sager.read.domain.UGParam;
import br.org.ons.sager.read.repository.InstalacaoTaxasRepository;
import br.org.ons.sager.read.repository.InterligacoesRepository;
import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;
import br.org.ons.sager.read.repository.ReprodutibilidadeRepository;
import br.org.ons.sager.read.repository.UsinasRepository;
import br.org.ons.sager.read.web.rest.util.JavaUtil;

@Service
public class ReprodutibilidadeService {
	
	private final Logger log = LoggerFactory.getLogger(ReprodutibilidadeService.class);
	
	@Inject
    private UsinasRepository usinasRepository;

	@Inject
    private InterligacoesRepository interligacoesRepository;

	@Inject
	private InstalacaoTaxasRepository instalacaoTaxasRepository;

	@Inject
	private MCEquipamentoParametroRepository mcEquipParamRepository;
	
	@Inject
	private ReprodutibilidadeRepository reprodutibilidadeRepository;
	
	protected SimpleDateFormat sdfAnoMes = new SimpleDateFormat("yyyyMM");
	
	private final Taxa qTaxa = alias(Taxa.class,"taxa");
	
	@SuppressWarnings("unchecked")
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event", 
	containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = TRUE")
	public void handleTaxasAcumuladasCalculadasV1Event(EventMessage<TaxasAcumuladasCalculadasV1Event> eventMessage) throws JMSException {


		EventMetadata metadata = null;
		TaxasAcumuladasCalculadasV1Event event = null;
		CalcularTaxasService calcularTaxasServices = new CalcularTaxasService(usinasRepository, interligacoesRepository,
				instalacaoTaxasRepository,mcEquipParamRepository);
		
		try{
			event = eventMessage.getEvent();
			metadata = eventMessage.getMetadata();
			
			//Adiciona apenas as TaxasMensais do Período
	        List<Taxa> taxas = from($(qTaxa),event.getTaxas()).
	        		where($(qTaxa.getPeriodo().getDataInicio()).eq(event.getDataInicioApuracao()))
	        .fetchResults().getResults();
	        taxas.addAll((List<Taxa>) (List<?>) event.getTaxasAcumuladas());
			
			/**
			 * Salva a Projeção Memoria de Calculo ( Taxa Mensal)
			 */
			this.saveMemoriaCalculoMensal(taxas,new ArrayList<ParametroTaxa>(event.getParametros()), 
					event.getDataInicioApuracao(), metadata,calcularTaxasServices);
			
			/**
			 * Salva a projeção UGParam(3,2,1) (Tela: memoria-calculo acumulada)
			 */
			this.saveMemoriaCalculoAcumuladas((List<Taxa>) (List<?>) event.getTaxasAcumuladas(),
					new ArrayList<ParametroTaxa>(event.getParametros()),event.getDataInicioApuracao(), metadata,
					calcularTaxasServices);
			
			
		}catch(Exception e){
			log.error("handleTaxasAcumuladasCalculadasV1Event",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event", 
	containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = TRUE")
	public void handleTaxasAcumuladasCalculadasV2Event(EventMessage<TaxasAcumuladasCalculadasV2Event> eventMessage) throws JMSException {
		
		EventMetadata metadata = null;
		TaxasAcumuladasCalculadasV2Event event;
		CalcularTaxasService calcularTaxasServices = new CalcularTaxasService(usinasRepository, interligacoesRepository,
				instalacaoTaxasRepository,mcEquipParamRepository);
		
		try{
			 event = eventMessage.getEvent();
			 metadata = eventMessage.getMetadata();
			 
			/**
			 * Salva a projeção UGParam(3,2,1) (Tela: memoria-calculo acumulada)
			 */
			this.saveMemoriaCalculoAcumuladas((List<Taxa>) (List<?>) event.getTaxas(),
					new ArrayList<ParametroTaxa>(event.getParametrosV1()),
				event.getDataInicioApuracao(), metadata,calcularTaxasServices);
						
		}catch(Exception e){
			log.error("handleTaxasAcumuladasCalculadasV2Event",e);
		}
	}
	
	public void saveMemoriaCalculoAcumuladas(List<Taxa> taxasAcumuladas,List<ParametroTaxa> listParametroTaxa,
			Date dataInicioApuracao, EventMetadata metadata,CalcularTaxasService calcularTaxasServices){
			
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
			
			String nomeTaxaAcumulada = calcularTaxasServices.getNomeTaxa(taxaAcumulada.getCodigo(), taxaAcumulada.getTipo());
			
			MCEquipamentoParametro mcEquipParam = new MCEquipamentoParametro();
			
			/**
			 * Para cada taxa, verificar a regulamentacao,
			 * caso antes  = memoriacalculoIds = lista de taxas (Usar o objeto taxasPassadas
			 * caso depois = memoriacalculoIds = lista de parametros
			 * Pega essa lista e a partir dela cria a memoriadecalculo
			 */
			if(1412132400000l > dataInicioApuracao.toInstant().toEpochMilli()){
				calcularTaxasServices.saveMemoriaCalculoTaxaAcumuladaV1((TaxaAcumulada) taxaAcumulada,listParametroTaxa, mcEquipParam);
			}else{
				calcularTaxasServices.saveMemoriaCalculoTaxasAcumuladasV2((TaxaAcumuladaEstendida) taxaAcumulada,listParametroTaxa,
						dataInicioApuracao,nomeTaxaAcumulada,mcEquipParam);
			}
			
			log.debug(mcEquipParam.getMemoriaCalculo().toString());
			
			/**
			 * Salva a Reprodutibilidade.
			 */
			
			Reprodutibilidade reprod = new Reprodutibilidade();
			reprod.setCorrelationId(metadata.getCorrelationId());
			reprod.setDataApuracao(dataInicioApuracao);
			reprod.setInstalacao(metadata.getAggregateId());
			reprod.setTaxaMemoriaCalculo(nomeTaxaAcumulada);
			reprod.setMemoriaCalculo(mcEquipParam.getMemoriaCalculo());
			reprodutibilidadeRepository.save(reprod);
			
		}
	}
	
	protected void saveMemoriaCalculoMensal(List<Taxa> taxas,List<ParametroTaxa> listParametroTaxa,Date dataInicioApuracao,
			EventMetadata metadata,CalcularTaxasService calcularTaxasServices){
		
		for(Taxa taxa : JavaUtil.emptyIfNull(taxas)){
			
			//Se não foi calculada memoria de calculo, então nao deveremos salvar a memoria de calculo.
			if(	(taxa.getValor() == null && 
				 taxa.getComentarios() != null &&
				!taxa.getComentarios().isEmpty()) ||
				 !taxa.getTipo().equals(TipoTaxa.MENSAL)
				){
				continue; //vai para a proxima taxa acumulada.
			}
			
			String nomeTaxa = calcularTaxasServices.getNomeTaxa(taxa.getCodigo(), taxa.getTipo());
			
			/**
			 * Se o MCEquipamentoParametro já existe, então adiciona a Memoria de Calculo,
			 * senão cria.
			 * Mesmos: instalacao,dataApuracao,taxa
			 */
			MCEquipamentoParametro mcEquipParam = new MCEquipamentoParametro();
			
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
					calcularTaxasServices.ordenarListaParticipante(taxa.getParticipacoesEquipamentos());
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
					calcularTaxasServices.carregarParametrosV1(taxa,listParametroTaxa,participacao,txEvtUGParam,mcEquipParam,equip);
				}
				
				log.debug(mcEquipParam.getMemoriaCalculo().toString());
								
				/**
				 * Salva a Reprodutibilidade.
				 */
				Reprodutibilidade reprod = new Reprodutibilidade();
				reprod.setCorrelationId(metadata.getCorrelationId());
				reprod.setDataApuracao(dataInicioApuracao);
				reprod.setInstalacao(metadata.getAggregateId());
				reprod.setTaxaMemoriaCalculo(nomeTaxa);
				reprod.setMemoriaCalculo(mcEquipParam.getMemoriaCalculo());
				reprodutibilidadeRepository.save(reprod);
		}
}


}
