package br.org.ons.sager.agendamento.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration for REST client template
 */
@Configuration
public class RestClientConfiguration {

	@Inject
	private ObjectMapper objectMapper;

	@Bean
	public RestTemplate restTemplate() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper);
		converters.add(jsonConverter);
		return new RestTemplate(converters);
	}
}
