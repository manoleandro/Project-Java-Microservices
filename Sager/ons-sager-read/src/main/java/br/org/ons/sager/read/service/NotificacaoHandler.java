package br.org.ons.sager.read.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import br.org.ons.platform.common.CommandFinishedEvent;
import br.org.ons.platform.common.CommandScheduledEvent;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.command.CalcularTaxasCommand;
import br.org.ons.sager.read.domain.Notificacao;
import br.org.ons.sager.read.repository.NotificacaoRepository;

@Component
public class NotificacaoHandler {

	private final Logger log = LoggerFactory.getLogger(EventoService.class);
	
//	Events de Calculo de taxa
	
	
	private NotificacaoRepository notificacaoRepository;
	
	private static final String MANTER_AGENDAMENTO = "manter-agendamento";
	
	
	@Inject
	public NotificacaoHandler(NotificacaoRepository notificacaoRepository) {
		super();
		this.notificacaoRepository = notificacaoRepository;
	}


	@JmsListener(destination = "platform/EventTopic/br.org.ons.platform.common.CommandScheduledEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleCommandScheduledEvent(EventMessage<CommandScheduledEvent> eventMessage,
			 @Header(name = "Authorization") String token) throws JMSException {
		log.debug("handleCommandScheduledEvent: {}", eventMessage);
		try{
			String commandName = eventMessage.getEvent().getCommandName();
			String nameClass = CalcularTaxasCommand.class.getName();
			
			if (commandName.equals(nameClass)){
				EventMetadata metadata = eventMessage.getMetadata();
				inserirNotificacao(metadata, "Cálculo agendado com sucesso!", "glyphicon-credit-card", "info", MANTER_AGENDAMENTO, Date.from(metadata.getTimelineDate().toInstant()));
			}
		}catch(Exception e){
			log.error("Erro ao Tentar Notificar o agendamento ao usuário. Causa do erro: "+e.getCause(), e);
		}
		
			
		
	}
	
	
	@JmsListener(destination = "platform/EventTopic/br.org.ons.platform.common.CommandFinishedEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleCommandFinishedEvent(EventMessage<CommandFinishedEvent> eventMessage
			) throws JMSException {
		
		try{
			log.debug("handleCommandFinishedEvent: {}", eventMessage);
							
			String commandName = eventMessage.getEvent().getCommandName();
			String nameClass = CalcularTaxasCommand.class.getName();
			if (commandName.equals(nameClass)){
				
				if ( eventMessage.getEvent().getStatus() == CommandFinishedEvent.Status.FAILED ){
					EventMetadata metadata = eventMessage.getMetadata();
					inserirNotificacao(metadata, "Cálculo finalizado com falha!", "glyphicon-check", "warning", MANTER_AGENDAMENTO, new Date());
				}
				else{
					EventMetadata metadata = eventMessage.getMetadata();
					inserirNotificacao(metadata, "Cálculo finalizado com sucesso!", "glyphicon-credit-card", "info", MANTER_AGENDAMENTO, new Date());
				}
				
			}
		}catch(Exception e){
			log.error("Erro ao notificar o cálculo ao usuário. Causa do erro: "+e.getCause(),e);
		}
	}
	

	
	
//	grava as notificações no repositório 
	
	public void inserirNotificacao(EventMetadata metadata, String comando, String status, String tipo, String protocoloTela, Date dateAgendamento){
			Notificacao notificacao = new Notificacao();
			notificacao.setUsuario(metadata.getProperties().get("principal").toString());
			String[] rolesArray = metadata.getProperties().get("authorities").toString().split(",");
			List<String> rolesList = new ArrayList<>(Arrays.asList(rolesArray));
			notificacao.setRoles(rolesList);
			if(metadata.getProperties().get("protocolo")!=null){
				notificacao.setProtocoloID(metadata.getProperties().get("protocolo").toString());
			}
			notificacao.setRoles(rolesList);		//lista de roles
			notificacao.setData(dateAgendamento);
			notificacao.setComando(comando);	//Comando é a Notificação
			notificacao.setId(metadata.getId());
			notificacao.setProtocolo(protocoloTela);	// Passar State da tela para o angular
			notificacao.setStatus(status);
			notificacao.setTipo(tipo);
			notificacao.setIdBusca(metadata.getCorrelationId());
			notificacaoRepository.save(notificacao);
	}
}
