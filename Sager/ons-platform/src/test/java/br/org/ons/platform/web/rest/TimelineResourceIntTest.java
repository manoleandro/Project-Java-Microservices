package br.org.ons.platform.web.rest;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.repository.TimelineRepository;


/**
 * Test class for the TimelineResource REST controller.
 *
 * @see TimelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class TimelineResourceIntTest {

	private static final String DEFAULT_ID = "AAAAA";

	private static final String DEFAULT_AGGREGATE_ID = "AAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBB";

    private static final Long DEFAULT_MAJOR_VERSION = 1L;
    private static final Long UPDATED_MAJOR_VERSION = 2L;

    private static final Boolean DEFAULT_MAIN = false;
    private static final Boolean UPDATED_MAIN = true;

    @Inject
    private TimelineRepository timelineRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimelineMockMvc;

    private Timeline timeline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimelineResource timelineResource = new TimelineResource(timelineRepository);
        this.restTimelineMockMvc = MockMvcBuilders.standaloneSetup(timelineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeline = new Timeline();
        timeline.setId(DEFAULT_ID);
        timeline.setAggregateId(DEFAULT_AGGREGATE_ID);
        timeline.setMajorVersion(DEFAULT_MAJOR_VERSION);
        timeline.setMain(DEFAULT_MAIN);
    }

    @Test
    @Transactional
    public void createTimeline() throws Exception {
        int databaseSizeBeforeCreate = timelineRepository.findAll().size();

        // Create the Timeline

        restTimelineMockMvc.perform(post("/api/timelines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeline)))
                .andExpect(status().isCreated());

        // Validate the Timeline in the database
        List<Timeline> timelines = timelineRepository.findAll();
        assertThat(timelines).hasSize(databaseSizeBeforeCreate + 1);
        Timeline testTimeline = timelines.get(timelines.size() - 1);
        assertThat(testTimeline.getAggregateId()).isEqualTo(DEFAULT_AGGREGATE_ID);
        assertThat(testTimeline.getMajorVersion()).isEqualTo(DEFAULT_MAJOR_VERSION);
        assertThat(testTimeline.getMain()).isEqualTo(DEFAULT_MAIN);
    }

    @Test
    @Transactional
    public void getAllTimelines() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get all the timelines
        restTimelineMockMvc.perform(get("/api/timelines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeline.getId())))
                .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID.toString())))
                .andExpect(jsonPath("$.[*].majorVersion").value(hasItem(DEFAULT_MAJOR_VERSION.intValue())))
                .andExpect(jsonPath("$.[*].main").value(hasItem(DEFAULT_MAIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", timeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").value(timeline.getId()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID.toString()))
            .andExpect(jsonPath("$.majorVersion").value(DEFAULT_MAJOR_VERSION.intValue()))
            .andExpect(jsonPath("$.main").value(DEFAULT_MAIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeline() throws Exception {
        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeUpdate = timelineRepository.findAll().size();

        // Update the timeline
        Timeline updatedTimeline = new Timeline();
        updatedTimeline.setId(timeline.getId());
        updatedTimeline.setAggregateId(UPDATED_AGGREGATE_ID);
        updatedTimeline.setMajorVersion(UPDATED_MAJOR_VERSION);
        updatedTimeline.setMain(UPDATED_MAIN);

        restTimelineMockMvc.perform(put("/api/timelines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTimeline)))
                .andExpect(status().isOk());

        // Validate the Timeline in the database
        List<Timeline> timelines = timelineRepository.findAll();
        assertThat(timelines).hasSize(databaseSizeBeforeUpdate);
        Timeline testTimeline = timelines.get(timelines.size() - 1);
        assertThat(testTimeline.getAggregateId()).isEqualTo(UPDATED_AGGREGATE_ID);
        assertThat(testTimeline.getMajorVersion()).isEqualTo(UPDATED_MAJOR_VERSION);
        assertThat(testTimeline.getMain()).isEqualTo(UPDATED_MAIN);
    }

    @Test
    @Transactional
    public void updateTimelineNullId() throws Exception {
        timeline.setId(null);
        
        restTimelineMockMvc.perform(put("/api/timelines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeline)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeDelete = timelineRepository.findAll().size();

        // Get the timeline
        restTimelineMockMvc.perform(delete("/api/timelines/{id}", timeline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Timeline> timelines = timelineRepository.findAll();
        assertThat(timelines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
