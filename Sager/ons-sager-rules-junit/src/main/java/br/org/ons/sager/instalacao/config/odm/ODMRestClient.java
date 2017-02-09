package br.org.ons.sager.instalacao.config.odm;

import java.util.HashMap;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;

/**
 * Cliente para invocação ao IBM ODM via REST
 */
public class ODMRestClient implements RuleClient {

	private RestTemplate restTemplate;

//	@Inject
//	private ODMProperties properties;

	protected final Logger log = LoggerFactory.getLogger(getClass());

	public ODMRestClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public RuleResponse invoke(RuleRequest request) {
		try {
			return new RuleResponse(restTemplate.exchange(
					"http://172.20.249.68/DecisionService/rest/v1"
							+ request.getRulePath(),
					HttpMethod.POST, new HttpEntity(request.getInputParameters()),
					new ParameterizedTypeReference<HashMap<String, Object>>() {
					}, new HashMap<String, Object>()).getBody());
		} catch (HttpStatusCodeException e) {
			log.error("HttpStatusCodeException {} {} {}", e.getStatusCode(), e.getStatusText(),
					e.getResponseBodyAsString());
			throw e;
		}
	}
}
