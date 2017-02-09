package br.org.ons.exemplo.config.mq;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Conversor de mensagens MQ/JMS, para serialização e deserialização de objetos
 * Java em JSON. Utiliza o cabeçalho JMSType para identificar o nome da classe
 * Java dos objetos
 */
public class JMSTypeJackson2MessageConverter extends MappingJackson2MessageConverter {

	public JMSTypeJackson2MessageConverter(ObjectMapper objectMapper) {
		super();
		this.setObjectMapper(objectMapper);
		this.setTargetType(MessageType.TEXT);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MappingJackson2MessageConverter#setTypeIdOnMessage(java.lang.Object, javax.jms.Message)
	 */
	@Override
	protected void setTypeIdOnMessage(Object object, Message message) throws JMSException {
		// Insere o nome da classe no cabeçalho JMSType
		message.setJMSType(object.getClass().getName());
	}

	@Override
	protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
		// Busca o nome da classe no cabeçalho JMSType para deserializar a mensagem
		String jmsType = message.getJMSType();
		if (jmsType == null) {
			throw new MessageConversionException("Could not find type id property [JMSType]");
		}
		try {
			Class<?> typeClass = ClassUtils.forName(jmsType, this.getClass().getClassLoader());
			return TypeFactory.defaultInstance().constructType(typeClass);
		} catch (Exception ex) {
			throw new MessageConversionException("Failed to resolve type id [" + jmsType + "]", ex);
		}
	}

}