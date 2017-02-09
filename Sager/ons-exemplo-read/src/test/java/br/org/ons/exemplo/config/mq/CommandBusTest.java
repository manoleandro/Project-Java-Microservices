package br.org.ons.exemplo.config.mq;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.org.ons.exemplo.OnsExemploReadApp;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.security.jwt.TokenProvider;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Teste para CommandBus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class CommandBusTest {

	@Mock
	private TokenProvider tokenProvider;

	@Mock
	private JmsTemplate jmsTemplate;
	
	@Mock
	private MessageConverter messageConverter;
	
	@Mock 
	private TextMessage message;
	
	private CommandBus commandBus;
	
	@PostConstruct
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        commandBus = new CommandBus(jmsTemplate, tokenProvider);
        
        doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				invocation.getArgumentAt(1, MessageCreator.class).createMessage(mock(Session.class));
				return null;
			}
        }).when(jmsTemplate).send(anyString(), any(MessageCreator.class));

        when(jmsTemplate.getMessageConverter()).thenReturn(messageConverter);
        
        when(messageConverter.toMessage(anyObject(), any(Session.class))).thenReturn(message);

        when(jmsTemplate.getDestinationResolver()).thenReturn(mock(DestinationResolver.class));
    }

	@Test
	public void send() throws Exception {
		CommandMessage<CriarUsinaCommand> commandMessage = new CommandMessage<>();
		CriarUsinaCommand command = new CriarUsinaCommand();
		commandMessage.setCommand(command);
		
		commandBus.send(commandMessage);

		ArgumentCaptor<MessageCreator> messageCreatorCaptor = (ArgumentCaptor<MessageCreator>) ArgumentCaptor
				.forClass(MessageCreator.class);
        verify(jmsTemplate).send(anyString(), messageCreatorCaptor.capture());
        verify(message).setStringProperty(eq("Authorization"), startsWith("Bearer "));
	}
	
	@Test
	public void sendAndWait() throws Exception {
		CommandMessage<CriarUsinaCommand> commandMessage = new CommandMessage<>();
		CriarUsinaCommand command = new CriarUsinaCommand();
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
		CommandMessage<CriarUsinaCommand> commandMessage = new CommandMessage<>();
		CriarUsinaCommand command = new CriarUsinaCommand();
		commandMessage.setCommand(command);
		
		when(jmsTemplate.receiveSelected(anyString(), anyString())).thenReturn(null);
		
		commandBus.sendAndWait(commandMessage);
	}
}
