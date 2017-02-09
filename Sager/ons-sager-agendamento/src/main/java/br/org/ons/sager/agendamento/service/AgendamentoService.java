package br.org.ons.sager.agendamento.service;

import static com.querydsl.collections.CollQueryFactory.from;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.LogComentarios;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.platform.common.CommandFinishedEvent;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.CommandStartedEvent;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.agendamento.JavaUtil;
import br.org.ons.sager.agendamento.TipoAgendamento;
import br.org.ons.sager.agendamento.config.mq.CommandBus;
import br.org.ons.sager.agendamento.domain.AgendamentoCalculo;
import br.org.ons.sager.agendamento.domain.AgendamentoCenario;
import br.org.ons.sager.agendamento.domain.AgendamentoRetificacao;
import br.org.ons.sager.agendamento.domain.Instalacao;
import br.org.ons.sager.agendamento.domain.QAgendamentoCalculo;
import br.org.ons.sager.agendamento.domain.QAgendamentoCenario;
import br.org.ons.sager.agendamento.domain.QAgendamentoRetificacao;
import br.org.ons.sager.agendamento.repository.AgendamentoCalculoRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoCenarioRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoRetificacaoRepository;
import br.org.ons.sager.agendamento.security.jwt.TokenProvider;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.instalacao.command.CalcularTaxasCommand;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event;
import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

@Service
public class AgendamentoService {

	private final Logger log = LoggerFactory.getLogger(AgendamentoService.class);
	
	@Autowired
	private AgendamentoCalculoRepository agendamentoCalculoRepository;
	
	@Inject
	private AgendamentoCenarioRepository agendamentoCenarioRepository;
	
	@Inject
	private AgendamentoRetificacaoRepository agendamentoRetificacaoRepository;
	
	@Inject
    private CommandBus commandBus; 
	
	private final AgendamentoCalculo qAgendamentoCalculo = alias(AgendamentoCalculo.class, "agendamentoCalculo");
	
	private RestTemplate restTemplate = new RestTemplate();
		
	private static final String BEARER = "Bearer ";
	
	private static final String PROTOCOLO = "protocolo";
	
	private static final String SCENARIO_NAME = "scenarioName";
	
	private static final int MILISSEGUNDO = 10000000;
	
	@Inject
	private TokenProvider tokenProvider;	
	
	@Value("${onsplatform.url}")
	private String platformEndpoint;
	
	 /**
		 * Recebe eventos de DisponibilidadesCalculadasEvent as disponibilidades calculadas
		 * no repositório de dados de leitura
		 * 
		 * @param eventMessage
		 *            Evento
		 * @throws JMSException
	 */
	@JmsListener(destination = "platform/EventTopic/br.org.ons.platform.common.CommandStartedEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleCommandStartedEvent(EventMessage<CommandStartedEvent> eventMessage,
			 @Header(name = "Authorization") String token) throws JMSException {
		log.debug("handleCommandStartedEvent: {}", eventMessage);
		
		try{
			SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token.substring(BEARER.length())));
		
			String protocoloStr = ""+eventMessage.getMetadata().getProperties().get(PROTOCOLO);
			
			AgendamentoCalculo agendamentoCalculo = this.agendamentoCalculoRepository.findOneByProtocoloStr(protocoloStr);
			
			if (agendamentoCalculo != null){
				agendamentoCalculo.setSituacao(TipoAgendamento.EM_EXECUCAO.getDescricao());

				//Atualiza todas as instalacoes para em_execucao
				for(Instalacao inst : JavaUtil.emptyIfNull(agendamentoCalculo.getInstalacoesCenarios())){
					inst.setSituacao(TipoAgendamento.EM_EXECUCAO.getDescricao());
				}

				this.agendamentoCalculoRepository.save(agendamentoCalculo);
			}
		}catch(Exception e){
			log.error("handleCommandStartedEvent",e);
		}finally {
            SecurityContextHolder.clearContext();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@JmsListener(destination = "platform/EventTopic/br.org.ons.platform.common.CommandFinishedEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleCommandFinishedEvent(EventMessage<CommandFinishedEvent> eventMessage,
			 @Header(name = "Authorization") String token) throws JMSException {
		log.debug("handleCommandFinishedEvent: {}", eventMessage);
		
		try{
			SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token.substring(BEARER.length())));
			
			String protocoloStr = ""+eventMessage.getMetadata().getProperties().get(PROTOCOLO);
			
			CommandFinishedEvent event = eventMessage.getEvent();
			
			AgendamentoCalculo agendamentoCalculo = this.agendamentoCalculoRepository.findOneByProtocoloStr(protocoloStr);
			
			if (agendamentoCalculo != null){
				agendamentoCalculo.setSituacao(TipoAgendamento.FINALIZADO.getDescricao());
				//Atualiza a lista de InstalacoesCenarios
				this.atualizarSituacao(agendamentoCalculo, TipoAgendamento.FINALIZADO, 
						eventMessage.getMetadata().getAggregateId());
				
				//adicionar os comentarios da situacao
				if(!event.getResults().isEmpty()){
					
					List<LogComentarios> logComentarios = (List<LogComentarios>)event.getResults();
					
					agendamentoCalculo.addComentarios(logComentarios);
					for(LogComentarios logComentario : logComentarios){
						if(logComentario.getNomeCenario() != null && !logComentario.getListaComentarios().isEmpty()){
							this.adiconarComentariosNaInstalacaoCenario(logComentario,
									eventMessage.getMetadata().getAggregateId(), 
									logComentario.getNomeCenario(), 
									agendamentoCalculo);
						}
					}
					
				}
				
				if ( event.getStatus() == CommandFinishedEvent.Status.FAILED ){
					agendamentoCalculo.setResultado(TipoAgendamento.ERRO.getDescricao());
					this.atualizarResultado(agendamentoCalculo, TipoAgendamento.ERRO, 
							eventMessage.getMetadata().getAggregateId());
					
					List<Comentario> comentariosErro = new ArrayList<>();
					Comentario comentarioErro = new Comentario();
					comentarioErro.setDataInsercao(new Date());
					comentarioErro.setDescricao(event.getMessage());
					comentarioErro.setOrigem(OrigemComentario.SISTEMA);
					comentarioErro.setTipo(TipoComentario.ERRO);
					comentariosErro.add(comentarioErro);
					if(!comentariosErro.isEmpty()){
						agendamentoCalculo.addComentario(new LogComentarios(comentariosErro,new Date()));
					}
					
				}else{
					this.atualizarResultado(agendamentoCalculo, TipoAgendamento.SUCESSO, 
							eventMessage.getMetadata().getAggregateId());
					agendamentoCalculo.setResultado(TipoAgendamento.SUCESSO.getDescricao());
					
				}				
				this.agendamentoCalculoRepository.save(agendamentoCalculo);
			}
		}catch(Exception e){
			log.error("handleCommandFinishedEvent",e);
		}finally {

            SecurityContextHolder.clearContext();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleTaxasAcumuladasCalculadasV1Event(EventMessage<TaxasAcumuladasCalculadasV1Event> eventMessage,
			 @Header(name = "Authorization") String token) throws JMSException {
		
		TaxasAcumuladasCalculadasV1Event event;
		
		try{
			String protocoloStr = eventMessage.getMetadata().getProperties().get(PROTOCOLO).toString();
			String nomeCenario = ""+eventMessage.getMetadata().getProperties().get(SCENARIO_NAME);
			event = eventMessage.getEvent();
			
			this.criarInstalacaoCenario(eventMessage.getMetadata(), protocoloStr, 
					TipoAgendamento.SUCESSO.getDescricao(), TipoAgendamento.FINALIZADO.getDescricao(),token);
			
			//adicionando os comentarios das taxas mensais
			this.adiconarComentariosDasTaxasNaInstalacaoCenario((List<Taxa>) event.getTaxas(), token,
														protocoloStr,event.getDataInicioApuracao(),
														eventMessage.getMetadata().getAggregateId(),
														nomeCenario,
														eventMessage.getMetadata());
			
			//adicionando os comentarios das taxas acumuladas
			this.adiconarComentariosDasTaxasNaInstalacaoCenario((List<Taxa>) (List<?>) event.getTaxasAcumuladas(), 
																token,protocoloStr,event.getDataInicioApuracao(),
																eventMessage.getMetadata().getAggregateId(),
																nomeCenario,
																eventMessage.getMetadata());
			
		}catch(Exception e){
			log.error("handleTaxasAcumuladasCalculadasV1Event",e);
		}finally{
			SecurityContextHolder.clearContext();
		}		
		
	}
	
	@SuppressWarnings("unchecked")
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV2Event", 
	containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleTaxasAcumuladasCalculadasV2Event(EventMessage<TaxasAcumuladasCalculadasV2Event> eventMessage,
			 @Header(name = "Authorization") String token) throws JMSException {
		
		TaxasAcumuladasCalculadasV2Event event;
		
		try{
			String protocoloStr = eventMessage.getMetadata().getProperties().get(PROTOCOLO).toString();
			String nomeCenario = ""+eventMessage.getMetadata().getProperties().get(SCENARIO_NAME);
			event = eventMessage.getEvent();
			
			this.criarInstalacaoCenario(eventMessage.getMetadata(), protocoloStr, 
					TipoAgendamento.SUCESSO.getDescricao(), TipoAgendamento.FINALIZADO.getDescricao(),token);
			
			this.adiconarComentariosDasTaxasNaInstalacaoCenario((List<Taxa>) (List<?>) event.getTaxas(), 
					token,protocoloStr,event.getDataInicioApuracao(),
					eventMessage.getMetadata().getAggregateId(),
					nomeCenario,
					eventMessage.getMetadata());
			
		}catch(Exception e){
			log.error("handleTaxasAcumuladasCalculadasV2Event",e);
		}finally{
			SecurityContextHolder.clearContext();
		}
		
	}

	private void adiconarComentariosNaInstalacaoCenario(LogComentarios comentarios, 
			String aggregateId,String nomeCenario, AgendamentoCalculo agendamentoCalculo){
		
		Instalacao qInstalacao = alias(Instalacao.class, "instalacao");
		Instalacao instalacaoScenario = from(qInstalacao,agendamentoCalculo.getInstalacoesCenarios()).
    	        where($(qInstalacao.getId()).eq(aggregateId).
    	        		and($(qInstalacao.getNomeCenario()).eq(nomeCenario))).fetchOne();
		
		if(instalacaoScenario != null){
			//adiciona o instalacaoScenario
			instalacaoScenario.addComentario(comentarios);
		}else{
			//criar instalacaoScenario correta
			Instalacao instalacaoCenario = new Instalacao();
			instalacaoCenario.setId(aggregateId);
			instalacaoCenario.setNome(aggregateId);
			instalacaoCenario.setNomeCenario(nomeCenario);
			instalacaoCenario.addComentario(comentarios);
			agendamentoCalculo.getInstalacoesCenarios().add(instalacaoCenario);
		}
	
	}
	
	private void criarInstalacaoCenario(EventMetadata metadata, String protocoloStr, String resultado, String situacao,String token){
		
		SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token.substring(BEARER.length())));
		
		AgendamentoCalculo agendamentoCalculo = this.agendamentoCalculoRepository.findOneByProtocoloStr(protocoloStr);
		String aggregateId = metadata.getAggregateId();
		String nomeCenario = ""+metadata.getProperties().get(SCENARIO_NAME);
		
		if(agendamentoCalculo != null && !"null".equalsIgnoreCase(nomeCenario)){
			Instalacao qInstalacao = alias(Instalacao.class, "instalacao");
			Instalacao instalacaoScenario = from(qInstalacao,agendamentoCalculo.getInstalacoesCenarios()).
	    	        where($(qInstalacao.getId()).eq(aggregateId).
	    	        		and($(qInstalacao.getNomeCenario()).eq(nomeCenario))).fetchOne();
			
			if(instalacaoScenario == null){
				//criar instalacaoScenario correta
				Instalacao instalacaoCenario = new Instalacao();
				instalacaoCenario.setId(metadata.getAggregateId());
				instalacaoCenario.setMinorVersion(metadata.getMinorVersion());
				instalacaoCenario.setNome(metadata.getAggregateId());
				instalacaoCenario.setNomeCenario(nomeCenario);
				instalacaoCenario.setResultado(resultado);
				instalacaoCenario.setSituacao(situacao);
				agendamentoCalculo.getInstalacoesCenarios().add(instalacaoCenario);
			}
		}
	}
	
	private void adiconarComentariosDasTaxasNaInstalacaoCenario(List<Taxa> taxas, String token, 
			String protocoloStr, Date dataInicioApuracao,String aggregateId,String nomeCenario,EventMetadata metadata){
		

			SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token.substring(BEARER.length())));
			
			AgendamentoCalculo agendamentoCalculo = this.agendamentoCalculoRepository.findOneByProtocoloStr(protocoloStr);
			
			if (agendamentoCalculo != null){
				agendamentoCalculo.setSituacao(TipoAgendamento.FINALIZADO.getDescricao());
				
				List<Comentario> comentariosErro = new ArrayList<>();
				//loop por lista de taxa
				for(Taxa taxa : JavaUtil.emptyIfNull(taxas)){
					comentariosErro.addAll(taxa.getComentarios());
				}
				this.adiconarComentariosNaInstalacaoCenario(new LogComentarios(comentariosErro,dataInicioApuracao), 
						aggregateId, nomeCenario, agendamentoCalculo);
				
				this.agendamentoCalculoRepository.save(agendamentoCalculo);
				
			}
	}

	public void cancelarAgendamentoCalculoTaxa(AgendamentoCalculo agendamentoCalculo) {
		
	   	AgendamentoCalculo agendamento = agendamentoCalculoRepository.findOne(agendamentoCalculo.getId());
	   	
	   	if(agendamento == null){
	   		throw new NotFoundException();
	   	}
	   	
	   	if(TipoAgendamento.AGENDADO.getDescricao().equalsIgnoreCase(agendamento.getSituacao())){

		    //Chama o rest schedule do ons-platform para reagendar o cálculo
		    String parameters = agendamento.getProtocoloStr();
		
			ResponseEntity<String> response = restTemplate.exchange(platformEndpoint + "/api/schedule/"+parameters, HttpMethod.DELETE, requestEntity(null),String.class);
			String protocoloStr = response.getBody();

	   		agendamento.setProtocoloStr(protocoloStr);
	   		agendamento.setSituacao(TipoAgendamento.CANCELADO.getDescricao());
	   		agendamentoCalculoRepository.save(agendamento);
	   	}else{
	   		throw new OnsRuntimeException("O agendamento não está no status AGENDADO, por isso não poderá executar o comando.");
	   	}
	   	
   }
	
	public void reagendarAgendamentoCalculoTaxa(AgendamentoCalculo agendamentoCalculo) {
		
		
		AgendamentoCalculo agendamento = agendamentoCalculoRepository.findOne(agendamentoCalculo.getId());
		
	   	if(agendamento == null){
	   		throw new NotFoundException();
	   	}
	   	
	   	if(TipoAgendamento.AGENDADO.getDescricao().equalsIgnoreCase(agendamento.getSituacao())){
		
			//Chama o rest schedule do ons-platform para reagendar o cálculo
	        String parameters = "scheduleDate="+agendamentoCalculo.getDataAgendamento().format(DateTimeFormatter.ISO_DATE_TIME);
	        parameters += "&id=" + agendamento.getProtocoloStr();
	
			ResponseEntity<String> response = restTemplate.exchange(platformEndpoint + "/api/schedule?"+parameters, HttpMethod.PUT, requestEntity(null),String.class);
			String protocoloStr = response.getBody();

	   		agendamento.setDataAgendamento(agendamentoCalculo.getDataAgendamento());
	   		agendamento.setProtocoloStr(protocoloStr);
	   		agendamento.setSolicitante(agendamentoCalculo.getSolicitante());
	   		agendamento.setSituacao(TipoAgendamento.EM_EXECUCAO.getDescricao());
	   		agendamentoCalculoRepository.save(agendamento);
	   	}else{
	   		throw new OnsRuntimeException("O agendamento não está no status AGENDADO, por isso não poderá executar o comando.");
	   	}
	}
	
	public  AgendamentoCalculo salvarAgendamentoCalculoTaxa(AgendamentoCalculo agendamentoCalculo) {
   	 //Chama o rest schedule do ons-platform para agendar o cálculo
       
       AgendamentoCalculo preAgendado = this.generateProtocoloAgendamento(agendamentoCalculo.getDataCriacao());
       int protocolo = preAgendado.getProtocolo();
       String protocoloStr = String.format("%06d", protocolo)  + '-' + agendamentoCalculo.getDataCriacao().getYear();
       
       //Enviar uma lista de CalcularTaxaCommand por Mes / Instalacao
       List<CommandMessage<CalcularTaxasCommand>> criarMessages = new ArrayList<>();
       
       // for por instalacao
       for(Instalacao instalacao : agendamentoCalculo.getInstalacoesCenarios()){
    	   
    	   // for por mes
           for(ZonedDateTime mesComand = agendamentoCalculo.getMesInicial();
    		   mesComand.isBefore(agendamentoCalculo.getMesFinal());
    		   mesComand = mesComand.plusMonths(1L)){
        	   
        	   CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
        	   calcularTaxas.setDataInicio(
       				Date.from(ZonedDateTime.of(mesComand.getYear(), mesComand.getMonth().getValue(),
       						1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant()));
        	   calcularTaxas.setDataFim(
       				Date.from(ZonedDateTime.of(mesComand.getYear(), mesComand.getMonth().getValue(),
       						1, 0, 0, 0, 0, ZoneId.systemDefault())
       				.plusMonths(1L).minusNanos(MILISSEGUNDO).toInstant()));
        	   
               CommandMetadata metadata = new CommandMetadata();
               metadata.setId(IdGenerator.newId());
               metadata.setCorrelationId(null);
               metadata.setAggregateId(instalacao.getId());
               metadata.setMajorVersion(null);
               metadata.setMinorVersion(instalacao.getMinorVersion());
               metadata.setTimelineDate(ZonedDateTime.now());
               metadata.setIsReplay(false);
               metadata.putProperty(PROTOCOLO, protocoloStr);
        	   
               CommandMessage<CalcularTaxasCommand> criarMessage = new CommandMessage<>();
        	   criarMessage.setCommand(calcularTaxas);
        	   criarMessage.setMetadata(metadata);
        	   
        	   criarMessages.add(criarMessage);
        	   
           }
    	   
       }

       String parameters = "scheduleDate="+agendamentoCalculo.getDataAgendamento().format(DateTimeFormatter.ISO_DATE_TIME)+
   			   "&id="+protocoloStr;
		
		agendamentoCalculo.setProtocolo(protocolo);
		agendamentoCalculo.setAnoCriacao(agendamentoCalculo.getDataCriacao().getYear());
		agendamentoCalculo.setProtocoloStr( protocoloStr );
		agendamentoCalculo.setId(preAgendado.getId());
		
		//salvar as instalacoes (cenario = vigente)
		for(Instalacao inst : JavaUtil.emptyIfNull(agendamentoCalculo.getInstalacoesCenarios())){
			inst.setResultado("NA");
			inst.setSituacao(TipoAgendamento.AGENDADO.getDescricao());
			inst.setNomeCenario("Principal");
		}
	
		//Salvar agendamento antes de chamar o plataforma para evitar desincronizmo dos status (EM AGENDAMENTO/AGENDADO)
		agendamentoCalculoRepository.save(agendamentoCalculo);

		ResponseEntity<String> response = restTemplate.exchange(platformEndpoint + "/api/schedule?"+parameters, HttpMethod.POST, 
				requestEntity(criarMessages),String.class);
		if ( response.getStatusCodeValue() == HttpStatus.SC_OK ){
			String protocoloStrReturn = response.getBody();
			agendamentoCalculo.setSituacao(TipoAgendamento.AGENDADO.getDescricao());
			agendamentoCalculo.setProtocoloStr( protocoloStrReturn );
	
			// Salva na base do XScale o agendamento enviado ao Quartz.
			return agendamentoCalculoRepository.save(agendamentoCalculo);
		}
		
		return null;
	}
	
	private  AgendamentoCalculo generateProtocoloAgendamento(ZonedDateTime dataCriacao) {
    	
				
    	AgendamentoCalculo lastAgendamento = agendamentoCalculoRepository.findTopByAnoCriacaoOrderByProtocoloDesc(dataCriacao.getYear());
    	
    	int protocolo = 0;
    	if (lastAgendamento != null){
    		protocolo = lastAgendamento.getProtocolo();
    	}
    	protocolo += 1;
    	AgendamentoCalculo agendamentoCalculo = new AgendamentoCalculo();
    	agendamentoCalculo.setProtocolo(protocolo);
    	return agendamentoCalculoRepository.save(agendamentoCalculo);

    }
	
	public List<AgendamentoCalculo> getAllAgendamentosCalculoByInstalacao(List<UsinaDTO> instalacoes) {
        log.debug("get all AgendamentosCalculo By Instalacao");
        QAgendamentoCalculo pred = new QAgendamentoCalculo("agendamentoCalculo");
        
    	List<AgendamentoCalculo> agCalcSituacao = IterableConverter.toList(agendamentoCalculoRepository.findAll(
    			pred.situacao.notEqualsIgnoreCase(TipoAgendamento.CANCELADO.getDescricao())));
        
        List<Instalacao> listInst = new ArrayList<>();
        for (UsinaDTO instalacao : instalacoes) {
        	listInst.add(new Instalacao(instalacao.getId(),instalacao.getNome(),instalacao.getMinorVersion()));
        }
    	
    	List<AgendamentoCalculo> listAgCalc = from(pred,agCalcSituacao).
    	        where(pred.instalacoesCenarios.any().in(listInst)).
    	        fetchResults().getResults(); 

        OrderSpecifier<Integer> sortOrder1 = QAgendamentoCalculo.agendamentoCalculo.protocolo.desc();      
        return from($(qAgendamentoCalculo),listAgCalc).orderBy(sortOrder1).fetchResults().getResults();
	}
	
	public AgendamentoCalculo getAgendamentosCalculoById(String idCalculo) {
        log.debug("get AgendamentosCalculo By Id Calculo");
        QAgendamentoCalculo pred = new QAgendamentoCalculo("agendamentoCalculo");
     
        return agendamentoCalculoRepository.findOne(pred.jobId.eq(idCalculo));

	}
	
	public AgendamentoCalculo getAgendamentosCalculoByProtocolo(String protocoloStr) {
        log.debug("get AgendamentosCalculo By Id ProtocoloStr");
        QAgendamentoCalculo pred = new QAgendamentoCalculo("agendamentoCalculo");
     
        return agendamentoCalculoRepository.findOne(pred.protocoloStr.eq(protocoloStr));

	}	
	
	public List<AgendamentoCalculo> getAllAgendamentosCalculo() {
        log.debug("get all AgendamentosCalculo");
        QAgendamentoCalculo pred = new QAgendamentoCalculo("agendamentoCalculo");
        
    	return IterableConverter.toList(agendamentoCalculoRepository.findAll(pred.protocolo.desc()));
    }

	public Page<AgendamentoCalculo> getAllAgendamentosCalculoPageble(Pageable pageable) {
		log.debug("get all AgendamentosCalculo");
		QAgendamentoCalculo pred = new QAgendamentoCalculo("agendamentoCalculo");
		
		return agendamentoCalculoRepository.findAll(pred.id.isNotNull(), pageable);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<AgendamentoCalculo> getAllAgendamentosCalculo(List<UsinaDTO> instid, ZonedDateTime dtInicio, ZonedDateTime dtFim, Pageable pageable) {
    	log.debug("get AgendamentosCalculo by inst and date");
    	
    	QAgendamentoCalculo qAgend = new QAgendamentoCalculo("agendamentoCalculo");
    																	
    	List<Instalacao> instalacoes = new ArrayList<>();
    	for (UsinaDTO usinaDTO : instid) {
    		instalacoes.add(new Instalacao(usinaDTO.getId(),usinaDTO.getNome(),usinaDTO.getMinorVersion()));
		}
    	
    	List<AgendamentoCalculo> agCalcByDates = IterableConverter.toList(agendamentoCalculoRepository.findAll(
				qAgend.dataAgendamento.between(dtInicio, dtFim)));
    	
    	List<AgendamentoCalculo> listAgCalc = from(qAgend,agCalcByDates).
    	        where(qAgend.instalacoesCenarios.any().in(instalacoes)).
    	        fetchResults().getResults(); 
    	
    	return new PageImpl(listAgCalc, pageable,pageable.getOffset() + pageable.getPageSize() + 1);

    }
    
    public Page<AgendamentoCenario> getAgendamentosCenarioByDate(ZonedDateTime dtInicio, ZonedDateTime dtFim, Pageable pageable){
    	log.debug("get AgendamentosCenario by Date");
    	
    	QAgendamentoCenario qAgendamentoCenario = new QAgendamentoCenario("agendamentoCenario");
    	
    	return agendamentoCenarioRepository.findAll(
    			qAgendamentoCenario.dataAgendamento.between(dtInicio, dtFim),pageable);
    	
    }
    
    public List<AgendamentoCenario> getAllAgendamentosCenario() {
        log.debug("get all AgendamentosCenario");
        QAgendamentoCenario pred = new QAgendamentoCenario("agendamentoCenario");
        
    	return IterableConverter.toList(agendamentoCenarioRepository.findAll(pred.dataCriacao.desc()));
    }
    
    public Page<AgendamentoRetificacao> getAllAgendamentosRetificacaoByDate(ZonedDateTime dtInicio, ZonedDateTime dtFim,
			Pageable pageable) {
    	log.debug("get AgendamentoRetificacao by Date");
    	
    	QAgendamentoRetificacao qAgendamentoRetificacao = new QAgendamentoRetificacao("agendamentoRetificacao");
    	
    	return agendamentoRetificacaoRepository.findAll(
    			qAgendamentoRetificacao.dataAgendamento.between(dtInicio, dtFim), pageable);
    	
	}
    
    public List<AgendamentoRetificacao> getAllAgendamentosRetificacao() {
        log.debug("get all AgendamentosRetificacao");
        QAgendamentoRetificacao pred = new QAgendamentoRetificacao("agendamentoRetificacao");
        
    	return IterableConverter.toList(agendamentoRetificacaoRepository.findAll(pred.dataCriacao.desc()));
    }
    
	@SuppressWarnings("unchecked")
	public VerificarSituacaoInstalacaoResponse verificarSituacaoInstalacao(UsinaDTO usina, ZonedDateTime dtInicio, ZonedDateTime dtFim) {
		
		VerificarSituacaoInstalacaoCommand command = new VerificarSituacaoInstalacaoCommand();
		
		command.setDataInicio(Date.from(dtInicio.toInstant()));
    	command.setDataFim(Date.from(dtFim.toInstant()) );
    	
    	CommandMessage<VerificarSituacaoInstalacaoCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
        commandMessage.getMetadata().setAggregateId(usina.getId());
		commandMessage.getMetadata().setMajorVersion(null); // Default timeline
		commandMessage.getMetadata().setMinorVersion(usina.getMinorVersion()); // First Version
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
        commandMessage.getMetadata().setIsReplay(false);
        
        //TODO - jcardoso - Tratar exceções (Timeout, por exemplo)
        try{
        	ResultMessage<VerificarSituacaoInstalacaoResponse> response = (ResultMessage<VerificarSituacaoInstalacaoResponse>)(ResultMessage<?>)commandBus.sendAndWait(commandMessage);
        	log.debug("VerificarSituacaoInstalacaoCommand.ResultMessage : {}", response);
        	
        	 List<VerificarSituacaoInstalacaoResponse> results = response.getResults();
             if ( !results.isEmpty() ){
            	 return results.get(0);
             }        	
        } catch (RuntimeException e){
        	log.error("verificarSituacaoInstalacao",e);
        }
        
        return null;
	}
    
    private <B> HttpEntity<B> requestEntity(B body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Bearer " + tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
		return new HttpEntity<>(body, headers);
	}
    
	/**
	 * Exceção lançada caso uma timeline ou record não seja encontrado
	 */
	@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
	class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	private void atualizarSituacao(AgendamentoCalculo agendamento,TipoAgendamento tipo,String aggregateId){
		
		Instalacao qInstalacao = alias(Instalacao.class, "instalacao");
		List<Instalacao> listInstAgg = from(qInstalacao,agendamento.getInstalacoesCenarios()).
    	        where($(qInstalacao.getId()).eq(aggregateId)).fetch();
		for(Instalacao instAgg : JavaUtil.emptyIfNull(listInstAgg)){
			instAgg.setSituacao(tipo.getDescricao());
		}
	}
	
	private void atualizarResultado(AgendamentoCalculo agendamento,TipoAgendamento tipo,String aggregateId){
		
			Instalacao qInstalacao = alias(Instalacao.class, "instalacao");
			List<Instalacao> listInstAgg = from(qInstalacao,agendamento.getInstalacoesCenarios()).
	    	        where($(qInstalacao.getId()).eq(aggregateId)).fetch();
			for(Instalacao instAgg : JavaUtil.emptyIfNull(listInstAgg)){
				instAgg.setResultado(tipo.getDescricao());
			}
	}	
	
}
