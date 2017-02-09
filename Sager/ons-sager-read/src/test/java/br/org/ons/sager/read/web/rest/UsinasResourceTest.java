package br.org.ons.sager.read.web.rest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.UnidadeGeradora;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.UsinasRepository;
import br.org.ons.sager.read.service.UsinasService;

/**
 * REST controller for managing Usinas.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class UsinasResourceTest {

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
    @Inject
    private UsinasService usinasService;

    @Inject
    private UsinasRepository UsinasRepository;
    
	private MockMvc restUsinaMockMvc;

	@PostConstruct
	public void setup() {
		UsinasResource parametrizacaoResource = new UsinasResource(usinasService);
		this.restUsinaMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}
	
	@Before
	public void initTest() {

		Usinas usina1 = new Usinas();
		UsinasRepository.deleteAll();
		
		usina1.setId("idUsina01");
		usina1.setNome("NomeUsina01");
		usina1.setTipo_id("tipoUsina");
		List<Equipamento> equipamentos = null;
		usina1.setEquipamentos(equipamentos);
		usina1.setAgente(null);
		usina1.setCentroopid(null);
		usina1.setMinorVersion(null);
		
		UsinasRepository.save(usina1);
	}
	
	@Test
	public void ct010001() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String idUsina = "idUsina01";
		
		String response = restUsinaMockMvc.perform(get("/api/usinas/"+idUsina))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(); 

		System.out.println(response);
		
		assertThat(UsinasRepository.count() == 1).isTrue();

	}
	
	@Test
	public void ct010002() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String idUsina = "";
		
		String response = restUsinaMockMvc.perform(get("/api/usinas/"+idUsina))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString(); 

		System.out.println(response);
		
		assertThat(response.equals("[]")).isTrue();

	}
	
	@Test
	public void ct010003() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String idUsina = null;
		
		String response = restUsinaMockMvc.perform(get("/api/usinas/"+idUsina))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString(); 

	}
	
	
	@Test
	public void ct010004() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String idUsina = "idUsina02";
		
		String response = restUsinaMockMvc.perform(get("/api/usinas/"+idUsina))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString(); 

		System.out.println(response);
		
		assertThat(UsinasRepository.count() == 1).isTrue();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	
//	@Test
//	public void getAllUsinass() throws Exception {
//		Collection<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
//		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
//
//		Usinas content = UsinasRepository.findOne("idUsina01");
//		restUsinaMockMvc.perform(get("/api/usinas/"))
//				.andExpect(status().isOk());
//		//String content = response.andReturn().getResponse().getContentAsString();
//		//String[] arrayTeste = content.split(",");
//		//String valorID = arrayTeste[0].split(":")[1];
//		
//		//response.andDo(MockMvcResultHandlers.print());
//		System.out.println(content.getId());
//		assertThat(UsinasRepository.count() == 1).isTrue();
//		assertThat(UsinasRepository.findOne(content.getId()));
//
//	}
}
