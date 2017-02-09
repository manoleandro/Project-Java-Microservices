package br.org.ons.sager.instalacao.service;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.collections.CollQuery;
import com.querydsl.collections.QueryEngine;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.LogComentarios;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoOperacao;
import br.org.ons.geracao.evento.taxa.HorasIndisponibilidade;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
import br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida;
import br.org.ons.geracao.evento.taxa.TipoTaxa;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.JavaUtil;
import br.org.ons.sager.instalacao.command.ApurarEventosCommand;
import br.org.ons.sager.instalacao.command.CadastrarEquipamentoCommand;
import br.org.ons.sager.instalacao.command.CadastrarInstalacaoCommand;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.command.CalcularTaxasCommand;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeRequest;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.CalcularParametrosRequest;
import br.org.ons.sager.regra.parameters.CalcularParametrosResponse;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasRequestV1;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasRequestV2;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasResponseV1;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasResponseV2;
import br.org.ons.sager.regra.parameters.CalcularTaxasMensaisRequest;
import br.org.ons.sager.regra.parameters.CalcularTaxasMensaisResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoRequest;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * Aggregate que representa o estado e a lógica de negócio de uma Instalação e
 * seus períodos de apuração de taxas de indisponibilidade.
 */
public class InstalacaoAggregate extends Aggregate {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private QueryEngine queryEngine;

	private final PeriodoApuracao qApuracao = alias(PeriodoApuracao.class, "apuracao");
	
	private final EventoMudancaEstadoOperativo qEvento = alias(EventoMudancaEstadoOperativo.class, "evento");
	
	private final Equipamento qEquipamento = alias(Equipamento.class, "equipamento");
	
	private final ComentarioSituacao qComentario = alias(ComentarioSituacao.class, "comentario");
	
	private final Taxa qTaxa = alias(Taxa.class,"taxa");
	
	private final ParametroTaxa qParametroTaxa = alias(ParametroTaxa.class, "parametroTaxa");
	
	private static final int REGULAMENTACAO_ANTES_OUTUBRO_2014 = 1;
	
	private static final int REGULAMENTACAO_DEPOIS_OUTUBRO_2014 = 2;
	
	private static final String CALCULAR_PARAMETROS = "calcularParametros";
	
	private static final String VERIFICAR_SITUACAO_INSTALACAO = "verificarSituacaoInstalacao";
	
	private static final String SAGER_CRITICAS = "SagerCriticas";
	
	private static final String SAGER_APURACAO_INDISPONIBILIDADE = "SagerApuracaoIndisponibilidade";
	
	private static final String CALCULAR_DISPONIBILIDADE = "calcularDisponibilidade";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S");

	/**
	 * Atributo de estado da entidade instalação (raiz do aggregate)
	 */
	private Instalacao instalacao;

	/**
	 * Atributo de estado dos equipamentos pertencentes à instalação
	 */
	private List<Equipamento> equipamentos = new ArrayList<>();

	/**
	 * Atributo de estado dos períodos de apuração de taxas da instalação
	 */
	private List<PeriodoApuracao> apuracoes = new ArrayList<>();

	/**
	 * Atributo de estado dos comentários sobre a situação da instalação ao
	 * longo do tempo
	 */
	private List<ComentarioSituacao> comentariosSituacao = new ArrayList<>();

	@SuppressWarnings("unused")
	private void handle(CadastrarInstalacaoCommand cadastrarInstalacao) {
		// Validação do estado do aggregate
		Assert.state(instalacao == null);
		Assert.state(equipamentos.isEmpty());

		// Validações do comando
		Assert.notNull(cadastrarInstalacao);
		Assert.notNull(cadastrarInstalacao.getInstalacao());
		Assert.notNull(cadastrarInstalacao.getInstalacao().getNomeCurto());

		// Instanciando evento
		InstalacaoCadastradaEvent event = new InstalacaoCadastradaEvent();
		event.setInstalacao(cadastrarInstalacao.getInstalacao());

		// Aplicando evento
		play(event);
	}

	@SuppressWarnings("unused")
	private void handle(CadastrarEquipamentoCommand cadastrarEquipamento) {
		// Validação do estado do aggregate
		Assert.state(instalacao != null);

		// Validações do comando
		Assert.notNull(cadastrarEquipamento);
		Assert.notNull(cadastrarEquipamento.getEquipamento());

		// Instanciando evento
		EquipamentoCadastradoEvent event = new EquipamentoCadastradoEvent();
		event.setEquipamento(cadastrarEquipamento.getEquipamento());

		// Aplicando evento
		play(event);
	}

	@SuppressWarnings("unused")
	private void handle(ApurarEventosCommand apurarEventos) {
		// Validações do comando
		Assert.notNull(apurarEventos);
		Assert.notNull(apurarEventos.getDataInicio());
		Assert.notNull(apurarEventos.getDataFim());
		Assert.notNull(apurarEventos.getEventos());
		Assert.notEmpty(apurarEventos.getEventos());

		// Validação do estado do aggregate
		Assert.state(instalacao != null);
		Assert.state(new CollQuery<>(queryEngine).from($(qApuracao), apuracoes).where($(qApuracao.getDataInicio()).eq(apurarEventos.getDataInicio()))
				.fetchCount() == 0);

		//Chamar ODM ContabilizarUsoFranquiasRequest
		//TODO: MSilva: Precisa implementar, ficou para depois. CasoDeUso 06 this.contabilizarUsoFranquiasODM(null);
		
		// Instanciando evento
		EventosApuradosEvent event = new EventosApuradosEvent();
		event.setApuracao(new PeriodoApuracao());
		event.getApuracao().setDataInicio(apurarEventos.getDataInicio());
		event.getApuracao().setDataFim(apurarEventos.getDataFim());
		event.getApuracao().setEventos(apurarEventos.getEventos());

		// Aplicando evento
		play(event);
	}

	@SuppressWarnings("unused")
	private LogComentarios handle(CalcularTaxasCommand command) {

		/**
		 * Validar estado do aggregate e comando usando assertions
		 */
		Assert.state(command.getDataInicio() != null);
		Assert.state(command.getDataFim() != null);
		Assert.state(command.getName() != null);
		
		//setando os horarios
		Calendar dtInicio = Calendar.getInstance();
		dtInicio.setTime(command.getDataInicio());
		dtInicio.set(Calendar.SECOND,0);
		dtInicio.set(Calendar.MILLISECOND, 000);
		
		Calendar dtFim = Calendar.getInstance();
		dtFim.setTime(command.getDataFim());
		dtFim.set(Calendar.SECOND,59);
		dtFim.set(Calendar.MILLISECOND, 999);
		
		List<Comentario> comentariosRetorno = new ArrayList<>();
		
		logger.debug("handle[CalcularTaxasCommand] : Instalacao: "+instalacao.getNomeCurto()+" dataInicio:["+sdf.format(dtInicio.getTime())+"]"
				+ " dataFim:["+sdf.format(dtFim.getTime())+"]");

		//Dividir em dois, pois pode vir mais de um mes e isso nao está funcionando.
		// Filtra os períodos de apuração abrangidos pela janela de cálculo

		List<PeriodoApuracao> apuracoesCalculo = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes)
				.where($(qApuracao.getDataInicio()).goe(dtInicio.getTime())
						.and($(qApuracao.getDataFim()).loe(dtFim.getTime())))
				.fetch();
		
		//Caso não exista janela de Calculo, então vamos sai
		if(apuracoesCalculo.isEmpty()){
			throw new OnsRuntimeException("Não existe Eventos para os critérios selecionados:"
					+ "Instalacao:["+instalacao.getNomeCurto()+"] dataInicio:["+sdf.format(dtInicio.getTime())+"]"
				+ " dataFim:["+sdf.format(dtFim.getTime())+"]");
		}
		
		// Monta lista com os eventos abrangidos pela janela de cálculo, a
		// partir dos períodos filtrados
		try{
			for (PeriodoApuracao apuracao : apuracoesCalculo) {		
				
				VerificarSituacaoInstalacaoResponse verificarSituacaoInstalacaoResponse = 
						verificarSituacaoInstalacaoODM(apuracao.getDataInicio(),apuracao.getDataFim());
				//Para cada período de apuração, verificar a situação da Instalação.
				List<ComentarioSituacao> comentariosSituacaoAtualizado = verificarSituacaoInstalacaoResponse.getComentarios();
				comentariosRetorno.addAll(comentariosSituacaoAtualizado);
				
				List<ComentarioSituacao> comentarios = new CollQuery<ComentarioSituacao>(queryEngine)
						.from($(qComentario), JavaUtil.emptyIfNull(comentariosSituacaoAtualizado))
						.where($(qComentario.getTipoObjeto()).eq(TipoObjeto.USINA).
								or($(qComentario.getTipoObjeto()).eq(TipoObjeto.INTERLIGACAO_INTERNACIONAL)))
				.fetch();
				
				//Monta uma lista com os equipamentos desativados para não incluir na lista de enviar ao ODM
				List<ComentarioSituacao> comentarioEquipamento = new CollQuery<ComentarioSituacao>(queryEngine)
						.from($(qComentario), JavaUtil.emptyIfNull(comentariosSituacaoAtualizado))
						.where(
								//Regra 1 - DESATIVADO -  SUSPENSO
								$(qComentario.getTipoObjeto()).eq(TipoObjeto.UNIDADE_GERADORA).and(
								$(qComentario.getStatusObjeto()).eq(StatusObjeto.DESATIVADO).or(
								$(qComentario.getStatusObjeto()).eq(StatusObjeto.SUSPENSO))).and(
										//Data Inicio <= paramDtInico and Data Fim >= paramDtFim
								$(qComentario.getDataInicio()).loe(apuracao.getDataInicio()).and(
								$(qComentario.getDataInicio()).goe(apuracao.getDataFim()).or(
								$(qComentario.getDataInicio()).isNull()))).or(
								//Regra 2 - FORA_OPERACAO_COMERCIAL
								$(qComentario.getTipoObjeto()).eq(TipoObjeto.UNIDADE_GERADORA).and(
								$(qComentario.getStatusObjeto()).eq(StatusObjeto.DESATIVADO)).and(
								$(qComentario.getDataFim()).goe(apuracao.getDataInicio())))
								).fetch();
				
				ArrayList<String> idEquipamentoComComentario = new ArrayList<>();
				if(comentariosSituacaoAtualizado != null && !comentariosSituacaoAtualizado.isEmpty()){
					for(ComentarioSituacao cs : comentarios){
						idEquipamentoComComentario.add(cs.getNomeObjeto());
					}
				}
				
				//Remover os equipamentos que estão desativados no período de apuração.
				List<Equipamento> equipamentosAtivos = new CollQuery<Equipamento>(queryEngine).from($(qEquipamento), equipamentos)
						.where($(qEquipamento.getId()).notIn(idEquipamentoComComentario)).fetch();
				
				// Se não houver nenhum comentário, prossegue com o cálculo das
				// disponibilidades
				if (verificarSituacaoInstalacaoResponse.isProssegue()) {

					if (1412132400000l > apuracao.getDataInicio().toInstant().toEpochMilli()) {
						// Instanciar eventos e aplicar usando play(Event)
						TaxasAcumuladasCalculadasV1Event taxasEvent = new TaxasAcumuladasCalculadasV1Event();
						//Carrega os Parametros do Período de Apuracao atual
						taxasEvent.addParametros(apuracao.getParametrosV1());
						//Adiciona apenas as TaxasMensais do Período
				        List<Taxa> taxas = from($(qTaxa),JavaUtil.emptyIfNull(apuracao.getTaxas())).
				        		where($(qTaxa.getTipo()).eq(TipoTaxa.MENSAL))
				        .fetchResults().getResults();
						taxasEvent.addTaxas(taxas);
						
						this.calcularTaxasMensais(apuracao, REGULAMENTACAO_ANTES_OUTUBRO_2014,comentarioEquipamento,equipamentosAtivos,taxasEvent);
						this.calcularTaxasAcumuladasV1(apuracao,taxasEvent);
						//Adiciona os parametros de todas os Ids das memórias de Calculo.
						
						//Verifica se a Memoria de Calculo é igual a já existente no PeriodoApuracao.
						if(this.verificaCalculoIgual(taxasEvent, apuracao)){
							Comentario comentario = new Comentario();
							comentario.setDataInsercao(new Date());
							comentario.setOrigem(OrigemComentario.SISTEMA);
							comentario.setTipo(TipoComentario.AVISO);
							comentario.setDescricao("A memória de cálculo é igual a versão anterior e não será versionada");
							comentariosRetorno.add(comentario);
						}else{
							play(taxasEvent);
						}
						
					} else {
						TaxasAcumuladasCalculadasV2Event taxasEvent = new TaxasAcumuladasCalculadasV2Event();
						//Carrega os Parametros do Período de Apuracao atual
						taxasEvent.addParametrosV1(apuracao.getParametrosV1());
						taxasEvent.addParametrosV2(apuracao.getParametrosV2());
						
						this.calcularTaxasAcumuladasV2(apuracao, REGULAMENTACAO_DEPOIS_OUTUBRO_2014,taxasEvent);
						
						//Verifica se a Memoria de Calculo é igual a já existente no PeriodoApuracao.
						if(this.verificaCalculoIgual(taxasEvent, apuracao)){
							Comentario comentario = new Comentario();
							comentario.setDataInsercao(new Date());
							comentario.setOrigem(OrigemComentario.SISTEMA);
							comentario.setTipo(TipoComentario.AVISO);
							comentario.setDescricao("A memória de cálculo é igual a versão anterior e não será versionada");
							comentariosRetorno.add(comentario);
						}else{
							play(taxasEvent);
						}
					}
				}
			}
		}catch(OnsRuntimeException e){
			throw new OnsRuntimeException(e);
		}catch(Exception e){
			log.error("Falha ao chamar a Regra",e);
			throw new OnsRuntimeException("Falha ao chamar a Regra",e.getCause());
		}
		
		LogComentarios listComentarios = new LogComentarios();
		listComentarios.setListaComentarios(comentariosRetorno);
		listComentarios.setNomeCenario(this.getScenarioName());
		return listComentarios;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean verificaCalculoIgual(Event event,PeriodoApuracao periodoAtual){
		
		PeriodoApuracao paTemp = new PeriodoApuracao();
		if( event instanceof TaxasAcumuladasCalculadasV1Event){
			TaxasAcumuladasCalculadasV1Event eventV1 = (TaxasAcumuladasCalculadasV1Event) event;
			paTemp.setDataInicio(eventV1.getDataInicioApuracao());
			paTemp.setParametrosV1(new ArrayList<ParametroTaxa>(eventV1.getParametros()));
			List<Taxa> taxas = new ArrayList<>();
			taxas.addAll(eventV1.getTaxas());
			taxas.addAll(eventV1.getTaxasAcumuladas());
			paTemp.setHandlerVersion(this.getHandlerVersion());
			paTemp.setTaxas(taxas);
		}else{
			TaxasAcumuladasCalculadasV2Event eventV2 = (TaxasAcumuladasCalculadasV2Event) event;
			paTemp.setDataInicio(eventV2.getDataInicioApuracao());
			paTemp.setParametrosV1(new ArrayList<ParametroTaxa>(eventV2.getParametrosV1()));
			paTemp.setParametrosV2(new ArrayList<ParametroTaxa>(eventV2.getParametrosV2()));
			paTemp.setHandlerVersion(this.getHandlerVersion());
			paTemp.setTaxas((List<Taxa>) (List<?>) eventV2.getTaxas());
		}
		return periodoAtual.hashCode() == paTemp.hashCode();

	}
	
	@SuppressWarnings("unchecked")
	private void calcularTaxasAcumuladasV2(PeriodoApuracao apuracao,
			int regulamentacao,TaxasAcumuladasCalculadasV2Event taxasEvent) {
		
		// criando a janela de calculo
		Periodo janelaCalculo = new Periodo();
		janelaCalculo.setDataFim(apuracao.getDataFim());
		janelaCalculo.setDataInicio(apuracao.getDataInicio());
		
		// Como cada período de apuração deve conter todos os
		// equipamentos, então vamos iterar os equipamentos ativos
		for (Equipamento equipamento : equipamentos) {
			// Adiciona os eventos do equipamento
			List<EventoMudancaEstadoOperativo> eventosEquipamento = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
					.from($(qEvento), JavaUtil.emptyIfNull(apuracao.getEventos()))
					.where($(qEvento.getIdEquipamento()).eq(equipamento.getId()))
					.fetch();
			
			eventosEquipamento.add(this.adicionarEventoEspelho(apuracao.getDataInicio(), equipamento.getId()));

			CalcularParametrosRequest calcularParametrosRequest = new CalcularParametrosRequest();
			// populando equipamento
			calcularParametrosRequest.setEquipamento(equipamento);
			// pooulando a janela de calculo
			calcularParametrosRequest.setJanelaCalculo(janelaCalculo);
			// populando os eventos
			calcularParametrosRequest.setEventos(eventosEquipamento);

			// PARAMETROS TAXA ODM
			CalcularParametrosResponse calcularParametrosResponse = this
					.calcularParametrosODM(calcularParametrosRequest,regulamentacao);

			// Instanciar ParametrosEventos e aplicar usando play(Event)
			taxasEvent.setDataInicioApuracao(janelaCalculo.getDataInicio());
			
			this.adicionarParametros(taxasEvent.getParametrosV2(),calcularParametrosResponse.getParametrosTaxa());
			
			
		}
		
		// TAXAS ACUMULADAS
		CalcularTaxasAcumuladasRequestV2 requestTaxa = new CalcularTaxasAcumuladasRequestV2();

		if (instalacao instanceof Usina) {
			((Usina) instalacao).setUnidadesGeradoras((List<UnidadeGeradora>) (List<?>) equipamentos);
		} else if (instalacao instanceof InterligacaoInternacional) {
			equipamentos.forEach(equipamento -> ((InterligacaoInternacional) instalacao)
					.setEquipamento((EquipamentoInterligacaoInternacional) equipamento));
		}
		requestTaxa.setInstalacao(instalacao);
		requestTaxa.setJanelaCalculo(janelaCalculo);
		
		//Adicionar as 59+Atual ParametrosTaxas ( V1 e V2)
		this.getAllParametros(apuracao,taxasEvent);
		requestTaxa.setParametrosRes614_2014(new ArrayList<ParametroTaxa>(taxasEvent.getParametrosV1()));// V1
		requestTaxa.setParametrosRes688_2003(new ArrayList<ParametroTaxa>(taxasEvent.getParametrosV2()));// V1

		CalcularTaxasAcumuladasResponseV2 calcularTaxasMensaisResponse = this.calcularTaxasAcumuladasV2ODM(requestTaxa);

		// Instanciar eventos e aplicar usando play(Event)
		
		taxasEvent.setDataInicioApuracao(apuracao.getDataInicio());
		taxasEvent.addTaxas(calcularTaxasMensaisResponse.getTaxasAcumuladas());
		
	}
	

	@SuppressWarnings("unchecked")
	private void calcularTaxasAcumuladasV1(PeriodoApuracao apuracao,TaxasAcumuladasCalculadasV1Event taxasEvent) {
		
		// criando a janela de calculo
		Periodo janelaCalculo = new Periodo();
		janelaCalculo.setDataFim(apuracao.getDataFim());
		janelaCalculo.setDataInicio(apuracao.getDataInicio());
		
		// TAXAS ACUMULADAS
		CalcularTaxasAcumuladasRequestV1 requestTaxa = new CalcularTaxasAcumuladasRequestV1();
		// Adiciona a instalação com os equipamentos
		if (instalacao instanceof Usina) {
			((Usina) instalacao).setUnidadesGeradoras((List<UnidadeGeradora>) (List<?>) equipamentos);
		} else if (instalacao instanceof InterligacaoInternacional) {
			equipamentos.forEach(equipamento -> ((InterligacaoInternacional) instalacao)
					.setEquipamento((EquipamentoInterligacaoInternacional) equipamento));
		}
		requestTaxa.setInstalacao(instalacao);
		requestTaxa.setJanelaCalculo(janelaCalculo);
		
		// Adicionar Todas as taxas [Atual+59mesesAtras] de todos equipamentos da Instalacao.
		this.getAllTaxasMensais(apuracao,taxasEvent);
		requestTaxa.setTaxas(new ArrayList<Taxa>(taxasEvent.getTaxas())); 
		
		CalcularTaxasAcumuladasResponseV1 calcularTaxasAcumuladasResponse = this.calcularTaxasAcumuladasV1ODM(requestTaxa);

		taxasEvent.setDataInicioApuracao(apuracao.getDataInicio());
		taxasEvent.addTaxasAcumuladas(calcularTaxasAcumuladasResponse.getTaxasAcumuladas());
	}

	@SuppressWarnings("unchecked")
	private void calcularTaxasMensais(PeriodoApuracao apuracao,int regulamentacao,
			List<ComentarioSituacao> comentariosEquipamentos,List<Equipamento> equipamentosAtivos,
			TaxasAcumuladasCalculadasV1Event taxasEvent) {

			// criando a janela de calculo
			Periodo janelaCalculo = new Periodo();
			janelaCalculo.setDataFim(apuracao.getDataFim());
			janelaCalculo.setDataInicio(apuracao.getDataInicio());

			// Como cada período de apuração deve conter todos os
			// equipamentos, então vamos iterar os equipamentos ativos
			for (Equipamento equipamento : equipamentosAtivos) {
				
				
				
				// Adiciona os eventos do equipamento
				List<EventoMudancaEstadoOperativo> eventosEquipamento = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEvento), JavaUtil.emptyIfNull(apuracao.getEventos()))
						.where($(qEvento.getIdEquipamento()).eq(equipamento.getId()).and(
//								$(qEvento.getTipoOperacao()).ne(TipoOperacao.O).or(
								$(qEvento.getTipoOperacao()).ne(TipoOperacao.C)))
						.fetch();
				
				eventosEquipamento.add(this.adicionarEventoEspelho(apuracao.getDataInicio(), equipamento.getId()));

				CalcularParametrosRequest calcularParametrosRequest = new CalcularParametrosRequest();
				// populando equipamento
				calcularParametrosRequest.setEquipamento(equipamento);
				// pooulando a janela de calculo
				calcularParametrosRequest.setJanelaCalculo(janelaCalculo);
				// populando os eventos
				calcularParametrosRequest.setEventos(eventosEquipamento);

				// PARAMETROS TAXA ODM
				CalcularParametrosResponse calcularParametrosResponse = this
						.calcularParametrosODM(calcularParametrosRequest,regulamentacao);

				taxasEvent.setDataInicioApuracao(janelaCalculo.getDataInicio());
				
				this.adicionarParametros(taxasEvent.getParametros(),
						calcularParametrosResponse.getParametrosTaxa());
				
			}

			// TAXAS MENSAIS
			CalcularTaxasMensaisRequest requestTaxa = new CalcularTaxasMensaisRequest();
			requestTaxa.setEquipamentos(equipamentos);
			requestTaxa.setJanelaCalculo(janelaCalculo);
			requestTaxa.setListaParametros(new ArrayList<ParametroTaxa>(taxasEvent.getParametros()));

			CalcularTaxasMensaisResponse calcularTaxasMensaisResponse = this.calcularTaxasMensaisODM(requestTaxa);

			//Varre as taxas Mensais e adiciona os comentários de equipamentos que não foram enviados.
			if(comentariosEquipamentos != null){
					for(Taxa  taxa : JavaUtil.emptyIfNull(calcularTaxasMensaisResponse.getTaxasMensais())){
						taxa.addComentarios((List<Comentario>) (List<?>) comentariosEquipamentos);
					}
			}
			
			taxasEvent.setTaxas(this.adicionarTaxas(taxasEvent.getTaxas(),
					calcularTaxasMensaisResponse.getTaxasMensais()));
	}

	@SuppressWarnings("unused")
	private CalcularDisponibilidadeResponse handle(CalcularDisponibilidadesCommand command) {
		// Validações do comando
		Assert.notNull(command);
		Assert.notNull(command.getDataInicio());
		Assert.notNull(command.getDataFim());
		Assert.notNull(command.getIdUnidadeGeradora());
		Assert.notEmpty(command.getTiposDisponibilidade());

		// Validação do estado do aggregate
		Assert.state(instalacao != null);
		Equipamento equipamento = new CollQuery<Equipamento>(queryEngine).from($(qEquipamento), equipamentos)
				.where($(qEquipamento.getId()).eq(command.getIdUnidadeGeradora())).fetchFirst();
		Assert.state(equipamento != null);

		// Se a verificação de situação contiver algum comentário, retorna a
		// lista de comentários
		List<Comentario> comentarios = new CollQuery<Comentario>(queryEngine)
				.from($(qComentario), comentariosSituacao)
				.where($(qComentario.getDataFim()).goe(command.getDataInicio())
						.or($(qComentario.getDataInicio()).loe(command.getDataFim())))
				.fetch();

		// Se não houver nenhum comentário, prossegue com o cálculo das
		// disponibilidades

		// Prepara invocação da regra "calcularDisponibilidade"
		CalcularDisponibilidadeRequest calcularRequest = new CalcularDisponibilidadeRequest();

		// Filtra os períodos de apuração abrangidos pela janela de cálculo
		List<PeriodoApuracao> apuracoesCalculo = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes)
				.where($(qApuracao.getDataInicio()).goe(command.getDataInicio())
						.and($(qApuracao.getDataFim()).loe(command.getDataFim())))
				.fetch();
		// Monta lista com os eventos abrangidos pela janela de cálculo, a
		// partir dos períodos filtrados
		List<EventoMudancaEstadoOperativo> eventosCalculo = new ArrayList<>();
		for (PeriodoApuracao apuracao : apuracoesCalculo) {
			// Adiciona o evento espelho na lista
//			EventoMudancaEstadoOperativo eventoEspelho = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
//					.from($(qEvento), apuracao.getEventos())
//					.where($(qEvento.getDataVerificada()).loe(command.getDataInicio())
//						.and($(qEvento.getIdEquipamento()).eq(command.getIdUnidadeGeradora())).and(
//						$(qEvento.getTipoOperacao()).ne(TipoOperacao.O).or(
//						$(qEvento.getTipoOperacao()).ne(TipoOperacao.C))))
//					.orderBy($(qEvento.getDataVerificada()).desc())
//					.fetchFirst();
//			if (eventoEspelho != null) {
//				eventoEspelho.setDataVerificada(command.getDataInicio());
//				eventosCalculo.add(eventoEspelho);
//			}
			eventosCalculo.add(this.adicionarEventoEspelho(apuracao.getDataInicio(), equipamento.getId()));
			// Adiciona os eventos da janela de cálculo
			eventosCalculo.addAll(new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
					.from($(qEvento), apuracao.getEventos())
					.where($(qEvento.getDataVerificada()).gt(command.getDataInicio())
							.and($(qEvento.getDataVerificada()).loe(command.getDataFim()))
							.and($(qEvento.getIdEquipamento()).eq(command.getIdUnidadeGeradora())))
					.fetch());
		}
		calcularRequest.setEventos(eventosCalculo);

		// Tipos de disponibilidade solicitados pelo comando
		calcularRequest.setTipoDisponibilidades(command.getTiposDisponibilidade());

		// Período (janela) de cálculo solicitada pelo comando
		Periodo periodoCalculo = new Periodo();
		periodoCalculo.setDataInicio(command.getDataInicio());
		periodoCalculo.setDataFim(command.getDataFim());
		calcularRequest.setPeriodo(periodoCalculo);

		// Equipamento solicitado pelo comando
		calcularRequest.setEquipamento(equipamento);

		// Invoca a regra "calcularDisponibilidade" e obtém o resultado
		RuleRequest calcularRuleRequest = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", CALCULAR_DISPONIBILIDADE,
				null);
		calcularRuleRequest.getInputParameters().put("calcularDisponibilidadeRequest", calcularRequest);
		RuleResponse calcularRuleResponse = ruleClient.invoke(calcularRuleRequest);
		CalcularDisponibilidadeResponse calcularResponse;
		try {
			calcularResponse = objectMapper.convertValue(
					calcularRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
		
		calcularResponse.setComentarios(comentarios);

		// Instanciando e aplicando evento com os dados do resultado
		play(getDisponibilidadesCalculadasEvent(command, calcularResponse));

		// Retorna o resultado
		return calcularResponse;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private VerificarSituacaoInstalacaoResponse handle(VerificarSituacaoInstalacaoCommand command) {
		
		// Validações do comando
		Assert.notNull(command);
		Assert.notNull(command.getDataInicio());
		Assert.notNull(command.getDataFim());

		// Validação do estado do aggregate
		Assert.state(instalacao != null);
		
		// Prepara invocação da regra "verificarSituacaoInstalacao"
		VerificarSituacaoInstalacaoRequest verificarRequest = new VerificarSituacaoInstalacaoRequest();

		// Adiciona a instalação com os equipamentos
		verificarRequest.setInstalacao(instalacao);

		// Período (janela) de cálculo solicitada pelo comando
		Periodo periodoCalculo = new Periodo();
		periodoCalculo.setDataInicio(command.getDataInicio());
		periodoCalculo.setDataFim(command.getDataFim());
		verificarRequest.setJanelaCalculo(periodoCalculo);

		// Atividade
		verificarRequest.setAtividade("CalculoDisponibilidade");
		
		// Adiciona a instalação com os equipamentos
		verificarRequest.setInstalacao(instalacao);
		if (instalacao instanceof Usina) {
			((Usina) instalacao)
					.setUnidadesGeradoras((List<UnidadeGeradora>) (List<?>) equipamentos); // NOSONAR
		} else if (instalacao instanceof InterligacaoInternacional) {
			equipamentos.forEach(equipamento -> ((InterligacaoInternacional) instalacao)
					.setEquipamento((EquipamentoInterligacaoInternacional) equipamento));
		}

		// Invoca a regra "verificarSituacaoInstalacao" e obtém o resultado
		RuleRequest verificarRuleRequest = new RuleRequest(SAGER_CRITICAS, "1.0",
				VERIFICAR_SITUACAO_INSTALACAO, "1.0");
		verificarRuleRequest.getInputParameters().put("Vrequest", verificarRequest);
		RuleResponse verificarRuleResponse = ruleClient.invoke(verificarRuleRequest);
		
		// Limpa os equipamentos da instalação
		if (instalacao instanceof Usina) {
			((Usina) instalacao).setUnidadesGeradoras(new ArrayList<>());
		} else if (instalacao instanceof InterligacaoInternacional) {
			((InterligacaoInternacional) instalacao).setEquipamento(null);
		}

		
		return objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
						VerificarSituacaoInstalacaoResponse.class);
		
	}

	@SuppressWarnings("unused")
	private void when(InstalacaoCadastradaEvent instalacaoCadastrada) {
		instalacao = instalacaoCadastrada.getInstalacao();
		setName(instalacaoCadastrada.getInstalacao().getNomeCurto());
	}

	@SuppressWarnings("unused")
	private void when(EquipamentoCadastradoEvent equipamentoCadastrado) {
		equipamentos.add(equipamentoCadastrado.getEquipamento());
		VerificarSituacaoInstalacaoResponse verificarSit = 
				verificarSituacaoInstalacaoODM(instalacao.getDataOutorgaImplantacao(),
				Date.from(Instant.now()));
		comentariosSituacao = verificarSit.getComentarios();
	}

	@SuppressWarnings("unused")
	private void when(EventosApuradosEvent eventosApurados) {
		apuracoes.add(eventosApurados.getApuracao());
	}
	
	private void adicionarParametros(List<ParametroTaxa> listParametros,List<ParametroTaxa> listNovosParametros){
		
		for(ParametroTaxa novoParametroTaxa : JavaUtil.emptyIfNull(listNovosParametros)){
			
			ParametroTaxa parametroTaxa = from($(qParametroTaxa),JavaUtil.emptyIfNull(listParametros)).
	    		where($(qParametroTaxa.getAno()).eq(novoParametroTaxa.getAno()).
					and($(qParametroTaxa.getMes()).eq(novoParametroTaxa.getMes()).
					and($(qParametroTaxa.getIdEquipamento()).eq(novoParametroTaxa.getIdEquipamento()).
					and($(qParametroTaxa.getCodigo()).eq(novoParametroTaxa.getCodigo()))))).fetchFirst();

			if(parametroTaxa != null){
				novoParametroTaxa.setId(parametroTaxa.getId());
				parametroTaxa = novoParametroTaxa;
			}else{
				//Se não existe então adiciona
				listParametros.add(novoParametroTaxa);
			}
		}
	}		
	
	@SuppressWarnings("unused")
	private void when(TaxasAcumuladasCalculadasV1Event taxasAcumuladasCalculadasEvt) {
		
		//clonar a taxa para enviar o evento carregado e salvar no periodoapuracao sem a MC apenas os ids.
		TaxasAcumuladasCalculadasV1Event taxasAcumuladasCalculadas = 
				(TaxasAcumuladasCalculadasV1Event) SerializationUtils.clone(taxasAcumuladasCalculadasEvt);
		
		
		//Save o handlerVerion
		PeriodoApuracao periodoApuracao = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes)
				.where($(qApuracao.getDataInicio()).eq(taxasAcumuladasCalculadas.getDataInicioApuracao()))
				.fetchOne();
		periodoApuracao.setHandlerVersion(this.getHandlerVersion());
						
		Calendar calDtInicio = Calendar.getInstance();
		calDtInicio.setTime(periodoApuracao.getDataInicio());
		
		// Aplicar o evento modificando os atributos de estado (apuracoes) ParametroTaxa
		List<ParametroTaxa> parametrosTaxasPeriodoApuracao = periodoApuracao.getParametrosV1(); 
		//Filtrar somente os Parametros do PeriodoAtual, pois existem 60 meses de parametros (utilizados no read)
		List<ParametroTaxa> parametroTaxaMesAtual = from($(qParametroTaxa),JavaUtil.emptyIfNull(taxasAcumuladasCalculadas.getParametros())).
	    		where($(qParametroTaxa.getAno()).eq(calDtInicio.get(Calendar.YEAR)).
					and($(qParametroTaxa.getMes()).eq(calDtInicio.get(Calendar.MONTH)+1))).
	    		fetch();
		this.adicionarParametros(parametrosTaxasPeriodoApuracao, 
				new ArrayList<ParametroTaxa>(parametroTaxaMesAtual));
		
		// Aplicar o evento modificando os atributos de estado (apuracoes) Taxa
		List<Taxa> taxasPeriodoApuracao = periodoApuracao.getTaxas();
		//Filtra Somente as taxas do período atual, pois existem as 60meses de taxas juntas
		List<Taxa> taxasMesAtual = from($(qTaxa),JavaUtil.emptyIfNull(taxasAcumuladasCalculadas.getTaxas())).
				where($(qTaxa.getPeriodo().getDataInicio()).eq(periodoApuracao.getDataInicio()))
				.fetch();
		this.adicionarTaxas(taxasPeriodoApuracao,taxasMesAtual);	

		//Salva as Taxas Passadas na lista de Taxas Mensais (inclusive as taxas de referencia)
		this.adicionarTaxasAcumuladasV1(taxasPeriodoApuracao,taxasAcumuladasCalculadas.getTaxasAcumuladas());

	}
	
	private List<Taxa> adicionarTaxas(List<Taxa> listTaxas,List<Taxa> listNovasTaxas){

		for(Taxa novaTaxa : JavaUtil.emptyIfNull(listNovasTaxas)){
			
			Taxa taxaMensal = from($(qTaxa),JavaUtil.emptyIfNull(listTaxas)).
					where($(qTaxa.getPeriodo().getDataInicio()).eq(novaTaxa.getPeriodo().getDataInicio()).
							and($(qTaxa.getTipo()).eq(novaTaxa.getTipo())).
							and($(qTaxa.getCodigo()).eq(novaTaxa.getCodigo()))).
					fetchFirst();
			
			if( taxaMensal != null ){
				novaTaxa.setId(taxaMensal.getId());
				taxaMensal = novaTaxa;
			}else{
				listTaxas.add(novaTaxa);
			}
		}
		return new ArrayList<Taxa>(listTaxas);
	}
	
	private List<String> salvarTaxasPassadas(List<Taxa> listNovasTaxas){
		
		List<String> idsTaxas = new ArrayList<>();
		
		//Varre a lista de novas taxas
		for(Taxa novaTaxa : JavaUtil.emptyIfNull(listNovasTaxas)){
			
			//Recupera a taxa no período específico
			PeriodoApuracao periodoApuracao = new CollQuery<PeriodoApuracao>(queryEngine)
					.from($(qApuracao), apuracoes)
					.where($(qApuracao.getDataInicio()).eq(novaTaxa.getPeriodo().getDataInicio()))
					.fetchOne();
			if(periodoApuracao == null)
				continue;
			//Recupera as Taxas do período específico
			Taxa taxa = from($(qTaxa),JavaUtil.emptyIfNull(periodoApuracao.getTaxas())).
					where($(qTaxa.getPeriodo().getDataInicio()).eq(novaTaxa.getPeriodo().getDataInicio()).
							and($(qTaxa.getTipo()).eq(novaTaxa.getTipo())).
							and($(qTaxa.getCodigo()).eq(novaTaxa.getCodigo()))).
					fetchFirst();
			
			if( taxa != null ){
				novaTaxa.setId(taxa.getId());
				taxa = novaTaxa;
			}else{
				//nao existe, adicona a nova taxa
				periodoApuracao.getTaxas().add(novaTaxa);
			}
			idsTaxas.add(novaTaxa.getId());
		}
		return idsTaxas;
	}
	
	private void adicionarTaxasAcumuladasV1(List<Taxa> taxasPeriodoApuracao, List<TaxaAcumulada> taxasAcumuladasEvt){
		
		List<TaxaAcumulada> taxasAcumuladas = new ArrayList<>(taxasAcumuladasEvt);
		
		for(TaxaAcumulada taxaAcumulada : taxasAcumuladas){
			
			//copia as taxas passadas para salvar nas taxas mensais (
			List<Taxa> taxasPassadas = new ArrayList<>();
			if(taxaAcumulada != null && taxaAcumulada.getTaxasPassadas() != null){
				taxasPassadas.addAll(taxaAcumulada.getTaxasPassadas());
			}
			
			//Salva as taxas passadas no respectivo Periodo e retorna uma lista de Ids para popular a Memoria de Calculo
			List<String> idsTaxasPassadas = this.salvarTaxasPassadas(taxasPassadas);
			
			//Remove as taxas passadas para salvar a taxa acumulada
			taxaAcumulada.setTaxasPassadas(new ArrayList<Taxa>());
			//Adiciona os ids das taxas passadas na memoria de calculo
			taxaAcumulada.setMemoriaCalculo(idsTaxasPassadas);
			
			//Salva as Taxas Acumuladas
			Taxa taxa = from($(qTaxa),JavaUtil.emptyIfNull(taxasPeriodoApuracao)).
					where($(qTaxa.getPeriodo().getDataInicio()).eq(taxaAcumulada.getPeriodo().getDataInicio()).
							and($(qTaxa.getTipo()).eq(taxaAcumulada.getTipo())).
							and($(qTaxa.getCodigo()).eq(taxaAcumulada.getCodigo()))).
					fetchFirst();
			
			if( taxa != null ){
				taxaAcumulada.setId(taxa.getId());
				taxa = taxaAcumulada;
			}else{
				taxasPeriodoApuracao.add(taxaAcumulada);
			}
		}
	}
	
	private void adicionarTaxasAcumuladasV2(List<Taxa> taxasPeriodoApuracao, List<TaxaAcumuladaEstendida> taxasAcumuladasEvt){
		
		List<TaxaAcumuladaEstendida> taxasAcumuladas = new ArrayList<>(taxasAcumuladasEvt);
		
		for(TaxaAcumuladaEstendida taxaAcumulada : taxasAcumuladas){
			
			//copia as taxas passadas para salvar nas taxas mensais (
			List<Taxa> taxasPassadas = new ArrayList<>();
			if(taxaAcumulada != null && taxaAcumulada.getHorasIndisponibilidade() != null)
			taxasPassadas.addAll(taxaAcumulada.getHorasIndisponibilidade());
			
			//Salva as taxas passadas no respectivo Periodo e retorna uma lista de Ids para popular a Memoria de Calculo
			List<String> idsTaxasPassadas = this.salvarTaxasPassadas(taxasPassadas);
			
			//Remove as taxas passadas para salvar a taxa acumulada
			taxaAcumulada.setHorasIndisponibilidade(new ArrayList<HorasIndisponibilidade>());
			//Adiciona os ids das taxas passadas na memoria de calculo
			taxaAcumulada.setMemoriaCalculo(idsTaxasPassadas);
			
			//Salva as Taxas Acumuladas
			Taxa taxa = from($(qTaxa),JavaUtil.emptyIfNull(taxasPeriodoApuracao)).
					where($(qTaxa.getPeriodo().getDataInicio()).eq(taxaAcumulada.getPeriodo().getDataInicio()).
							and($(qTaxa.getTipo()).eq(taxaAcumulada.getTipo())).
							and($(qTaxa.getCodigo()).eq(taxaAcumulada.getCodigo()))).
					fetchFirst();
			
			if( taxa != null ){
				taxaAcumulada.setId(taxa.getId());
				taxa = taxaAcumulada;
			}else{
				taxasPeriodoApuracao.add(taxaAcumulada);
			}
		}
	}	
	
	@SuppressWarnings("unused")
	private void when(TaxasAcumuladasCalculadasV2Event taxasAcumuladasCalculadasV2Evt) {
		
		//clonar a taxa para enviar o evento carregado e salvar no periodoapuracao sem a MC apenas os ids.
		TaxasAcumuladasCalculadasV2Event taxasAcumuladasCalculadasV2 = 
				(TaxasAcumuladasCalculadasV2Event) SerializationUtils.clone(taxasAcumuladasCalculadasV2Evt);
		
		//Save o handlerVerion
		PeriodoApuracao periodoApuracao = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes)
				.where($(qApuracao.getDataInicio()).eq(taxasAcumuladasCalculadasV2.getDataInicioApuracao()))
				.fetchOne();
		periodoApuracao.setHandlerVersion(this.getHandlerVersion());
		
		// Aplicar o evento modificando os atributos de estado (apuracoes)
		List<ParametroTaxa> parametrosTaxasV1PeriodoApuracao = periodoApuracao.getParametrosV1(); 
		//Filtrar somente os Parametros do PeriodoAtual, pois existem 60 meses de parametros (utilizados no read)
		List<ParametroTaxa> parametroTaxaV1MesAtual = from($(qParametroTaxa),
				JavaUtil.emptyIfNull(taxasAcumuladasCalculadasV2.getParametrosV1())).
	    		where($(qParametroTaxa.getAno()).eq(periodoApuracao.getDataInicio().getYear()).
					and($(qParametroTaxa.getMes()).eq(periodoApuracao.getDataInicio().getMonth()))).
	    		fetch();
		this.adicionarParametros(parametrosTaxasV1PeriodoApuracao,parametroTaxaV1MesAtual);
		
		// Aplicar o evento modificando os atributos de estado (apuracoes)
		List<ParametroTaxa> parametrosTaxasV2PeriodoApuracao = periodoApuracao.getParametrosV1(); 
		//Filtrar somente os Parametros do PeriodoAtual, pois existem 60 meses de parametros (utilizados no read)
		List<ParametroTaxa> parametroTaxaV2MesAtual = from($(qParametroTaxa),
				JavaUtil.emptyIfNull(taxasAcumuladasCalculadasV2.getParametrosV2())).
	    		where($(qParametroTaxa.getAno()).eq(periodoApuracao.getDataInicio().getYear()).
					and($(qParametroTaxa.getMes()).eq(periodoApuracao.getDataInicio().getMonth()))).
	    		fetch();		
		this.adicionarParametros(parametrosTaxasV2PeriodoApuracao,parametroTaxaV2MesAtual);		
		
		// Aplicar o evento modificando os atributos de estado (apuracoes)
		List<Taxa> taxasAcumuladasPeriodoApuracao = periodoApuracao.getTaxas();
		
		//Varre a lista de novas taxas
		this.adicionarTaxasAcumuladasV2(taxasAcumuladasPeriodoApuracao,taxasAcumuladasCalculadasV2.getTaxas());
		
	}	

	@SuppressWarnings("unused")
	private void when(DisponibilidadesCalculadasEvent disponibilidadesCalculadas) {
		// TODO Aplicar o evento modificando os atributos de estado (apuracoes)
	}
	

	/**
	 * @param command
	 * @param calcularResponse
	 * @return
	 */
	private DisponibilidadesCalculadasEvent getDisponibilidadesCalculadasEvent(CalcularDisponibilidadesCommand command,
			CalcularDisponibilidadeResponse calcularResponse) {
		DisponibilidadesCalculadasEvent event = new DisponibilidadesCalculadasEvent();
		event.setDataInicio(command.getDataInicio());
		event.setDataFim(command.getDataFim());
		event.setIdEquipamento(command.getIdUnidadeGeradora());
		event.setDisponibilidades(calcularResponse.getDisponibilidades());
		event.setComentarios(calcularResponse.getComentarios());
		return event;
	}

	@SuppressWarnings("unchecked")
	private VerificarSituacaoInstalacaoResponse verificarSituacaoInstalacaoODM(Date dataInicio, Date dataFim) {
		// Prepara invocação da regra "verificarSituacaoInstalacao"
		VerificarSituacaoInstalacaoRequest verificarRequest = new VerificarSituacaoInstalacaoRequest();

		// Adiciona a instalação com os equipamentos
		verificarRequest.setInstalacao(instalacao);
		if (instalacao instanceof Usina) {
			((Usina) instalacao).setUnidadesGeradoras((List<UnidadeGeradora>) (List<?>) equipamentos); // NOSONAR
		} else if (instalacao instanceof InterligacaoInternacional) {
			equipamentos.forEach(equipamento -> ((InterligacaoInternacional) instalacao)
					.setEquipamento((EquipamentoInterligacaoInternacional) equipamento));
		}

		// Período (janela) de cálculo solicitada pelo comando
		Periodo periodoCalculo = new Periodo();
		periodoCalculo.setDataInicio(dataInicio);
		periodoCalculo.setDataFim(dataFim);
		verificarRequest.setJanelaCalculo(periodoCalculo);

		// Atividade
		verificarRequest.setAtividade("CalculoDisponibilidade");

		// Invoca a regra "verificarSituacaoInstalacao" e obtém o resultado
		RuleRequest verificarRuleRequest = new RuleRequest(SAGER_CRITICAS, "1.0",
				VERIFICAR_SITUACAO_INSTALACAO, "1.0");
		verificarRuleRequest.getInputParameters().put("Vrequest", verificarRequest);
		RuleResponse verificarRuleResponse = ruleClient.invoke(verificarRuleRequest);

		// Limpa os equipamentos da instalação
		if (instalacao instanceof Usina) {
			((Usina) instalacao).setUnidadesGeradoras(new ArrayList<>());
		} else if (instalacao instanceof InterligacaoInternacional) {
			((InterligacaoInternacional) instalacao).setEquipamento(null);
		}

		return objectMapper
				.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
						VerificarSituacaoInstalacaoResponse.class);
	}
	

	private CalcularParametrosResponse calcularParametrosODM(CalcularParametrosRequest calcularParametrosRequest,int regulamentacao) {
		// Invocando regra do ODM Para os Parametros
		RuleRequest ruleRequest;
		if(regulamentacao == REGULAMENTACAO_ANTES_OUTUBRO_2014){
			ruleRequest = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", CALCULAR_PARAMETROS, "1.0");
		}else{
			ruleRequest = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", CALCULAR_PARAMETROS, "2.0");
		}
			
		ruleRequest.getInputParameters().put("calcularParametrosRequest", calcularParametrosRequest);

		// Mockado a chamada do edson, não commitar
		RuleResponse response = ruleClient.invoke(ruleRequest);
		// Obter parâmetros de saída
		return objectMapper.convertValue(response.getOutputParameters().get("calcularParametrosResponse"),
						CalcularParametrosResponse.class);

	}

	private CalcularTaxasMensaisResponse calcularTaxasMensaisODM(
			CalcularTaxasMensaisRequest calcularTaxasMensaisRequest) {
		// Invocando regra do ODM Para os Parametros
		RuleRequest ruleRequestTaxa = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", "calcularTaxasMensais","1.0");
		ruleRequestTaxa.getInputParameters().put("calcularTaxasMensaisRequest", calcularTaxasMensaisRequest);
		// Mockado a chamada do edson, não commitar
		RuleResponse response = ruleClient.invoke(ruleRequestTaxa);
		// Obter parâmetros de saída
		return objectMapper.convertValue(response.getOutputParameters().get("calcularTaxasMensaisResponse"),
						CalcularTaxasMensaisResponse.class);

	}

	/**
	 * @param regulamentacao
	 * @return
	 */
	private CalcularTaxasAcumuladasResponseV1 calcularTaxasAcumuladasV1ODM(CalcularTaxasAcumuladasRequestV1 requestCalcularTax) {
		// Invocando regra do ODM Para os Parametros
		RuleRequest ruleRequestTaxa = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", "calcularTaxasAcumuladas", "1.0");
		ruleRequestTaxa.getInputParameters().put("calcularTaxasAcumuladasRequestV1", requestCalcularTax);
		// Mockado a chamada do edson, não commitar
		RuleResponse response = ruleClient.invoke(ruleRequestTaxa);
		// Obter parâmetros de saída
		return objectMapper.convertValue(response.getOutputParameters().get("calcularTaxasAcumuladasResponseV1"),
						CalcularTaxasAcumuladasResponseV1.class);

	}
	
	/**
	 * @param regulamentacao
	 * @return
	 */
	private CalcularTaxasAcumuladasResponseV2 calcularTaxasAcumuladasV2ODM(CalcularTaxasAcumuladasRequestV2 requestCalcularTax) {
		// Invocando regra do ODM Para os Parametros
		RuleRequest ruleRequestTaxa = new RuleRequest(SAGER_APURACAO_INDISPONIBILIDADE, "1.0", "calcularTaxasAcumuladas", "2.0");
		ruleRequestTaxa.getInputParameters().put("calcularTaxasAcumuladasRequestV2", requestCalcularTax);
		// Mockado a chamada do edson, não commitar
		RuleResponse response = ruleClient.invoke(ruleRequestTaxa);
		// Obter parâmetros de saída
		return objectMapper.convertValue(response.getOutputParameters().get("calcularTaxasAcumuladasResponseV2"),
						CalcularTaxasAcumuladasResponseV2.class);

	}

	/*
	TODO: UC06 private ContabilizarUsoFranquiasResponse contabilizarUsoFranquiasODM(ContabilizarUsoFranquiasRequest requestContablizar) {
		// Invocando regra do ODM Para contabilizarUsoFranquias
		RuleRequest ruleRequestTaxa = new RuleRequest("calcularTaxasAcumuladasV2", "1.0", "calcularTaxasAcumuladas", null);
		ruleRequestTaxa.getInputParameters().put("calcularTaxasAcumuladasRequestV2", requestCalcularTax);
		// Mockado a chamada do edson, não commitar
		RuleResponse response = ruleClient.invoke(ruleRequestTaxa);
		// Obter parâmetros de saída
		CalcularTaxasAcumuladasResponseV2 calcularTaxasResponse = (CalcularTaxasAcumuladasResponseV2) objectMapper 
				.convertValue(response.getOutputParameters().get("calcularTaxasAcumuladasResponseV2"),
						CalcularTaxasAcumuladasResponseV2.class);
		return null;
	}
	*/
	
	/**
	 * Dado um periodo de apuracao (mes) - Retorna uma lista de todas as tadas desse período
	 * mais todas as taxas dos 59 períodos anteriores que tem taxas ( não necessariamente os 59 anteriores, 
	 * pois se existirem periodos sem taxas no meio do período, pega um periodo a mais dos 59, e assim por adiante)
	 * @param apuracao
	 * @return
	 */
	private void getAllTaxasMensais(PeriodoApuracao periodoAtual,TaxasAcumuladasCalculadasV1Event event){
		
		List<Taxa> allTaxas = new ArrayList<>();
		
		//Carregando o periodo atual  e Pesquisa apenas no periodoAnterior, pois o periodo atual já está em taxas
		// com o merge dos ids já corrigidos.
		allTaxas.addAll(event.getTaxas());
		int periodoCarregados = event.getTaxas().size();
		Calendar dataInicioPeriodoAnterior = Calendar.getInstance();
		dataInicioPeriodoAnterior.setTime(periodoAtual.getDataInicio());
		dataInicioPeriodoAnterior.set(Calendar.MONTH, dataInicioPeriodoAnterior.get(Calendar.MONTH)-1);
		
		// Filtra os períodos de apuração abrangidos a partir do periodoAtual
		List<PeriodoApuracao> apuracoesCalculo = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes).where($(qApuracao.getDataInicio())
				.loe(dataInicioPeriodoAnterior.getTime())).orderBy($(qApuracao.getDataInicio()).desc()).fetch();

		// Monta lista com os eventos abrangidos pelos eventos do periodo atual + 59 periodos anteriores
		for (PeriodoApuracao apuracao : apuracoesCalculo) {
				for(Taxa tx : JavaUtil.emptyIfNull(apuracao.getTaxas())){
					//Só adiciona se já não contiver na lista
					
					Taxa taxaMensal = from($(qTaxa),JavaUtil.emptyIfNull(allTaxas)).
							where($(qTaxa.getPeriodo().getDataInicio()).eq(tx.getPeriodo().getDataInicio()).
									and($(qTaxa.getTipo()).eq(TipoTaxa.MENSAL)).
									and($(qTaxa.getCodigo()).eq(tx.getCodigo()))).
							fetchFirst();
					
					if(taxaMensal == null ){
						allTaxas.add(tx);
						//Carrega todos os parametros necessários para essa taxa.
						this.adicionarParametros(event.getParametros(), apuracao.getParametrosV1());
						this.adicionarParametros(event.getParametros(), apuracao.getParametrosV2());
					}
				}
			periodoCarregados++; // por mes apuracao
			if(periodoCarregados == 60){
				break;
			}
		}
		
		event.setTaxas(allTaxas);

	}
	
	/**
	 * Dado um periodo de apuracao (mes) - Retorna uma lista de todas os parametros desse período
	 * mais todos os parametros dos 59 períodos anteriores que tem equipamento ligado ( não necessariamente os 59 anteriores, 
	 * pois se existirem periodos sem equuipamento ligado no meio do período, pega um periodo a mais dos 59, e assim por adiante)
	 * O que for antes de Outubro2014 pegar V1 e V2
	 * O que for depois de Outubro2014 pegar V2
	 * @param apuracao
	 * @return
	 */
	private void getAllParametros(PeriodoApuracao periodoAtual,TaxasAcumuladasCalculadasV2Event  tavaEventV2){
		
		//Carregando o periodo atual  e Pesquisa apenas no periodoAnterior, pois o periodo atual já está em listParametros
		// com o merge dos ids já corrigidos.
		int periodoCarregados = tavaEventV2.getParametrosV2().size();
		Calendar dataInicioPeriodoAnterior = Calendar.getInstance();
		dataInicioPeriodoAnterior.setTime(periodoAtual.getDataInicio());
		dataInicioPeriodoAnterior.set(Calendar.MONTH, dataInicioPeriodoAnterior.get(Calendar.MONTH)-1);
		
		// Filtra os períodos de apuração abrangidos a partir do periodoAtual
		List<PeriodoApuracao> apuracoesCalculo = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes).where($(qApuracao.getDataInicio())
				.loe(dataInicioPeriodoAnterior.getTime())).orderBy($(qApuracao.getDataInicio()).desc()).fetch();

		// Monta lista com os eventos abrangidos pelos eventos do periodo atual + 59 periodos anteriores
		for (PeriodoApuracao apuracao : apuracoesCalculo) {
			
			boolean periodoCarregado = false;
			//V1
			if (1412132400000l > apuracao.getDataInicio().toInstant().toEpochMilli()){
				//Se existe evento então existe pelo menos 1 equipamento ligado
				if( apuracao.getParametrosV1() != null){
					for(ParametroTaxa tx : JavaUtil.emptyIfNull(apuracao.getParametrosV1())){
						
						ParametroTaxa parametroTaxa = from($(qParametroTaxa),
								JavaUtil.emptyIfNull(tavaEventV2.getParametrosV1())).
					    		where($(qParametroTaxa.getAno()).eq(tx.getAno()).
									and($(qParametroTaxa.getMes()).eq(tx.getMes()).
									and($(qParametroTaxa.getIdEquipamento()).eq(tx.getIdEquipamento()).
									and($(qParametroTaxa.getCodigo()).eq(tx.getCodigo()))))).fetchFirst();
						
						//Só adiciona se já não contiver na lista
						if(parametroTaxa == null ){
							tavaEventV2.getParametrosV1().add(tx);
						}
					}
					periodoCarregado = true;
				}
			}

			//V2
			//Se existe evento então existe pelo menos 1 equipamento ligado
			if( apuracao.getParametrosV2() != null){
				for(ParametroTaxa tx : JavaUtil.emptyIfNull(apuracao.getParametrosV2())){
					
					ParametroTaxa parametroTaxa = from($(qParametroTaxa),JavaUtil.emptyIfNull(tavaEventV2.getParametrosV2())).
				    		where($(qParametroTaxa.getAno()).eq(tx.getAno()).
								and($(qParametroTaxa.getMes()).eq(tx.getMes()).
								and($(qParametroTaxa.getIdEquipamento()).eq(tx.getIdEquipamento()).
								and($(qParametroTaxa.getCodigo()).eq(tx.getCodigo()))))).fetchFirst();
					
					//Só adiciona se já não contiver na lista
					if(parametroTaxa == null ){
						tavaEventV2.getParametrosV2().add(tx);
					}
				}
				
				periodoCarregado = true; // por mes apuracao
			}
			
			if(periodoCarregado){
				periodoCarregados++;
			}

			if(periodoCarregados == 60){
				break;
			}
		}
	}		
	

	/**
	 * @return o campo instalacao
	 */
	public Instalacao getInstalacao() {
		return instalacao;
	}

	/**
	 * @param instalacao
	 *            o campo instalacao a ser definido
	 */
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
	}

	/**
	 * @return o campo equipamentos
	 */
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	/**
	 * @param equipamentos
	 *            o campo equipamentos a ser definido
	 */
	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	/**
	 * @return o campo apuracoes
	 */
	public List<PeriodoApuracao> getApuracoes() {
		return apuracoes;
	}

	public void setApuracoes(List<PeriodoApuracao> apuracoes) {
		this.apuracoes = apuracoes;
	}

	/**
	 * @return o campo comentariosSituacao
	 */
	public List<ComentarioSituacao> getComentariosSituacao() {
		return comentariosSituacao;
	}

	/**
	 * @param comentariosSituacao
	 *            o campo comentariosSituacao a ser definido
	 */
	public void setComentariosSituacao(List<ComentarioSituacao> comentariosSituacao) {
		this.comentariosSituacao = comentariosSituacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InstalacaoAggregate [instalacao=" + instalacao + ", equipamentos=" + equipamentos + ", apuracoes="
				+ apuracoes + ", comentariosSituacao=" + comentariosSituacao + "]";
	}
	
	private EventoMudancaEstadoOperativo adicionarEventoEspelho(Date dataEventoEspelho,String idEquipamento ){
		
		List<PeriodoApuracao> listaApuracoes = new CollQuery<PeriodoApuracao>(queryEngine)
				.from($(qApuracao), apuracoes)
				.where($(qApuracao.getDataInicio()).lt(dataEventoEspelho))
				.orderBy($(qApuracao.getDataInicio()).desc())
				.fetch();
		
		for(PeriodoApuracao apuracao : listaApuracoes){
		
			// Adiciona o evento espelho na lista
			EventoMudancaEstadoOperativo eventoEspelho = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
					.from($(qEvento), JavaUtil.emptyIfNull(apuracao.getEventos()))
					.where($(qEvento.getDataVerificada()).loe(dataEventoEspelho)
						.and($(qEvento.getIdEquipamento()).eq(idEquipamento)).and(
						$(qEvento.getTipoOperacao()).ne(TipoOperacao.O).or(
						$(qEvento.getTipoOperacao()).ne(TipoOperacao.C))))
					.orderBy($(qEvento.getDataVerificada()).desc())
					.fetchFirst();
			
			if (eventoEspelho != null) {
				eventoEspelho.setDataVerificada(dataEventoEspelho);
				eventoEspelho.setEventoEspelho(true);
				return eventoEspelho;
			}
			
		}

		throw new OnsRuntimeException("Não existe nenhum evento emitido até o momento."+
				"DataEventoEspelho["+sdf.format(dataEventoEspelho)+"] IdEquipamento["+idEquipamento+"]");
	}

}
