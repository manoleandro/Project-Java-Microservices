package br.org.ons.platform.service;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.OnsPlatformApp;
import br.org.ons.platform.bus.EventBus;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.EventMetadata;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.service.EventSourcingRepositoryService.ConflictException;
import br.org.ons.platform.service.EventSourcingRepositoryService.NotFoundException;
import br.org.ons.platform.web.rest.dto.Commit;
import br.org.ons.platform.domain.model.GenericAggregate;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.domain.model.GenericEvent;

/**
 * Testes para os casos de exceção de EventSourcingRepositoryService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class EventSourcingRepositoryExceptionTest {

	private static final String AGGREGATE_ID = "AAAAA";
	private static final String AGGREGATE_TYPE = "AAAAA";
	private static final String AGGREGATE_NAME = "AAAAA";
	private static final Long MAJOR_VERSION = 1L;
	private static final Long MINOR_VERSION = 0L;

	private static final String COMMAND_NAME = "AAAAA";
	private static final String COMMAND_ID = "AAAAA";
	private static final ZonedDateTime COMMAND_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L),
			ZoneId.systemDefault());

	private static final String EVENT_NAME = "AAAAA";

	private static final ZonedDateTime QUERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(1L),
			ZoneId.systemDefault());

	@MockBean
	private ObjectMapper objectMapper;

	@Inject
	private TimelineRepository timelineRepository;

	@Inject
	private RecordRepository recordRepository;

	@MockBean
	private EventBus eventBus;

	@MockBean
	private ScenarioManager scenarioManager;
	
	private EventSourcingRepositoryService repository;
	
    @Before
    public void initTest() {
    	repository = new EventSourcingRepositoryService(timelineRepository, recordRepository, eventBus, scenarioManager,
    			objectMapper);
    	// Reiniciando a base de dados
    	timelineRepository.deleteAll();
    	recordRepository.deleteAll();
    }
    
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCreateMainWriteSnapshotError() throws Exception {
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.writeValueAsBytes(anyObject())).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
	}

	@Test(expected=OnsRuntimeException.class)
	public void testCheckOutNullSnapshot() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
    	recordRepository.deleteAll();
		repository.checkOutLatestSnapshotBeforeDate(AGGREGATE_ID, MAJOR_VERSION, QUERY_DATE);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckOutReadValueError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.readValue(any(byte[].class), any(Class.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.checkOutLatestSnapshotBeforeDate(AGGREGATE_ID, MAJOR_VERSION, QUERY_DATE);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckOutNullAggregate() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.readValue(any(byte[].class), any(Class.class))).thenReturn(null);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.checkOutLatestSnapshotBeforeDate(AGGREGATE_ID, MAJOR_VERSION, QUERY_DATE);
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckInWriteCommandMetadataError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.writeValueAsBytes(any(CommandMetadata.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.checkIn(commit());
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckInWriteCommandError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.writeValueAsBytes(any(Command.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);

		repository.checkIn(commit());
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckInWriteEventMetadataError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.writeValueAsBytes(any(EventMetadata.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);

		repository.checkIn(commit());
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCheckInWriteEventError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.writeValueAsBytes(any(Event.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);

		repository.checkIn(commit());
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testGetUpdatesReadValueError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);

    	repository.checkIn(commit());
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.readValue(any(byte[].class), any(Class.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.getUpdatesAfterMinorVersionBeforeDate(AGGREGATE_ID, MAJOR_VERSION, MINOR_VERSION, QUERY_DATE);
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testGetCommitsReadCommandError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);

    	repository.checkIn(commit());
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.readValue(any(byte[].class), eq(GenericCommand.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.getCommitsAfterMinorVersion(AGGREGATE_ID, MAJOR_VERSION, MINOR_VERSION);
	}

	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testGetCommitsReadMetadataError() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);

    	repository.checkIn(commit());
		
		ObjectMapper failMapper = mock(ObjectMapper.class);
		when(failMapper.readValue(any(byte[].class), eq(CommandMetadata.class))).thenThrow(IOException.class);
		ReflectionTestUtils.setField(repository, "objectMapper", failMapper);
		
		repository.getCommitsAfterMinorVersion(AGGREGATE_ID, MAJOR_VERSION, MINOR_VERSION);
	}

	@Test(expected=NotFoundException.class)
	public void testCheckOutNotFound() throws Exception {
		repository.checkOutLatestSnapshotBeforeDate(AGGREGATE_ID, MAJOR_VERSION, QUERY_DATE);
	}

	@Test(expected=NotFoundException.class)
	public void testGetUpdatesNotFound() throws Exception {
		repository.getUpdatesAfterMinorVersionBeforeDate(AGGREGATE_ID, MAJOR_VERSION, MINOR_VERSION, QUERY_DATE);
	}

	@Test(expected=ConflictException.class)
	public void testCreateMainFail() throws Exception {
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
		repository.createMain(AGGREGATE_ID, AGGREGATE_TYPE, COMMAND_DATE);
	}

	private Commit commit() {
		Commit commit = new Commit();
		GenericCommand command = new GenericCommand();
		command.setName(COMMAND_NAME);
		commit.setCommand(command);
		CommandMetadata metadata = new CommandMetadata();
		metadata.setId(COMMAND_ID);
		metadata.setTimelineDate(COMMAND_DATE);
		metadata.setMajorVersion(MAJOR_VERSION);
		metadata.setMinorVersion(MINOR_VERSION);
		commit.setMetadata(metadata);
		GenericAggregate aggregate = new GenericAggregate();
		aggregate.setId(AGGREGATE_ID);
		aggregate.setAggregateType(AGGREGATE_TYPE);
		aggregate.setName(AGGREGATE_NAME);
		aggregate.setMajorVersion(MAJOR_VERSION);
		aggregate.setMinorVersion(MINOR_VERSION);
		GenericEvent event = new GenericEvent();
		event.setName(EVENT_NAME);
		aggregate.getEventsToSave().add(event);
		commit.getAggregates().add(aggregate);
		return commit;
	}
}
