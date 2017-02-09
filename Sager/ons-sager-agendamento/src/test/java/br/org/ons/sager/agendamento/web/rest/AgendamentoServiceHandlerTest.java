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

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.OrigemComentario;
import br.org.ons.geracao.evento.StatusEvento;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.CommandFinishedEvent;
import br.org.ons.platform.common.CommandStartedEvent;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.sager.agendamento.OnsSagerAgendamentoApp;
import br.org.ons.sager.agendamento.domain.AgendamentoCalculo;
import br.org.ons.sager.agendamento.domain.AgendamentoCenario;
import br.org.ons.sager.agendamento.domain.AgendamentoRetificacao;
import br.org.ons.sager.agendamento.repository.AgendamentoCalculoRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoCenarioRepository;
import br.org.ons.sager.agendamento.repository.AgendamentoRetificacaoRepository;
import br.org.ons.sager.agendamento.service.AgendamentoService;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;

/**
 * API REST para as telas e diretivas Manter Agendamento
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerAgendamentoApp.class)
public class AgendamentoServiceHandlerTest {
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
	public void comandoIniciarEventoExistente() throws Exception {
		Date data = new Date("2016/11/28 03:00:00");
			
		CommandStartedEvent eventosApurados = new CommandStartedEvent();
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("id01");
			metadata.setCorrelationId("idJob01");
			
		EventMessage<CommandStartedEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);

		agendamentoService.handleCommandStartedEvent( eventMessage, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfQUdFTlRFLFJPTEVfQ05PUyxST0xFX0NPU1JfTkNPLFJPTEVfQ09TUl9ORSxST0xFX0NPU1JfUyxST0xFX0NPU1JfU0UsUk9MRV9VU0VSIiwiZXhwIjoxNDgzOTEwMTY1fQ.Zp9lwpvSUSDWoGyhyaVKwro75iIrLkgvR5km0rhJ19R-KYc8msZ04Bj0YnqNUWuNqyDWGrK3ridZV7_Yz0_6Vw");
	}

	@Test
	public void comandoFinalizarEventoExistente() throws Exception {
			
		CommandFinishedEvent eventosApurados = new CommandFinishedEvent();
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("id01");
			metadata.setCorrelationId("idJob01");
		
		EventMessage<CommandFinishedEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);

		agendamentoService.handleCommandFinishedEvent( eventMessage, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfQUdFTlRFLFJPTEVfQ05PUyxST0xFX0NPU1JfTkNPLFJPTEVfQ09TUl9ORSxST0xFX0NPU1JfUyxST0xFX0NPU1JfU0UsUk9MRV9VU0VSIiwiZXhwIjoxNDgzOTEwMTY1fQ.Zp9lwpvSUSDWoGyhyaVKwro75iIrLkgvR5km0rhJ19R-KYc8msZ04Bj0YnqNUWuNqyDWGrK3ridZV7_Yz0_6Vw");
	}
	
	@Test
	public void comandoFinalizarEventoExistenteComFalha() throws Exception {
			
		CommandFinishedEvent eventosApurados = new CommandFinishedEvent();
		eventosApurados.setStatus(CommandFinishedEvent.Status.FAILED);
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("id01");
			metadata.setCorrelationId("idJob01");
			
		
		EventMessage<CommandFinishedEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);
			
		agendamentoService.handleCommandFinishedEvent( eventMessage, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfQUdFTlRFLFJPTEVfQ05PUyxST0xFX0NPU1JfTkNPLFJPTEVfQ09TUl9ORSxST0xFX0NPU1JfUyxST0xFX0NPU1JfU0UsUk9MRV9VU0VSIiwiZXhwIjoxNDgzOTEwMTY1fQ.Zp9lwpvSUSDWoGyhyaVKwro75iIrLkgvR5km0rhJ19R-KYc8msZ04Bj0YnqNUWuNqyDWGrK3ridZV7_Yz0_6Vw");
	}
	
	@Test
	public void comandoIniciarEventoNaoExistente() throws Exception {
		Date data = new Date("2016/11/28 03:00:00");
			
		CommandStartedEvent eventosApurados = new CommandStartedEvent();
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("id01");
			metadata.setCorrelationId("idJob02");
			
		EventMessage<CommandStartedEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);

		agendamentoService.handleCommandStartedEvent( eventMessage, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfQUdFTlRFLFJPTEVfQ05PUyxST0xFX0NPU1JfTkNPLFJPTEVfQ09TUl9ORSxST0xFX0NPU1JfUyxST0xFX0NPU1JfU0UsUk9MRV9VU0VSIiwiZXhwIjoxNDgzOTEwMTY1fQ.Zp9lwpvSUSDWoGyhyaVKwro75iIrLkgvR5km0rhJ19R-KYc8msZ04Bj0YnqNUWuNqyDWGrK3ridZV7_Yz0_6Vw");
	}

	@Test
	public void comandoFinalizarEventoNaoExistente() throws Exception {
			
		CommandFinishedEvent eventosApurados = new CommandFinishedEvent();
			
		EventMetadata metadata= new EventMetadata();
			metadata.setAggregateId("id01");
			metadata.setCorrelationId("idJob02");
		
		EventMessage<CommandFinishedEvent> eventMessage = new EventMessage<>();
			eventMessage.setEvent(eventosApurados);
			eventMessage.setMetadata(metadata);

		agendamentoService.handleCommandFinishedEvent( eventMessage, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfQUdFTlRFLFJPTEVfQ05PUyxST0xFX0NPU1JfTkNPLFJPTEVfQ09TUl9ORSxST0xFX0NPU1JfUyxST0xFX0NPU1JfU0UsUk9MRV9VU0VSIiwiZXhwIjoxNDgzOTEwMTY1fQ.Zp9lwpvSUSDWoGyhyaVKwro75iIrLkgvR5km0rhJ19R-KYc8msZ04Bj0YnqNUWuNqyDWGrK3ridZV7_Yz0_6Vw");
	}

}
