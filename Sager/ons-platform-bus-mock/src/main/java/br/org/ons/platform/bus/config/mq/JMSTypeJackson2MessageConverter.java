package br.org.ons.platform.bus.config.mq;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.org.ons.platform.bus.dto.GenericCommandMessage;
import br.org.ons.platform.common.ResultMessage;

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

	@Override
	protected void setTypeIdOnMessage(Object object, Message message) throws JMSException {
		message.setJMSType(ResultMessage.class.getName());
	}

	@Override
	protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
		return TypeFactory.defaultInstance().constructType(GenericCommandMessage.class);
	}

}