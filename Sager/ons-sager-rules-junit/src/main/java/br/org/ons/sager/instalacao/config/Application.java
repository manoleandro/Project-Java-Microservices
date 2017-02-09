package br.org.ons.sager.instalacao.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		return builder.build();
//	}
//
//	@Bean
//	public RestTemplate restTemplate() {
//	    return new RestTemplate();
//	}
	
	@Autowired
	private RestOperations restTemplate;
}