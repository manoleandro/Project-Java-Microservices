package br.org.ons.platform.web.rest;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import br.org.ons.platform.OnsPlatformApp;
import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.enumeration.RecordType;
import br.org.ons.platform.repository.RecordRepository;

/**
 * Test class for the RecordResource REST controller.
 *
 * @see RecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class RecordResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_ID = "AAAAA";

    private static final Long DEFAULT_MINOR_VERSION = 1L;
    private static final Long UPDATED_MINOR_VERSION = 2L;
    private static final String DEFAULT_CORRELATION_ID = "AAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATION_DATE);

    private static final ZonedDateTime DEFAULT_RECORD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_RECORD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_RECORD_DATE_STR = dateTimeFormatter.format(DEFAULT_RECORD_DATE);

    private static final RecordType DEFAULT_TYPE = RecordType.COMMAND;
    private static final RecordType UPDATED_TYPE = RecordType.EVENT;
    private static final String DEFAULT_PAYLOAD_TYPE = "AAAAA";
    private static final String UPDATED_PAYLOAD_TYPE = "BBBBB";

    private static final byte[] DEFAULT_METADATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_METADATA = TestUtil.createByteArray(2, "1");

    private static final byte[] DEFAULT_PAYLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PAYLOAD = TestUtil.createByteArray(2, "1");

    private static final String DEFAULT_TIMELINE_ID = "AAAAA";
    private static final String UPDATED_TIMELINE_ID = "BBBBB";
    
    @Inject
    private RecordRepository recordRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRecordMockMvc;

    private Record record;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RecordResource recordResource = new RecordResource(recordRepository);
        this.restRecordMockMvc = MockMvcBuilders.standaloneSetup(recordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        record = new Record();
        record.setId(DEFAULT_ID);
        record.setMinorVersion(DEFAULT_MINOR_VERSION);
        record.setCorrelationId(DEFAULT_CORRELATION_ID);
        record.setCreationDate(DEFAULT_CREATION_DATE);
        record.setRecordDate(DEFAULT_RECORD_DATE);
        record.setType(DEFAULT_TYPE);
        record.setPayloadType(DEFAULT_PAYLOAD_TYPE);
        record.setMetadata(DEFAULT_METADATA);
        record.setPayload(DEFAULT_PAYLOAD);
        record.setTimelineId(DEFAULT_TIMELINE_ID);
    }

    @Test
    @Transactional
    public void createRecord() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record
        restRecordMockMvc.perform(post("/api/records")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(record)))
                .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> records = recordRepository.findAll();
        assertThat(records).hasSize(databaseSizeBeforeCreate + 1);
        Record testRecord = records.get(records.size() - 1);
        assertThat(testRecord.getMinorVersion()).isEqualTo(DEFAULT_MINOR_VERSION);
        assertThat(testRecord.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testRecord.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRecord.getRecordDate()).isEqualTo(DEFAULT_RECORD_DATE);
        assertThat(testRecord.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRecord.getPayloadType()).isEqualTo(DEFAULT_PAYLOAD_TYPE);
        assertThat(testRecord.getMetadata()).isEqualTo(DEFAULT_METADATA);
        assertThat(testRecord.getPayload()).isEqualTo(DEFAULT_PAYLOAD);
        assertThat(testRecord.getTimelineId()).isEqualTo(DEFAULT_TIMELINE_ID);
    }

    @Test
    @Transactional
    public void getAllRecords() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get all the records
        restRecordMockMvc.perform(get("/api/records?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(record.getId())))
                .andExpect(jsonPath("$.[*].minorVersion").value(hasItem(DEFAULT_MINOR_VERSION.intValue())))
                .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
                .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
                .andExpect(jsonPath("$.[*].recordDate").value(hasItem(DEFAULT_RECORD_DATE_STR)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].payloadType").value(hasItem(DEFAULT_PAYLOAD_TYPE.toString())))
                .andExpect(jsonPath("$.[*].metadata").value(hasItem(Base64Utils.encodeToString(DEFAULT_METADATA))))
                .andExpect(jsonPath("$.[*].payload").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAYLOAD))))
                .andExpect(jsonPath("$.[*].timelineId").value(hasItem(DEFAULT_TIMELINE_ID)));
    }
    
    @Test
    @Transactional
    public void getAllRecordsByTimelineId() throws Exception {
    	// Initialize the database
    	recordRepository.saveAndFlush(record);
    	
    	// Get records by timelineId 
    	restRecordMockMvc.perform(get("/api/records?sort=id,desc&timelineId=" + DEFAULT_TIMELINE_ID))
		    	.andExpect(status().isOk())
		    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		    	.andExpect(jsonPath("$.[*].id").value(hasItem(record.getId())))
		    	.andExpect(jsonPath("$.[*].minorVersion").value(hasItem(DEFAULT_MINOR_VERSION.intValue())))
		    	.andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
		    	.andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
		    	.andExpect(jsonPath("$.[*].recordDate").value(hasItem(DEFAULT_RECORD_DATE_STR)))
		    	.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
		    	.andExpect(jsonPath("$.[*].payloadType").value(hasItem(DEFAULT_PAYLOAD_TYPE.toString())))
		    	.andExpect(jsonPath("$.[*].metadata").value(hasItem(Base64Utils.encodeToString(DEFAULT_METADATA))))
		    	.andExpect(jsonPath("$.[*].payload").value(hasItem(Base64Utils.encodeToString(DEFAULT_PAYLOAD))))
		    	.andExpect(jsonPath("$.[*].timelineId").value(hasItem(DEFAULT_TIMELINE_ID)));
    }

    @Test
    @Transactional
    public void getRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", record.getId()))
	        	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	            .andExpect(jsonPath("$.id").value(record.getId()))
	            .andExpect(jsonPath("$.minorVersion").value(DEFAULT_MINOR_VERSION.intValue()))
	            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
	            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
	            .andExpect(jsonPath("$.recordDate").value(DEFAULT_RECORD_DATE_STR))
	            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
	            .andExpect(jsonPath("$.payloadType").value(DEFAULT_PAYLOAD_TYPE.toString()))
	            .andExpect(jsonPath("$.metadata").value(Base64Utils.encodeToString(DEFAULT_METADATA)))
	            .andExpect(jsonPath("$.payload").value(Base64Utils.encodeToString(DEFAULT_PAYLOAD)))
	            .andExpect(jsonPath("$.timelineId").value(DEFAULT_TIMELINE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingRecord() throws Exception {
        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Update the record
        Record updatedRecord = new Record();
        updatedRecord.setId(record.getId());
        updatedRecord.setMinorVersion(UPDATED_MINOR_VERSION);
        updatedRecord.setCorrelationId(UPDATED_CORRELATION_ID);
        updatedRecord.setCreationDate(UPDATED_CREATION_DATE);
        updatedRecord.setRecordDate(UPDATED_RECORD_DATE);
        updatedRecord.setType(UPDATED_TYPE);
        updatedRecord.setPayloadType(UPDATED_PAYLOAD_TYPE);
        updatedRecord.setMetadata(UPDATED_METADATA);
        updatedRecord.setPayload(UPDATED_PAYLOAD);
        updatedRecord.setTimelineId(UPDATED_TIMELINE_ID);

        restRecordMockMvc.perform(put("/api/records")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRecord)))
                .andExpect(status().isOk());

        // Validate the Record in the database
        List<Record> records = recordRepository.findAll();
        assertThat(records).hasSize(databaseSizeBeforeUpdate);
        Record testRecord = records.get(records.size() - 1);
        assertThat(testRecord.getMinorVersion()).isEqualTo(UPDATED_MINOR_VERSION);
        assertThat(testRecord.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testRecord.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRecord.getRecordDate()).isEqualTo(UPDATED_RECORD_DATE);
        assertThat(testRecord.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRecord.getPayloadType()).isEqualTo(UPDATED_PAYLOAD_TYPE);
        assertThat(testRecord.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testRecord.getPayload()).isEqualTo(UPDATED_PAYLOAD);
        assertThat(testRecord.getTimelineId()).isEqualTo(UPDATED_TIMELINE_ID);
    }

    @Test
    @Transactional
    public void updateRecordNullId() throws Exception {
        record.setId(null);
        
        restRecordMockMvc.perform(put("/api/records")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(record)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void deleteRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);
        int databaseSizeBeforeDelete = recordRepository.findAll().size();

        // Get the record
        restRecordMockMvc.perform(delete("/api/records/{id}", record.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Record> records = recordRepository.findAll();
        assertThat(records).hasSize(databaseSizeBeforeDelete - 1);
    }
}
