package br.org.ons.exemplo.config.mq;

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

import br.org.ons.exemplo.security.jwt.TokenProvider;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Classe utilitária que provê acesso para envio de comandos ao barramento
 */
@Component
public class CommandBus {

	private static final String COMMAND_QUEUE = "platform/CommandQueue";

	private static final String RESULT_QUEUE = "platform/ResultQueue";

	private final Logger log = LoggerFactory.getLogger(CommandBus.class);

	private TokenProvider tokenProvider;

	private JmsTemplate jmsTemplate;

	@Inject
	public CommandBus(@Named("JmsQueueTemplate") JmsTemplate jmsTemplate, TokenProvider tokenProvider) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.tokenProvider = tokenProvider;
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
			log.debug("JMSMessageID : {}", messageId);
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
	@SuppressWarnings("unchecked")
	public <C extends Command, R> ResultMessage<R> sendAndWait(CommandMessage<C> commandMessage) {
		try {
			String messageId = internalSend(commandMessage, RESULT_QUEUE);
			log.debug("JMSMessageID : {}", messageId);
			log.debug("Waiting for ResultMessage: {}", messageId);
			// Após o envio da mensagem de comando, recupera a mensagem de
			// resultado
			Message replyMessage = jmsTemplate.receiveSelected(RESULT_QUEUE, "JMSCorrelationID='" + messageId + "'");
			log.debug("Received ResultMessage: {}", replyMessage);
			if (replyMessage == null) {
				throw new OnsRuntimeException("O comando não recebeu uma resposta dentro do tempo limite");
			}
			log.debug("Received ResultMessage.body: {}", ((TextMessage) replyMessage).getText());
			// Deserializa a mensagem a partir do formato JSON
			ResultMessage<R> result = (ResultMessage<R>) jmsTemplate.getMessageConverter().fromMessage(replyMessage);
			log.debug("Converted ResultMessage: {}", result);
			return result;
		} catch (JMSException e) {
			throw new OnsRuntimeException(e);
		}
	}

	private <C extends Command> String internalSend(CommandMessage<C> commandMessage, String replyTo)
			throws JMSException {
		log.debug("Sending Command : {}", commandMessage);
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
			if (replyTo != null)
				message.get().setJMSReplyTo(
						jmsTemplate.getDestinationResolver().resolveDestinationName(session, replyTo, false));

			return message.get();
		});
		return message.get().getJMSMessageID();
	}

}