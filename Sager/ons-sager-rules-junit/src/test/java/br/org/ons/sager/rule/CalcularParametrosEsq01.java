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
import br.org.ons.sager.regra.parameters.CalcularParametrosRequest;
import br.org.ons.sager.regra.parameters.CalcularParametrosResponse;
import br.org.ons.sager.regra.parameters.CalcularTaxasMensaisRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class CalcularParametrosEsq01 {

	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;

	Map<String, Object> inputParameters = new HashMap<String, Object>();
	
	RuleRequest request = new RuleRequest("SagerApuracaoIndisponibilidade", "1.0", "calcularParametros", null);

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	protected void setUp() {

	}

	/**
	 * 
	 * CT01001 Equipamento sem suspensão e lista de eventos que possui valores
	 * para criar as disponibilidade.
	 * 
	 */
	@Test
	public void EsqueletoTeste00() throws Exception {

		//Carrega a CalcularTaxasMensaisRequest
		JsonNode jn = objectMapper.readTree(new File(getClass().getResource("/load/CalcularParametrosEsq01/EsqueletoTeste01-Request.json").getFile()));
		CalcularParametrosRequest creq = objectMapper.convertValue(jn.fields().next().getValue(), CalcularParametrosRequest.class);

		inputParameters.put("calcularParametrosRequest", creq);
		request.setInputParameters(inputParameters);

		RuleResponse cpRuleResponse = ruleClient.invoke(request);

		CalcularParametrosResponse cpResponse = (CalcularParametrosResponse) objectMapper.convertValue(
				cpRuleResponse.getOutputParameters().get("calcularParametrosResponse"),
				CalcularParametrosResponse.class);

		assertEquals(15, cpResponse.getParametrosTaxa().size());
			
	}
}
