package br.org.ons.exemplo.rule;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.org.ons.exemplo.OnsExemploWriteApp;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.rule.RuleClient;
import br.org.ons.exemplo.rule.RuleRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploWriteApp.class)
@IntegrationTest
public class RuleClientTest {

	private static final ZonedDateTime DEFAULT_DATA_INICIO = ZonedDateTime.of(2016, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	
	@Inject
	private RuleClient ruleClient;

	@Test
	public void test() {
		RuleRequest request = new RuleRequest("ONSExemploRules", "1.0", "calcularParametros", "1.0");
		
		List<Evento> eventos = new ArrayList<>();
		eventos.add(new Evento("1", DEFAULT_DATA_INICIO, "LIG", "NOR", "-", 100.0));
		eventos.add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		eventos.add(new Evento("3", DEFAULT_DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", 100.0));
		
		request.getInputParameters().put("eventos", eventos.toArray());

		RuleResponse response = ruleClient.invoke(request);
		
		System.out.println(response.getOutputParameters().get("parametros"));
	}
}
