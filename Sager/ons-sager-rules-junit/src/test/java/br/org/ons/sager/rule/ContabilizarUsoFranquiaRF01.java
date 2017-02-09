package br.org.ons.sager.rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoFonteEnergetica;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.sager.instalacao.config.Application;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeRequest;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.ContabilizarUsoFranquiasRequest;
import br.org.ons.sager.regra.parameters.ContabilizarUsoFranquiasResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class ContabilizarUsoFranquiaRF01 {

	
	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;

	Map<String, Object> inputParameters = new HashMap<String, Object>();

	RuleRequest request = new RuleRequest("SagerCriticas", "1.0", "contabilizarUsoFranquias", null);

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
	public void CT01001() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			ContabilizarUsoFranquiasRequest Crequest = new ContabilizarUsoFranquiasRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("NOR");
			eM.setValorPotenciaDisponivel(100.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GUM");
			eM1.setEstadoOperativo("NOR");
			eM1.setValorPotenciaDisponivel(15.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setJanelaCalculo(periodo);;

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			ug.setTipoFonteEnergetica(TipoFonteEnergetica.UHE);
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			List<Franquia> fqs = new ArrayList<Franquia>();
			Franquia f1 = new Franquia();
			f1.setCodigo("GIM");
			f1.setIdEquipamento(ug.getId());
			f1.setPeriodoValidade(periodo);
			f1.setPeriodoVigencia(periodo);
			fqs.add(f1);
			ug.setFranquias(fqs);
			
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			Crequest.setModoAutoCorrecao(false);

			inputParameters.put("contabilizarUsoFranquiasRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			ContabilizarUsoFranquiasResponse cdResponse = (ContabilizarUsoFranquiasResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("contabilizarUsoFranquiasResponse"),
					ContabilizarUsoFranquiasResponse.class);

			assertEquals(1, cdResponse.getEquipamento().getFranquias().size());
			
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}
