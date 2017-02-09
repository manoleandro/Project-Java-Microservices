package br.org.ons.platform.web.rest.mapper;

import br.org.ons.platform.domain.*;
import br.org.ons.platform.web.rest.dto.ScenarioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Scenario and its DTO ScenarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScenarioMapper {

    ScenarioDTO scenarioToScenarioDTO(Scenario scenario);

    List<ScenarioDTO> scenariosToScenarioDTOs(List<Scenario> scenarios);

    Scenario scenarioDTOToScenario(ScenarioDTO scenarioDTO);

    List<Scenario> scenarioDTOsToScenarios(List<ScenarioDTO> scenarioDTOs);
}
