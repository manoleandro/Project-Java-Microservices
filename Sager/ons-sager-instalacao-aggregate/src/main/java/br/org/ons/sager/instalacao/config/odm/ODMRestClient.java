package br.org.ons.sager.instalacao.config.odm;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;

/**
 * Cliente para invocação ao IBM ODM via REST
 */
public class ODMRestClient implements RuleClient {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;

	private ODMProperties properties;

	public ODMRestClient(RestTemplate restTemplate, ODMProperties properties) {
		super();
		this.restTemplate = restTemplate;
		this.properties = properties;
	}

	@Override
	public RuleResponse invoke(RuleRequest request) {
		try {
			return new RuleResponse(restTemplate.exchange(
					"http://" + properties.getHost() + ":" + properties.getPort() + "/DecisionService/rest/v1"
							+ request.getRulePath(),
					HttpMethod.POST, new HttpEntity<>(request.getInputParameters()),
					new ParameterizedTypeReference<HashMap<String, Object>>() {
					}, new HashMap<String, Object>()).getBody());
		} catch (HttpStatusCodeException e) {
			log.error("HttpStatusCodeException {} {} {}", e.getStatusCode(), e.getStatusText(),
					e.getResponseBodyAsString());
			throw new OnsRuntimeException("Erro ao chamar regra");
			
		}
	}
}
