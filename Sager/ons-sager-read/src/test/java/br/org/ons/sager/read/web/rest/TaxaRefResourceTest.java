package br.org.ons.sager.read.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.domain.TaxaRef;
import br.org.ons.sager.read.repository.TaxaRefRepository;
import br.org.ons.sager.read.service.TaxaRefService;
import br.org.ons.sager.read.web.rest.util.HeaderUtil;

/**
 * REST controller for managing TaxaRef.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class TaxaRefResourceTest {
	
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
    @Inject
    private TaxaRefService taxaRefService;
    
    @Inject
    private TaxaRefRepository taxaRefRepository;
    
    private MockMvc restTaxaRefMockMvc;
    /**
     * POST  /taxa-refs : Create a new taxaRef.
     *
     * @param taxaRef the taxaRef to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxaRef, or with status 400 (Bad Request) if the taxaRef has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
	@PostConstruct
	public void setup() {
		TaxaRefResource parametrizacaoResource = new TaxaRefResource(taxaRefService);
		this.restTaxaRefMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}
	
	@Before
	public void initTest() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		taxaRefRepository.deleteAll();
		
		TaxaRef taxaRef1 = new TaxaRef();
		taxaRef1.setId("idTaxaRef1");
		taxaRef1.setUsi("TaxaRef01");

		taxaRefRepository.save(taxaRef1);
		
		TaxaRef taxaRef2 = new TaxaRef();
		taxaRef2.setId("idTaxaRef2");
		taxaRef2.setUsi("TaxaRef02");
		
		taxaRefRepository.save(taxaRef2);
	}
	
	@Test
	public void getTaxaRef() throws Exception {

		TaxaRef content = taxaRefRepository.findOne("idTaxaRef1");
			restTaxaRefMockMvc.perform(get("/api/taxa-refs/"+content.getId()))
				.andExpect(status().isOk());

		System.out.println(content.getId());
		assertThat(taxaRefRepository.count() == 2).isTrue();
		assertThat(taxaRefRepository.findOne(content.getId()));

	}
	
	
	@Test
	public void getAllTaxaRefs() throws Exception {

		TaxaRef content = taxaRefRepository.findOne("idTaxaRef1");
			restTaxaRefMockMvc.perform(get("/api/taxa-refs/"))
				.andExpect(status().isOk());

		System.out.println(content.getId());
		assertThat(taxaRefRepository.count() == 2).isTrue();
		assertThat(taxaRefRepository.findOne(content.getId()));

	}
	
	@Test
	public void ct10001() throws Exception {
		Date data = new Date("2016/11/28 03:00:00");
		BigDecimal denominador = new BigDecimal("12");
		
		Taxa taxa = new Taxa();
			taxa.setCodigo("cod01");
			taxa.setDenominador(denominador);
			taxa.setDescricao("desc01");
			taxa.setDescricaoAuxiliar("desc Auxiliar");
			taxa.setId("id01");
			taxa.setNumerador(denominador);
		
		List<Taxa> taxasAjustadas = new ArrayList<>();
			taxasAjustadas.add(taxa);
		
		
		Usina instalacao = new Usina(); 
			instalacao.setDataOutorgaImplantacao(data);
			instalacao.setNomeCurto("nomeCurto");
			instalacao.setTaxasAjustadas(taxasAjustadas);

		
		InstalacaoCadastradaEvent event = new InstalacaoCadastradaEvent();
			event.setInstalacao(instalacao);
		
		EventMetadata metadata = new EventMetadata();
		
		EventMessage<InstalacaoCadastradaEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(event);
			eventMessage.setMetadata(metadata);
		
		taxaRefService.handleInstalacaoCadastradaEvent(eventMessage);

		TaxaRef content = taxaRefRepository.findOne("idTaxaRef1");
			restTaxaRefMockMvc.perform(get("/api/taxa-refs/"))
				.andExpect(status().isOk());

		System.out.println(content.getId());
		assertThat(taxaRefRepository.count() == 2).isTrue();
		assertThat(taxaRefRepository.findOne(content.getId()));

	}
	
}
