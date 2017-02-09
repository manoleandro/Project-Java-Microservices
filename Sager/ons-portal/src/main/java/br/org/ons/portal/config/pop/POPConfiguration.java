package br.org.ons.portal.config.pop;

import javax.inject.Inject;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import br.org.ons.portal.security.pop.POPClient;

/**
 * Configuração da conexão com o POP
 */
@Configuration
@EnableConfigurationProperties(POPProperties.class)
public class POPConfiguration {

	private POPProperties properties;

	@Inject
	public POPConfiguration(POPProperties properties) {
		this.properties = properties;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("br.org.ons.portal.config.pop.service");
		return marshaller;
	}

	/**
	 * @return Cliente para integração com o POP
	 */
	@Bean
	public POPClient popClient(Jaxb2Marshaller marshaller) {
		POPClient popClient = new POPClient(properties.getServiceUrl(), properties.getDomain(),
				properties.getUsername(), properties.getPassword());
		popClient.setDefaultUri(properties.getServiceUrl() + "/AuthorizationServices/POPService.svc");
		popClient.setMarshaller(marshaller);
		popClient.setUnmarshaller(marshaller);
		return popClient;
	}
}
