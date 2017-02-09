package br.org.ons.sager.read.config.mq;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.jms.MQXAConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Configuração da conexão com o IBM WebSphere MQ
 */
@Configuration
@EnableConfigurationProperties(MQProperties.class)
@EnableJms
public class MQConfiguration {

	/**
	 * Tempo limite de espera de mensagens recebidas através de GET
	 */
	private static final long RECEIVE_TIMEOUT = 300000;

	private MQProperties properties;

	private ObjectMapper objectMapper;

	@Inject
	public MQConfiguration(MQProperties properties, ObjectMapper objectMapper) {
		super();
		this.properties = properties;
		this.objectMapper = objectMapper;
	}

	/**
	 * @return Listener para filas
	 */
	@Bean(name = "JmsQueueListenerContainerFactory")
	public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(
			DestinationResolver destinationResolver) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("5-10");
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		factory.setSessionTransacted(true);
		factory.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		factory.setPubSubDomain(false);
		factory.setDestinationResolver(destinationResolver);
		return factory;
	}

	/**
	 * @return Listener para tópicos
	 */
	@Bean(name = "JmsTopicListenerContainerFactory")
	public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(
			DestinationResolver destinationResolver) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1");
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		factory.setSessionTransacted(true);
		factory.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		factory.setPubSubDomain(true);
		factory.setDestinationResolver(destinationResolver);
		return factory;
	}

	/**
	 * @return Template para envio e recebimento de mensagens em filas
	 */
	@Bean(name = "JmsQueueTemplate")
	public JmsTemplate jmsQueueTemplate(DestinationResolver destinationResolver) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.setDestinationResolver(destinationResolver);
		return jmsTemplate;
	}

	/**
	 * @return Template para publicação e recebimento de mensagens em tópicos
	 */
	@Bean(name = "JmsTopicTemplate")
	public JmsTemplate jmsTopicTemplate(DestinationResolver destinationResolver) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setMessageConverter(new JMSTypeJackson2MessageConverter(objectMapper));
		jmsTemplate.setReceiveTimeout(RECEIVE_TIMEOUT);
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.setDestinationResolver(destinationResolver);
		return jmsTemplate;
	}

	/**
	 * @return Conexão com o WebSphere MQ
	 */
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

	@Primary
	@Bean
	@Profile({"dev", "prod"})
	public DestinationResolver destinationResolver() {
		return new DynamicDestinationResolver();
	}
	
	@Primary
	@Bean
	@Profile({"test","cucumber"})
	public DestinationResolver testDestinationResolver() {
		return new DynamicDestinationResolver() {
			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {
				return super.resolveDestinationName(session, destinationName.replace("platform", "test"), pubSubDomain);
			}
		};
	}
}
