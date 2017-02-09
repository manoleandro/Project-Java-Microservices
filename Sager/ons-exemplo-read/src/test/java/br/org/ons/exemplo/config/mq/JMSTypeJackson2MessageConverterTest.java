package br.org.ons.exemplo.config.mq;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.annotation.PostConstruct;
import javax.jms.Message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.exemplo.OnsExemploReadApp;

/**
 * Teste para JMSTypeJackson2MessageConverter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploReadApp.class)
@WebAppConfiguration
@IntegrationTest
public class JMSTypeJackson2MessageConverterTest {

	@Mock
	private Message message;
	
	private JMSTypeJackson2MessageConverter converter;
	
	@PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        converter = new JMSTypeJackson2MessageConverter(mock(ObjectMapper.class));
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
}
