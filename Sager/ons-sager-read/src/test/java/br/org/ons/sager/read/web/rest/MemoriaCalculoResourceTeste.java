package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jayway.jsonpath.JsonPath;
import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.taxa.Participacao;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.MCEquipamentoParametro;
import br.org.ons.sager.read.domain.QMCEquipamentoParametro;
import br.org.ons.sager.read.domain.Taxa;
import br.org.ons.sager.read.domain.UGParam;
import br.org.ons.sager.read.domain.Valores;
import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;

/**
 * REST controller for managing mcEquipamentoParametro.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class MemoriaCalculoResourceTeste {
        
    @Inject
    private MCEquipamentoParametroRepository mcEquipParamRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restCalculoMockMvc;

	@PostConstruct
	public void setup() {
//		dispService = new DispService(dispRepository, comentarioRepository, commandBus);
		MemoriaCalculoResource parametrizacaoResource = new MemoriaCalculoResource(mcEquipParamRepository);
		this.restCalculoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}     
	
	@SuppressWarnings("deprecation")
	@Before
	public void initTest() {

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
		
		mcEquipParamRepository.deleteAll();
		
		Date data = new Date("2016/11/28 03:00:00");
		
		BigDecimal denominador = new BigDecimal("12");
		
		Participacao participacao = new Participacao();
			participacao.setCodigo("cod01");
			participacao.setDenominador(denominador);
			participacao.setDescricao("desc01");
			participacao.setId("id01");
			participacao.setNumerador(denominador);
			participacao.setPotencia(denominador);
			participacao.setValor(denominador);
			participacao.setVersao("01");
			
		List<Participacao> participacoesEquipamentos = new ArrayList<>();
			participacoesEquipamentos.add(participacao);
		
		Valores valor = new Valores();
			valor.setComentario("comentario01");
			valor.setNome("nome01");
			valor.setTipo("tipo01");
			valor.setValor(denominador);
		
		List<Valores> valoresList = new ArrayList<>();
			valoresList.add(valor);
		
			
		UGParam param = new UGParam();
			param.setData(data);
			param.setIdent(10);
			param.setIdOrdenacao(1);
			param.setIdTaxa("idTaxa01");
			param.setTaxa("taxa01");
			param.setTipoTaxa("tipoTaxa01");
		
		List<UGParam> memoriaCalculo = new ArrayList<>();
			memoriaCalculo.add(param);
			
		MCEquipamentoParametro equip = new MCEquipamentoParametro();
			equip.setDataApuracao(data);
			equip.setId("id01");
			equip.setInstalacao("instalacao01");
			equip.setMemoriaCalculo(memoriaCalculo);
			equip.setTaxaMemoriaCalculo("taxaMemoriaCacl01");
			equip.setVersaoCenario(1);
			equip.setVersaoTaxa(2);
			
		mcEquipParamRepository.save(equip);

	}
	
	@Test
	public void pesquisarTodasCalculos() throws Exception {
	
		restCalculoMockMvc.perform(get("/api/mcequipamentoParametro"))
				.andExpect(status().isOk());
		
		assertThat(mcEquipParamRepository.count() == 1).isTrue();

	}
	
	@Test
	public void pesquisarCalculosPorInstalacao() throws Exception {
	
		restCalculoMockMvc.perform(post("/api/memoriacalculo")
				.param("instalacao", "instalacao01")
				.param("versaoTaxa", "1")
				.param("versaoCenario", "2")
				.param("dataApuracao", "2016-12-16T18:43:24.813Z")
				.param("taxaMemoriaCalculo", "taxaMemoriaCacl01"))
				.andExpect(status().isOk());
		
		assertThat(mcEquipParamRepository.count() == 1).isTrue();

	}

}
