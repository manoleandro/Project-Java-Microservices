package br.org.ons.exemplo.config.mq;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import br.org.ons.platform.common.exception.OnsRuntimeException;

@Configuration
@EnableConfigurationProperties(MQProperties.class)
@EnableJms
public class MQConfiguration {
	
	private static final long RECEIVE_TIMEOUT = 120000;

    @Inject
    private MQProperties properties;

    @Inject
    private ObjectMapper objectMapper;

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

    @Bean(name = "JmsQueueTemplate")
    public JmsTemplate jmsQueueTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
		jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }
    
    @Bean(name = "JmsTopicTemplate")
    public JmsTemplate jmsTopicTemplate() {
    	JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    	jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
    	jmsTemplate.setPubSubDomain(true);
    	return jmsTemplate;
    }

    private ConnectionFactory connectionFactory() {
        MQXAConnectionFactory factory = new MQXAConnectionFactory();
        try {
            factory.setHostName(properties.getHost());
            factory.setPort(properties.getPort());
            factory.setQueueManager(properties.getQueueManager());
            factory.setChannel(properties.getChannel());
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        } catch (JMSException e) {
            throw new OnsRuntimeException(e);
        }

        UserCredentialsConnectionFactoryAdapter adapter = new UserCredentialsConnectionFactoryAdapter();
        adapter.setTargetConnectionFactory(factory);
        adapter.setUsername(properties.getUsername());
        adapter.setPassword(properties.getPassword());

        return adapter;
    }
}
