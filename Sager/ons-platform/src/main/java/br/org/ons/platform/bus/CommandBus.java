package br.org.ons.platform.bus;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.domain.model.GenericResultMessage;
import br.org.ons.platform.security.jwt.TokenProvider;

/**
 * Classe utilitária que provê acesso para envio de comandos ao barramento
 */
@Component
public class CommandBus {

	private static final String COMMAND_QUEUE = "platform/CommandQueue";

	private static final String RESULT_QUEUE = "platform/ResultQueue";

	private static final Logger LOG = LoggerFactory.getLogger(CommandBus.class);

	private TokenProvider tokenProvider;

	private JmsTemplate jmsTemplate;

	private ObjectMapper objectMapper;

	@Inject
	public CommandBus(@Named("JmsQueueTemplate") JmsTemplate jmsTemplate, TokenProvider tokenProvider,
			ObjectMapper objectMapper) {
		this.jmsTemplate = jmsTemplate;
		this.tokenProvider = tokenProvider;
		this.objectMapper = objectMapper;
	}

	/**
	 * Envia uma mensagem de comando de forma assíncrona, sem aguardar resposta
	 * 
	 * @param commandMessage
	 *            Mensagem de comando
	 */
	public <C extends Command> void send(CommandMessage<C> commandMessage) {
		try {
			String messageId = internalSend(commandMessage, null);
			LOG.debug("JMSMessageID : {}", messageId);
		} catch (JMSException e) {
			throw new OnsRuntimeException(e);
		}
	}

	/**
	 * Envia uma mensagem de comando de forma semi-síncrona, aguardando como
	 * resposta o resultado da execução do comando
	 * 
	 * @param commandMessage
	 *            Mensagem de comando
	 * @return Mensagem com o resultado do comando
	 */
	public <C extends Command> GenericResultMessage sendAndWait(CommandMessage<C> commandMessage) {
		try {
			String messageId = internalSend(commandMessage, RESULT_QUEUE);
			LOG.debug("JMSMessageID : {}", messageId);
			LOG.debug("Waiting for ResultMessage: {}", messageId);
			// Após o envio da mensagem de comando, recupera a mensagem de
			// resultado
			Message replyMessage = jmsTemplate.receiveSelected(RESULT_QUEUE, "JMSCorrelationID='" + messageId + "'");
			LOG.debug("Received ResultMessage: {}", replyMessage);
			if (replyMessage == null) {
				throw new OnsRuntimeException("O comando não recebeu uma resposta dentro do tempo limite");
			}
			LOG.debug("Received ResultMessage.body: {}", ((TextMessage) replyMessage).getText());
			// Deserializa a mensagem a partir do formato JSON
			GenericResultMessage result = objectMapper.readValue(((TextMessage) replyMessage).getText(),
					GenericResultMessage.class);
			LOG.debug("Converted ResultMessage: {}", result);
			return result;
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}

	private <C extends Command> String internalSend(CommandMessage<C> commandMessage, String replyTo)
			throws JMSException {
		LOG.debug("Sending Command : {}", commandMessage);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		commandMessage.getMetadata().putProperty("principal", auth.getPrincipal() instanceof UserDetails
				? ((UserDetails) auth.getPrincipal()).getUsername() : auth.getPrincipal());
		commandMessage.getMetadata().putProperty("authorities",
				auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")));
		final AtomicReference<Message> message = new AtomicReference<>();
		jmsTemplate.send(COMMAND_QUEUE, session -> {
			// Serializa a mensagem em JSON
			message.set(jmsTemplate.getMessageConverter().toMessage(commandMessage, session));
			// Insere o token de autenticação no cabeçalho RFH2 da mensagem
			message.get().setStringProperty("Authorization", "Bearer "
					+ tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));

			// Especifica uma fila para recebimento da resposta
			if (replyTo != null) {
				message.get().setJMSReplyTo(
						jmsTemplate.getDestinationResolver().resolveDestinationName(session, replyTo, false));
			}

			return message.get();
		});
		return message.get().getJMSMessageID();
	}

}