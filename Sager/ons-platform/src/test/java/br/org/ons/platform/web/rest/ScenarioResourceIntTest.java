package br.org.ons.platform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import br.org.ons.platform.OnsPlatformApp;
import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CreateScenarioCommand;
import br.org.ons.platform.common.EndScenarioCommand;
import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.Scenario;
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.domain.enumeration.RecordType;
import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.repository.ScenarioRepository;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.web.rest.dto.ScenarioDTO;
import br.org.ons.platform.web.rest.mapper.ScenarioMapper;

/**
 * Test class for the ScenarioResource REST controller.
 *
 * @see ScenarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class ScenarioResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_ID = "AAAAA";
    private static final String DEFAULT_AGGREGATE_ID = "AAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBB";
    private static final String DEFAULT_AGGREGATE_NAME = "AAAAA";
    private static final String UPDATED_AGGREGATE_NAME = "BBBBB";
    private static final String DEFAULT_TIMELINE_ID = "AAAAA";
    private static final String UPDATED_TIMELINE_ID = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ScenarioType DEFAULT_TYPE = ScenarioType.DEFAULT;
    private static final ScenarioType UPDATED_TYPE = ScenarioType.PARALLEL;

    private static final ScenarioStatus DEFAULT_STATUS = ScenarioStatus.ACTIVE;
    private static final ScenarioStatus UPDATED_STATUS = ScenarioStatus.INACTIVE;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);
    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";

    private static final Long DEFAULT_MAJOR_VERSION = 1L;

    private static final Boolean DEFAULT_MAIN = true;

    private static final RecordType DEFAULT_RECORD_TYPE = RecordType.COMMAND;
    
    @Inject
    private ScenarioRepository scenarioRepository;
    
    @Inject
    private TimelineRepository timelineRepository;
    
    @Inject
    private RecordRepository recordRepository;

    @Inject
    private ScenarioMapper scenarioMapper;

    @Inject
	private CommandBus commandBus;
	
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScenarioMockMvc;

    private Scenario scenario;

    private Timeline timeline;

    private Record record;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
		ScenarioResource scenarioResource = new ScenarioResource(scenarioRepository, timelineRepository,
				recordRepository, scenarioMapper, commandBus);
        this.restScenarioMockMvc = MockMvcBuilders.standaloneSetup(scenarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        scenario = new Scenario();
        scenario.setId(DEFAULT_ID);
        scenario.setAggregateId(DEFAULT_AGGREGATE_ID);
        scenario.setAggregateName(DEFAULT_AGGREGATE_NAME);
        scenario.setTimelineId(DEFAULT_TIMELINE_ID);
        scenario.setDescription(DEFAULT_DESCRIPTION);
        scenario.setType(DEFAULT_TYPE);
        scenario.setStatus(DEFAULT_STATUS);
        scenario.setStartDate(DEFAULT_START_DATE);
        scenario.setEndDate(DEFAULT_END_DATE);
        scenario.setCreationDate(DEFAULT_CREATION_DATE);
        scenario.setUserId(DEFAULT_USER_ID);
        
        timeline = new Timeline();
        timeline.setId(DEFAULT_ID);
        timeline.setAggregateId(DEFAULT_AGGREGATE_ID);
        timeline.setMajorVersion(DEFAULT_MAJOR_VERSION);
        timeline.setMain(DEFAULT_MAIN);

        record = new Record();
        record.setId(DEFAULT_ID);
        record.setMinorVersion(DEFAULT_MAJOR_VERSION);
        record.setCreationDate(DEFAULT_CREATION_DATE);
        record.setRecordDate(DEFAULT_CREATION_DATE);
        record.setType(DEFAULT_RECORD_TYPE);
        record.setTimelineId(DEFAULT_ID);

        // Initialize the database
        scenarioRepository.deleteAll();
        timelineRepository.deleteAll();
        recordRepository.deleteAll();
        
        Mockito.reset(commandBus);
    }

	@SuppressWarnings("unchecked")
	@Test
    @Transactional
    public void createScenario() throws Exception {
        // Create the Scenario
        ScenarioDTO scenarioDTO = scenarioMapper.scenarioToScenarioDTO(scenario);
        scenarioDTO.setId(null);

        restScenarioMockMvc.perform(post("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
                .andExpect(status().isOk());

		ArgumentCaptor<CommandMessage<CreateScenarioCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<CreateScenarioCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<CreateScenarioCommand>().getClass());
        Mockito.verify(commandBus, Mockito.times(1)).send(messageCaptor.capture());

        assertThat(messageCaptor.getValue().getMetadata().getAggregateId()).isEqualTo(DEFAULT_AGGREGATE_ID);
        assertThat(messageCaptor.getValue().getCommand().getAggregateName()).isEqualTo(DEFAULT_AGGREGATE_NAME);
        assertThat(messageCaptor.getValue().getCommand().getScenarioDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(messageCaptor.getValue().getCommand().getScenarioType()).isEqualTo(DEFAULT_TYPE.name());
        assertThat(messageCaptor.getValue().getMetadata().getTimelineDate()).isEqualTo(DEFAULT_START_DATE);
    }
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void endScenario() throws Exception {
		// Initialize the database
		scenarioRepository.saveAndFlush(scenario);
		timelineRepository.saveAndFlush(timeline);
		recordRepository.saveAndFlush(record);
		
		restScenarioMockMvc.perform(post("/api/scenarios/" + DEFAULT_ID + "/end?makeDefault=true"))
				.andExpect(status().isOk());
		
		ArgumentCaptor<CommandMessage<EndScenarioCommand>> messageCaptor = (ArgumentCaptor<CommandMessage<EndScenarioCommand>>) ArgumentCaptor
				.forClass(new CommandMessage<CreateScenarioCommand>().getClass());
		Mockito.verify(commandBus, Mockito.times(1)).send(messageCaptor.capture());
		
		assertThat(messageCaptor.getValue().getCommand().getScenarioId()).isEqualTo(DEFAULT_ID);
		assertThat(messageCaptor.getValue().getCommand().getMakeDefault()).isEqualTo(true);
	}
	
	@Test
	@Transactional
	public void getAllScenarios() throws Exception {
		// Initialize the database
		scenarioRepository.saveAndFlush(scenario);
		timelineRepository.saveAndFlush(timeline);
		recordRepository.saveAndFlush(record);
		
		// Get all the scenarios
		restScenarioMockMvc.perform(get("/api/scenarios?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId().toString())))
		.andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID.toString())))
		.andExpect(jsonPath("$.[*].aggregateName").value(hasItem(DEFAULT_AGGREGATE_NAME.toString())))
		.andExpect(jsonPath("$.[*].timelineId").value(hasItem(DEFAULT_TIMELINE_ID.toString())))
		.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
		.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
		.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
		.andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
		.andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
		.andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
		.andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
	}

    @Test
    @Transactional
    public void getAllScenariosForAggregate() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);
        timelineRepository.saveAndFlush(timeline);
        recordRepository.saveAndFlush(record);

        // Get all the scenarios
        restScenarioMockMvc.perform(get("/api/scenarios?sort=id,desc&aggregateId=" + DEFAULT_AGGREGATE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scenario.getId().toString())))
                .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID.toString())))
                .andExpect(jsonPath("$.[*].aggregateName").value(hasItem(DEFAULT_AGGREGATE_NAME.toString())))
                .andExpect(jsonPath("$.[*].timelineId").value(hasItem(DEFAULT_TIMELINE_ID.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);
        timelineRepository.saveAndFlush(timeline);
        recordRepository.saveAndFlush(record);

        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", scenario.getId()))
    		.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").value(scenario.getId().toString()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID.toString()))
            .andExpect(jsonPath("$.aggregateName").value(DEFAULT_AGGREGATE_NAME.toString()))
            .andExpect(jsonPath("$.timelineId").value(DEFAULT_TIMELINE_ID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScenario() throws Exception {
        // Get the scenario
        restScenarioMockMvc.perform(get("/api/scenarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);
        int databaseSizeBeforeUpdate = scenarioRepository.findAll().size();

        // Update the scenario
        Scenario updatedScenario = new Scenario();
        updatedScenario.setId(scenario.getId());
        updatedScenario.setAggregateId(UPDATED_AGGREGATE_ID);
        updatedScenario.setAggregateName(UPDATED_AGGREGATE_NAME);
        updatedScenario.setTimelineId(UPDATED_TIMELINE_ID);
        updatedScenario.setDescription(UPDATED_DESCRIPTION);
        updatedScenario.setType(UPDATED_TYPE);
        updatedScenario.setStatus(UPDATED_STATUS);
        updatedScenario.setStartDate(UPDATED_START_DATE);
        updatedScenario.setEndDate(UPDATED_END_DATE);
        updatedScenario.setCreationDate(UPDATED_CREATION_DATE);
        updatedScenario.setUserId(UPDATED_USER_ID);
        ScenarioDTO scenarioDTO = scenarioMapper.scenarioToScenarioDTO(updatedScenario);

        restScenarioMockMvc.perform(put("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scenarioDTO)))
                .andExpect(status().isOk());

        // Validate the Scenario in the database
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeUpdate);
        Scenario testScenario = scenarios.get(scenarios.size() - 1);
        assertThat(testScenario.getAggregateId()).isEqualTo(UPDATED_AGGREGATE_ID);
        assertThat(testScenario.getAggregateName()).isEqualTo(UPDATED_AGGREGATE_NAME);
        assertThat(testScenario.getTimelineId()).isEqualTo(UPDATED_TIMELINE_ID);
        assertThat(testScenario.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScenario.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testScenario.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testScenario.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testScenario.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testScenario.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testScenario.getUserId()).isEqualTo(UPDATED_USER_ID);
    }


    @Test
    public void updateNullScenario() throws Exception {
        restScenarioMockMvc.perform(put("/api/scenarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new ScenarioDTO())))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @Transactional
    public void deleteScenario() throws Exception {
        // Initialize the database
        scenarioRepository.saveAndFlush(scenario);
        int databaseSizeBeforeDelete = scenarioRepository.findAll().size();

        // Get the scenario
        restScenarioMockMvc.perform(delete("/api/scenarios/{id}", scenario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertThat(scenarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
