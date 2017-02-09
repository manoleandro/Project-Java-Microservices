package br.org.ons.platform.service;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.platform.OnsPlatformApp;
import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.bus.EventBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.CreateScenarioCommand;
import br.org.ons.platform.common.EndScenarioCommand;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.ReplayCommand;
import br.org.ons.platform.common.ReprocessCommand;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.ScenarioCommandModification;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.Scenario;
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.domain.enumeration.RecordType;
import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.repository.ScenarioRepository;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.web.rest.TestUtil;
import br.org.ons.platform.web.rest.dto.Commit;
import br.org.ons.platform.domain.model.GenericAggregate;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.domain.model.GenericCommandModification;
import br.org.ons.platform.domain.model.GenericEvent;

/**
 * Teste para os serviços EventSourcingRepositoryService e ReprocessorService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class EventSourcingPlatformTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String AGGREGATE_ID = "AAAAA";
    private static final String AGGREGATE_TYPE = "AAAAA";
    private static final String AGGREGATE_NAME_PROPERTY = "aggregateName";
    private static final String DEFAULT_AGGREGATE_NAME = "AAAAA";
    private static final String CHANGED_AGGREGATE_NAME = "BBBBB";
    
	private static final Long FIRST_MAJOR_VERSION = 1L;
	private static final Long FIRST_MINOR_VERSION = 0L;

	private static final String CREATE_COMMAND_ID = IdGenerator.newId();
	private static final String CREATE_COMMAND_NAME = "CreateAggregate";
	private static final String CREATED_EVENT_NAME = "AggregateCreated";
	private static final ZonedDateTime CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
	private static final String CREATE_DATE_STR = dateTimeFormatter.format(CREATE_DATE);
	private static final Long CREATE_MINOR_VERSION = 1L;
	
	private static final String CHANGE_COMMAND_ID = IdGenerator.newId();
	private static final String CHANGE_COMMAND_NAME = "ChangeAggregate";
	private static final String CHANGED_EVENT_NAME = "AggregateChanged";
	private static final ZonedDateTime CHANGE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(10L), ZoneId.systemDefault());
	private static final String CHANGE_DATE_STR = dateTimeFormatter.format(CHANGE_DATE);
	private static final Long CHANGE_MINOR_VERSION = 2L;

	private static final String MODIFICATION_NAME = "ModifyChangeAggregate";

	private static final Long REPROCESS_MAJOR_VERSION = 2L;

	private static final String SCENARIO_DESCRIPTION = "Cenário";
	private static final String SCENARIO_TYPE = ScenarioType.PARALLEL.name();
	private static final Long SCENARIO_MAJOR_VERSION = 2L;
	
	@Inject
	private TimelineRepository timelineRepository;

	@Inject
	private RecordRepository recordRepository;
	
	@Inject
	private ScenarioRepository scenarioRepository;

	@Inject
	private EventBus eventBus;

	@Inject
	private CommandBus commandBus;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
	private EventSourcingRepositoryService esRepo;

    @Inject
	private ReprocessorService reprocessor;
    
    private MockMvc restMockMvc;
    
    @SuppressWarnings("unchecked")
	@PostConstruct
    public void setup() {
        when(commandBus.sendAndWait(any(CommandMessage.class))).thenAnswer(new Answer<ResultMessage<Void>>() {
			@Override
			public ResultMessage<Void> answer(InvocationOnMock invocation) throws Throwable {
				ResultMessage<Void> result = new ResultMessage<>();
				result.setStatusCode(HttpStatus.OK.value());
				result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
				return result;
			}
		});

		restMockMvc = MockMvcBuilders.standaloneSetup(esRepo, reprocessor)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
    }

    @Before
    public void initTest() {
    	// Reiniciando a base de dados
    	timelineRepository.deleteAll();
    	recordRepository.deleteAll();
    	scenarioRepository.deleteAll();
    }

	@Test
    public void testCreateMainAndReprocess() throws Exception {
		createMain();
        
        checkinCreate();
        
        checkoutAfterCreate();

        getUpdates();

        checkinChange();

        reprocess();
    }

	@Test
    public void testCreateMainAndReplay() throws Exception {
		createMain();
        
        checkinCreate();
        
        replayCreate();
    }
	
	@Test
	public void testCreateMainAndScenarioDefault() throws Exception {
		createMain();
		
		checkinCreate();
		
		checkinChange();
		
		createScenario();
		
		Scenario scenario = scenarioRepository.findByType(ScenarioType.PARALLEL).get(0);
		
		endScenario(scenario.getId(), true);
	}
	
	@Test
	public void testCreateMainAndScenarioArchived() throws Exception {
		createMain();
		
		checkinCreate();
		
		checkinChange();
		
		createScenario();
		
		endScenario(scenarioRepository.findByType(ScenarioType.PARALLEL).get(0).getId(), false);
	}
	
	@Test
	public void testCreateMainAndScenarioError() throws Exception {
		createMain();
		
		checkinCreate();
		
		checkinChange();
		
		createScenario();
		
		String scenarioId = scenarioRepository.findByType(ScenarioType.PARALLEL).get(0).getId();
		scenarioRepository.delete(scenarioId);

		CommandMessage<EndScenarioCommand> message = new CommandMessage<>();
		
		EndScenarioCommand command = new EndScenarioCommand();
		command.setScenarioId(scenarioId);
		command.setMakeDefault(true);
		message.setCommand(command);
		
		message.getMetadata().setAggregateId(AGGREGATE_ID);
		message.getMetadata().setTimelineDate(CHANGE_DATE);

		// Iniciando criação do cenário
		restMockMvc
				.perform(post("/api/reprocessor/end-scenario-command")
				.content(TestUtil.convertObjectToJsonBytes(message))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isInternalServerError())
				.andDo(print());
	}

	private void createMain() throws Exception {
		// Criando timeline MAIN
		restMockMvc
				.perform(
						post("/api/es-repository/main?aggregateId={aggregateId}&aggregateType={aggregateType}&startDate={startDate}", 
								AGGREGATE_ID, AGGREGATE_TYPE, CREATE_DATE_STR)
				.accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
    	
    	// Validando a timeline criada
    	List<Timeline> timelines = timelineRepository.findAll();
    	assertThat(timelines).hasSize(1);
    	Timeline testTimeline = timelines.get(0);
    	assertThat(testTimeline.getAggregateId()).isEqualTo(AGGREGATE_ID);
    	assertThat(testTimeline.getAggregateType()).isEqualTo(AGGREGATE_TYPE);
    	assertThat(testTimeline.getMain()).isEqualTo(true);
    	assertThat(testTimeline.getMajorVersion()).isEqualTo(FIRST_MAJOR_VERSION);
    	assertThat(testTimeline.getParentId()).isEqualTo(null);
    	
    	// Validando o record criado
    	List<Record> records = recordRepository.findAll();
    	assertThat(records).hasSize(1);
    	Record testRecord = records.get(0);
    	assertThat(testRecord.getRecordDate()).isEqualTo(CREATE_DATE);
    	assertThat(testRecord.getPayloadType()).isEqualTo(AGGREGATE_TYPE);
    	assertThat(testRecord.getType()).isEqualTo(RecordType.SNAPSHOT);
    	assertThat(testRecord.getTimelineId()).isEqualTo(testTimeline.getId());
    	assertThat(testRecord.getMinorVersion()).isEqualTo(0);
    	
        // Validando o scenario criado
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(1);
        Scenario testScenario = scenarios.get(0);
        assertThat(testScenario.getAggregateId()).isEqualTo(AGGREGATE_ID);
        assertThat(testScenario.getId()).isEqualTo(testTimeline.getId());
        assertThat(testScenario.getStartDate()).isEqualTo(CREATE_DATE);
        assertThat(testScenario.getType()).isEqualTo(ScenarioType.DEFAULT);
        assertThat(testScenario.getStatus()).isEqualTo(ScenarioStatus.ACTIVE);
	}

	@SuppressWarnings("unchecked")
	private void checkinCreate() throws Exception {
    	Commit commit = new Commit();

        GenericCommand command = new GenericCommand();
        command.setName(CREATE_COMMAND_NAME);
        command.putProperty(AGGREGATE_NAME_PROPERTY, DEFAULT_AGGREGATE_NAME);
        commit.setCommand(command);
        
        CommandMetadata metadata = new CommandMetadata();
        metadata.setId(CREATE_COMMAND_ID);
        metadata.setTimelineDate(CREATE_DATE);
    	metadata.setMajorVersion(FIRST_MAJOR_VERSION);
    	metadata.setMinorVersion(FIRST_MINOR_VERSION);
        commit.setMetadata(metadata);
        
        GenericAggregate aggregate = new GenericAggregate();
        aggregate.setId(AGGREGATE_ID);
        aggregate.setAggregateType(AGGREGATE_TYPE);
        aggregate.setName(DEFAULT_AGGREGATE_NAME);
        aggregate.setMajorVersion(FIRST_MAJOR_VERSION);
        aggregate.setMinorVersion(FIRST_MINOR_VERSION);
        GenericEvent event = new GenericEvent();
        event.setName(CREATED_EVENT_NAME);
        event.putProperty(AGGREGATE_NAME_PROPERTY, DEFAULT_AGGREGATE_NAME);
        aggregate.getEventsToSave().add(event);
        commit.getAggregates().add(aggregate);
        
		// Realizando check-in de um evento gerado por um comando
		restMockMvc
				.perform(post("/api/es-repository/checkin")
				.content(TestUtil.convertObjectToJsonBytes(commit))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando os records criados
    	assertThat(recordRepository.findAll()).hasSize(3);
		
		// Verificando o evento publicado
		ArgumentCaptor<EventMessage<GenericEvent>> createMessageCaptor = (ArgumentCaptor<EventMessage<GenericEvent>>) ArgumentCaptor
				.forClass(new EventMessage<GenericEvent>().getClass());
        Mockito.verify(eventBus, Mockito.times(1)).publish(createMessageCaptor.capture());
        assertThat(createMessageCaptor.getValue().getEvent().getName()).isEqualTo(CREATED_EVENT_NAME);
        assertThat(createMessageCaptor.getValue().getEvent().getProperties().get(AGGREGATE_NAME_PROPERTY)).isEqualTo(DEFAULT_AGGREGATE_NAME);
        assertThat(createMessageCaptor.getValue().getMetadata().getMinorVersion()).isEqualTo(CREATE_MINOR_VERSION);
        Mockito.reset(eventBus);
	}

	private void checkoutAfterCreate() throws Exception {
		// Executando check-out e verificando snapshot
        restMockMvc
        	.perform(
	        		post("/api/es-repository/checkout?aggregateId={aggregateId}&majorVersion={majorVersion}&beforeDate={beforeDate}", 
							AGGREGATE_ID, FIRST_MAJOR_VERSION, CHANGE_DATE_STR)
    		.accept(TestUtil.APPLICATION_JSON_UTF8))
        	.andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.[*].id").value(hasItem(AGGREGATE_ID)))
            .andExpect(jsonPath("$.[*].aggregateType").value(hasItem(AGGREGATE_TYPE)))
            .andExpect(jsonPath("$.[*].majorVersion").value(hasItem(FIRST_MAJOR_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].minorVersion").value(hasItem(FIRST_MINOR_VERSION.intValue())));
	}

	private void getUpdates() throws Exception {
		// Obtendo atualizações e verificando records
        restMockMvc
        	.perform(
	        		get("/api/es-repository/updates?aggregateId={aggregateId}&majorVersion={minorVersion}&minorVersion={minorVersion}&beforeDate={beforeDate}", 
							AGGREGATE_ID, FIRST_MAJOR_VERSION, FIRST_MINOR_VERSION, CHANGE_DATE_STR)
    		.accept(TestUtil.APPLICATION_JSON_UTF8))
        	.andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andDo(print())
            .andExpect(jsonPath("$.[*].name").value(hasItem(CREATED_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].aggregateName").value(hasItem(DEFAULT_AGGREGATE_NAME)));
	}

	@SuppressWarnings("unchecked")
	private void checkinChange() throws Exception, IOException {
		Commit commit = new Commit();
    	
    	GenericCommand command = new GenericCommand();
    	command.setName(CHANGE_COMMAND_NAME);
    	command.putProperty(AGGREGATE_NAME_PROPERTY, CHANGED_AGGREGATE_NAME);
    	commit.setCommand(command);
    	
    	CommandMetadata metadata = new CommandMetadata();
    	metadata.setId(CHANGE_COMMAND_ID);
    	metadata.setTimelineDate(CHANGE_DATE);
    	metadata.setMajorVersion(FIRST_MAJOR_VERSION);
    	metadata.setMinorVersion(CREATE_MINOR_VERSION);
    	commit.setMetadata(metadata);
    	
    	GenericAggregate aggregate = new GenericAggregate();
    	aggregate.setId(AGGREGATE_ID);
    	aggregate.setAggregateType(AGGREGATE_TYPE);
    	aggregate.setName(CHANGED_AGGREGATE_NAME);
    	aggregate.setMajorVersion(FIRST_MAJOR_VERSION);
    	aggregate.setMinorVersion(CREATE_MINOR_VERSION);
    	GenericEvent event = new GenericEvent();
    	event.setName(CHANGED_EVENT_NAME);
    	event.putProperty(AGGREGATE_NAME_PROPERTY, CHANGED_AGGREGATE_NAME);
    	aggregate.getEventsToSave().add(event);
    	commit.getAggregates().add(aggregate);
    	
		// Realizando check-in de novo evento gerado por um comando
		restMockMvc
				.perform(post("/api/es-repository/checkin")
				.content(TestUtil.convertObjectToJsonBytes(commit))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando os records criados
    	assertThat(recordRepository.findAll()).hasSize(5);

		// Verificando o evento publicado
		ArgumentCaptor<EventMessage<GenericEvent>> changeMessageCaptor = (ArgumentCaptor<EventMessage<GenericEvent>>) ArgumentCaptor
				.forClass(new EventMessage<GenericEvent>().getClass());
        Mockito.verify(eventBus, Mockito.times(1)).publish(changeMessageCaptor.capture());
        assertThat(changeMessageCaptor.getValue().getEvent().getName()).isEqualTo(CHANGED_EVENT_NAME);
        assertThat(changeMessageCaptor.getValue().getEvent().getProperties().get(AGGREGATE_NAME_PROPERTY)).isEqualTo(CHANGED_AGGREGATE_NAME);
        assertThat(changeMessageCaptor.getValue().getMetadata().getMinorVersion()).isEqualTo(CHANGE_MINOR_VERSION);
        Mockito.reset(eventBus);
	}

	@SuppressWarnings("unchecked")
	private void reprocess() throws Exception, IOException {
		CommandMessage<ReprocessCommand<GenericCommandModification>> message = new CommandMessage<>();
    	
    	ReprocessCommand<GenericCommandModification> command = new ReprocessCommand<>();
    	GenericCommandModification mod = new GenericCommandModification();
    	mod.setCorrelationId(CHANGE_COMMAND_ID);
    	mod.setName(MODIFICATION_NAME);
    	mod.setTimelineDate(CHANGE_DATE);
    	command.addModification(mod);
    	message.setCommand(command);

    	message.getMetadata().setAggregateId(AGGREGATE_ID);
    	message.getMetadata().setMajorVersion(FIRST_MAJOR_VERSION);
    	message.getMetadata().setTimelineDate(CHANGE_DATE);
		
		// Iniciando reprocessamento
		restMockMvc
				.perform(post("/api/reprocessor/reprocess-command")
				.content(TestUtil.convertObjectToJsonBytes(message))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando o comando enviado
		ArgumentCaptor<CommandMessage<GenericCommand>> reprocessMessageCaptor = (ArgumentCaptor<CommandMessage<GenericCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<GenericCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(reprocessMessageCaptor.capture());
        assertThat(reprocessMessageCaptor.getValue().getCommand().getName()).isEqualTo(CHANGE_COMMAND_NAME);
        assertThat(reprocessMessageCaptor.getValue().getModification().getCorrelationId()).isEqualTo(CHANGE_COMMAND_ID);
        Mockito.reset(commandBus);

		// Verificando timeline e scenario criados
        List<Timeline> timelines = timelineRepository.findAll();
    	assertThat(timelines).hasSize(2);
    	assertThat(timelines).extracting(Timeline::getMain).contains(true, false);
    	assertThat(timelines).extracting(Timeline::getMajorVersion).contains(FIRST_MAJOR_VERSION, REPROCESS_MAJOR_VERSION);
    	assertThat(timelines).allMatch(timeline -> timeline.getAggregateId().equals(AGGREGATE_ID));
    	List<Scenario> scenarios = scenarioRepository.findAll(); 
    	assertThat(scenarios).hasSize(2);
    	assertThat(scenarios).extracting(Scenario::getStatus).contains(ScenarioStatus.ACTIVE, ScenarioStatus.INACTIVE);
    	assertThat(scenarios).allMatch(scenario -> scenario.getType().equals(ScenarioType.DEFAULT));
    	assertThat(scenarios).allMatch(scenario -> scenario.getAggregateId().equals(AGGREGATE_ID));
	}

	@SuppressWarnings("unchecked")
	private void replayCreate() throws Exception {
		CommandMessage<ReplayCommand<GenericCommandModification>> message = new CommandMessage<>();
		
    	ReplayCommand<GenericCommandModification> replay = new ReplayCommand<>();
    	replay.setCorrelationId(CREATE_COMMAND_ID);

    	GenericCommandModification mod = new GenericCommandModification();
    	mod.setCorrelationId(CREATE_COMMAND_ID);
    	mod.setName(MODIFICATION_NAME);
    	mod.setTimelineDate(CHANGE_DATE);
    	replay.setModification(mod);
    	
    	message.setCommand(replay);
    	
		// Realizando check-in de um evento gerado por um comando
		restMockMvc
				.perform(post("/api/reprocessor/replay-command")
				.content(TestUtil.convertObjectToJsonBytes(message))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando o comando enviado
		ArgumentCaptor<CommandMessage<GenericCommand>> replayMessageCaptor = (ArgumentCaptor<CommandMessage<GenericCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<GenericCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(replayMessageCaptor.capture());
        assertThat(replayMessageCaptor.getValue().getCommand().getName()).isEqualTo(CREATE_COMMAND_NAME);
        assertThat(replayMessageCaptor.getValue().getModification().getCorrelationId()).isEqualTo(CREATE_COMMAND_ID);
        assertThat(replayMessageCaptor.getValue().getMetadata().getIsReplay()).isEqualTo(true);
        Mockito.reset(commandBus);
	}

	@SuppressWarnings("unchecked")
	private void createScenario() throws Exception {
		CommandMessage<CreateScenarioCommand> message = new CommandMessage<>();
		
		CreateScenarioCommand command = new CreateScenarioCommand();
		command.setAggregateName(CHANGED_AGGREGATE_NAME);
		command.setScenarioDescription(SCENARIO_DESCRIPTION);
		command.setScenarioType(SCENARIO_TYPE);
		message.setCommand(command);
		
		message.getMetadata().setAggregateId(AGGREGATE_ID);
		message.getMetadata().setTimelineDate(CHANGE_DATE);

		// Iniciando criação do cenário
		restMockMvc
				.perform(post("/api/reprocessor/create-scenario-command")
				.content(TestUtil.convertObjectToJsonBytes(message))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando o comando enviado
		ArgumentCaptor<CommandMessage<GenericCommand>> scenarioMessageCaptor = (ArgumentCaptor<CommandMessage<GenericCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<GenericCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).sendAndWait(scenarioMessageCaptor.capture());
        assertThat(scenarioMessageCaptor.getValue().getCommand().getName()).isEqualTo(CHANGE_COMMAND_NAME);
        assertThat(scenarioMessageCaptor.getValue().getModification().getCorrelationId()).isEqualTo(CHANGE_COMMAND_ID);
        assertThat(scenarioMessageCaptor.getValue().getModification().getName()).isEqualTo(ScenarioCommandModification.class.getName());
        Mockito.reset(commandBus);

		// Verificando timeline e scenario criados
        List<Timeline> timelines = timelineRepository.findAll();
    	assertThat(timelines).hasSize(2);
    	assertThat(timelines).allMatch(timeline -> timeline.getMain().equals(true));
    	assertThat(timelines).extracting(Timeline::getMajorVersion).contains(FIRST_MAJOR_VERSION, SCENARIO_MAJOR_VERSION);
    	assertThat(timelines).allMatch(timeline -> timeline.getAggregateId().equals(AGGREGATE_ID));
    	List<Scenario> scenarios = scenarioRepository.findAll(); 
    	assertThat(scenarios).hasSize(2);
    	assertThat(scenarios).allMatch(scenario -> scenario.getStatus().equals(ScenarioStatus.ACTIVE));
    	assertThat(scenarios).extracting(Scenario::getType).contains(ScenarioType.DEFAULT, ScenarioType.PARALLEL);
    	assertThat(scenarios).allMatch(scenario -> scenario.getAggregateId().equals(AGGREGATE_ID));
	}

	private void endScenario(String scenarioId, boolean makeDefault) throws Exception {
		CommandMessage<EndScenarioCommand> message = new CommandMessage<>();
		
		EndScenarioCommand command = new EndScenarioCommand();
		command.setScenarioId(scenarioId);
		command.setMakeDefault(makeDefault);
		message.setCommand(command);
		
		message.getMetadata().setAggregateId(AGGREGATE_ID);
		message.getMetadata().setTimelineDate(CHANGE_DATE);

		// Iniciando criação do cenário
		restMockMvc
				.perform(post("/api/reprocessor/end-scenario-command")
				.content(TestUtil.convertObjectToJsonBytes(message))
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Verificando timeline e scenario criados
        List<Timeline> timelines = timelineRepository.findAll();
    	assertThat(timelines).hasSize(2);
    	assertThat(timelines).extracting(Timeline::getMain).contains(true, false);
    	List<Scenario> scenarios = scenarioRepository.findAll(); 
    	assertThat(scenarios).hasSize(2);
    	assertThat(scenarios).extracting(Scenario::getStatus).contains(ScenarioStatus.ACTIVE, ScenarioStatus.INACTIVE);
    	if (makeDefault) {
    		assertThat(scenarios).allMatch(scenario -> scenario.getType().equals(ScenarioType.DEFAULT));
    	} else {
        	assertThat(scenarios).extracting(Scenario::getType).contains(ScenarioType.DEFAULT, ScenarioType.PARALLEL);
    	}
	}
}
