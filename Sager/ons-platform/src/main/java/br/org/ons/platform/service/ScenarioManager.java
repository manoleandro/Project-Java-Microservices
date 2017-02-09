package br.org.ons.platform.service;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.domain.Scenario;
import br.org.ons.platform.domain.Timeline;
import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;
import br.org.ons.platform.repository.ScenarioRepository;
import br.org.ons.platform.security.SecurityUtils;

/**
 * Componente responsável pelo ciclo de vida das entidades Scenario
 * 
 * Cada cenário deve sempre possuir uma Timeline associada, possuindo o mesmo ID
 */
@Component
public class ScenarioManager {

	private static final Logger LOG = LoggerFactory.getLogger(ScenarioManager.class);

	private ScenarioRepository scenarioRepository;

	@Inject
	public ScenarioManager(ScenarioRepository scenarioRepository) {
		this.scenarioRepository = scenarioRepository;
	}

	/**
	 * Cria uma nova entidade do tipo Scenario
	 * @param timeline Timeline correpondente ao cenário
	 * @param startDate Data de início do cenário
	 * @param scenarioDescription Descrição do cenário
	 * @param scenarioStatus Status do cenário
	 * @param scenarioType Tipo do cenário
	 */
	public void create(Timeline timeline, ZonedDateTime startDate, String scenarioDescription, String scenarioStatus,
			String scenarioType) {
		Scenario scenario = new Scenario();
		scenario.setId(timeline.getId());
		scenario.setAggregateId(timeline.getAggregateId());
		scenario.setCreationDate(ZonedDateTime.now());
		scenario.setDescription(scenarioDescription);
		scenario.setStartDate(startDate);
		scenario.setStatus(ScenarioStatus.valueOf(scenarioStatus));
		scenario.setType(ScenarioType.valueOf(scenarioType));
		scenario.setUserId(SecurityUtils.getCurrentUserLogin());
		scenarioRepository.save(scenario);
		LOG.debug("Created Scenario {}", scenario);
	}
	
	/**
	 * Altera o tipo do cenário para DEFAULT
	 * @param timelineId ID da timeline correspondente ao cenário
	 */
	public void makeDefault(String timelineId) {
		Scenario scenario = scenarioRepository.findOne(timelineId);
		assertNotNull(scenario);
		scenario.setType(ScenarioType.DEFAULT);
		scenarioRepository.save(scenario);
		LOG.debug("Made Scenario default {}", scenario);
	}
	
	/**
	 * Adiciona o nome do aggregate ao cenário 
	 * @param timelineId ID da timeline correspondente ao cenário
	 * @param aggregateName Nome do aggregate
	 */
	public void setAggregateName(String timelineId, String aggregateName) {
		Scenario scenario = scenarioRepository.findOne(timelineId);
		assertNotNull(scenario);
		scenario.setAggregateName(aggregateName);
		scenarioRepository.save(scenario);
		LOG.debug("Set aggregate name on Scenario {} {}", aggregateName, scenario);
	}
	
	/**
	 * Retorna o nome de um cenário 
	 * @param timelineId ID da timeline correspondente ao cenário
	 */
	public String getScenarioName(String timelineId) {
		Scenario scenario = scenarioRepository.findOne(timelineId);
		assertNotNull(scenario);
		return scenario.getDescription();
	}
	
	/**
	 * Ativa um cenário
	 * @param timelineId ID da timeline correspondente ao cenário
	 */
	public void activate(String timelineId) {
		Scenario scenario = scenarioRepository.findOne(timelineId);
		assertNotNull(scenario);
		scenario.setStatus(ScenarioStatus.ACTIVE);
		scenarioRepository.save(scenario);
		LOG.debug("Activated Scenario {}", scenario);
	}
	
	/**
	 * Desativa um cenário
	 * @param timelineId ID da timeline correspondente ao cenário
	 */
	public void inactivate(String timelineId) {
		Scenario scenario = scenarioRepository.findOne(timelineId);
		assertNotNull(scenario);
		scenario.setStatus(ScenarioStatus.INACTIVE);
		scenario.setEndDate(ZonedDateTime.now());
		scenarioRepository.save(scenario);
		LOG.debug("Inactivated Scenario {}", scenario);
	}
	
	private void assertNotNull(Scenario scenario) {
		if (scenario == null) {
			throw new OnsRuntimeException("Cenário não encontrado");
		}
	}
}
