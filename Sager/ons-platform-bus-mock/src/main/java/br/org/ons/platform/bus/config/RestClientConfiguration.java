package br.org.ons.platform.bus.config;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration for REST client template
 */
@Configuration
public class RestClientConfiguration {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper,
			RibbonClientHttpRequestFactory ribbonClientHttpRequestFactory) {
		return builder.additionalMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
				.errorHandler(new DefaultResponseErrorHandler() {
					@Override
					public void handleError(ClientHttpResponse response) throws IOException {
						// Do nothing
					}
				}).build();
	} 
	
}
