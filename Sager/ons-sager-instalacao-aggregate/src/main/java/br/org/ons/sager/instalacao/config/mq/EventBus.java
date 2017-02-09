package br.org.ons.sager.instalacao.config.mq;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.security.jwt.TokenProvider;

/**
 * Classe utilitária que provê acesso para publicação de eventos no barramento
 */
@Component
public class EventBus {

	private static final String EVENT_TOPIC = "platform/EventTopic";

	private final Logger log = LoggerFactory.getLogger(EventBus.class);

	private JmsTemplate jmsTemplate;

	private TokenProvider tokenProvider;

	@Inject
	public EventBus(@Named("JmsTopicTemplate") JmsTemplate jmsTemplate, TokenProvider tokenProvider) {
		this.jmsTemplate = jmsTemplate;
		this.tokenProvider = tokenProvider;
	}

	/**
	 * Publica uma mensagem de evento no tópico de eventos do barramento
	 * 
	 * @param eventMessage
	 *            Mensagem de evento
	 */
	public <E extends Event> void publish(EventMessage<E> eventMessage) {
		log.debug("Publishing Event : {}", eventMessage);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		eventMessage.getMetadata().putProperty("principal", auth.getPrincipal() instanceof UserDetails
				? ((UserDetails) auth.getPrincipal()).getUsername() : auth.getPrincipal());
		eventMessage.getMetadata().putProperty("authorities",
				auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(",")));
		// TopicString contém o nome do tópico e o nome completo da classe de
		// evento
		String topicString = EVENT_TOPIC + "/" + eventMessage.getEvent().getName();
		eventMessage.getMetadata().setTopicString(topicString);
		final AtomicReference<Message> message = new AtomicReference<>();
		jmsTemplate.send(topicString, session -> {
			// Serializa a mensagem em JSON
			message.set(jmsTemplate.getMessageConverter().toMessage(eventMessage, session));
			// Insere o token de autenticação no cabeçalho RFH2 da mensagem
			message.get().setStringProperty("Authorization", "Bearer "
					+ tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
			// Indica se o evento é resultado de uma reprodutibilidade
			message.get().setBooleanProperty("isReplay", eventMessage.getMetadata().getIsReplay());
			return message.get();
		});
		try {
			log.debug("JMSMessageID : {}", message.get().getJMSMessageID());
		} catch (JMSException e) {
			throw new OnsRuntimeException(e);
		}
	}
}
