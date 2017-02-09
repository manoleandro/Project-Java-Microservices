package br.org.ons.sager.instalacao.service;

import java.lang.reflect.ParameterizedType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandFinishedEvent;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.CommandStartedEvent;
import br.org.ons.platform.common.CreateAggregateCommand;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.ScenarioCommandModification;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.config.mq.EventBus;
import br.org.ons.sager.instalacao.repository.AggregateRepository;

/**
 * Classe abstrata para a exposição da lógica do aggregate através de um serviço
 * REST.
 * <p>
 * Subclasses devem utilizar as annotations <code>@RestController</code> e
 * <code>@RequestMapping("/api/{nome-do-aggregate-em-minusculas-separado-por-hifen}")</code>
 * no nível da classe para configuração na forma de um serviço Spring REST.
 * <p>
 * Para cada comando suportado pelo aggregate, deve ser implementado um método (NOSONAR) 
 * seguindo o template abaixo:
 * <p>
 * <code>
 * 	&#64;RequestMapping(<br>
			value = "/{nome-do-comando-em-minusculas-separado-por-hifen}", <br>
			method = RequestMethod.POST,<br>
		    produces = MediaType.APPLICATION_JSON_VALUE)<br>
	public ResponseEntity&lt;ResultMessage&lt;{RetornoDoComando}&gt;&gt; criarUsina(@RequestBody CommandMessage&lt;{Comando}&gt; message) {<br>
		return execute(message);<br>
	}<br>
 * </code>
 * <p>
 * Substitua os trechos de código entre <code>{}</code>
 * 
 * @param <T>
 *            Tipo de aggregate tratado pelo serviço
 */
public abstract class AggregateService<T extends Aggregate> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Repositório de persistência dos aggregates
	 */
	@Inject
	protected AggregateRepository<T> repository;

	@Inject
	protected AutowireCapableBeanFactory beanFactory;

	@Inject
	protected EventBus eventBus;

	/**
	 * Executa um comando sobre uma instância de aggregate.
	 * 
	 * @param C
	 *            Tipo do comando
	 * @param R
	 *            Tipo do resultado retornado pelo comando
	 * @param message
	 *            Mensagem de comando
	 * @return <code>ResponseEntity</code> contendo a mensagem de resultado do
	 *         comando
	 */
	protected <C extends Command, R> ResponseEntity<ResultMessage<R>> execute(CommandMessage<C> message) {
		ResultMessage<R> result = new ResultMessage<>();
		publishStartedEvent(message.getMetadata(), message.getCommand().getName());
		try {
			List<T> aggregates;
			// Caso seja um comando de criação de aggregate, cria uma nova
			// timeline DEFAULT no repositório, obtendo um snapshot zerado do
			// aggregate
			if (message.getCommand() instanceof CreateAggregateCommand) {
				log.debug("- createMain: {}, {}", message.getMetadata().getAggregateId(),
						message.getMetadata().getTimelineDate());
				aggregates = new ArrayList<>();
				aggregates.add(repository.createMain(message.getMetadata().getAggregateId(),
						message.getMetadata().getTimelineDate()));
				log.debug("- aggregates after createMain: " + aggregates);
			}
			// Se não, busca no repositório os snapshots do aggregate a partir
			// de suas timelines ativas (pode haver mais de uma em caso de
			// cenários paralelos)
			else {
				log.debug("- checkOutLatestSnapshotBeforeDate: {}, {}, {}", message.getMetadata().getAggregateId(),
						message.getMetadata().getMajorVersion(), message.getMetadata().getTimelineDate());
				aggregates = repository.checkOutLatestSnapshotBeforeDate(message.getMetadata().getAggregateId(),
						message.getMetadata().getMajorVersion(), message.getMetadata().getTimelineDate());
				log.debug("- aggregates after checkOutLatestSnapshotBeforeDate: {}", aggregates);
			}
			// Para cada snapshot do aggregate
			List<R> results = new ArrayList<>();
			for (T aggregate : aggregates) {
				// Injetando dependências do Spring no aggregate
				beanFactory.autowireBean(aggregate);
				
				// Injetando versão do handler no aggregate
				aggregate.setHandlerVersion(message.getMetadata().getHandlerVersion());

				// Busca no repositório os eventos posteriores ao snapshot e os
				// reaplica ao estado do aggregate, obtendo o estado do
				// aggregate na data de execução do comando
				log.debug("- getUpdatesAfterMinorVersionBeforeDate: {}, {}, {}, {}",
						message.getMetadata().getAggregateId(), aggregate.getMajorVersion(),
						aggregate.getMinorVersion(), message.getMetadata().getTimelineDate());
				repository.getUpdatesAfterMinorVersionBeforeDate(message.getMetadata().getAggregateId(),
						aggregate.getMajorVersion(), aggregate.getMinorVersion(),
						message.getMetadata().getTimelineDate()).forEach(aggregate::replay);
				log.debug("- aggregate after replay events: {}", aggregate);

				log.debug("- command: {}", message.getCommand());
				R res;
				// Se for um comando de reprocessamento, modifica o comando e
				// aplica ao aggregate
				if (message.getModification() != null) {
					log.debug("- command modification: {}", message.getModification());
					res = aggregate.modifyAndApply(message.getCommand(), message.getModification());
					log.debug("- modified command: {}", message.getCommand());
				}
				// Se for um comando de cenário, modifica o comando através da
				// regra de cenário e aplica ao aggregate
				else if (aggregate.getScenarioName() != null) {
					log.debug("- scenario modification: {}", aggregate.getScenarioName());
					ScenarioCommandModification modification = new ScenarioCommandModification();
					modification.setScenarioName(aggregate.getScenarioName());
					res = aggregate.modifyAndApply(message.getCommand(), modification);
					log.debug("- modified command: {}", message.getCommand());
				}
				// Se for um novo comando, aplica ao aggregate
				else {
					res = aggregate.apply(message.getCommand());
				}
				results.add(res);
				log.debug("- aggregate after handle command: {}", aggregate);
				log.debug("- result: {}", res);
			}

			// Salva no repositório todos os estados do aggregate atualizados
			// após a execução do comando
			log.debug("- checkIn");
			repository.checkIn(message.getCommand(), message.getMetadata(), aggregates);

			// Retorna os resultados da execução do comando
			result.setResults(results);
			result.setStatusCode(HttpStatus.OK.value());
			result.setStatusMessage(HttpStatus.OK.getReasonPhrase());
			publishFinishedEvent(message.getMetadata(), message.getCommand().getName(), results);
		} catch (IllegalArgumentException e) {
			// Exceção lançada em caso de comando inválido
			log.error("COMMAND MESSAGE {}", message);
			log.error("INVALID COMMAND EXCEPTION {} {}", e.getMessage(), e);
			result.setStatusCode(HttpStatus.BAD_REQUEST.value());
			result.setStatusMessage(e.getMessage());
			publishFailedEvent(message.getMetadata(), e.getMessage(), message.getCommand().getName());
		} catch (IllegalStateException e) {
			// Exceção lançada em caso de estado inconsistente com o comando
			log.error("COMMAND {}", message);
			log.error("INVALID STATE EXCEPTION {} {}", e.getMessage(), e);
			result.setStatusCode(HttpStatus.CONFLICT.value());
			result.setStatusMessage(e.getMessage());
			publishFailedEvent(message.getMetadata(), e.getMessage(), message.getCommand().getName());
		} catch (Exception e) {
			// Erro interno
			log.error("COMMAND {}", message);
			log.error("RUNTIME EXCEPTION {} {}", e.getMessage(), e);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setStatusMessage(e.getMessage());
			publishFailedEvent(message.getMetadata(), e.getMessage(), message.getCommand().getName());
		} finally {
			// Caso não tenha sido processado com sucesso, cancela o checkout
			// para destravar a timeline
//			if (HttpStatus.OK.value() != result.getStatusCode()) {
//				repository.undoCheckOut(message.getMetadata().getAggregateId(),
//						message.getMetadata().getMajorVersion());
//			}
		}
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @param metadata
	 * @param message
	 */
	private void publishFailedEvent(CommandMetadata metadata, String message, String commandName) {
		CommandFinishedEvent<Void> event = new CommandFinishedEvent<>();
		event.setCommandName(commandName);
		event.setStatus(CommandFinishedEvent.Status.FAILED);
		event.setMessage(message);
		publishEvent(metadata, event);
	}

	/**
	 * @param metadata
	 */
	private <R> void publishFinishedEvent(CommandMetadata metadata, String commandName, List<R> results) {
		CommandFinishedEvent<R> event = new CommandFinishedEvent<>();
		event.setCommandName(commandName);
		event.setStatus(CommandFinishedEvent.Status.SUCCESS);
		event.setResults(results);
		publishEvent(metadata, event);
	}

	/**
	 * @param metadata
	 */
	private void publishStartedEvent(CommandMetadata metadata, String commandName) {
		CommandStartedEvent event = new CommandStartedEvent();
		event.setCommandName(commandName);
		publishEvent(metadata, event);
	}

	/**
	 * @param metadata
	 * @param event
	 */
	private <E extends Event> void publishEvent(CommandMetadata metadata, E event) { // NOSONAR
		EventMessage<E> eventMessage = new EventMessage<>();
		eventMessage.setEvent(event);
		eventMessage.getMetadata().setId(IdGenerator.newId());
		eventMessage.getMetadata().setCorrelationId(metadata.getId());
		eventMessage.getMetadata().setCreationDate(ZonedDateTime.now());
		eventMessage.getMetadata().setTimelineDate(metadata.getTimelineDate());
		eventMessage.getMetadata().setAggregateId(metadata.getAggregateId());
		eventMessage.getMetadata().setMajorVersion(metadata.getMajorVersion());
		eventMessage.getMetadata().setMinorVersion(metadata.getMinorVersion());
		eventMessage.getMetadata().setIsReplay(metadata.getIsReplay());
		eventMessage.getMetadata().getProperties().putAll(metadata.getProperties());
		eventBus.publish(eventMessage);
	}

	/**
	 * Consulta o estado do aggregate em qualquer instante do tempo
	 * 
	 * @param id
	 *            ID do aggregate
	 * @param majorVersion
	 *            Versão da timeline do aggregate. Se for null, utiliza a
	 *            timeline MAIN
	 * @param timelineDate
	 *            Instante da consulta. Se for null, utiliza a data atual
	 * @return O estado do aggregate
	 */
	@RequestMapping(value = "/{id}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<T> get(@PathVariable String id, @RequestParam(required = false) Long majorVersion,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime timelineDate) {
		T aggregate = repository.checkOutLatestSnapshotBeforeDate(id, majorVersion, timelineDate).get(0);
		if (aggregate == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		beanFactory.autowireBean(aggregate);
		repository.getUpdatesAfterMinorVersionBeforeDate(id, aggregate.getMajorVersion(), aggregate.getMinorVersion(),
				timelineDate).forEach(aggregate::replay);
		return new ResponseEntity<>(aggregate, HttpStatus.OK);
	}

	@PostConstruct
	@SuppressWarnings("unchecked")
	private void initRepository() {
		repository.setAggregateClass(
				(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
}
