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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoUsina;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.TipoComentario;
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
public class VerificaSituacaoInstalacaoRF02 {

	
	@Autowired
	private RuleClient ruleClient;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	Map<String,Object>  inputParameters = null;
	
	RuleRequest request   = new  RuleRequest("verificaSituacaoInstalacao", "1.0",
			"verificaSituacaoInstalacao", null);
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	protected void setUp(){
		
		
	}
	
		
	/**
	 * CT01001  Usina com todos equipamento destivados antes da janela de cálculo.
	 */
	@Test
	public void testCT01001() {
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
			ug.setDataDesativacao(sdf.parse("01/09/2016 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2016 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2016 00:00"));
			ug1.setDataDesativacao(sdf.parse("01/09/2016 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			
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
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01001 "+verificarResponse.getComentarios());
			assertEquals(2, UGsuspenso);
			assertEquals(1, USSuspensa);
			assertEquals(false, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	

	
	
	/**
	 * CT01002 Usina com 1 equipamento destivado antes da janela de cálculo.
	 */
	
	@Test
	public void testCT01002() {
			
		try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",	"verificaSituacaoInstalacao", null);
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
			
			
			
			UnidadeGeradora ug = new UnidadeGeradora();
			ug.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug.setDataDesativacao(sdf.parse("01/09/2016 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			
			
			
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();		
			ugs.add(ug1);
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
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01002 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * CT01003 Usina com 1 equipamento destivado depois da janela de cálculo.
	 */
	@Test
	public void testCT01003() {
			
			try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",	"verificaSituacaoInstalacao", null);
			
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
			ug.setDataDesativacao(sdf.parse("01/11/2016 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
		
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug2.setSuspensoes(new ArrayList<Periodo>());
			
			
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
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
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
	 * Usina com todos equipamento destivados depois da janela de cálculo.
	 * 
	 */
	@Test
	public void testCT01004() {
			
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
			ug.setDataDesativacao(sdf.parse("01/11/2016 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setDataDesativacao(sdf.parse("01/11/2016 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			
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
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01004 "+verificarResponse.getComentarios());
			assertEquals(0, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Usina com 1 equipamento destivado dentro da janela de cálculo.
	 */
	
	@Test
	public void testCT01005() {
			
			try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",	"verificaSituacaoInstalacao", null);
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
			ug.setDataDesativacao(sdf.parse("06/10/2010 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));			
			ug2.setSuspensoes(new ArrayList<Periodo>());
			
			
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
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.UNIDADE_GERADORA)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01005 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Usina com todos equipamento destivados dentro da janela de cálculo.
	 */
	@Test
	public void testCT01006() {
			
			try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",	"verificaSituacaoInstalacao", null);
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
			ug.setDataDesativacao(sdf.parse("06/10/2016 00:00"));
			ug.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug1 = new UnidadeGeradora();
			ug1.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug1.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug1.setDataDesativacao(sdf.parse("06/10/2016 00:00"));
			ug1.setSuspensoes(new ArrayList<Periodo>());
			
			UnidadeGeradora ug2 = new UnidadeGeradora();
			ug2.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ug2.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ug2.setDataDesativacao(sdf.parse("06/10/2016 00:00"));
			ug2.setSuspensoes(new ArrayList<Periodo>());
						
			List<UnidadeGeradora> ugs = new ArrayList<UnidadeGeradora>();
			ugs.add(ug);ugs.add(ug1);ugs.add(ug2);
						
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
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.USINA)&&
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						USSuspensa = USSuspensa + 1;
					}
				}
			}
			
			System.out.println(" CT01006 "+verificarResponse.getComentarios());
			
			assertEquals(3, UGsuspenso);
			assertEquals(0, USSuspensa);
			assertEquals(true, verificarResponse.isProssegue());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Instalacação Internacional destivada antes da janela de cálculo.
	 */
	@Test
	public void testCT01007() {
			
			try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",	"verificaSituacaoInstalacao", null);
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			InterligacaoInternacional it = new InterligacaoInternacional();	
			
			
			EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
			ei.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ei.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ei.setDataDesativacao(sdf.parse("01/09/2016 00:00"));
			it.setEquipamento(ei);
			vrequest.setInstalacao(it);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
		
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					
				}
			}
			
			System.out.println(" CT01007 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(false, verificarResponse.isProssegue());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Instalacação Internacional destivada depois da janela de cálculo.
	 *
	 */
	@Test
	public void testCT01008() {
			
		try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",
					"verificaSituacaoInstalacao", null);
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			InterligacaoInternacional it = new InterligacaoInternacional();	
			
			
			EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
			ei.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ei.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ei.setDataDesativacao(sdf.parse("01/11/2016 00:00"));
			it.setEquipamento(ei);
			vrequest.setInstalacao(it);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
		
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					
				}
			}
			
			System.out.println(" CT01008 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Instalacação Internacional destivada dentro da janela de cálculo.
	 */
	@Test
	public void testCT01009() {
			
			try {	
			
			request = new  RuleRequest("verificaSituacaoInstalacao", "1.0",
					"verificaSituacaoInstalacao", null);
			inputParameters = new HashMap<String,Object>();
			
			VerificarSituacaoInstalacaoRequest vrequest = new VerificarSituacaoInstalacaoRequest();
		
			Periodo periodo = new Periodo();
			periodo.setDataInicio(sdf.parse("01/10/2016 00:00"));		
			periodo.setDataFim(sdf.parse("31/10/2016 00:00"));
			
			vrequest.setJanelaCalculo(periodo);			
			
			InterligacaoInternacional it = new InterligacaoInternacional();	
			
			
			EquipamentoInterligacaoInternacional ei = new EquipamentoInterligacaoInternacional();
			ei.setDataImplantacao(sdf.parse("01/10/2010 00:00"));
			ei.setDataEventoEOC(sdf.parse("01/10/2010 00:00"));
			ei.setDataDesativacao(sdf.parse("05/10/2016 00:00"));
			
			
			it.setEquipamento(ei);
			vrequest.setInstalacao(it);
			vrequest.setAtividade("CalculoDisponibilidade");
			
			inputParameters.put("Vrequest",vrequest);				
			request.setInputParameters(inputParameters);
			
			RuleResponse verificarRuleResponse  = ruleClient.invoke(request);
			
			VerificarSituacaoInstalacaoResponse verificarResponse = 
			(VerificarSituacaoInstalacaoResponse) objectMapper.convertValue(verificarRuleResponse.getOutputParameters().get("Vresponse"),
							VerificarSituacaoInstalacaoResponse.class);
			
			int UGsuspenso = 0;
		
			if( verificarResponse.getComentarios() != null && verificarResponse.getComentarios().size() > 0 ){
				for (ComentarioSituacao cs : verificarResponse.getComentarios()) {
					if ( cs.getTipoObjeto().equals(ComentarioSituacao.TipoObjeto.INTERLIGACAO_INTERNACIONAL)&& 
							cs.getStatusObjeto().equals(ComentarioSituacao.StatusObjeto.DESATIVADO)) {
						UGsuspenso = UGsuspenso + 1;
					}
					
				}
			}
			
			System.out.println(" CT01009 "+verificarResponse.getComentarios());
			assertEquals(1, UGsuspenso);
			assertEquals(true, verificarResponse.isProssegue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
