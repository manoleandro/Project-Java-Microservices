package br.org.ons.platform.bus;


import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

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
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.security.jwt.TokenProvider;
import br.org.ons.platform.domain.model.GenericCommand;

/**
 * Teste para CommandBus
 */
public class CommandBusTest {

	@Mock
	private TokenProvider tokenProvider;

	@Mock
	private JmsTemplate jmsTemplate;

	@Mock
	private MessageConverter messageConverter;

	@Mock
	private TextMessage message;

	@Mock
	private DestinationResolver destinationResolver;
	
	private CommandBus commandBus;
	
	private ObjectMapper objectMapper;
	
	@Before
    public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

        commandBus = new CommandBus(jmsTemplate, tokenProvider,objectMapper);
        
        doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(1, MessageCreator.class).createMessage(mock(Session.class));
				return null;
			}
        }).when(jmsTemplate).send(anyString(), any(MessageCreator.class));

        when(jmsTemplate.getMessageConverter()).thenReturn(messageConverter);
        
        when(messageConverter.toMessage(anyObject(), any(Session.class))).thenReturn(message);

        when(jmsTemplate.getDestinationResolver()).thenReturn(destinationResolver);

		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", ""));
    }

	@Test
	public void send() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
        SecurityContextHolder.setContext(securityContext);
        
		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		commandBus.send(commandMessage);

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
		
		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		commandBus.send(commandMessage);
		
		ArgumentCaptor<MessageCreator> messageCreatorCaptor = (ArgumentCaptor<MessageCreator>) ArgumentCaptor
				.forClass(MessageCreator.class);
		verify(jmsTemplate).send(anyString(), messageCreatorCaptor.capture());
		verify(message).setStringProperty(eq("Authorization"), startsWith("Bearer "));
	}
	
	@Test
	public void sendAndWait() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
        SecurityContextHolder.setContext(securityContext);
        
		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		when(jmsTemplate.receiveSelected(anyString(), anyString())).thenReturn(message);
		
		commandBus.sendAndWait(commandMessage);
		
		ArgumentCaptor<MessageCreator> messageCreatorCaptor = (ArgumentCaptor<MessageCreator>) ArgumentCaptor
				.forClass(MessageCreator.class);
		verify(jmsTemplate).send(anyString(), messageCreatorCaptor.capture());
		verify(message).setJMSReplyTo(any(Destination.class));
		verify(message).setStringProperty(eq("Authorization"), startsWith("Bearer "));
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void sendAndWaitTimeout() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
        SecurityContextHolder.setContext(securityContext);
		
		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		when(jmsTemplate.receiveSelected(anyString(), anyString())).thenReturn(null);
		
		commandBus.sendAndWait(commandMessage);
	}

	@Test(expected=OnsRuntimeException.class)
	public void sendJMSException() throws Exception {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
		SecurityContextHolder.setContext(securityContext);

		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		when(message.getJMSMessageID()).thenThrow(new JMSException("Error"));

		commandBus.send(commandMessage);
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void sendAndWaitJMSException() throws Exception {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", ""));
		SecurityContextHolder.setContext(securityContext);
		
		CommandMessage<GenericCommand> commandMessage = new CommandMessage<>();
		GenericCommand command = new GenericCommand();
		commandMessage.setCommand(command);
		
		when(message.getJMSMessageID()).thenThrow(new JMSException("Error"));
		
		commandBus.sendAndWait(commandMessage);
	}
}
