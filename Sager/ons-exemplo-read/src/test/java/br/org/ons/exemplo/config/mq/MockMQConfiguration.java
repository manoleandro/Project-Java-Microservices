package br.org.ons.exemplo.config.mq;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockrunner.jms.ConfigurationManager;
import com.mockrunner.jms.DestinationManager;
import com.mockrunner.mock.jms.MockConnectionFactory;

/**
 * Mock para emular o acesso ao WebSphere MQ
 */
@Profile("test")
@Configuration
@EnableJms
public class MockMQConfiguration {
	
	private static final long RECEIVE_TIMEOUT = 120000;

    @Inject
    private ObjectMapper objectMapper;

    @Primary
    @Bean(name = "JmsQueueListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("5-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setSessionTransacted(true);
        factory.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
        factory.setPubSubDomain(false);
        return factory;
    }

    @Primary
    @Bean(name = "JmsTopicListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory() {
    	DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    	factory.setConnectionFactory(connectionFactory());
    	factory.setConcurrency("1");
    	factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
    	factory.setSessionTransacted(true);
    	factory.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
    	factory.setPubSubDomain(true);
    	return factory;
    }

    @Primary
    @Bean(name = "JmsQueueTemplate")
    public JmsTemplate jmsQueueTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
		jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }

    @Primary
    @Bean(name = "JmsTopicTemplate")
    public JmsTemplate jmsTopicTemplate() {
    	JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    	jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
    	jmsTemplate.setPubSubDomain(true);
    	return jmsTemplate;
    }

    private ConnectionFactory connectionFactory() {
        DestinationManager destinationManager = new DestinationManager();
        destinationManager.createTopic("platform/EventTopic/br.org.ons.exemplo.common.UsinaCriadaEvent");
		return new MockConnectionFactory(destinationManager, new ConfigurationManager());
    }
}
