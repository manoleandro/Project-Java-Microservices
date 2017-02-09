package br.org.ons.platform.service;

import java.time.ZonedDateTime;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CreateScenarioCommand;
import br.org.ons.platform.common.EndScenarioCommand;
import br.org.ons.platform.common.ReplayCommand;
import br.org.ons.platform.common.ReprocessCommand;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.ScenarioCommandModification;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.model.GenericResultMessage;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.domain.model.GenericCommandModification;
import br.org.ons.platform.web.rest.dto.Version;

/**
 * Serviço responsável por executar o reprocessamento em cascata para
 * reconstrução de timelines ou para a construção de cenários paralelos
 */
@RestController
@RequestMapping("/api/reprocessor")
public class ReprocessorService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReprocessorService.class);

	private EventSourcingRepositoryService repo;
	
	private CommandBus commandBus;

	@Inject
	public ReprocessorService(EventSourcingRepositoryService repo, CommandBus commandBus) {
		this.repo = repo;
		this.commandBus = commandBus;
	}

	/**
	 * Reprocessa a timeline principal de um aggregate a partir de um dado
	 * instante, aplicando modificações aos comandos originais da timeline
	 * 
	 * @param commandMessage Comando para reprocessar a timeline
	 * @return void em caso de sucesso, ou uma mensagem de resultado em caso de
	 *         erro
	 */
	@RequestMapping(value = "/reprocess-command", 
		method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResultMessage> reprocess(
			@RequestBody CommandMessage<ReprocessCommand<GenericCommandModification>> commandMessage) {
		LOG.debug("POST reprocess(ReprocessCommand) : {}", commandMessage);
		// Cria uma timeline branch a partir da timeline default
		Version branchVersion = repo.createBranchFromMain(commandMessage.getMetadata().getAggregateId(),
				commandMessage.getMetadata().getTimelineDate(), null, null);
		// Recupera todos os root commands posteriores ao início da timeline branch
		List<CommandMessage<GenericCommand>> commands = repo.getCommitsAfterMinorVersion(
				commandMessage.getMetadata().getAggregateId(), null, branchVersion.getMinor());
		ReprocessCommand<GenericCommandModification> reprocess = commandMessage.getCommand();
		// Re-envia para execução todos os comandos em sequência, aplicando as modificações 
		for (CommandMessage<GenericCommand> command : commands) {
			GenericCommandModification modification = reprocess.getModification(command.getMetadata().getId());
			if (modification != null) {
				LOG.debug("Adding command modification: {}", modification);
				command.setModification(modification);
			}

			command.getMetadata().setId(IdGenerator.newId());
			command.getMetadata().setCorrelationId(null);
			command.getMetadata().setCreationDate(ZonedDateTime.now());
			command.getMetadata().setMajorVersion(branchVersion.getMajor());

			LOG.debug("Sending command to reprocess: {}", command.getCommand());
			GenericResultMessage result = commandBus.sendAndWait(command);
			LOG.debug("Reprocessed command result: {}", command.getCommand());
			if (!HttpStatus.valueOf(result.getStatusCode()).is2xxSuccessful()) {
				return ResponseEntity.status(result.getStatusCode()).body(result);
			}
		}
		// Realiza o merge, fazendo branch timeline passar a ser a default timeline
		repo.mergeBranchIntoMain(commandMessage.getMetadata().getAggregateId(), branchVersion.getMajor());
		return ResponseEntity.ok().body(new GenericResultMessage(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Reproduz um comando registrado na timeline de um aggregate
	 * 
	 * @param commandMessage
	 *            Comando para reproduzir
	 * @return Mensagem de resultado do comando reproduzido
	 */
	@RequestMapping(value = "/replay-command", 
			method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResultMessage> replay(
			@RequestBody CommandMessage<ReplayCommand<GenericCommandModification>> commandMessage) {
		LOG.debug("POST replay(ReplayCommand) : {}", commandMessage);
		ReplayCommand<GenericCommandModification> replay = commandMessage.getCommand();
		// Busca o comando
		CommandMessage<GenericCommand> command = repo.getCommit(replay.getCorrelationId());
		// Aplica a modificação, caso presente
		GenericCommandModification modification = replay.getModification();
		if (modification != null) {
			LOG.debug("Adding command modification: {}", modification);
			command.setModification(modification);
		}
		// Modifica a data de criação para o instante atual
		command.getMetadata().setCreationDate(ZonedDateTime.now());
		// Indicando que se trata de uma reprodução de comando
		command.getMetadata().setIsReplay(true);
		// Reenviando o comando
		LOG.debug("Sending command to replay: {}", command.getCommand());
		GenericResultMessage result = commandBus.sendAndWait(command);
		// Retornano o resultado do comando
		LOG.debug("Replayed command result: {}", command.getCommand());
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * Cria um novo cenário a partir do cenário principal de um aggregate
	 * 
	 * @param commandMessage
	 *            Comando para criar o novo cenário
	 * @return void em caso de sucesso, ou uma mensagem de resultado em caso de
	 *         erro
	 */
	@RequestMapping(value = "/create-scenario-command", 
		method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResultMessage> createScenario(
			@RequestBody CommandMessage<CreateScenarioCommand> commandMessage) {
		CreateScenarioCommand createScenario = commandMessage.getCommand();
		LOG.debug("POST createScenario(CreateScenarioCommand) : {}", commandMessage);
		// Cria uma timeline branch a partir da timeline default
		Version branchVersion = repo.createBranchFromMain(commandMessage.getMetadata().getAggregateId(),
				commandMessage.getMetadata().getTimelineDate(), createScenario.getScenarioDescription(),
				createScenario.getScenarioType());
		// Recupera todos os root commands posteriores ao início da timeline branch
		List<CommandMessage<GenericCommand>> commands = repo.getCommitsAfterMinorVersion(
				commandMessage.getMetadata().getAggregateId(), null, branchVersion.getMinor());
		// Re-envia para execução todos os comandos em sequência, aplicando modificações de cenário
		for (CommandMessage<GenericCommand> command : commands) {
			LOG.debug("Copying command to scenario: {}", command);
			ScenarioCommandModification modification = new ScenarioCommandModification();
			modification.setCorrelationId(command.getMetadata().getId());
			modification.setTimelineDate(command.getMetadata().getTimelineDate());
			modification.setScenarioName(createScenario.getScenarioDescription());
			LOG.debug("Adding scenario command modification: {}", modification);
			command.setModification(modification);

			command.getMetadata().setId(IdGenerator.newId());
			command.getMetadata().setCorrelationId(null);
			command.getMetadata().setMajorVersion(branchVersion.getMajor());
			command.getMetadata().setCreationDate(ZonedDateTime.now());

			LOG.debug("Sending command to reprocess: {}", command.getCommand());
			GenericResultMessage result = commandBus.sendAndWait(command);
			LOG.debug("Reprocessed command result: {}", command.getCommand());
			if (!HttpStatus.valueOf(result.getStatusCode()).is2xxSuccessful()) {
				return ResponseEntity.status(result.getStatusCode()).body(result);
			}
		}
		// Ativa a branch timeline para ficar em paralelo
		repo.enableParallelBranch(commandMessage.getMetadata().getAggregateId(), branchVersion.getMajor());
		return ResponseEntity.ok().body(new GenericResultMessage(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
	}

	/**
	 * Encerra um cenário ativo
	 * 
	 * @param commandMessage
	 *            Comando para encerrar o cenário
	 * @return void
	 */
	@RequestMapping(value = "/end-scenario-command", 
		method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMessage<Void>> endScenario(
			@RequestBody CommandMessage<EndScenarioCommand> commandMessage) {
		EndScenarioCommand endScenario = commandMessage.getCommand();
		LOG.debug("POST endScenario(EndScenarioCommand) : {}", commandMessage);
		if (endScenario.getMakeDefault()) {
			repo.mergeBranchIntoMain(endScenario.getScenarioId());
		} else {
			repo.disableParallelBranch(endScenario.getScenarioId());
		}
		return ResponseEntity.ok().body(new ResultMessage<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
	}
	
	/**
	 * Tratamento padrão para exceções
	 * @param exception Exceção a ser tratada
	 * @return Resultado com mensagem da exceção
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultMessage<Void> handleException(Exception exception) {
		LOG.error("RUNTIME EXCEPTION {}", exception);
		return new ResultMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
	}
}
