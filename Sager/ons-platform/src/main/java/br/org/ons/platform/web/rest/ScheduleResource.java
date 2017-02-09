package br.org.ons.platform.web.rest;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.scheduler.SendCommandJob;
import br.org.ons.platform.security.jwt.TokenProvider;

/**
 * API REST para as telas Manter Agendamento
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleResource.class);

	private static final String JOB_GROUP = "SendCommandsJob";
	
	private static final long SECONDS = 5l;
	
	private Scheduler scheduler;

	private ObjectMapper objectMapper;

	private TokenProvider tokenProvider;

	@Inject
	public ScheduleResource(Scheduler scheduler, TokenProvider tokenProvider, ObjectMapper objectMapper) {
		this.scheduler = scheduler;
		this.tokenProvider = tokenProvider;
		this.objectMapper = objectMapper;
	}

   @RequestMapping(value = "/schedule-detail/{id}", 
		   method = RequestMethod.GET, 
		   produces = MediaType.APPLICATION_JSON_VALUE)
	public String getScheduleDetail(@PathVariable String id) throws SchedulerException {
		LOG.debug("getScheduleDetail: {}" + id);
		JobKey jobKey = JobKey.jobKey(id, JOB_GROUP);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail != null) {
			return jobDetail.getKey().getName();
		}
		return null;
	}
	
	
	@RequestMapping( value="/schedule-detail", 
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<String> getSchedules() throws SchedulerException {
		LOG.debug("getSchedules: {}");
		// enumerate each job group
		List<String> result = new ArrayList<>();
		for (String group : scheduler.getJobGroupNames()) {
			// enumerate each job in group
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
				LOG.debug("Found job identified by: " + jobKey);
				result.add(jobKey.getName());
			}
		}
		return result;
	}
	
	@RequestMapping( value="/schedule-triggers", 
			method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Date> getSchedulesTriggers(@RequestParam String id) throws SchedulerException {
		LOG.debug("getSchedulesTriggers: {}");
		// enumerate each job group
		List<Date> dates = new ArrayList<>();
		JobKey jobKey = JobKey.jobKey(id, JOB_GROUP);
		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
		for (Trigger trigger : triggers) {
			dates.add(trigger.getNextFireTime());
		}
		return dates;
	}
	
	@RequestMapping(value = "/schedule",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> scheduleCommands(@RequestBody List<CommandMessage<GenericCommand>> commands,
			@RequestParam String id, @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime scheduleDate)
			throws SchedulerException {
		LOG.debug("schedulingCommands: {}", commands);
		JobDataMap jobData = new JobDataMap();
		jobData.put("token", tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
		try {
			jobData.put("commands", objectMapper.writeValueAsString(commands));
		} catch (JsonProcessingException e) {
			LOG.error("JsonProcessingException {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		// OBS - jcardoso - setando Id do comando no jobId para poder
		// identificar que o job rodou pelo CorrelationId do Evento do comando
		// schedulado.
		JobDetail job = newJob(SendCommandJob.class)
				.withIdentity(id, JOB_GROUP)
				.storeDurably()
				.usingJobData(jobData)
				.build();

		// Converte para o TimeZone local
		ZonedDateTime localDate = scheduleDate.withZoneSameInstant(ZoneId.systemDefault());

		// Se for uma data anterior de now, então atualiza para disparar now.
		if (localDate.isBefore(ZonedDateTime.now())) {
			localDate = ZonedDateTime.now().plusSeconds(SECONDS);
		}

		Trigger trigger = newTrigger()
				.withIdentity(IdGenerator.newId(), JOB_GROUP)
				.startNow()
				.withSchedule(cronSchedule(toCronExpression(localDate)))
				.build();

		scheduler.scheduleJob(job, trigger);

		return ResponseEntity.ok().body(id);
	}
	
	@RequestMapping(value = "/schedule",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> rescheduleCommands(@RequestParam String id,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime scheduleDate) throws SchedulerException {
		LOG.debug("reschedulingCommand: {}", id);

		// Converte para o TimeZone local
		ZonedDateTime localDate = scheduleDate.withZoneSameInstant(ZoneId.systemDefault());

		// Se for uma data anterior de now, então atualiza para disparar now.
		if (localDate.isBefore(ZonedDateTime.now())) {
			localDate = ZonedDateTime.now().plusSeconds(SECONDS);
		}

		Trigger newTrigger = newTrigger()
				.withIdentity(IdGenerator.newId(), JOB_GROUP)
				.startNow()
				.withSchedule(cronSchedule(toCronExpression(localDate)))
				.build();

		for (Trigger trigger : scheduler.getTriggersOfJob(JobKey.jobKey(id, JOB_GROUP))) {
			scheduler.rescheduleJob(trigger.getKey(), newTrigger);
		}

		return ResponseEntity.ok().body(id);
	}

	/**
	 * Retorna uma expressão CRON a partir de uma data
	 * @param dateTime Data a ser convertida
	 * @return Expressão CRON
	 */
	private String toCronExpression(ZonedDateTime dateTime) {
		return
			// Seconds
			dateTime.getSecond() + " "
			// Minutes
			+ dateTime.getMinute() + " "
			// Hours
			+ dateTime.getHour() + " "
			// Day of month
			+ dateTime.getDayOfMonth() + " "
			// Month
			+ dateTime.getMonthValue() + " "
			// Day of week
			+ "?" + " "
			// Year
			+ dateTime.getYear();
	}

	@RequestMapping(value = "/schedule", 
			method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> deleteAllJobSchedule() throws SchedulerException {
		List<String> jobKeysFailed = deleteJobsAndReturnFailedJobKeys();

		if (jobKeysFailed.isEmpty()) {
			return ResponseEntity.ok().body(null);
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).body(jobKeysFailed);
	}

	/**
	 * Remove todos os jobs do scheduler e retornar as keys dos jobs que não
	 * puderam ser removidos
	 * 
	 * @return Keys dos jobs que falharam a serem removidos
	 * @throws SchedulerException 
	 */
	private List<String> deleteJobsAndReturnFailedJobKeys() throws SchedulerException {
		List<String> failedJobKeys = new ArrayList<>();
		for (String group : scheduler.getJobGroupNames()) {
			// enumerate each job in group
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
				if (!scheduler.deleteJob(jobKey)) {
					failedJobKeys.add(jobKey.getName());
				}
			}
		}
		return failedJobKeys;
	}

	@RequestMapping(value = "/schedule/{id}", 
			method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cancelScheduleCommands(@PathVariable String id) throws SchedulerException {
		LOG.debug("cancelScheduleCommand: {}", id);
		
		if (scheduler.deleteJob(JobKey.jobKey(id, JOB_GROUP))) {
			return ResponseEntity.ok().body(id);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
	}
	
	@ExceptionHandler(SchedulerException.class)
    public ResponseEntity<String> handleSchedulerException(SchedulerException e) {
		LOG.error("SchedulerException {}", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
