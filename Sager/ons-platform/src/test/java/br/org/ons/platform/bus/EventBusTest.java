package br.org.ons.platform.bus;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.security.jwt.TokenProvider;
import br.org.ons.platform.domain.model.GenericEvent;

/**
 * Teste para EventBus
 */
public class EventBusTest {

	@Mock
	private TokenProvider tokenProvider;

	@Mock
	private JmsTemplate jmsTemplate;

	@Mock
	private Session session;

	@Mock
	private MessageConverter messageConverter;

	@Mock
	private Message message;

	private EventBus eventBus;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		eventBus = new EventBus(jmsTemplate, tokenProvider);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(1, MessageCreator.class).createMessage(session);
				return null;
			}
		}).when(jmsTemplate).send(anyString(), any(MessageCreator.class));

		when(jmsTemplate.getMessageConverter()).thenReturn(messageConverter);

		when(messageConverter.toMessage(anyObject(), any(Session.class))).thenReturn(message);
	}

	@Test
	public void send() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
        SecurityContextHolder.setContext(securityContext);
		
		EventMessage<GenericEvent> eventMessage = new EventMessage<>();
		GenericEvent event = new GenericEvent();
		eventMessage.setEvent(event);

		eventBus.publish(eventMessage);

		ArgumentCaptor<MessageCreator> messageCreatorCaptor = (ArgumentCaptor<MessageCreator>) ArgumentCaptor
				.forClass(MessageCreator.class);
		verify(jmsTemplate).send(anyString(), messageCreatorCaptor.capture());
		verify(message).setStringProperty(eq("Authorization"), startsWith("Bearer "));
	}
	
	@Test
	public void sendWithUserDetails() throws Exception {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    	securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(new User("admin", "", new HashSet<>()), "admin"));
		SecurityContextHolder.setContext(securityContext);
		
		EventMessage<GenericEvent> eventMessage = new EventMessage<>();
		GenericEvent event = new GenericEvent();
		eventMessage.setEvent(event);
		
		eventBus.publish(eventMessage);
		
		ArgumentCaptor<MessageCreator> messageCreatorCaptor = (ArgumentCaptor<MessageCreator>) ArgumentCaptor
				.forClass(MessageCreator.class);
		verify(jmsTemplate).send(anyString(), messageCreatorCaptor.capture());
		verify(message).setStringProperty(eq("Authorization"), startsWith("Bearer "));
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void sendJMSException() throws Exception {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
		SecurityContextHolder.setContext(securityContext);
		
		EventMessage<GenericEvent> eventMessage = new EventMessage<>();
		GenericEvent event = new GenericEvent();
		eventMessage.setEvent(event);
		
		when(message.getJMSMessageID()).thenThrow(new JMSException("Error"));
		
		eventBus.publish(eventMessage);
	}
}
