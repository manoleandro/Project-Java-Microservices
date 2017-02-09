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
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.sager.read.OnsSagerReadApp;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.Notificacao;
import br.org.ons.sager.read.domain.NotificacoesLidas;
import br.org.ons.sager.read.domain.QNotificacao;
import br.org.ons.sager.read.domain.QNotificacoesLidas;
import br.org.ons.sager.read.domain.Taxa;
import br.org.ons.sager.read.domain.Valores;
import br.org.ons.sager.read.repository.NotificacaoRepository;
import br.org.ons.sager.read.repository.NotificacoesLidasRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class NotificacaoResourceTest {
	
	@Inject
	NotificacaoRepository notificacaoRepository;
	
	@Inject
	NotificacoesLidasRepository notificacoesLidasRepository;
	
	QNotificacao qNotificacao = new QNotificacao("notificacao");
	
	QNotificacoesLidas qNotificacoesLidas = new QNotificacoesLidas("notificacoesLidas");

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restNotificacaoMockMvc;

	@PostConstruct
	public void setup() {
//		dispService = new DispService(dispRepository, comentarioRepository, commandBus);
		NotificacaoResource parametrizacaoResource = new NotificacaoResource(notificacaoRepository , notificacoesLidasRepository );
		this.restNotificacaoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}
	
	@SuppressWarnings("deprecation")
	@Before
	public void initTest() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		notificacaoRepository.deleteAll();
		notificacoesLidasRepository.deleteAll();
		
		Date data = new Date("2016/11/28 03:00:00");
		BigDecimal bigDecimal = new BigDecimal("12");
		
		List<String> roles = new ArrayList<>();
			roles.add(0, "ROLE_CNOS");
		
		Notificacao notificacao = new Notificacao();
			notificacao.setComando("comando01");
			notificacao.setData(data);
			notificacao.setId("id01");
			notificacao.setIdBusca("idBusca01");
			notificacao.setProtocolo("protocolo01");
			notificacao.setProtocoloID("protocolo01");
			notificacao.setRoles(roles);
			notificacao.setStatus("status01");
			notificacao.setTipo("tipo01");
			notificacao.setUsuario("usuario01");
		
		NotificacoesLidas notificacoesLidas = new NotificacoesLidas();
			notificacoesLidas.setComando("comando01");
			notificacoesLidas.setData(data);
			notificacoesLidas.setId("id01");
			notificacoesLidas.setIdBusca("idBusca01");
			notificacoesLidas.setIdLida("idLida01");
			notificacoesLidas.setProtocolo("protocolo01");
			notificacoesLidas.setProtocoloID("protocoloId01");
			notificacoesLidas.setRoles(roles);
			notificacoesLidas.setStatus("status01");
			notificacoesLidas.setTipo("tipo01");
			notificacoesLidas.setUsuario("usuario01");
			
		notificacaoRepository.save(notificacao);
		notificacoesLidasRepository.save(notificacoesLidas);
	}
	
	@Test
	public void pesquisarNotificacoesPorPermissao() throws Exception {
	
		restNotificacaoMockMvc.perform(get("/api/notificacaoByRoles/")
								.param("roles", "ROLE_CNOS")
								.param("usuario", "usuario01")
								.contentType(MediaType.APPLICATION_JSON_UTF8))
								.andExpect(status().isOk());
				
		assertThat(notificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void pesquisarNotificacoesPorPermissaoSemNotificacoesLidas() throws Exception {
	
		restNotificacaoMockMvc.perform(get("/api/notificacaoByRoles/")
								.param("roles", "ROLE_CNOS")
								.param("usuario", "usuario02")
								.contentType(MediaType.APPLICATION_JSON_UTF8))
								.andExpect(status().isOk());
				
		assertThat(notificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void marcarNotificacaoLida() throws Exception {
		
		String content = "[{\"comando\": \"string\","
						+"\"data\": \"2016-12-16T16:42:07.408Z\","
						+"\"id\": \"string\","
						+"\"idBusca\": \"string\","
						+"\"idLida\": \"string\","
						+"\"lida\": \"string\","
						+"\"protocolo\": \"string\","
						+"\"protocoloID\": \"string\","
						+"\"roles\": [\"string\"],"
						+"\"status\": \"string\","
						+"\"tipo\": \"string\","
						+"\"usuario\": \"string\"}]";
			
		restNotificacaoMockMvc.perform(post("/api/lerNotificacao")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(status().isOk());
		
		assertThat(notificacoesLidasRepository.count() == 2).isTrue();

	}
	
	@Test
	public void marcarNotificacaoErrada() throws Exception {
		
		String content = "[{teste}]";
			
		restNotificacaoMockMvc.perform(post("/api/lerNotificacao")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(status().isBadRequest());
		
		restNotificacaoMockMvc.perform(post("/api/lerNotificacao")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());
		
		assertThat(notificacoesLidasRepository.count() == 1).isTrue();

	}
	
	@Test
	public void pesquisarNotificacaoPorUsuario() throws Exception {
			
		restNotificacaoMockMvc.perform(get("/api/getNotificacoesLidasByUser")
						.param("usuario", "usuario01")
						.contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(status().isOk());
		
		assertThat(notificacoesLidasRepository.count() == 1).isTrue();

	}
	
	@Test
	public void pesquisarNotificacaoPorUsuarioInvalido() throws Exception {
			
		restNotificacaoMockMvc.perform(get("/api/getNotificacoesLidasByUser")
						.param("usuario", "null")
						.contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(status().isOk());
		
		assertThat(notificacoesLidasRepository.count() == 1).isTrue();

	}
}
