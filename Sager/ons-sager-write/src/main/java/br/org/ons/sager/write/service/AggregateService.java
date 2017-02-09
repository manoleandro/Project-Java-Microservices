package br.org.ons.sager.write.service;

import java.lang.reflect.ParameterizedType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ons.sager.write.repository.AggregateRepository;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CreateAggregateCommand;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.ScenarioCommandModification;

public abstract class AggregateService<T extends Aggregate> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	protected AggregateRepository<T> repository;

	protected <C extends Command, R> ResponseEntity<ResultMessage<R>> execute(CommandMessage<C> message) {
		ResultMessage<R> result = new ResultMessage<>();
		try {
			List<T> aggregates;
			if (message.getCommand() instanceof CreateAggregateCommand) {
				log.debug("- createMain: {}, {}", message.getMetadata().getAggregateId(),
						message.getMetadata().getTimelineDate());
				aggregates = new ArrayList<>();
				aggregates.add(repository.createMain(message.getMetadata().getAggregateId(),
						message.getMetadata().getTimelineDate()));
				log.debug("- aggregates after createMain: " + aggregates);
			} else {
				log.debug("- checkOutLatestSnapshotBeforeDate: {}, {}, {}", message.getMetadata().getAggregateId(),
						message.getMetadata().getMajorVersion(), message.getMetadata().getTimelineDate());
				aggregates = repository.checkOutLatestSnapshotBeforeDate(message.getMetadata().getAggregateId(),
						message.getMetadata().getMajorVersion(), message.getMetadata().getTimelineDate());
				log.debug("- aggregates after checkOutLatestSnapshotBeforeDate: {}", aggregates);
			}
			
			List<R> results = new ArrayList<>();
			for (T aggregate : aggregates) {
				log.debug("- getUpdatesAfterMinorVersionBeforeDate: {}, {}, {}, {}",
						message.getMetadata().getAggregateId(), aggregate.getMajorVersion(),
						aggregate.getMinorVersion(), message.getMetadata().getTimelineDate());
				repository.getUpdatesAfterMinorVersionBeforeDate(message.getMetadata().getAggregateId(),
						aggregate.getMajorVersion(), aggregate.getMinorVersion(), message.getMetadata().getTimelineDate())
						.forEach((event) -> aggregate.replay(event));
				log.debug("- aggregate after replay events: {}", aggregate);
				
				log.debug("- command: {}", message.getCommand());
				R res;
				if (message.getModification() != null) {
					log.debug("- command modification: {}", message.getModification());
					res = aggregate.modifyAndApply(message.getCommand(), message.getModification());
					log.debug("- modified command: {}", message.getCommand());
				} else if (aggregate.getScenarioName() != null) {
					log.debug("- scenario modification: {}", aggregate.getScenarioName());
					ScenarioCommandModification modification = new ScenarioCommandModification();
					modification.setScenarioName(aggregate.getScenarioName());
					res = aggregate.modifyAndApply(message.getCommand(), modification);
					log.debug("- modified command: {}", message.getCommand());
				}
				else {
					res = aggregate.apply(message.getCommand());
				}
				results.add(res);
				log.debug("- aggregate after handle command: {}", aggregate);
				log.debug("- result: {}", res);
			}

			log.debug("- checkIn");
			repository.checkIn(message.getCommand(), message.getMetadata(), aggregates);
			
			result.setResults(results);
			result.setStatusCode(HttpStatus.OK.value());
			result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
		} catch (IllegalArgumentException e) {
			log.error("INVALID COMMAND EXCEPTION {}", e);
			result.setStatusCode(HttpStatus.BAD_REQUEST.value());
			result.setStatusMessage(e.getMessage());
		} catch (IllegalStateException e) {
			log.error("INVALID STATE EXCEPTION {}", e);
			result.setStatusCode(HttpStatus.CONFLICT.value());
			result.setStatusMessage(e.getMessage());
		} catch (Exception e) {
			log.error("RUNTIME EXCEPTION {}", e);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setStatusMessage(e.getMessage());
		} finally {
			// TODO: UNDO CHECKOUT
		}
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	@RequestMapping(value = "/{id}", 
			method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<T> get(@PathVariable String id, @RequestParam(required = false) Long majorVersion,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime timelineDate) {
		T aggregate = repository.checkOutLatestSnapshotBeforeDate(id, majorVersion, timelineDate).get(0);
		if (aggregate == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		repository.getUpdatesAfterMinorVersionBeforeDate(id, aggregate.getMajorVersion(), aggregate.getMinorVersion(),
				timelineDate).forEach((event) -> aggregate.replay(event));
		return new ResponseEntity<>(aggregate, HttpStatus.OK);
	}

	@PostConstruct
	@SuppressWarnings("unchecked")
	private void initRepository() {
		repository.setAggregateClass(
				(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
}
