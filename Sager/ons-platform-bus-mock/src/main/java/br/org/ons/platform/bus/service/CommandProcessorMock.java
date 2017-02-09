package br.org.ons.platform.bus.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.org.ons.platform.bus.dto.GenericCommandMessage;

@RestController
public class CommandProcessorMock {

	private static final String INSTALACAO_AGGREGATE = "InstalacaoAggregateService";

	private static final String REPROCESSOR = "ReprocessorService";

	private static final String USINA_AGGREGATES = "UsinaAggregateService";
	
	private static final String INSTALACAO_AGGREGATE_ENDPOINT = "http://localhost:8086/onssagerinstalacaoaggregate/api/instalacao-aggregate";

	private static final String REPROCESSOR_ENDPOINT = "http://localhost:8081/onsplatform/api/reprocessor";

	private static final String USINA_AGGREGATES_ENDPOINT = "http://localhost:8082/onsexemplowrite/api/usina-aggregates";

	private RestTemplate restTemplate;

	private JmsTemplate jmsTemplate;

	private Map<String, String> commandServices = new HashMap<>();
	private Map<String, String> serviceEndpoints = new HashMap<>();

	@Inject
	public CommandProcessorMock(RestTemplate restTemplate, JmsTemplate jmsTemplate) {
		this.restTemplate = restTemplate;
		this.jmsTemplate = jmsTemplate;
		this.commandServices.put("br.org.ons.sager.instalacao.command.CadastrarInstalacaoCommand",
				INSTALACAO_AGGREGATE);
		this.commandServices.put("br.org.ons.sager.instalacao.command.CadastrarEquipamentoCommand",
				INSTALACAO_AGGREGATE);
		this.commandServices.put("br.org.ons.sager.instalacao.command.ApurarEventosCommand", INSTALACAO_AGGREGATE);
		this.commandServices.put("br.org.ons.sager.instalacao.command.CalcularTaxasCommand", INSTALACAO_AGGREGATE);
		this.commandServices.put("br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand",
				INSTALACAO_AGGREGATE);
		this.commandServices.put("br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand",
				INSTALACAO_AGGREGATE);

		this.commandServices.put("br.org.ons.platform.common.ReprocessCommand", REPROCESSOR);
		this.commandServices.put("br.org.ons.platform.common.ReplayCommand", REPROCESSOR);
		this.commandServices.put("br.org.ons.platform.common.CreateScenarioCommand", REPROCESSOR);
		this.commandServices.put("br.org.ons.platform.common.SetMainScenarioCommand", REPROCESSOR);
		this.commandServices.put("br.org.ons.platform.common.ArchiveScenarioCommand", REPROCESSOR);

		this.commandServices.put("br.org.ons.exemplo.common.CriarUsinaCommand", USINA_AGGREGATES);
		this.commandServices.put("br.org.ons.exemplo.common.AtualizarUsinaCommand", USINA_AGGREGATES);
		this.commandServices.put("br.org.ons.exemplo.common.ExcluirUsinaCommand", USINA_AGGREGATES);
		this.commandServices.put("br.org.ons.exemplo.common.ApurarEventosCommand", USINA_AGGREGATES);
		this.commandServices.put("br.org.ons.exemplo.common.CalcularParametrosCommand", USINA_AGGREGATES);
		this.commandServices.put("br.org.ons.exemplo.common.CalcularTaxasCommand", USINA_AGGREGATES);

		this.serviceEndpoints.put(INSTALACAO_AGGREGATE, INSTALACAO_AGGREGATE_ENDPOINT);
		this.serviceEndpoints.put(REPROCESSOR, REPROCESSOR_ENDPOINT);
		this.serviceEndpoints.put(USINA_AGGREGATES, USINA_AGGREGATES_ENDPOINT);
	}

	@JmsListener(destination = "platform/CommandQueue", containerFactory = "JmsQueueListenerContainerFactory")
	public void process(GenericCommandMessage commandMessage, @Header("Authorization") String token,
			@Header(JmsHeaders.MESSAGE_ID) String messageId,
			@Header(name = JmsHeaders.REPLY_TO, required = false) String replyTo) throws JMSException {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, token);
		String commandName = commandMessage.getCommand().getName();
		String handlerName = commandMessage.getMetadata().getHandlerName();
		
		String endpoint;
		if (handlerName == null) {
			// Busca serviço correspondente ao comando
			String service = commandServices.get(commandName);
			// Busca endpoint correspondente ao serviço
			endpoint = serviceEndpoints.get(service);
			
			// Se for InstalacaoAggregateService, seta a versão de acordo com a 614
			if (service.equals(INSTALACAO_AGGREGATE) && !commandMessage.getMetadata().getTimelineDate()
					.isBefore(ZonedDateTime.of(2014, 10, 1, 0, 0, 0, 0, ZoneId.systemDefault()))) {
				commandMessage.getMetadata().setHandlerName(service);
				commandMessage.getMetadata().setHandlerVersion("2.0");
			} else {
				commandMessage.getMetadata().setHandlerName(service);
				commandMessage.getMetadata().setHandlerVersion("1.0");
			}
		} else {
			endpoint = serviceEndpoints.get(handlerName);
		}
		
		HashMap<String, Object> result = restTemplate.exchange(
				endpoint + "/"
						+ commandName.substring(commandName.lastIndexOf('.') + 1).replaceAll("([a-z])([A-Z]+)", "$1-$2")
								.toLowerCase(),
				HttpMethod.POST, new HttpEntity<>(commandMessage, headers),
				new ParameterizedTypeReference<HashMap<String, Object>>() {
				}, new HashMap<String, Object>()).getBody();

		if (replyTo != null) {
			jmsTemplate.send(replyTo, session -> {
				Message message = jmsTemplate.getMessageConverter().toMessage(result, session);
				message.setStringProperty("Authorization", token);
				message.setJMSCorrelationID(messageId);
				return message;
			});
		}
	}

    @RequestMapping(value = "/",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> get(){
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}
