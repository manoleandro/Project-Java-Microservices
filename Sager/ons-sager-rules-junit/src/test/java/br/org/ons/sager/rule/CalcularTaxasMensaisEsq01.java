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
import br.org.ons.sager.regra.parameters.CalcularTaxasMensaisRequest;
import br.org.ons.sager.regra.parameters.CalcularTaxasMensaisResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
@IntegrationTest
public class CalcularTaxasMensaisEsq01 {

	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;

	Map<String, Object> inputParameters = new HashMap<String, Object>();
	
	RuleRequest request = new RuleRequest("SagerApuracaoIndisponibilidade", "1.0", "calcularTaxasMensais", null);

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
			
		//Carrega a CalcularTaxasMensaisRequest
		JsonNode jn = objectMapper.readTree(new File(getClass().getResource("/load/CalcularTaxasMensaisEsq01/EsqueletoTeste01-Request.json").getFile()));
		CalcularTaxasMensaisRequest creq = objectMapper.convertValue(jn.fields().next().getValue(), CalcularTaxasMensaisRequest.class);
		
		inputParameters.put("calcularTaxasMensaisRequest", creq);
		request.setInputParameters(inputParameters);

		RuleResponse cpRuleResponse = ruleClient.invoke(request);

		CalcularTaxasMensaisResponse cpResponse = (CalcularTaxasMensaisResponse) objectMapper.convertValue(
				cpRuleResponse.getOutputParameters().get("calcularTaxasMensaisResponse"),
				CalcularTaxasMensaisResponse.class);

		assertEquals(6, cpResponse.getTaxasMensais().size());
			
	}
}
