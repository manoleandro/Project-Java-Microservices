package br.org.ons.sager.agendamento.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import br.org.ons.sager.agendamento.OnsSagerAgendamentoApp;
import br.org.ons.sager.agendamento.domain.AgendamentoCalculo;
import br.org.ons.sager.agendamento.domain.AgendamentoCenario;
import br.org.ons.sager.agendamento.domain.AgendamentoRetificacao;
import br.org.ons.sager.agendamento.repository.AgendamentoCalculoRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoCenarioRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoRetificacaoRepository;
import br.org.ons.sager.agendamento.service.AgendamentoService;

/**
 * API REST para as telas e diretivas Manter Agendamento
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerAgendamentoApp.class)
public class AgendamentoResourceTest {
	@Inject
	private AgendamentoService agendamentoService;
	
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
	
	@Inject
	private AgendamentoCalculoRepository agendamentoCalculoRepository;
	
	@Inject
	private AgendamentoCenarioRepository agendamentoCenarioRepository;
	
	@Inject
	private AgendamentoRetificacaoRepository agendamentoRetificacaoRepository;

	private MockMvc restAgendamentoMockMvc;
	
	@PostConstruct
	public void setup() {
//		dispService = new DispService(dispRepository, comentarioRepository, commandBus);
		AgendamentoResource parametrizacaoResource = new AgendamentoResource(agendamentoService);
		this.restAgendamentoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	@SuppressWarnings("deprecation")
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
		agendamentoCalculoRepository.deleteAll();
		agendamentoCenarioRepository.deleteAll();
		agendamentoRetificacaoRepository.deleteAll();

		ZonedDateTime dataAgendamento = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
		
		AgendamentoCalculo agendamentoCalc = new AgendamentoCalculo();
			agendamentoCalc.setAnoCriacao(2001);
			agendamentoCalc.setDataAgendamento(dataAgendamento);
			agendamentoCalc.setDataCriacao(dataAgendamento);
			agendamentoCalc.setDataTermino(dataAgendamento);
			agendamentoCalc.setId("id01");
//			agendamentoCalc.setIdInstalacao("idInstalação01");
			agendamentoCalc.setJobId("idJob01");
			agendamentoCalc.setMesFinal(dataAgendamento);
			agendamentoCalc.setMesInicial(dataAgendamento);
//			agendamentoCalc.setMinorVersion(1);
//			agendamentoCalc.setNomeInstalacao("nomInstalacao01");
			agendamentoCalc.setProtocolo(10);
			agendamentoCalc.setProtocoloStr("protocolo01");
			agendamentoCalc.setResultado("resultado01");
			agendamentoCalc.setSituacao("situacao01");
			agendamentoCalc.setSolicitante("solicitante01");
			
		AgendamentoCenario agendamentoCena = new AgendamentoCenario();
			agendamentoCena.setDataAgendamento(dataAgendamento);
			agendamentoCena.setDataCriacao(dataAgendamento);
			agendamentoCena.setDataTermino(dataAgendamento);
			agendamentoCena.setId("id01");
			agendamentoCena.setIdCenario("idCenario01");
//			agendamentoCena.setIdInstalacao("idInstalacao01");
			agendamentoCena.setJustificativa("justificativa01");
			agendamentoCena.setNomeCenario("nomeCenario01");
//			agendamentoCena.setIdInstalacao("idInstalacao01");
			agendamentoCena.setJustificativa("justificativa01");
			agendamentoCena.setNomeCenario("nomeCenario01");
//			agendamentoCena.setNomeInstalacao("nomeCenario01");
//			agendamentoCena.setNomeInstalacao("nomeInstalacao01");
			agendamentoCena.setResultado("resultado01");
			agendamentoCena.setSituacao("situacao01");
			agendamentoCena.setSolicitante("solicitante01");
		
		AgendamentoRetificacao agendamentoReti = new AgendamentoRetificacao();
			agendamentoReti.setDataAgendamento(dataAgendamento);
			agendamentoReti.setDataCriacao(dataAgendamento);
			agendamentoReti.setDataTermino(dataAgendamento);
			agendamentoReti.setId("id01");
//			agendamentoReti.setIdInstalacao("idInstalacao01");
//			agendamentoReti.setNomeInstalacao("nomeInstalacao01");
			agendamentoReti.setNumeroTarefa("numeroTarefa01");
			agendamentoReti.setResultado("resultado01");
			agendamentoReti.setSituacao("situacao01");
			agendamentoReti.setSolicitante("solicitante01");
			agendamentoReti.setTarefa("tarefa01");
			
		agendamentoCalculoRepository.save(agendamentoCalc);
		agendamentoCenarioRepository.save(agendamentoCena);
		agendamentoRetificacaoRepository.save(agendamentoReti);

	}

	@Test
	public void inserirAgendamento() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content =   "{\"anoCriacao\": 0,"
						  +"\"dataAgendamento\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataCriacao\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataTermino\": \"2016-12-13T16:42:21.838Z\","
						  +"\"id\": \"string\","
						  +"\"idInstalacao\": \"string\","
						  +"\"jobId\": \"string\","
						  +"\"mesFinal\": \"2016-12-13T16:42:21.838Z\","
						  +"\"mesInicial\": \"2016-12-13T16:42:21.838Z\","
						  +"\"minorVersion\": 0,"
						  +"\"nomeInstalacao\": \"string\","
						  +"\"protocolo\": 0,"
						  +"\"protocoloStr\": \"string\","
						  +"\"resultado\": \"string\","
						  +"\"situacao\": \"string\","
						  +"\"solicitante\": \"string\"}";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "SALVAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		
		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "SALVAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 2).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	
	@Test
	public void excluirAgentamento() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content =   "{\"anoCriacao\": 0,"
						  +"\"dataAgendamento\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataCriacao\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataTermino\": \"2016-12-13T16:42:21.838Z\","
						  +"\"id\": \"id01\","
						  +"\"idInstalacao\": \"string\","
						  +"\"jobId\": \"string\","
						  +"\"mesFinal\": \"2016-12-13T16:42:21.838Z\","
						  +"\"mesInicial\": \"2016-12-13T16:42:21.838Z\","
						  +"\"minorVersion\": 0,"
						  +"\"nomeInstalacao\": \"string\","
						  +"\"protocolo\": 0,"
						  +"\"protocoloStr\": \"string\","
						  +"\"resultado\": \"string\","
						  +"\"situacao\": \"string\","
						  +"\"solicitante\": \"string\"}";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "CANCELAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void excluirAgentamentoComIdErrado() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content =   "{\"anoCriacao\": 0,"
						  +"\"dataAgendamento\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataCriacao\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataTermino\": \"2016-12-13T16:42:21.838Z\","
						  +"\"id\": \"id02\","
						  +"\"idInstalacao\": \"string\","
						  +"\"jobId\": \"string\","
						  +"\"mesFinal\": \"2016-12-13T16:42:21.838Z\","
						  +"\"mesInicial\": \"2016-12-13T16:42:21.838Z\","
						  +"\"minorVersion\": 0,"
						  +"\"nomeInstalacao\": \"string\","
						  +"\"protocolo\": 0,"
						  +"\"protocoloStr\": \"string\","
						  +"\"resultado\": \"string\","
						  +"\"situacao\": \"string\","
						  +"\"solicitante\": \"string\"}";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "CANCELAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void reagendarAgentamento() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content =   "{\"anoCriacao\": 0,"
						  +"\"dataAgendamento\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataCriacao\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataTermino\": \"2016-12-13T16:42:21.838Z\","
						  +"\"id\": \"id01\","
						  +"\"idInstalacao\": \"string\","
						  +"\"jobId\": \"string\","
						  +"\"mesFinal\": \"2016-12-13T16:42:21.838Z\","
						  +"\"mesInicial\": \"2016-12-13T16:42:21.838Z\","
						  +"\"minorVersion\": 0,"
						  +"\"nomeInstalacao\": \"string\","
						  +"\"protocolo\": 0,"
						  +"\"protocoloStr\": \"string\","
						  +"\"resultado\": \"string\","
						  +"\"situacao\": \"string\","
						  +"\"solicitante\": \"string\"}";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "REAGENDAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	

	@Test
	public void reagendarAgentamentoComIdErrado() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content =   "{\"anoCriacao\": 0,"
						  +"\"dataAgendamento\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataCriacao\": \"2016-12-13T16:42:21.838Z\","
						  +"\"dataTermino\": \"2016-12-13T16:42:21.838Z\","
						  +"\"id\": \"id02\","
						  +"\"idInstalacao\": \"string\","
						  +"\"jobId\": \"string\","
						  +"\"mesFinal\": \"2016-12-13T16:42:21.838Z\","
						  +"\"mesInicial\": \"2016-12-13T16:42:21.838Z\","
						  +"\"minorVersion\": 0,"
						  +"\"nomeInstalacao\": \"string\","
						  +"\"protocolo\": 0,"
						  +"\"protocoloStr\": \"string\","
						  +"\"resultado\": \"string\","
						  +"\"situacao\": \"string\","
						  +"\"solicitante\": \"string\"}";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo")
				.param("acao", "REAGENDAR")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	
	@Test
	public void consultarTodosAgendamentos() throws Exception {

		restAgendamentoMockMvc.perform(get("/api/agendamentos-calculo")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
    
	@Test
	public void inserirAgendamentosPorInstalacao() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content = "[{\"equipamentos\": [{"
						+"\"id\": \"string\","
						+"\"nome\": \"string\"}],"
						+"\"id\": \"string\","
						+"\"minorVersion\": 0,"
						+"\"nome\": \"string\"}]";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo-by-instalacao")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void inserirAgendamentosPorData() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));
		
		String content = "[{\"equipamentos\": [{"
						+"\"id\": \"string\","
						+"\"nome\": \"string\"}],"
						+"\"id\": \"string\","
						+"\"minorVersion\": 0,"
						+"\"nome\": \"string\"}]";

		restAgendamentoMockMvc.perform(post("/api/agendamentos-calculo-by-date")
				.param("page", "1")
				.param("size", "2")
				.param("dtInicio", "2016-12-13T18:18:54.459Z")
				.param("dtFim", "2016-12-13T18:18:54.459Z")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void procurarAgendamentosRetificados() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		restAgendamentoMockMvc.perform(get("/api/agendamentos-retificacao")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void procurarAgendamentosPorCenario() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		restAgendamentoMockMvc.perform(get("/api/agendamentos-cenario")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void procurarAgendamentosPorData() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		restAgendamentoMockMvc.perform(get("/api/agendamentos-calculo-by-date")
				.param("idAgendamento", "id01")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void procurarAgendamentosPorCenarioData() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		restAgendamentoMockMvc.perform(post("/api/agendamentos-cenario-by-date")
				.param("dtInicio", "2016-12-13T18:18:54.459Z")
				.param("dtFim", "2016-12-13T18:18:54.459Z")
				.param("pageable", "2")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
	
	@Test
	public void procurarAgendamentosPorCenarioEData() throws Exception {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_S"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_SE"));
		authorities.add(new SimpleGrantedAuthority("ROLE_COSR_NCO"));
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", "", authorities));

		restAgendamentoMockMvc.perform(post("/api/agendamentos-retificacoes-by-date")
				.param("dtInicio", "2016-12-13T18:18:54.459Z")
				.param("dtFim", "2016-12-13T18:18:54.459Z")
				.param("pageable", "2")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		assertThat(agendamentoCalculoRepository.count() == 1).isTrue();
		assertThat(agendamentoCenarioRepository.count() == 1).isTrue();
		assertThat(agendamentoRetificacaoRepository.count() == 1).isTrue();

	}
    
}
