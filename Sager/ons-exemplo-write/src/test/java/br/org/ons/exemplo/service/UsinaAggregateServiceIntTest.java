package br.org.ons.exemplo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import br.org.ons.exemplo.OnsExemploWriteApp;
import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.ApurarEventosCommandModification;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.exemplo.common.Usina;
import br.org.ons.exemplo.common.UsinaAtualizadaEvent;
import br.org.ons.exemplo.repository.AggregateRepository;
import br.org.ons.exemplo.security.jwt.TokenProvider;
import br.org.ons.exemplo.web.rest.TestUtil;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.Event;

/**
 * Teste de integração para o serviço UsinaAggregateService e sua superclasse
 * AggregateService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsExemploWriteApp.class)
@WebAppConfiguration
@IntegrationTest
public class UsinaAggregateServiceIntTest {

	private static final String DEFAULT_USINA_ID = "MTUSCU";
	private static final String DEFAULT_USINA_NOME_CURTO = "TUCURUÍ";
	private static final String DEFAULT_USINA_TIPO = "UTE";
	private static final Double DEFAULT_USINA_POTENCIA_CALCULO = 100.0;
	private static final String UPDATED_USINA_ID = "RSUPME";
	private static final String UPDATED_USINA_NOME_CURTO = "P.MEDICI";
	private static final String UPDATED_USINA_TIPO = "UHE";
	private static final Double UPDATED_USINA_POTENCIA_CALCULO = 200.0;
	
	private static final ZonedDateTime DEFAULT_TIMELINE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneId.systemDefault());
	private static final ZonedDateTime UPDATED_TIMELINE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1L),
			ZoneId.systemDefault());
	
	private static final Long DEFAULT_MAJOR_VERSION = 1L;
	private static final Long DEFAULT_MINOR_VERSION = 1L;
	
	protected static final String DEFAULT_SCENARIO_NAME = "Cenario 1";

	/**
	 * Mock para emular o contexto de segurança
	 */
	@Mock(answer=Answers.RETURNS_MOCKS)
	private TokenProvider tokenProvider;

	/**
	 * Mock para emular acesso ao serviço REST EventSourcingRepositoryService
	 */
	@Mock
	private RestTemplate restTemplate;
	
	private AggregateRepository<UsinaAggregate> repository;
	
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMockMvc;
    
	@SuppressWarnings("unchecked")
	@PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);

		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class),
				any(Map.class))).then(new Answer<ResponseEntity<?>>() {
					@SuppressWarnings("rawtypes")
					@Override
					public ResponseEntity<?> answer(InvocationOnMock invocation) throws Throwable {
						String url = invocation.getArgumentAt(0, String.class);
						
						Usina usina = new Usina();
						usina.setId(DEFAULT_USINA_ID);
						usina.setNomeCurto(DEFAULT_USINA_NOME_CURTO);
						usina.setPotenciaCalculo(DEFAULT_USINA_POTENCIA_CALCULO);
						usina.setTipoUsina(DEFAULT_USINA_TIPO);

						UsinaAggregate aggregate = new UsinaAggregate();
						aggregate.setId(DEFAULT_USINA_ID);
						aggregate.setMajorVersion(DEFAULT_MAJOR_VERSION);
						aggregate.setMinorVersion(DEFAULT_MINOR_VERSION);

						if (url.contains("/main")) {
							return ResponseEntity.ok(aggregate);
						} else if (url.contains("/checkout")) {
							aggregate.setUsina(usina);
							aggregate.setName(DEFAULT_USINA_NOME_CURTO);
							aggregate.setScenarioName(DEFAULT_SCENARIO_NAME);
							return ResponseEntity
									.ok(new UsinaAggregate[] { aggregate });
						} else if (url.contains("/updates")) {
							Map params = invocation.getArgumentAt(4, Map.class);
							if (DEFAULT_TIMELINE_DATE.isEqual(ZonedDateTime.parse((String) params.get("beforeDate"),
									DateTimeFormatter.ISO_DATE_TIME))) {
								return ResponseEntity
										.ok(new Event[] {});
							} else {
								return ResponseEntity
										.ok(new Event[] { new UsinaAtualizadaEvent(usina) });
							}
						} else if (url.contains("/checkin")) {
							return ResponseEntity.ok(null);
						}
						return ResponseEntity.ok(null);
					}
				});
        
        repository = new AggregateRepository<>();
        repository.setAggregateClass(UsinaAggregate.class);
        ReflectionTestUtils.setField(repository, "tokenProvider", tokenProvider);
        ReflectionTestUtils.setField(repository, "restTemplate", restTemplate);
        
        UsinaAggregateService mockService = new UsinaAggregateService();
    	ReflectionTestUtils.setField(mockService, "repository", repository);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(mockService)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }
    
	/**
	 * Testa a execução de todos os comandos
	 * @throws Exception
	 */
	@Test
	public void testCommands() throws Exception {
		CommandMetadata metadata = new CommandMetadata();
		metadata.setAggregateId(DEFAULT_USINA_ID);
		metadata.setTimelineDate(DEFAULT_TIMELINE_DATE);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		
		CriarUsinaCommand criarUsina = new CriarUsinaCommand();
		criarUsina.setUsina(new Usina());
		criarUsina.getUsina().setId(DEFAULT_USINA_ID);
		criarUsina.getUsina().setNomeCurto(DEFAULT_USINA_NOME_CURTO);
		criarUsina.getUsina().setTipoUsina(DEFAULT_USINA_TIPO);
		criarUsina.getUsina().setPotenciaCalculo(DEFAULT_USINA_POTENCIA_CALCULO);
		
		CommandMessage<CriarUsinaCommand> criarMessage = new CommandMessage<>();
		criarMessage.setCommand(criarUsina);
		criarMessage.setMetadata(metadata);
		
        restMockMvc.perform(post("/api/usina-aggregates/criar-usina-command")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(criarMessage)))
                .andExpect(status().isOk());
		
		AtualizarUsinaCommand atualizarUsina = new AtualizarUsinaCommand();
		atualizarUsina.setUsina(new Usina());
		atualizarUsina.getUsina().setId(UPDATED_USINA_ID);
		atualizarUsina.getUsina().setNomeCurto(UPDATED_USINA_NOME_CURTO);
		atualizarUsina.getUsina().setTipoUsina(UPDATED_USINA_TIPO);
		atualizarUsina.getUsina().setPotenciaCalculo(UPDATED_USINA_POTENCIA_CALCULO);
		
		CommandMessage<AtualizarUsinaCommand> atualizarMessage = new CommandMessage<>();
		atualizarMessage.setCommand(atualizarUsina);
		metadata.setTimelineDate(UPDATED_TIMELINE_DATE);
		atualizarMessage.setMetadata(metadata);
		
        restMockMvc.perform(post("/api/usina-aggregates/atualizar-usina-command")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(atualizarMessage)))
                .andExpect(status().isOk());

		ExcluirUsinaCommand excluirUsina = new ExcluirUsinaCommand();
		
		CommandMessage<ExcluirUsinaCommand> excluirMessage = new CommandMessage<>();
		excluirMessage.setCommand(excluirUsina);
		excluirMessage.setMetadata(metadata);
		
        restMockMvc.perform(post("/api/usina-aggregates/excluir-usina-command")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(excluirMessage)))
                .andExpect(status().isOk());

		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(DEFAULT_TIMELINE_DATE);
		apurarEventos.setDataFim(UPDATED_TIMELINE_DATE);
		CommandMessage<ApurarEventosCommand> apurarMessage = new CommandMessage<>();
		apurarMessage.setCommand(apurarEventos);
		apurarMessage.setMetadata(metadata);
		apurarMessage.setModification(new ApurarEventosCommandModification());
		
        restMockMvc.perform(post("/api/usina-aggregates/apurar-eventos-command")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apurarMessage)))
                .andExpect(status().isOk());
	}

	/**
	 * Testa a recuperação de estado
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
        restMockMvc.perform(get("/api/usina-aggregates/" + DEFAULT_USINA_ID + "?majorVersion=" + DEFAULT_MAJOR_VERSION 
        		+ "&timelineDate=" + UPDATED_TIMELINE_DATE.format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(DEFAULT_USINA_ID));
	}
	
	/**
	 * Testa o tratamento de erro para comando inválido
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testBadRequest() {
		CommandMetadata metadata = new CommandMetadata();
		metadata.setAggregateId(DEFAULT_USINA_ID);
		metadata.setTimelineDate(DEFAULT_TIMELINE_DATE);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		
		ExcluirUsinaCommand excluirUsina = new ExcluirUsinaCommand();
		
		CommandMessage<ExcluirUsinaCommand> excluirMessage = new CommandMessage<>();
		excluirMessage.setCommand(excluirUsina);
		excluirMessage.setMetadata(metadata);
		
		UsinaAggregate mockAggregate = Mockito.mock(UsinaAggregate.class);
		when(mockAggregate.getScenarioName()).thenReturn(null);
		when(mockAggregate.apply(Mockito.any(Command.class))).thenThrow(IllegalArgumentException.class);

		AggregateRepository mockRepository = Mockito.mock(AggregateRepository.class);
		when(mockRepository.checkOutLatestSnapshotBeforeDate(Mockito.anyString(), Mockito.anyLong(),
				Mockito.any(ZonedDateTime.class))).thenReturn(Collections.singletonList(mockAggregate));

        UsinaAggregateService service = new UsinaAggregateService();
		ReflectionTestUtils.setField(service, "repository", mockRepository);
    	
		assertThat(service.excluirUsina(excluirMessage).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Testa o tratamento de erro para estado inválido do aggregate
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testConflict() {
		CommandMetadata metadata = new CommandMetadata();
		metadata.setAggregateId(DEFAULT_USINA_ID);
		metadata.setTimelineDate(DEFAULT_TIMELINE_DATE);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		
		ExcluirUsinaCommand excluirUsina = new ExcluirUsinaCommand();
		
		CommandMessage<ExcluirUsinaCommand> excluirMessage = new CommandMessage<>();
		excluirMessage.setCommand(excluirUsina);
		excluirMessage.setMetadata(metadata);
		
		UsinaAggregate mockAggregate = Mockito.mock(UsinaAggregate.class);
		when(mockAggregate.getScenarioName()).thenReturn(null);
		when(mockAggregate.apply(Mockito.any(Command.class))).thenThrow(IllegalStateException.class);
		
		AggregateRepository mockRepository = Mockito.mock(AggregateRepository.class);
		when(mockRepository.checkOutLatestSnapshotBeforeDate(Mockito.anyString(), Mockito.anyLong(),
				Mockito.any(ZonedDateTime.class))).thenReturn(Collections.singletonList(mockAggregate));

        UsinaAggregateService service = new UsinaAggregateService();
		ReflectionTestUtils.setField(service, "repository", mockRepository);
		
		assertThat(service.excluirUsina(excluirMessage).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	/**
	 * Testa o tratamento de erro interno de tempo de execução
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testInternalServerError() {
		CommandMetadata metadata = new CommandMetadata();
		metadata.setAggregateId(DEFAULT_USINA_ID);
		metadata.setTimelineDate(DEFAULT_TIMELINE_DATE);
		metadata.setMajorVersion(DEFAULT_MAJOR_VERSION);
		metadata.setMinorVersion(DEFAULT_MINOR_VERSION);
		
		ExcluirUsinaCommand excluirUsina = new ExcluirUsinaCommand();
		
		CommandMessage<ExcluirUsinaCommand> excluirMessage = new CommandMessage<>();
		excluirMessage.setCommand(excluirUsina);
		excluirMessage.setMetadata(metadata);
		
		UsinaAggregate mockAggregate = Mockito.mock(UsinaAggregate.class);
		when(mockAggregate.getScenarioName()).thenReturn(null);
		when(mockAggregate.apply(Mockito.any(Command.class))).thenThrow(RuntimeException.class);
		
		AggregateRepository mockRepository = Mockito.mock(AggregateRepository.class);
		when(mockRepository.checkOutLatestSnapshotBeforeDate(Mockito.anyString(), Mockito.anyLong(),
				Mockito.any(ZonedDateTime.class))).thenReturn(Collections.singletonList(mockAggregate));

        UsinaAggregateService service = new UsinaAggregateService();
		ReflectionTestUtils.setField(service, "repository", mockRepository);
		
		assertThat(service.excluirUsina(excluirMessage).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
