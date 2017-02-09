package br.org.ons.platform.config;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.bus.EventBus;
import br.org.ons.platform.security.jwt.TokenProvider;

/**
 * Configuração de teste que substitui Spring beans por mocks
 */
@Profile("test")
@Configuration
public class SpringTestConfiguration {

    private final Logger log = LoggerFactory.getLogger(SpringTestConfiguration.class);

	@Bean
	@Primary
	public CommandBus commandBus() {
		log.debug("Overriding bean commandBus");
		return Mockito.mock(CommandBus.class);
	}
	
	@Bean
	@Primary
	public EventBus eventBus() {
		log.debug("Overriding bean eventBus");
		return Mockito.mock(EventBus.class);
	}
	
	@Bean
	@Primary
	public TokenProvider tokenProvider() {
		log.debug("Overriding bean tokenProvider");
		return Mockito.mock(TokenProvider.class);
	}
}
