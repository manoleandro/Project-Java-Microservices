package br.org.ons.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.org.ons.portal.gateway.responserewriting.SwaggerBasePathRewritingFilter;

public class GatewayConfiguration {

	private GatewayConfiguration() {
		super();
	}

	@Configuration
	public static class SwaggerBasePathRewritingConfiguration {

		@Bean
		public SwaggerBasePathRewritingFilter swaggerBasePathRewritingFilter() {
			return new SwaggerBasePathRewritingFilter();
		}
	}
}
