package br.org.ons.platform.web.rest;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CreateScenarioCommand;
import br.org.ons.platform.common.EndScenarioCommand;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.Scenario;
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.repository.RecordRepository;
import br.org.ons.platform.repository.ScenarioRepository;
import br.org.ons.platform.repository.TimelineRepository;
import br.org.ons.platform.web.rest.dto.ScenarioDTO;
import br.org.ons.platform.web.rest.mapper.ScenarioMapper;
import br.org.ons.platform.web.rest.util.HeaderUtil;

/**
 * API REST para as telas Manter Cenário
 */
@RestController
@RequestMapping("/api")
public class ScenarioResource {

	private static final Logger LOG = LoggerFactory.getLogger(ScenarioResource.class);
        
	private static final String SCENARIO_ENTITY = "scenario";
	
    private RecordRepository recordRepository;
    
    private TimelineRepository timelineRepository;
    
    private ScenarioRepository scenarioRepository;
    
    private ScenarioMapper scenarioMapper;

	private CommandBus commandBus;

	@Inject
    public ScenarioResource(ScenarioRepository scenarioRepository, TimelineRepository timelineRepository,
			RecordRepository recordRepository, ScenarioMapper scenarioMapper, CommandBus commandBus) {
		this.scenarioRepository = scenarioRepository;
		this.timelineRepository = timelineRepository;
		this.recordRepository = recordRepository;
		this.scenarioMapper = scenarioMapper;
		this.commandBus = commandBus;
	}

	/**
     * POST  /scenarios : Envia o comando para a criação de um novo cenário
     *
     * @param scenarioDTO O cenário a ser criado
     * @return ResponseEntity com status 200 (OK)
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createScenario(@RequestBody ScenarioDTO scenarioDTO) {
        LOG.debug("REST request to save Scenario : {}", scenarioDTO);
        CreateScenarioCommand command = new CreateScenarioCommand();
        command.setAggregateName(scenarioDTO.getAggregateName());
        command.setScenarioDescription(scenarioDTO.getDescription());
        command.setScenarioType(scenarioDTO.getType().name());

		CommandMessage<CreateScenarioCommand> commandMessage = new CommandMessage<>();
		commandMessage.setCommand(command);
		commandMessage.getMetadata().setId(IdGenerator.newId());
		commandMessage.getMetadata().setCorrelationId(null);
		commandMessage.getMetadata().setAggregateId(scenarioDTO.getAggregateId());
		commandMessage.getMetadata().setMajorVersion(null);
		commandMessage.getMetadata().setMinorVersion(null);
		commandMessage.getMetadata().setTimelineDate(scenarioDTO.getStartDate());
		commandBus.send(commandMessage);

        return ResponseEntity.ok().build();
    }

    /**
     * POST /scenarios/:id/end : Envia o comando para o encerramento de um cenário
     * @param id ID do cenário a ser encerrado
     * @param makeDefault Indica se o cenário deve passar a ser o DEFAULT
     * @return ResponseEntity com status 200 (OK)
     */
    @RequestMapping(value = "/scenarios/{id}/end",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> endScenario(@PathVariable String id, @RequestParam Boolean makeDefault) {
        LOG.debug("REST request to end Scenario : {} {}", id, makeDefault);
        EndScenarioCommand command = new EndScenarioCommand();
        command.setScenarioId(id);
        command.setMakeDefault(makeDefault);

		CommandMessage<EndScenarioCommand> commandMessage = new CommandMessage<>();
		commandMessage.setCommand(command);
		commandMessage.getMetadata().setId(IdGenerator.newId());
		commandMessage.getMetadata().setCorrelationId(null);
		commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
		commandBus.send(commandMessage);
        return ResponseEntity.ok().build();
    }
    
    /**
     * PUT  /scenarios : Atualiza um cenário
     *
     * @param scenarioDTO O cenário a ser atualizado
     * @return ResponseEntity com status 200 (OK) e body o cenário atualizado
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScenarioDTO> updateScenario(@RequestBody ScenarioDTO scenarioDTO) {
        LOG.debug("REST request to update Scenario : {}", scenarioDTO);
        if (scenarioDTO.getId() == null) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(SCENARIO_ENTITY, "idnull", "The ID for the scenario cannot be null")).body(null);
        }
        Scenario scenario = scenarioMapper.scenarioDTOToScenario(scenarioDTO);
        scenario = scenarioRepository.save(scenario);
        ScenarioDTO result = scenarioMapper.scenarioToScenarioDTO(scenario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(SCENARIO_ENTITY, scenarioDTO.getId()))
            .body(result);
    }

    /**
     * GET  /scenarios : Recupera todos os cenários
     *
     * @return ResponseEntity com status 200 (OK) e a lista de cenários no body
     */
    @RequestMapping(value = "/scenarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ScenarioDTO> getAllScenarios(@RequestParam(required = false) String aggregateId) {
        LOG.debug("REST request to get all Scenarios: aggregateId = {}", aggregateId);
        List<Scenario> scenarios;
        if (aggregateId != null) {
        	scenarios = scenarioRepository.findByAggregateId(aggregateId);
        } else {
        	scenarios = scenarioRepository.findAll();
        }
		List<ScenarioDTO> scenarioDTOs = scenarioMapper.scenariosToScenarioDTOs(scenarios);
		scenarioDTOs.forEach(scenarioDTO -> {
			Timeline timeline = timelineRepository.findOne(scenarioDTO.getId());
			scenarioDTO.setMajorVersion(timeline.getMajorVersion());
			scenarioDTO.setParentId(timeline.getParentId());
			scenarioDTO.setFirstMinorVersion(
					recordRepository.findFirstByTimelineIdOrderByMinorVersion(scenarioDTO.getId()).getMinorVersion());
		});
		scenarioDTOs.sort(Comparator.comparing(ScenarioDTO::getMajorVersion));
        return scenarioDTOs;
    }

	/**
	 * GET /scenarios/:id : Recupera um cenário
	 *
	 * @param id
	 *            ID do cenário a ser recuperado
	 * @return ResponseEntity com status 200 (OK) e body com os dados do
	 *         cenário, ou status 404 (Not Found)
	 */
    @RequestMapping(value = "/scenarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScenarioDTO> getScenario(@PathVariable String id) {
        LOG.debug("REST request to get Scenario : {}", id);
        Scenario scenario = scenarioRepository.findOne(id);
		ScenarioDTO scenarioDTO = scenarioMapper.scenarioToScenarioDTO(scenario);
		return Optional.ofNullable(scenarioDTO).map(result -> {
			Timeline timeline = timelineRepository.findOne(result.getId());
			result.setMajorVersion(timeline.getMajorVersion());
			result.setParentId(timeline.getParentId());
			result.setFirstMinorVersion(
					recordRepository.findFirstByTimelineIdOrderByMinorVersion(result.getId()).getMinorVersion());
			return new ResponseEntity<>(result, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scenarios/:id : Exclui um cenário
     *
     * @param id ID do cenário a ser excluído
     * @return ResponseEntity com status 200 (OK)
     */
    @RequestMapping(value = "/scenarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteScenario(@PathVariable String id) {
        LOG.debug("REST request to delete Scenario : {}", id);
        scenarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(SCENARIO_ENTITY, id)).build();
    }
}
