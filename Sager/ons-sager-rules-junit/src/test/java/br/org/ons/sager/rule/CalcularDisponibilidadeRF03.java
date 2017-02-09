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
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.sager.instalacao.config.Application;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeRequest;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class CalcularDisponibilidadeRF03 {
	@Autowired
	private RuleClient ruleClient;

	@Autowired
	private ObjectMapper objectMapper;

	Map<String, Object> inputParameters = new HashMap<String, Object>();

	RuleRequest request = new RuleRequest("SagerApuracaoIndisponibilidade", "1.0", "calcularDisponibilidade", null);

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
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
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
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26, cdResponse.getDisponibilidades().size());
			
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if (i == 0) {
						assertThat(d.getValor().doubleValue()).isEqualTo(16.666666666666668);
					}
					if (i == 1) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 2) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 3) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 4) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 5) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 6) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 7) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 8) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 9) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 10) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 11) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 12) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 13) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 14) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 15) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 16) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 17) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 18) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 19) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 20) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 31) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 22) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 23) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 24) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 25) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 26) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 
	 * CT01002 Equipamento sem suspensão e lista de eventos que não possuem
	 * valores para criar as disponibilidades
	 * 
	 * Aqui os valores irão ser subistituido pelo valor da potencia de calculo
	 * vigente.
	 * 
	 */
	@Test
	public void CT01002() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("NOR");
			eM.setValorPotenciaDisponivel(100.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("NOR");
			eM1.setValorPotenciaDisponivel(15.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26, cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if (i == 0) {
						assertThat(d.getValor().doubleValue()).isEqualTo(16.666666666666668);
					}
					if (i == 1) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 2) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 3) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 4) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 5) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 6) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 7) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 8) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 9) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 10) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 11) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 12) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 13) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 14) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 15) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 16) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 17) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 18) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 19) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 20) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 31) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 22) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 23) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 24) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 25) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 26) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					i++;
				}
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * CT01003 Equipamento com suspensão que começa antes da data de inicio da
	 * janela de calculo, e, termina antes da data fim da janela de calculo, e
	 * lista de eventos que possuem valores para criar as disponibilidades
	 * 
	 * 
	 */
	@Test
	public void CT01003() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);


			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			
			Periodo susp = new Periodo(sdf.parse("16/02/2013 14:00"), sdf.parse("17/02/2013 15:00"));
			suspensoes.add(susp);
			ug.setSuspensoes(suspensoes);
						
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(4,cdResponse.getDisponibilidades().size());
			int i = 0;
			
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * CT01004 Equipamento com suspensão que começa antes da data de inicio da
	 * janela de calculo, e, termina antes da data fim da janela de calculo, e
	 * lista de eventos que não possuem valores para criar as disponibilidades
	 * 
	 * 
	 */
	@Test
	public void CT01004() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);


			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			
			Periodo susp = new Periodo(sdf.parse("16/02/2013 14:00"), sdf.parse("17/02/2013 15:00"));
			suspensoes.add(susp);
			ug.setSuspensoes(suspensoes);
						
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(4,cdResponse.getDisponibilidades().size());
			int i = 0;
			
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01005 Equipamento com suspensão que começa depois da data de inicio da
	 * janela de calculo, e, termina depois da data fim da janela de calculo, e
	 * lista de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01005() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
					
			
			
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("16/02/2013 21:00:00"));
			p1.setDataFim(sdf.parse("18/02/2013 10:00:00"));
			suspensoes.add(p1);
			ug.setSuspensoes(suspensoes);

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(2,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
									
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01006 Equipamento com suspensão que começa depois da data de inicio da
	 * janela de calculo, e, termina depois da data fim da janela de calculo, e
	 * lista de eventos que possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01006() {
		

			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
				ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

				EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
				eM.setClassificacaoOrigem("LIG");
				eM.setEstadoOperativo("DAU");
				eM.setValorPotenciaDisponivel(10.0);
				eM.setFicticio(false);
				lista.add(eM);

				EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
				eM1.setClassificacaoOrigem("LIG");
				eM1.setEstadoOperativo("DCO");
				eM1.setValorPotenciaDisponivel(0.0);
				eM1.setFicticio(false);
				lista.add(eM1);

				Periodo periodo = new Periodo();
				// Periodo suspensao = new Periodo();
				periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
				periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

				Crequest.setEventos(lista);
				Crequest.setPeriodo(periodo);

				UnidadeGeradora ug = new UnidadeGeradora();
				ug.setCodigoOns("COSR-S  36732/2007");
				ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
				ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
				List<Periodo> suspensoes = new ArrayList<Periodo>();
				List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
				PotenciaCalculo pt = new PotenciaCalculo();
				pt.setIdEquipamento(ug.getId());
				pt.setValor(200.0);
				pt.setVigencia(periodo);
				pts.add(pt);
				ug.setPotenciasCalculo(pts);
						
				
				
				Periodo p1 = new Periodo();
				p1.setDataInicio(sdf.parse("16/02/2013 21:00:00"));
				p1.setDataFim(sdf.parse("18/02/2013 10:00:00"));
				suspensoes.add(p1);
				ug.setSuspensoes(suspensoes);

				ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

				Crequest.setEquipamento(ug);
				ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
				disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

				Crequest.setTipoDisponibilidades(disponibilidades);

				inputParameters.put("calcularDisponibilidadeRequest", Crequest);
				request.setInputParameters(inputParameters);

				RuleResponse cdRuleResponse = ruleClient.invoke(request);

				CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
						cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
						CalcularDisponibilidadeResponse.class);

				assertEquals(2,cdResponse.getDisponibilidades().size());
				int i = 0;
				if (cdResponse.getDisponibilidades() != null) {
					for (Disponibilidade d : cdResponse.getDisponibilidades()) {
						if(i == 0){
							assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
						}
						if(i == 1){
							assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
						}
										
						i++;
					}
				}


		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01007 Equipamento com suspensão que começa depois da data de fim da
	 * janela de calculo, e, termina depois da data fim da janela de calculo, e
	 * lista de eventos que possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01007() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);
			

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("01/03/2015 09:00:00"));
			p1.setDataFim(sdf.parse("01/02/2016 11:00:00"));
			suspensoes.add(p1);

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 20){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 31){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 22){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 23){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 24){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 25){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 26){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01008 Equipamento com suspensão que começa depois da data de fim da
	 * janela de calculo, e, termina depois da data fim da janela de calculo, e
	 * lista de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01008() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);
			

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("01/03/2015 09:00:00"));
			p1.setDataFim(sdf.parse("01/02/2016 11:00:00"));
			suspensoes.add(p1);

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 20){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 31){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 22){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 23){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 24){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 25){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 26){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01009 Equipamento com suspensão que começa e termina dentro da janela
	 * de calculo, e lista de eventos que possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01009() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("16/02/2013 20:00"));
			p1.setDataFim(sdf.parse("17/02/2013 18:00:00"));
			suspensoes.add(p1);
			ug.setSuspensoes(suspensoes);
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);
			assertEquals(2,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
							
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01010 Equipamento com suspensão que começa e  termina dentro da janela
	 * de calculo, e lista
	 * de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01010() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("16/02/2013 20:00"));
			p1.setDataFim(sdf.parse("17/02/2013 18:00:00"));
			suspensoes.add(p1);
			ug.setSuspensoes(suspensoes);
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);
			assertEquals(2,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
							
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	
	
	
	
	/**
	 * CT01011 Equipamento com 2 suspensões  que começam antes da data de inicio
	 * da janela de calculo, e, termina antes da data fim da janela de calculo,
	 * e lista de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01011() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("09/02/2013 17:00"));
			p1.setDataFim(sdf.parse("16/02/2013 20:00"));			
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("08/02/2013 09:00"));
			p2.setDataFim(sdf.parse("17/02/2013 00:00"));
			
			suspensoes.add(p1);
			ug.setSuspensoes(suspensoes);
			
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(24,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
						
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
	
	/**
	 * CT01012 Equipamento com 2 suspensões  que começam antes da data de inicio
	 * da janela de calculo, e, termina antes da data fim da janela de calculo,
	 * e lista de eventos que possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01012() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("09/02/2013 17:00:00"));
			p1.setDataFim(sdf.parse("16/02/2013 20:00:00"));			
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("08/02/2013 09:00"));
			p2.setDataFim(sdf.parse("17/02/2013 00:00"));
			suspensoes.add(p1);
			suspensoes.add(p2);
			ug.setSuspensoes(suspensoes);
			
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(19,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 1){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
						
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	
	/**
	 * CT01013 Equipamento com 2 suspensões  que começam depois da data de
	 * inicio da janela de calculo, e, termina depois da data fim da janela de
	 * calculo, e lista de eventos que não possuem valores para criar as
	 * disponibilidades
	 */
	@Test
	public void CT01013() {
		
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("16/02/2013 21:00"));
			p1.setDataFim(sdf.parse("19/02/2013 20:00"));			
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("16/02/2013 20:00"));
			p2.setDataFim(sdf.parse("20/02/2013 00:00"));
			
			suspensoes.add(p1);
			ug.setSuspensoes(suspensoes);
			
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(2,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					
					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01014 Equipamento com 2 suspensões  que começam depois da data de
	 * inicio da janela de calculo, e, termina depois da data fim da janela de
	 * calculo, e lista de eventos que possuem valores para criar as
	 * disponibilidades
	 */
	@Test
	public void CT01014() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			
			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			ug.setSuspensoes(new ArrayList<Periodo>());
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("16/02/2013 21:00"));
			p1.setDataFim(sdf.parse("19/02/2013 20:00"));			
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("16/02/2013 20:00"));
			p2.setDataFim(sdf.parse("20/02/2013 00:00"));
			
			suspensoes.add(p1);
			suspensoes.add(p2);
			ug.setSuspensoes(suspensoes);
			
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(1,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					
					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01015 Equipamento com 2 suspensões  que começam depois da data de fim
	 * da janela de calculo, e, termina depois da data fim da janela de calculo,
	 * e lista de eventos que possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01015() {
		try{
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
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
		Crequest.setPeriodo(periodo);

		UnidadeGeradora ug = new UnidadeGeradora();
		ug.setCodigoOns("COSR-S  36732/2007");
		ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
		ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
		
		List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
		PotenciaCalculo pt = new PotenciaCalculo();
		pt.setIdEquipamento(ug.getId());
		pt.setValor(200.0);
		pt.setVigencia(periodo);
		pts.add(pt);
		ug.setPotenciasCalculo(pts);
		Periodo p1 = new Periodo();
		p1.setDataInicio(sdf.parse("18/02/2013 21:00"));
		p1.setDataFim(sdf.parse("19/02/2013 20:00"));			
		Periodo p2 = new Periodo();
		p2.setDataInicio(sdf.parse("18/02/2013 20:00"));
		p2.setDataFim(sdf.parse("20/02/2013 00:00"));
		List<Periodo> suspensoes = new ArrayList<Periodo>();
		suspensoes.add(p1);
		suspensoes.add(p2);
		ug.setSuspensoes(suspensoes);
		ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

		Crequest.setEquipamento(ug);
		ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
		disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

		Crequest.setTipoDisponibilidades(disponibilidades);

		inputParameters.put("calcularDisponibilidadeRequest", Crequest);
		request.setInputParameters(inputParameters);

		RuleResponse cdRuleResponse = ruleClient.invoke(request);

		CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
				cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
				CalcularDisponibilidadeResponse.class);

		assertEquals(26, cdResponse.getDisponibilidades().size());
		
		int i = 0;
		if (cdResponse.getDisponibilidades() != null) {
			for (Disponibilidade d : cdResponse.getDisponibilidades()) {
				if (i == 0) {
					assertThat(d.getValor().doubleValue()).isEqualTo(16.666666666666668);
				}
				if (i == 1) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 2) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 3) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 4) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 5) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 6) {
					assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
				}
				if (i == 7) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 8) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 9) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 10) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 11) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 12) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 13) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 14) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 15) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 16) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 17) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 18) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 19) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 20) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 31) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 22) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 23) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 24) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 25) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				if (i == 26) {
					assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
				}
				i++;
			}
		}

	} catch (ParseException pe) {
		pe.printStackTrace();
	}
	}

	/**
	 * CT01016 Equipamento com 2 suspensões  que começam depois da data de fim
	 * da janela de calculo, e, termina depois da data fim da janela de calculo,
	 * e lista de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01016() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("NOR");
			eM.setValorPotenciaDisponivel(100.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("NOR");
			eM1.setValorPotenciaDisponivel(15.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("18/02/2013 21:00"));
			p1.setDataFim(sdf.parse("19/02/2013 20:00"));			
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("18/02/2013 20:00"));
			p2.setDataFim(sdf.parse("20/02/2013 00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			suspensoes.add(p1);
			suspensoes.add(p2);
			ug.setSuspensoes(suspensoes);

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26, cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if (i == 0) {
						assertThat(d.getValor().doubleValue()).isEqualTo(16.666666666666668);
					}
					if (i == 1) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 2) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 3) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 4) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 5) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 6) {
						assertThat(d.getValor().doubleValue()).isEqualTo(100.0);
					}
					if (i == 7) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 8) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 9) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 10) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 11) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 12) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 13) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 14) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 15) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 16) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 17) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 18) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 19) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 20) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 31) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 22) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 23) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 24) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 25) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					if (i == 26) {
						assertThat(d.getValor().doubleValue()).isEqualTo(15.0);
					}
					i++;
				}
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * 
	 * CT01017 Equipamento com 2 suspensões  que começam e termina dentro da
	 * janela de calculo, e lista de eventos que possuem valores para criar as disponibilidades
	 * 
	 */
	@Test
	public void CT01017() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("LIG");
			eM.setEstadoOperativo("DAU");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("LIG");
			eM1.setEstadoOperativo("DCO");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);
		

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("18/02/2013 23:00"));
			p1.setDataFim(sdf.parse("19/02/2016 03:00"));
			suspensoes.add(p1);
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("18/02/2013 05:00"));
			p2.setDataFim(sdf.parse("19/02/2013 10:00"));
			suspensoes.add(p2);
			ug.setSuspensoes(suspensoes);
			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);
			assertEquals(26,cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 20){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 31){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 22){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 23){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 24){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 25){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 26){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}					
					i++;
				}
			}

		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/**
	 * CT01018 Equipamento com 2 suspensões  que começam e  termina dentro da
	 * janela de calculo, e, termina depois da data fim da janela de calculo, e
	 * lista de eventos que não possuem valores para criar as disponibilidades
	 */
	@Test
	public void CT01018() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			CalcularDisponibilidadeRequest Crequest = new CalcularDisponibilidadeRequest();
			ArrayList<EventoMudancaEstadoOperativo> lista = new ArrayList<EventoMudancaEstadoOperativo>();

			EventoMudancaEstadoOperativo eM = new EventoMudancaEstadoOperativo("1", sdf.parse("16/02/2013 19:50"));
			eM.setClassificacaoOrigem("GOU");
			eM.setEstadoOperativo("NOR");
			eM.setValorPotenciaDisponivel(10.0);
			eM.setFicticio(false);
			lista.add(eM);

			EventoMudancaEstadoOperativo eM1 = new EventoMudancaEstadoOperativo("2", sdf.parse("17/02/2013 01:00"));
			eM1.setClassificacaoOrigem("GOU");
			eM1.setEstadoOperativo("NOR");
			eM1.setValorPotenciaDisponivel(0.0);
			eM1.setFicticio(false);
			lista.add(eM1);

			Periodo periodo = new Periodo();
			// Periodo suspensao = new Periodo();
			periodo.setDataInicio(sdf.parse("16/02/2013 19:00"));
			periodo.setDataFim(sdf.parse("17/02/2013 19:00"));

			Crequest.setEventos(lista);
			Crequest.setPeriodo(periodo);

			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setCodigoOns("COSR-S  36732/2007");
			ug.setId("UG   18 MW U.CHARQUEADAS             3 RS");
			ug.setDataDesativacao(sdf.parse("14/06/2007 00:00:00"));
			
			List<PotenciaCalculo> pts = new ArrayList<PotenciaCalculo>();
			PotenciaCalculo pt = new PotenciaCalculo();
			pt.setIdEquipamento(ug.getId());
			pt.setValor(200.0);
			pt.setVigencia(periodo);
			pts.add(pt);
			ug.setPotenciasCalculo(pts);
			List<Periodo> suspensoes = new ArrayList<Periodo>();
			Periodo p1 = new Periodo();
			p1.setDataInicio(sdf.parse("18/02/2013 23:00"));
			p1.setDataFim(sdf.parse("19/02/2016 03:00"));
			suspensoes.add(p1);
			Periodo p2 = new Periodo();
			p2.setDataInicio(sdf.parse("18/02/2013 05:00"));
			p2.setDataFim(sdf.parse("19/02/2013 10:00"));
			suspensoes.add(p2);
			ug.setSuspensoes(suspensoes);

			ug.setDataImplantacao(sdf.parse("01/02/2000 00:00:00"));

			Crequest.setEquipamento(ug);
			ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade> disponibilidades = new ArrayList<br.org.ons.geracao.evento.TipoDisponibilidade>();
			disponibilidades.add(br.org.ons.geracao.evento.TipoDisponibilidade.OPERACIONAL);

			Crequest.setTipoDisponibilidades(disponibilidades);

			inputParameters.put("calcularDisponibilidadeRequest", Crequest);
			request.setInputParameters(inputParameters);

			RuleResponse cdRuleResponse = ruleClient.invoke(request);

			CalcularDisponibilidadeResponse cdResponse = (CalcularDisponibilidadeResponse) objectMapper.convertValue(
					cdRuleResponse.getOutputParameters().get("calcularDisponibilidadeResponse"),
					CalcularDisponibilidadeResponse.class);

			assertEquals(26, cdResponse.getDisponibilidades().size());
			int i = 0;
			if (cdResponse.getDisponibilidades() != null) {
				for (Disponibilidade d : cdResponse.getDisponibilidades()) {
					if(i == 0){
						assertThat(d.getValor().doubleValue()).isEqualTo(1.6666666666666667);
					}
					if(i == 2){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 3){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 4){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 5){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 6){
						assertThat(d.getValor().doubleValue()).isEqualTo(10.0);
					}
					if(i == 7){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 8){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 9){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 10){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 11){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 12){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 13){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 14){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 15){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 16){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 17){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 18){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 19){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 20){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 31){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 22){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 23){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 24){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 25){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}
					if(i == 26){
						assertThat(d.getValor().doubleValue()).isEqualTo(0.0);
					}					
					i++;
				}
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}
