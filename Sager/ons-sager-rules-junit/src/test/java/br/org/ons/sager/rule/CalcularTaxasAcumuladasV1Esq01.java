package br.org.ons.sager.rule;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.sager.instalacao.config.Application;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasRequestV1;
import br.org.ons.sager.regra.parameters.CalcularTaxasAcumuladasResponseV1;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
@IntegrationTest
public class CalcularTaxasAcumuladasV1Esq01 {

	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;

	Map<String, Object> inputParameters = new HashMap<String, Object>();
	
	RuleRequest request = new RuleRequest("SagerApuracaoIndisponibilidade", "1.0", "calcularTaxasAcumuladas", "1.0");

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	protected void setUp() {

	}

	/**
	 * 
	 * EsqueletoTeste00 blablabla
	 * 
	 */
	@Test
	public void EsqueletoTeste00() throws Exception {
			
		//Carrega a CalcularTaxasAcumuladasRequestV1
		JsonNode jn = objectMapper.readTree(new File(getClass().getResource("/load/CalcularTaxasAcumuladasV1Esq01/EsqueletoTeste01-Request.json").getFile()));
		CalcularTaxasAcumuladasRequestV1 creq = objectMapper.convertValue(jn.fields().next().getValue(), CalcularTaxasAcumuladasRequestV1.class);
		
		inputParameters.put("calcularTaxasAcumuladasRequestV1", creq);
		request.setInputParameters(inputParameters);

		RuleResponse cpRuleResponse = ruleClient.invoke(request);

		CalcularTaxasAcumuladasResponseV1 cpResponse = (CalcularTaxasAcumuladasResponseV1) objectMapper.convertValue(
				cpRuleResponse.getOutputParameters().get("calcularTaxasAcumuladasResponseV1"),
				CalcularTaxasAcumuladasResponseV1.class);

		assertEquals(7, cpResponse.getTaxasAcumuladas().size());
			
	}
}
