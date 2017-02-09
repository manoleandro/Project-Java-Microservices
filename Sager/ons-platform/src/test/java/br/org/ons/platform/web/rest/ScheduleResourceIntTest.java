package br.org.ons.platform.web.rest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import br.org.ons.platform.OnsPlatformApp;
import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.scheduler.SendCommandJob;
import br.org.ons.platform.security.jwt.TokenProvider;

/**
 * Teste para o serviço ScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPlatformApp.class)
public class ScheduleResourceIntTest {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

	private static final String JOB_ID = "AAAAA";

	private static final String JOB_GROUP = "SendCommandsJob";
	
	private final Date jobDate = Date.from(
			ZonedDateTime.of(ZonedDateTime.now().getYear() + 1, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant());

	private final ZonedDateTime newJobDate = ZonedDateTime.of(ZonedDateTime.now().getYear() + 2, 1, 1, 0, 0, 0, 0,
			ZoneId.systemDefault());

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Inject
	private Scheduler scheduler;
	
	@Inject
	private ObjectMapper objectMapper;
	
	@Inject
	private TokenProvider tokenProvider;

	@Inject
	private CommandBus commandBus;
	
	private MockMvc restScheduleMockMvc;

    @Before
    public void setup() throws Exception {
		restScheduleMockMvc = MockMvcBuilders
				.standaloneSetup(new ScheduleResource(scheduler, tokenProvider, objectMapper))
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setMessageConverters(jacksonMessageConverter)
				.build();
        
        reset(commandBus);

		scheduler.scheduleJob(
				newJob(SendCommandJob.class)
					.withIdentity(JOB_ID, JOB_GROUP)
					.storeDurably()
					.build(), 
				newTrigger()
					.startAt(jobDate)
					.build());
    }

    @After
    public void teardown() throws Exception {
    	scheduler.clear();
    }
    
	@Test
    public void getScheduleDetail() throws Exception {
    	restScheduleMockMvc.perform(get("/api/schedule-detail/" + JOB_ID)
    			.accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + JOB_ID + "\""));
    }
	
	@Test
	public void getScheduleDetailNotFound() throws Exception {
    	scheduler.clear();
    	
		restScheduleMockMvc.perform(get("/api/schedule-detail/" + JOB_ID)
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().string(""));
	}
	
	@Test
	public void getSchedules() throws Exception {
		restScheduleMockMvc.perform(get("/api/schedule-detail")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(content().string("[\"" + JOB_ID + "\"]"));
	}

	@Test
    public void getSchedulesTriggers() throws Exception {
    	restScheduleMockMvc.perform(get("/api/schedule-triggers")
    			.param("nameJobKey", JOB_ID)
    			.accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(Arrays.asList(jobDate))));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void scheduleCommands() throws Exception {
    	scheduler.clear();
    	
    	List<CommandMessage<GenericCommand>> commands = new ArrayList<>();    	
    	CommandMessage<GenericCommand> command = new CommandMessage<>();
    	command.setCommand(new GenericCommand());
    	command.getMetadata().setId(JOB_ID);
    	commands.add(command);

    	restScheduleMockMvc.perform(post("/api/schedule")
    			.param("scheduleDate", dateTimeFormatter.format(ZonedDateTime.now().plusSeconds(10)))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(command)))
                .andExpect(status().isOk());

        verify(commandBus, after(20000).times(1)).send(any(command.getClass()));
    }
    
    @Test
    public void scheduleCommandsPastDate() throws Exception {
		ZonedDateTime pastDate = ZonedDateTime.now().minusDays(1);
		
    	scheduler.clear();
    	
    	CommandMessage<GenericCommand> command = new CommandMessage<>();
    	command.setCommand(new GenericCommand());
    	command.getMetadata().setId(JOB_ID);
    	
    	restScheduleMockMvc.perform(post("/api/schedule")
    			.param("scheduleDate", dateTimeFormatter.format(pastDate))
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
    			.content(TestUtil.convertObjectToJsonBytes(command)))
    			.andExpect(status().isOk());

		assertThat(scheduler.getTriggersOfJob(JobKey.jobKey(JOB_ID, JOB_GROUP)).get(0).getNextFireTime()
				.after(Date.from(pastDate.toInstant())));
    }

	@SuppressWarnings("unchecked")
	@Test
    public void scheduleCommandsJsonException() throws Exception {
		ObjectMapper objectMapperMock = mock(ObjectMapper.class);
		when(objectMapperMock.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
		
		restScheduleMockMvc = MockMvcBuilders
				.standaloneSetup(new ScheduleResource(scheduler, tokenProvider, objectMapperMock))
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setMessageConverters(jacksonMessageConverter)
				.build();
    	
    	CommandMessage<GenericCommand> command = new CommandMessage<>();
    	command.setCommand(new GenericCommand());
    	command.getMetadata().setId(JOB_ID);

    	restScheduleMockMvc.perform(post("/api/schedule")
    			.param("scheduleDate", dateTimeFormatter.format(ZonedDateTime.now().plusSeconds(10)))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(command)))
                .andExpect(status().isInternalServerError());
    }
	
	@Test
    public void rescheduleCommands() throws Exception {
    	restScheduleMockMvc.perform(put("/api/schedule")
    			.param("jobId", JOB_ID)
    			.param("scheduleDate", dateTimeFormatter.format(newJobDate)))
                .andExpect(status().isOk());
    	
		assertThat(scheduler.getTriggersOfJob(JobKey.jobKey(JOB_ID, JOB_GROUP)).get(0).getNextFireTime()
				.equals(Date.from(newJobDate.toInstant())));
    }
	
	@Test
	public void rescheduleCommandsPastDate() throws Exception {
		ZonedDateTime pastDate = ZonedDateTime.now().minusDays(1);
		
		restScheduleMockMvc.perform(put("/api/schedule")
				.param("jobId", JOB_ID)
				.param("scheduleDate", dateTimeFormatter.format(pastDate)))
				.andExpect(status().isOk());
		
		assertThat(scheduler.getTriggersOfJob(JobKey.jobKey(JOB_ID, JOB_GROUP)).get(0).getNextFireTime()
				.after(Date.from(pastDate.toInstant())));
	}

	@Test
	public void deleteAllJobSchedule() throws Exception {
		restScheduleMockMvc.perform(delete("/api/schedule").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		List<JobKey> jobs = new ArrayList<>();
		for (String group : scheduler.getJobGroupNames()) {
			jobs.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group)));
		}
		assertThat(jobs.isEmpty());
	}
	
	@Test
	public void deleteAllJobScheduleConflict() throws Exception {
		Scheduler schedulerMock = mock(Scheduler.class);
		when(schedulerMock.getJobGroupNames()).thenReturn(Lists.newArrayList(JOB_GROUP));
		when(schedulerMock.getJobKeys(any())).thenReturn(Collections.singleton(JobKey.jobKey(JOB_ID, JOB_GROUP)));
		when(schedulerMock.deleteJob(any())).thenReturn(false);
		
		restScheduleMockMvc = MockMvcBuilders
				.standaloneSetup(new ScheduleResource(schedulerMock, tokenProvider, objectMapper))
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setMessageConverters(jacksonMessageConverter)
				.build();
		
		restScheduleMockMvc.perform(delete("/api/schedule").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
    public void cancelScheduleCommands() throws Exception {
    	restScheduleMockMvc.perform(delete("/api/schedule/" + JOB_ID)
    			.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + JOB_ID + "\""));

		List<JobKey> jobs = new ArrayList<>();
		for (String group : scheduler.getJobGroupNames()) {
			jobs.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group)));
		}
		
    	assertThat(jobs.isEmpty());
    }
	
	@Test
	public void cancelScheduleCommandsNotFound() throws Exception {
		restScheduleMockMvc.perform(delete("/api/schedule/lala")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
    public void handleSchedulerException() throws Exception {
		Scheduler schedulerMock = mock(Scheduler.class);
		when(schedulerMock.getJobDetail(any())).thenThrow(new SchedulerException());
		
		restScheduleMockMvc = MockMvcBuilders
				.standaloneSetup(new ScheduleResource(schedulerMock, tokenProvider, objectMapper))
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setMessageConverters(jacksonMessageConverter)
				.build();
		
    	restScheduleMockMvc.perform(get("/api/schedule-detail/" + JOB_ID))
				.andExpect(status().isInternalServerError());
    }
}
