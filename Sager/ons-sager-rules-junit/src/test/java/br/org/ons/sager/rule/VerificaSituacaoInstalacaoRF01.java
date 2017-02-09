package br.org.ons.sager.rule;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoUsina;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.ComentarioSituacao;

import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.sager.instalacao.config.Application;
import br.org.ons.sager.instalacao.config.RestClientConfiguration;
import br.org.ons.sager.instalacao.rule.RuleClient;
import br.org.ons.sager.instalacao.rule.RuleRequest;
import br.org.ons.sager.instalacao.rule.RuleResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoRequest;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
public class VerificaSituacaoInstalacaoRF01 {

	
	@Autowired
	private RuleClient ruleClient;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	Map<String,Object>  inputParameters = null;
	
	RuleRequest  request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",
			"verificaSituacaoInstalacao", null);
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	protected void setUp(){
		
		
	}
	
		
	/**
	 * CT1001  Usina com todos os equipamentos sem suspensões
	 */
	@Test
	public void testCT1001() {
		try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			//ug.setDataDesativacao(sdf.parse("01/10/2019 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			//ug1.setDataDesativacao(sdf.parse("01/10/2019 00:00"));
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
						
			System.out.println(" CT01001 "+verificarResponse.getComentarios());
			
			//Valida se não há retorno de comentraios 
			assertEquals(0, verificarResponse.getComentarios().size());
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
	
			e.printStackTrace();
		}
		
	
	}
	

	
	
	/**
	 * CT1002 Usina com 1 equipamento com 1 suspensão antes da janela de cálculo.
	 */
	
	@Test
	public void testCT1002() {
			
		try {	
			
		
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setId("Jteste ");
			usina.setNomeCurto("Jteste ");
			usina.suspensao(periodo);
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);		
			
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			Periodo p = new Periodo();
			p.setDataInicio(sdf.parse("01/09/2016 00:00"));
			p.setDataFim(sdf.parse("11/09/2016 00:00"));
			List<Periodo> susp = new ArrayList<Periodo>();
			susp.add(p);
			ug1.setSuspensoes(susp);
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();		
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01002 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * CT1003 Usina com todos os equipamentos com 1 suspensão antes da janela de cálculo.
	 */
	@Test
	public void testCT1003() {
			
			try {	
			
		
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo p = new Periodo();
			p.setDataInicio(sdf.parse("01/09/2016 00:00"));
			p.setDataFim(sdf.parse("31/09/2016 00:00"));
			List<Periodo> susp = new ArrayList<Periodo>();
			susp.add(p);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			ug.setSuspensoes(susp);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			
			ug1.setSuspensoes(susp);
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			
			ug2.setSuspensoes(susp);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			ugs.add(ug2);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
			VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01003 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * CT1004 
	 * Usina com 1 equipamento com 1 suspensão com início antes da janela de cálculo e
	 * término dentro da janela de cálculo
	 * 
	 */
	@Test
	public void testCT1004() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("01/09/2016 00:00"));		
			susp.setDataFim(sdf.parse("15/10/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			ug.setSuspensoes(suspes);
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
		
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01004 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * CT1005
	 * Usina com todos equipamentos com 1 suspensão com início antes da janela de cálculo e término dentro da janela de cálculo
	 */
	
	@Test
	public void testCT1005() {
			
			try {	
			
		
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("01/09/2016 00:00"));		
			susp.setDataFim(sdf.parse("15/10/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug1.setSuspensoes(suspes);
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));			
			ug2.setSuspensoes(suspes);
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug); 
			ugs.add(ug1);
			ugs.add(ug2);
			
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01005 "+verificarResponse.getComentarios());
			assertEquals(3, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * CT1006 Usina com 1 equipamento com 1 suspensão dentro da janela de cálculo
	 */
	@Test
	public void testCT1006() {
			
			try {	
			
		
		inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("05/10/2016 00:00"));		
			susp.setDataFim(sdf.parse("15/10/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			ug.setSuspensoes(suspes);		
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01006 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * CT1007
	 * Usina com todos equipamentos com 1 suspensão dentro da janela de cálculo
	 */
	@Test
	public void testCT1007() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("02/10/2016 00:00"));		
			susp.setDataFim(sdf.parse("10/10/2016 00:00"));
			List<Periodo> supensoes = new ArrayList<Periodo>();
			supensoes.add(susp);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(supensoes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(supensoes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01007 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com 1 equipamento com 1 suspensão com data início dentro da janela de cálculo e data fim após a janela de cálculo.
	 */
	@Test
	public void testCT1008() {
			
		try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("05/10/2016 00:00"));		
			susp.setDataFim(sdf.parse("15/11/2016 00:00"));
			
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
						
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01008 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com todos equipamentos com 1 suspensão com data início dentro da janela de cálculo 
	 * e data fim após a janela de cálculo.
	 */
	@Test
	public void testCT1009() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("05/10/2016 00:00"));		
			susp.setDataFim(sdf.parse("15/11/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01009 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com 1 equipamento com 1 suspensão após a janela de cálculo
	 */
	@Test
	public void testCT010010() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("01/11/2016 00:00"));		
			susp.setDataFim(sdf.parse("31/11/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);	
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010010 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com todos equipamentos com 1 suspensão após a janela de cálculo
	 */
	@Test
	public void testCT10011() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo susp = new Periodo();
			susp.setDataInicio(sdf.parse("01/11/2016 00:00"));		
			susp.setDataFim(sdf.parse("31/11/2016 00:00"));
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(suspes);
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug2.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			ugs.add(ug2);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01011 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Usina com 1 equipamento com 2 suspensões dentro da janela de cálculo
	 * 
	 * 
	 */
	@Test
	public void testCT10012() {
			
			try {	
			
		
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/10/2016 00:00"));		
			susp1.setDataFim(sdf.parse("07/10/2016 00:00"));
			
			Periodo susp2 = new Periodo();
			susp2.setDataInicio(sdf.parse("10/10/2016 00:00"));		
			susp2.setDataFim(sdf.parse("15/10/2016 00:00"));
			
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			suspes.add(susp2);
			
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010012 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Usina com todos equipamentos com 2 suspensões dentro da janela de cálculo
	 * 
	 */
	@Test
	public void testCT10013() {
			
		try {	
			
		
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/10/2016 00:00"));		
			susp1.setDataFim(sdf.parse("07/10/2016 00:00"));
			
			Periodo susp2 = new Periodo();
			susp2.setDataInicio(sdf.parse("10/10/2016 00:00"));		
			susp2.setDataFim(sdf.parse("15/10/2016 00:00"));
			
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			suspes.add(susp2);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010013 "+verificarResponse.getComentarios());
			assertEquals(4, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Usina com 1 equipamento com 1 suspensão sem dataFim, e com início antes da janela de cálculo
	 */
	@Test
	public void testCT10014() {
			
		 try {				
				
			 	inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				Usina usina = new Usina();	
				usina.setDataProrrogacao(null);
				usina.setTipo(TipoUsina.UTE);
				
				Periodo susp1 = new Periodo();
				susp1.setDataInicio(sdf.parse("01/10/2015 00:00"));		
				susp1.setDataFim(null);
				
				List<Periodo> suspes = new ArrayList<Periodo>();
				suspes.add(susp1);
							
				UnidadeGeradora ug = new UnidadeGeradora();
				ug.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
				ug.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
				ug.setSuspensoes(suspes);	
												
				List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
				ugs.add(ug);			
				
				usina.setUnidadesGeradoras(ugs);
				vrequest.setInstalacao(usina);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
			    (VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
								
				int UGsuspenso = 0;
				int USSuspensa = 0;
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							UGsuspenso = UGsuspenso + 1;
						}
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							USSuspensa = USSuspensa + 1;
						}
					}
				}
				System.out.println(" CT010014 "+verificarResponse.getComentarios());		
				assertEquals(1, UGsuspenso);
				assertEquals(1, USSuspensa);
				assertEquals(false, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Usina com todos equipamentos com 1 suspensão sem dataFim com início antes da janela de cálculo
	 */
	@Test
	public void testCT10015() {
			
			try {	
			
					
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);			
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/09/2016 00:00"));		
			susp1.setDataFim(null);
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010015 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(1, USSuspensa);
			assertEquals(false, verificarResponse.isProssegue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com 1 equipamento com 1 suspensão sem dataFim com início dentro da janela de cálculo
	 */
	@Test
	public void testCT10016() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/10/2016 00:00"));		
			susp1.setDataFim(null);
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010016 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Usina com todos equipamentos com 1 suspensão sem dataFim com início dentro da janela de cálculo
	 */
	@Test
	public void testCT10017() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/10/2016 00:00"));		
			susp1.setDataFim(null);
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010017 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * CT10018
	 * Usina com 1 equipamento com 1 suspensão sem dataFim com início após a janela de cálculo
	 * 
	 */
	@Test
	public void testCT10018() {
		try {	
			
		
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/11/2016 00:00"));		
			susp1.setDataFim(null);
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);		
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug.setSuspensoes(suspes);
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
		
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010018 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com todos equipamentos com 1 suspensão sem dataFim com início após a janela de cálculo
	 */
	@Test
	public void testCT10019() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			Usina usina = new Usina();	
			usina.setDataProrrogacao(null);
			usina.setTipo(TipoUsina.UTE);
			
			Periodo susp1 = new Periodo();
			susp1.setDataInicio(sdf.parse("03/11/2016 00:00"));		
			susp1.setDataFim(null);
			List<Periodo> suspes = new ArrayList<Periodo>();
			suspes.add(susp1);
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			//ug.setDataDesativacao(sdf.parse("01/10/2019 00:00"));
			ug.setSuspensoes(suspes);
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
		//	ug1.setDataDesativacao(sdf.parse("01/10/2019 00:00"));
			ug1.setSuspensoes(suspes);
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);
			ugs.add(ug1);
			
			usina.setUnidadesGeradoras(ugs);
			vrequest.setInstalacao(usina);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int UGsuspenso = 0;
			int USSuspensa = 0;
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&& cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT010019 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	/**
	 * Interligação Internacional sem suspensões
	 */
	@Test
	public void testCT10020() {
			
			try {	
			
			
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			InterligacaoInternacional it = new InterligacaoInternacional();	
						
			
			EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
			ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ei.setSuspensoes(new ArrayList<Periodo>());
			
			it.setEquipamento(ei);		
			
			
			vrequest.setInstalacao(it);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			
			int EIsuspenso = 0;
			
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
						EIsuspenso = EIsuspenso + 1;
					}
					
				}
			}
			
			System.out.println(" CT010020 "+verificarResponse.getComentarios());
			assertEquals(0, EIsuspenso);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 1 suspensão antes da janela de cálculo.
	 */
	@Test
	public void testCT10021() {
			
			try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("01/09/2016 00:00"));
				suspe.setDataFim(sdf.parse("31/09/2016 00:00"));
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);				
				it.setEquipamento(ei);					
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010021 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Interligação Internacional com 1 suspensão com início antes da janela de cálculo e término dentro
	 */
	@Test
	public void testCT10023() {
			
			try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("01/09/2016 00:00"));
				suspe.setDataFim(sdf.parse("11/10/2016 00:00"));
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				
				ei.setSuspensoes(supensos);		
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010022 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Interligação Internacional com 1 suspensão dentro da janela de cálculo
	 */
	@Test
	public void testCT10024() {
			
			try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/10/2016 00:00"));
				suspe.setDataFim(sdf.parse("11/10/2016 00:00"));
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010023 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 1 suspensão com data início dentro da janela de cálculo 
	 * e data fim após a janela de cálculo.
	 */
	@Test
	public void testCT10025() {
			
			try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/10/2016 00:00"));
				suspe.setDataFim(sdf.parse("11/11/2016 00:00"));
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010024 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 1 suspensão após a janela de cálculo
	 */
	@Test
	public void testCT10026() {
			
			try {	
			
			
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/11/2016 00:00"));
				suspe.setDataFim(sdf.parse("11/11/2016 00:00"));
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);	
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010025 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 2 suspensões dentro da janela de cálculo
	 */
	@Test
	public void testCT10027() {
			
		try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/10/2016 00:00"));
				suspe.setDataFim(sdf.parse("11/10/2016 00:00"));
				
				Periodo suspe1 = new Periodo();
				suspe1.setDataInicio(sdf.parse("15/11/2016 00:00"));
				suspe1.setDataFim(sdf.parse("21/10/2016 00:00"));
				
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010026 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Interligação Internacional com 1 suspensão sem dataFim com início antes da janela de cálculo
	 */
	@Test
	public void testCT10028() {
			
		try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/09/2016 00:00"));
				suspe.setDataFim(null);
				
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010027 "+verificarResponse.getComentarios());
				assertEquals(1, EIsuspenso);
				assertEquals(false, verificarResponse.isProssegue());
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 1 suspensão sem dataFim com início dentro da janela de cálculo
	 */
	@Test
	public void testCT10029() {
			
		try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/10/2016 00:00"));
				suspe.setDataFim(null);
				
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010028 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interligação Internacional com 1 suspensão sem dataFim com início após a janela de cálculo
	 */
	@Test
	public void testCT10030() {
			
		try {	
			
				
				inputParameters = new HashMap<String,Object>();
				
				VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
			
				Periodo periodo = new Periodo();
				periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
				periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
				
				vrequest.setJanelaCalculo(periodo);			
				
				InterligacaoInternacional it = new InterligacaoInternacional();	
							
				
				EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
				ei.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
				ei.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
				
				Periodo suspe = new Periodo();
				suspe.setDataInicio(sdf.parse("05/11/2016 00:00"));
				suspe.setDataFim(null);
				
				List<Periodo> supensos = new ArrayList<Periodo>();
				supensos.add(suspe);
				ei.setSuspensoes(supensos);
				
				it.setEquipamento(ei);		
				
				
				vrequest.setInstalacao(it);
				vrequest.setAtividade("CalculoDisponibilidade");
				
				inputParameters.put("Vrequest",vrequest);				
				request.setInputParameters(inputParameters);
				
				RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
				
				VerificarSituacaoInstalacaoResponse verificarResponse = 
				(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
								VerificarSituacaoInstalacaoResponse.class);
				
				
				int EIsuspenso = 0;
				
				if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
					for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
						if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
								cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.SUSPENSO)) {
							EIsuspenso = EIsuspenso + 1;
						}
						
					}
				}
				
				System.out.println(" CT010030 "+verificarResponse.getComentarios());
				assertEquals(0, EIsuspenso);
				assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
