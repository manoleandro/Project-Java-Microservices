//package br.org.ons.sager.agendamento.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import javax.annotation.PostConstruct;
//import javax.inject.Inject;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//
//import com.jayway.jsonpath.JsonPath;
//
//import br.org.ons.sager.agendamento.OnsSagerAgendamentoApp;
//import br.org.ons.sager.agendamento.repository.AgendamentoCalculoRepository;
//import br.org.ons.sager.agendamento.security.AuthoritiesConstants;
//import br.org.ons.sager.agendamento.security.jwt.TokenProvider;
//import br.org.ons.sager.agendamento.service.AgendamentoService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OnsSagerAgendamentoApp.class)
//public class AgendamentoResourceIntTest {
//
//	@Inject
//	private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//	@Inject
//	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//	private MockMvc restAgendamentoMockMvc;
//
//	@Inject
//	private AgendamentoCalculoRepository agendamentoCalculoRepository;
//	
//	@Inject
//	private AgendamentoService agendamentoService;
//	
//	@Inject
//	private AutowireCapableBeanFactory beanFactory;
//	
//	@Inject
//	private TokenProvider tokenProvider;	
//	
//
//	@Value("${onsplatform.url}")
//	private String platformEndpoint;
//	
//	private RestTemplate restTemplate = new RestTemplate();
//	
//	@PostConstruct
//	public void setup() {
//		AgendamentoResource resource = new AgendamentoResource(agendamentoService);
//		beanFactory.autowireBean(resource);
//		this.restAgendamentoMockMvc = MockMvcBuilders.standaloneSetup(resource)
//				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
//				.build();
//	}
//
//	@Before
//	public void initTest() {
////		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
//		Collection<? extends GrantedAuthority> authorities = Arrays
//				.asList(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
//		SecurityContextHolder.getContext().setAuthentication(
//				new UsernamePasswordAuthenticationToken(new User("admin", "", authorities), "", authorities));
//		agendamentoCalculoRepository.deleteAll();
//		restTemplate.exchange(platformEndpoint + "/api/schedule", HttpMethod.DELETE, requestEntity(null),String.class);
//	}
//
//
//	@Test
//	public void saveAgendamentoCalculo() throws Exception{
//		
//		String content = "{\"dataAgendamento\":\"2016-12-01T16:09:51.000Z\","
//				+ "\"idInstalacao\":\"RJUSCP\","
//				+ "\"nomeInstalacao\":\"CAMPOS\","
//				+ "\"minorVersion\":\"1\","
//				+ "\"mesInicial\":\"2016-03-01T03:00:00.000Z\","
//				+ "\"mesFinal\":\"2016-03-01T03:00:00.000Z\","
//				+ "\"situacao\":\"AGENDADO\","
//				+ "\"resultado\":\"NA\","
//				+ "\"solicitante\":\"admin\","
//				+ "\"dataCriacao\":\"2016-11-24T16:10:05.543Z\""
//				+ "}";
//		
//		String result = restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
//				.param("acao", "SALVAR")
//				.content(content)
//				.contentType(MediaType.APPLICATION_JSON_UTF8))
//				.andExpect(status().isCreated())
//				.andReturn().getResponse().getContentAsString();			
//				
//				
//				
//		String authors = JsonPath.read(result, "$.idInstalacao");
//		
//		
//		assertThat(agendamentoCalculoRepository.count() == 1); 
//		
////		assertThat(retificacoesParamRepository.count() == 1);
//
//	}
//	
//	private <B> HttpEntity<B> requestEntity(B body) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization",
//				"Bearer " + tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
//		return new HttpEntity<B>(body, headers);
//	}
//	
//}
