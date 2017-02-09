package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.read.OnsSagerReadApp;
//import br.org.ons.sager.read.domain.EquipamentoCadastrado;
//import br.org.ons.sager.read.domain.EquipamentoParametro;
//import br.org.ons.sager.read.repository.EquipamentoCadastradoRepository;
//import br.org.ons.sager.read.repository.EquipamentoParametroRepository;
import br.org.ons.sager.read.web.rest.util.HeaderUtil;

/**
 * REST controller for managing equipamentoParametro.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class EquipamentoParametroResourceTest {
//
//    private final Logger log = LoggerFactory.getLogger(EquipamentoParametroResource.class);
//        
//    @Inject
//    private EquipamentoParametroRepository equipamentoParametroRepository;
//       
//	@Inject
//	private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//	@Inject
//	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//	
//	private MockMvc restEquipMockMvc;
//	
//	@PostConstruct
//	public void setup() {
//		EquipamentoParametroResource parametrizacaoResource = new EquipamentoParametroResource(equipamentoParametroRepository);
//		this.restEquipMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
//				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
//				.build();
//	}
//	
//	@Before
//	public void initTest() {
//		Date data = new Date("2016/11/28 03:00:00");
//		
//		equipamentoParametroRepository.deleteAll();
//		
//		List<String> taxas = new ArrayList();
//			taxas.add("01");
//			
//		BigDecimal valor = new BigDecimal(1.0);
//		
//		EquipamentoParametro equipP = new EquipamentoParametro();
//		equipP.setDataRef(data);
//		equipP.setEquipamento("equip01");
//		equipP.setId("id01");
//		equipP.setInstalacao("instalacao01");
//		equipP.setParamametro("param01");
//		equipP.setTaxas(taxas);
//		equipP.setValor(valor);
//		
//		equipamentoParametroRepository.save(equipP);
//	}
//	
//	
//	@Test
//	public void ct010001() throws Exception {
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
//		
//		EquipamentoParametro content = equipamentoParametroRepository.findOne("id01");
//
//		System.out.println(content.getId());
//		
//		restEquipMockMvc.perform(get("/api/equipamentoParametro/"))
//			.andExpect(status().isOk());
//
//		System.out.println(content.getId());
//		assertThat(equipamentoParametroRepository.count() == 1).isTrue();
//		assertThat(equipamentoParametroRepository.findOne(content.getId()));
//	}
//	
//	@Test
//	public void ct010002() throws Exception {
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
//		
//		EquipamentoParametro content = equipamentoParametroRepository.findOne("id01");
//
//		System.out.println(content.getId());
//		
//		restEquipMockMvc.perform(get("/api/equipamentoParametro/"+content.getId()))
//			.andExpect(status().isOk());
//
//		System.out.println(content.getId());
//		assertThat(equipamentoParametroRepository.count() == 1).isTrue();
//		assertThat(equipamentoParametroRepository.findOne(content.getId()));
//		
//	}
//	
//	@Test(expected=NullPointerException.class)
//	public void ct010003() throws Exception {
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
//		
//		EquipamentoParametro content = equipamentoParametroRepository.findOne("id02");
//
//		System.out.println(content.getId());
//		
//		restEquipMockMvc.perform(get("/api/equipamentoParametro/"+content.getId()))
//			.andExpect(status().isOk());
//		
//	}


}
