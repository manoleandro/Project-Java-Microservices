package br.org.ons.platform.bus;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.jms.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.support.converter.MessageConversionException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.bus.JMSTypeJackson2MessageConverter;

/**
 * Teste para JMSTypeJackson2MessageConverter
 */
public class JMSTypeJackson2MessageConverterTest {

	@Mock
	private Message message;
	
	@Mock
	private ObjectMapper objectMapper;
	
	private JMSTypeJackson2MessageConverter converter;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        converter = new JMSTypeJackson2MessageConverter(objectMapper);
	}
	
	@Test
	public void setTypeIdOnMessage() throws Exception {
		converter.setTypeIdOnMessage(this, message);
		
		verify(message).setJMSType(eq(getClass().getName()));
	}
	
	@Test
	public void getJavaTypeForMessage() throws Exception {
		when(message.getJMSType()).thenReturn(getClass().getName());
		
		assertThat(converter.getJavaTypeForMessage(message).getTypeName()).contains(getClass().getName());
	}
	
	@Test(expected=MessageConversionException.class)
	public void getJavaTypeForMessageNull() throws Exception {
		when(message.getJMSType()).thenReturn(null);
		
		converter.getJavaTypeForMessage(message);
	}
	
	@Test(expected=MessageConversionException.class)
	public void getJavaTypeForMessageInvalid() throws Exception {
		when(message.getJMSType()).thenReturn("lalala");
		
		converter.getJavaTypeForMessage(message);
	}
}
