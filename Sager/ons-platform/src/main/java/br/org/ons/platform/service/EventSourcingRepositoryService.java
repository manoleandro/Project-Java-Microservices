package br.org.ons.platform.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.bus.EventBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.domain.enumeration.RecordType;
import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;
import br.org.ons.platform.domain.util.RecordFactory;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.web.rest.dto.Commit;
import br.org.ons.platform.domain.model.GenericAggregate;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.domain.model.GenericEvent;
import br.org.ons.platform.web.rest.dto.Version;

/**
 * Repositório para persistência de Aggregates utilizando Event Sourcing, com
 * suporte a múltiplas timelines por Aggregate.
 * 
 * Obs.: Para facilitar a compreensão, os nomes dos métodos remetem a operações
 * de versionamento de código-fonte
 */
@RestController
@RequestMapping("/api/es-repository")
public class EventSourcingRepositoryService {

	private static final Logger LOG = LoggerFactory.getLogger(EventSourcingRepositoryService.class);
	
	private static final Long SNAPSHOT_EVENT_COUNT = 10L;

	private TimelineRepository timelineRepository;

	private RecordRepository recordRepository;
	
	private EventBus eventBus;

	private ScenarioManager scenarioManager;
	
	private ObjectMapper objectMapper;

	@Inject
	public EventSourcingRepositoryService(TimelineRepository timelineRepository, RecordRepository recordRepository,
			EventBus eventBus, ScenarioManager scenarioManager, ObjectMapper objectMapper) {
		this.timelineRepository = timelineRepository;
		this.recordRepository = recordRepository;
		this.eventBus = eventBus;
		this.scenarioManager = scenarioManager;
		this.objectMapper = objectMapper;
	}

	/**
	 * Cria uma nova instância de Aggregate, criando e inicializando sua
	 * Timeline MAIN
	 * 
	 * @param aggregateId
	 *            ID do Aggregate
	 * @param aggregateType
	 *            Nome completo da classe do Aggregate
	 * @param startDate
	 *            Data de início da timeline
	 * @return Retorna o estado inicial do Aggregate (vazio)
	 */
	@RequestMapping(value = "/main", 
			method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericAggregate createMain(@RequestParam String aggregateId, @RequestParam String aggregateType,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startDate) {
		// Verifica se existe uma timeline
		Timeline timeline = timelineRepository.findFirstByAggregateIdOrderByMajorVersion(aggregateId);
		// Se não existir, cria uma nova timeline main
		if (timeline == null) {
			timeline = new Timeline();
			timeline.setId(IdGenerator.newId());
			timeline.setAggregateId(aggregateId);
			timeline.setAggregateType(aggregateType);
			timeline.setMain(true);
			timeline.setMajorVersion(1L);
			timeline.setParentId(null);
			timeline = timelineRepository.save(timeline);
		} else {
			throw new ConflictException();
		}
		// Salva o primeiro snapshot
		GenericAggregate aggregate = new GenericAggregate();
		aggregate.setId(aggregateId);
		aggregate.setAggregateType(aggregateType);
		aggregate.setMajorVersion(timeline.getMajorVersion());
		aggregate.setMinorVersion(0L);
		saveSnapshotRecord(timeline, aggregate, startDate);
		
		scenarioManager.create(timeline, startDate, "Principal",
				ScenarioStatus.ACTIVE.name(), ScenarioType.DEFAULT.name());
		
		return aggregate;
	}

	/**
	 * Cria uma timeline BRANCH a partir da timeline MAIN de um Aggregate
	 * 
	 * @param aggregateId
	 *            ID do Aggregate
	 * @param branchStartDate
	 *            Data de início da timeline BRANCH
	 * @param branchName
	 *            Nome da branch
	 * @param branchType
	 *            Tipo da branch (DEFAULT, PARALLEL ou TEST)
	 * @return Versão (major + minor) do Aggregate na branch
	 */
	@RequestMapping(value = "/branch", 
			method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public Version createBranchFromMain(@RequestParam String aggregateId, 
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime branchStartDate,
			@RequestParam(required = false) String branchName, @RequestParam(required = false) String branchType) {
		// TODO: Checkout trava ambas as timelines
		// Busca timeline main
		Timeline parentTimeline = timelineRepository.findFirstByAggregateIdAndMainOrderByMajorVersion(aggregateId, true);
		LOG.debug("parentTimeline: {}", parentTimeline);
		
		// Busca o último snapshot do aggregate a partir da timeline main anterior à data branchDate
		GenericAggregate aggregate = checkOutLatestSnapshotFromMainBeforeDate(aggregateId, branchStartDate);
		
		// Cria nova timeline não-main tendo a timeline main como parent
		Timeline branchTimeline = new Timeline();
		branchTimeline.setId(IdGenerator.newId());
		branchTimeline.setAggregateId(aggregateId);
		branchTimeline.setAggregateType(parentTimeline.getAggregateType());
		branchTimeline.setMain(false);
		// TODO: Revisar o incremento de major version
		branchTimeline.setMajorVersion(parentTimeline.getMajorVersion() + 1);
		branchTimeline.setParentId(parentTimeline.getId());
		branchTimeline = timelineRepository.save(branchTimeline);
		LOG.debug("branchTimeline: {}", branchTimeline);
		
		// Salva o primeiro snapshot da branch
		aggregate.setMajorVersion(branchTimeline.getMajorVersion());
		aggregate.setScenarioName(branchName);
		saveSnapshotRecord(branchTimeline, aggregate, aggregate.getSnapshotDate());
		
		// Busca os event records da parent timeline, a partir da versão do snapshot até a data branchDate
		List<Record> records = recordRepository
				.findByTimelineIdAndTypeAndMinorVersionGreaterThanAndRecordDateBeforeOrderByMinorVersion(
						parentTimeline.getId(), RecordType.EVENT, aggregate.getMinorVersion(), branchStartDate);
		for (Record record : records) {
			// Copia cada record para a branch
			Record copy = RecordFactory.fromRecord(record);
			copy.setTimelineId(branchTimeline.getId());
			recordRepository.save(copy);
		}
		
		String scenarioName = branchName == null ? "Principal" : branchName;
		String scenarioType = branchType == null ? ScenarioType.DEFAULT.name() : branchType;
		scenarioManager.create(branchTimeline, branchStartDate, scenarioName, ScenarioStatus.INACTIVE.name(),
				scenarioType);
		scenarioManager.setAggregateName(branchTimeline.getId(), aggregate.getName());
		
		return new Version(branchTimeline.getMajorVersion(), aggregate.getMinorVersion() + records.size());
	}

	/**
	 * Realiza o merge, tornando a timeline BRANCH a nova timeline MAIN. A
	 * antiga timeline MAIN é arquivada.
	 * 
	 * @param branchTimeline
	 *            A timeline BRANCH a ser "merged"
	 */
	private void mergeBranchIntoMain(Timeline branchTimeline) {
		Timeline parentTimeline = timelineRepository.findOne(branchTimeline.getParentId());
		parentTimeline.setMain(false);
		branchTimeline.setMain(true);
		timelineRepository.save(branchTimeline);
		timelineRepository.save(parentTimeline);

		scenarioManager.inactivate(parentTimeline.getId());
		scenarioManager.activate(branchTimeline.getId());
		scenarioManager.makeDefault(branchTimeline.getId());
	}

	/**
	 * Realiza o merge, tornando a timeline BRANCH a nova timeline MAIN. A
	 * antiga timeline MAIN é arquivada.
	 * 
	 * @param branchTimelineId
	 *            ID da timeline BRANCH a ser "merged"
	 */
	public void mergeBranchIntoMain(String branchTimelineId) {
		mergeBranchIntoMain(timelineRepository.findOne(branchTimelineId));
	}

	/**
	 * Realiza o merge, tornando a timeline BRANCH a nova timeline MAIN. A
	 * antiga timeline MAIN é arquivada.
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline BRANCH a ser "merged"
	 */
	@RequestMapping(value = "/merge", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public void mergeBranchIntoMain(@RequestParam String aggregateId, @RequestParam Long majorVersion) {
		// TODO: Merge destrava ambas as timelines
		mergeBranchIntoMain(timelineRepository.findFirstByAggregateIdAndMajorVersion(aggregateId, majorVersion));
	}

	/**
	 * Ativa uma timeline BRANCH
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline BRANCH
	 */
	public void enableParallelBranch(String aggregateId, Long majorVersion) {
		Timeline branchTimeline = timelineRepository.findFirstByAggregateIdAndMajorVersion(aggregateId, majorVersion);
		branchTimeline.setMain(true);
		timelineRepository.save(branchTimeline);
		scenarioManager.activate(branchTimeline.getId());
	}

	/**
	 * Desativa uma timeline BRANCH
	 * 
	 * @param branchTimelineId
	 *            ID da timeline BRANCH
	 */
	public void disableParallelBranch(String branchTimelineId) {
		Timeline branchTimeline = timelineRepository.findOne(branchTimelineId);
		branchTimeline.setMain(false);
		timelineRepository.save(branchTimeline);
		scenarioManager.inactivate(branchTimelineId);
	}

	/**
	 * Realiza o check-out do snapshot mais recente do aggregate anterior a uma
	 * dada data, a partir da timeline MAIN
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param beforeDate
	 *            Data limite
	 * @return O estado do aggregate recuperado a partir do snapshot
	 */
	public GenericAggregate checkOutLatestSnapshotFromMainBeforeDate(@RequestParam String aggregateId,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime beforeDate) {
		return checkOutLatestSnapshotBeforeDate(aggregateId, null, beforeDate).get(0);
	}

	/**
	 * Realiza o check-out do snapshot mais recente do aggregate anterior a uma
	 * dada data, a partir de uma dada timeline
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline (caso seja null, considera a timeline MAIN)
	 * @param beforeDate
	 *            Data limite (caso seja null, considera a data atual)
	 * @return O estado do aggregate recuperado a partir do snapshot
	 */
	@RequestMapping(value = "/checkout", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public List<GenericAggregate> checkOutLatestSnapshotBeforeDate(@RequestParam String aggregateId,
			@RequestParam(required = false) Long majorVersion,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime beforeDate) {
		// TODO: Checkout trava a timeline
		// Busca todas as timelines ativas (main e paralelas)
		List<Timeline> timelines = getTimelines(aggregateId);
		Timeline branchTimeline = getTimeline(aggregateId, majorVersion);
		if (!timelines.contains(branchTimeline)) {
			// Busca timeline específica para a majorVersion
			timelines = Collections.singletonList(branchTimeline);
		}
		
		List<GenericAggregate> aggregates = new ArrayList<>();
		for (Timeline timeline : timelines) {
			// Utiliza timelineDate = data atual caso beforeDate seja null)
			ZonedDateTime timelineDate = beforeDate == null ? ZonedDateTime.now() : beforeDate;
			LOG.debug("snapshot query: timelineId {} timelineDate {}", timeline.getId(), timelineDate);
			// Busca o snapshot mais recente anterior à data timelineDate
			Record snapshotRecord = recordRepository.findFirstByTimelineIdAndTypeAndRecordDateLessThanEqualOrderByRecordDateDesc(
					timeline.getId(), RecordType.SNAPSHOT, timelineDate);
			GenericAggregate aggregate;
			if (snapshotRecord == null) {
				throw new OnsRuntimeException("Erro ao recuperar snapshot do aggregate {" + aggregateId + "}" +
						" majorVersion {" + majorVersion + "}" +
						" beforeDate {" + beforeDate + "}");
			}
			LOG.debug("found snapshot: {}", snapshotRecord.getRecordDate());
			try {
				// Restaura o estado do aggregate a partir do snapshot
				aggregate = objectMapper.readValue(snapshotRecord.getPayload(), GenericAggregate.class);
			} catch (Exception e) {
				throw new OnsRuntimeException(e);
			}
			if (aggregate == null) {
				throw new OnsRuntimeException("Erro ao restaurar aggregate a partir do snapshot");
			}
			aggregate.setSnapshotDate(snapshotRecord.getRecordDate());
			aggregates.add(aggregate);
		}
		
		return aggregates;
	}

	/**
	 * Busca um comando pelo ID
	 * 
	 * @param correlationId
	 *            ID de correlação do comando
	 * @return Comando
	 */
	public CommandMessage<GenericCommand> getCommit(String correlationId) {
		// Busca o record pelo correlationId
		Record record = recordRepository.findOne(correlationId);
		if (record == null) {
			throw new NotFoundException();
		}
		try {
			// Obtém payload e metadata do record e cria mensagem
			CommandMessage<GenericCommand> message = new CommandMessage<>();
			message.setCommand(objectMapper.readValue(record.getPayload(), GenericCommand.class));
			message.setMetadata(objectMapper.readValue(record.getMetadata(), CommandMetadata.class));
			return message;
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}
	
	/**
	 * Recupera os eventos registrados após uma dada versão anteriores a uma
	 * dada data
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline
	 * @param minorVersion
	 *            Versão inicial dos eventos
	 * @param beforeDate
	 *            Data limite
	 * @return Lista de eventos
	 */
	@RequestMapping(value = "/updates", 
			method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public List<GenericEvent> getUpdatesAfterMinorVersionBeforeDate(@RequestParam String aggregateId,
			@RequestParam(required = false) Long majorVersion, @RequestParam Long minorVersion,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime beforeDate) {
		// Busca main timeline caso majorVersion seja null
		Timeline timeline = getTimeline(aggregateId, majorVersion);

		// Utiliza timelineDate = data atual caso beforeDate seja null)
		ZonedDateTime timelineDate = beforeDate == null ? ZonedDateTime.now() : beforeDate;
		
		List<GenericEvent> events = new ArrayList<>();
		// Busca os event records da timeline selecionada, a partir da versão do snapshot até a data limite
		for (Record record : recordRepository
				.findByTimelineIdAndTypeAndMinorVersionGreaterThanAndRecordDateBeforeOrderByMinorVersion(
						timeline.getId(), RecordType.EVENT, minorVersion, timelineDate)) {
			try {
				// Obtém evento a partir do record e adiciona ao aggregate
				GenericEvent event = objectMapper.readValue(record.getPayload(), GenericEvent.class);
				events.add(event);
			} catch (Exception e) {
				throw new OnsRuntimeException(e);
			}
		}
		return events;
	}

	/**
	 * Realiza o check-in do aggregate atualizado com os eventos resultantes da
	 * execução de um comando. Persiste o comando e os eventos gerados nas
	 * linhas do tempo do aggregate e publica os eventos gerados no barramento.
	 * 
	 * @param commit
	 *            Commit contendo o comando atualizado e um ou mais estados do
	 *            aggregate atualizados
	 */
	@RequestMapping(value = "/checkin", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public void checkIn(@RequestBody Commit commit) {
		// TODO: Checkin destrava a timeline
		LOG.debug("checking in: {}", commit);
		for (GenericAggregate aggregate : commit.getAggregates()) {
			LOG.debug("- aggregate: {}", aggregate);
			// Busca a timeline selecionada (ou a timeline main caso majorVersion
			// do aggregate seja null)
			Timeline timeline = getTimeline(aggregate.getId(), aggregate.getMajorVersion());

			// Salva o command na timeline, se não for replay
			if (!commit.getMetadata().getIsReplay()) {
				LOG.debug("- saving command: {}", commit.getCommand());
				// Atualiza o nome do aggregate no cenário correspondente
				scenarioManager.setAggregateName(timeline.getId(), aggregate.getName());
				
				saveCommandRecord(timeline, commit);
			}

			// Obtém versão atual do aggregate
			Long currentVersion = aggregate.getMinorVersion();

			// Obtém os novos events ainda não salvos
			List<GenericEvent> events = aggregate.getEventsToSave();
			aggregate.setEventsToSave(new ArrayList<>());
			for (GenericEvent event : events) {
				// Incrementa a versão do aggregate
				aggregate.setMinorVersion(++currentVersion);

				// Salva os events na timeline e publica no event bus
				EventMessage<GenericEvent> eventMessage = new EventMessage<>();
				eventMessage.setEvent(event);
				eventMessage.getMetadata().setId(IdGenerator.newId());
				eventMessage.getMetadata().setCorrelationId(commit.getMetadata().getId());
				eventMessage.getMetadata().setCreationDate(ZonedDateTime.now());
				eventMessage.getMetadata().setTimelineDate(commit.getMetadata().getTimelineDate());
				eventMessage.getMetadata().setAggregateId(aggregate.getId());
				eventMessage.getMetadata().setMajorVersion(timeline.getMajorVersion());
				eventMessage.getMetadata().setMinorVersion(aggregate.getMinorVersion());
				eventMessage.getMetadata().setIsReplay(commit.getMetadata().getIsReplay());
				eventMessage.getMetadata().getProperties().putAll(commit.getMetadata().getProperties());
				eventMessage.getMetadata().getProperties().put("scenarioName",
						scenarioManager.getScenarioName(timeline.getId()));

				// Salva events na timeline, se não for replay
				if (!commit.getMetadata().getIsReplay()) {
					LOG.debug("- saving event: {}", eventMessage);
					saveEventRecord(timeline, eventMessage);
				}
				
				// Publica events no barramento
				LOG.debug("- publishing event: {}", eventMessage);
				eventBus.publish(eventMessage);

				// Salva um snapshot a cada N eventos, onde N = SNAPSHOT_EVENT_COUNT, se não for replay
				if (!commit.getMetadata().getIsReplay() && currentVersion % SNAPSHOT_EVENT_COUNT == 0) {
					saveSnapshotRecord(timeline, aggregate, commit.getMetadata().getTimelineDate());
				}
			}
		}
	}

	/**
	 * Recupera os comandos registrados após uma dada versão
	 * 
	 * @param aggregateId
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline
	 * @param minorVersion
	 *            Versão inicial dos comandos
	 * @return Lista de comandos
	 */
	@RequestMapping(value = "/commits", 
			method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public List<CommandMessage<GenericCommand>> getCommitsAfterMinorVersion(@RequestParam String aggregateId,
			@RequestParam(required = false) Long majorVersion, @RequestParam Long minorVersion) {
		List<CommandMessage<GenericCommand>> messages = new ArrayList<>();
		// Busca os root command records da timeline selecionada, a partir da
		// versão do snapshot
		List<Record> records = recordRepository
				.findByTimelineIdAndTypeAndMinorVersionGreaterThanEqualAndRecordDateBeforeAndCorrelationIdOrderByMinorVersion(
						getTimeline(aggregateId, majorVersion).getId(), RecordType.COMMAND, minorVersion,
						ZonedDateTime.now(), null);
		for (Record record : records) {
			try {
				// Obtém payload e metadata do record e cria mensagem
				CommandMessage<GenericCommand> message = new CommandMessage<>();
				message.setCommand(objectMapper.readValue(record.getPayload(), GenericCommand.class));
				message.setMetadata(objectMapper.readValue(record.getMetadata(), CommandMetadata.class));
				messages.add(message);
			} catch (Exception e) {
				throw new OnsRuntimeException(e);
			}
		}
		return messages;
	}

	/**
	 * Salva um registro de comando na timeline
	 * 
	 * @param timeline
	 *            Timeline onde será registrado o comando
	 * @param commit
	 *            Commit com o comando a ser salvo
	 */
	private void saveCommandRecord(Timeline timeline, Commit commit) {
		Record commandRecord = RecordFactory.fromCommit(commit);
		commit.getMetadata().setMajorVersion(timeline.getMajorVersion());
		commit.getMetadata().setCreationDate(commandRecord.getCreationDate());
		try {
			commandRecord.setMetadata(objectMapper.writeValueAsBytes(commit.getMetadata()));
			commandRecord.setPayload(objectMapper.writeValueAsBytes(commit.getCommand()));
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
		commandRecord.setTimelineId(timeline.getId());
		recordRepository.save(commandRecord);
	}

	/**
	 * Salva um registro de evento na timeline
	 * 
	 * @param timeline
	 *            Timeline onde será registrado o evento
	 * @param eventMessage
	 *            Mensagem do evento a ser salvo
	 */
	private void saveEventRecord(Timeline timeline, EventMessage<GenericEvent> eventMessage) {
		Record eventRecord = RecordFactory.fromEvent(eventMessage);
		try {
			eventRecord.setMetadata(objectMapper.writeValueAsBytes(eventMessage.getMetadata()));
			eventRecord.setPayload(objectMapper.writeValueAsBytes(eventMessage.getEvent()));
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
		eventRecord.setTimelineId(timeline.getId());
		recordRepository.save(eventRecord);
	}

	/**
	 * Salva um registro de snapshot na timeline
	 * 
	 * @param timeline
	 *            Timeline onde será registrado o snapshot
	 * @param aggregate
	 *            Estado do aggregate a ser registrado pelo snapshot
	 * @param timelineDate
	 *            Data do registro
	 */
	@Transactional
	private void saveSnapshotRecord(Timeline timeline, GenericAggregate aggregate, ZonedDateTime timelineDate) {
		Record snapshotRecord = RecordFactory.fromAggregate(aggregate, timelineDate);
		try {
			LOG.debug("recording snapshot: {}", objectMapper.writeValueAsString(aggregate));
			snapshotRecord.setPayload(objectMapper.writeValueAsBytes(aggregate));
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
		snapshotRecord.setTimelineId(timeline.getId());
		recordRepository.save(snapshotRecord);
		LOG.debug("snapshot recorded in timeline: {}", timeline.getId());
	}

	/**
	 * @param aggregateId ID do aggregate
	 * @param majorVersion Versão da timeline
	 * @return Retorna uma timeline específica, ou a timeline main
	 */
	@Transactional
	private Timeline getTimeline(String aggregateId, Long majorVersion) {
		Timeline timeline;
		if (majorVersion == null) {
			timeline = timelineRepository.findFirstByAggregateIdAndMainOrderByMajorVersion(aggregateId, true);
		} else {
			timeline = timelineRepository.findFirstByAggregateIdAndMajorVersion(aggregateId, majorVersion);
		}
		if (timeline == null) {
			throw new NotFoundException();
		}
		return timeline;
	}
	
	/**
	 * @param aggregateId ID do aggregate
	 * @return Retorna as timelines ativas (main e paralelas)
	 */
	@Transactional
	private List<Timeline> getTimelines(String aggregateId) {
		List<Timeline> timelines = timelineRepository.findByAggregateIdAndMainOrderByMajorVersion(aggregateId, true);
		if (timelines == null || timelines.isEmpty()) {
			throw new NotFoundException();
		}
		return timelines;
	}

	/**
	 * Exceção lançada caso uma timeline ou record não seja encontrado
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	class NotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	/**
	 * Exceção lançada em caso de operações concorrentes na mesma timeline
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	class ConflictException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
