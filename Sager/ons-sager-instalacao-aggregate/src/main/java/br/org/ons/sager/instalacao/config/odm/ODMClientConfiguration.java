package br.org.ons.sager.instalacao.config.odm;

import javax.inject.Inject;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.org.ons.sager.instalacao.rule.RuleClient;

/**
 * Configuração da conexão com o IBM ODM
 */
@Configuration
@EnableConfigurationProperties(ODMProperties.class)
public class ODMClientConfiguration {

	private RestTemplate restTemplate;

	private ODMProperties properties;

	@Inject
	public ODMClientConfiguration(RestTemplate restTemplate, ODMProperties properties) {
		super();
		this.restTemplate = restTemplate;
		this.properties = properties;
	}

	@Bean
	public RuleClient ruleClient() {
		return new ODMRestClient(restTemplate, properties);
	}
}
