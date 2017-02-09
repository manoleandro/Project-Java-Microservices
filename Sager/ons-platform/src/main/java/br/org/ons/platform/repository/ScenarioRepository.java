package br.org.ons.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.ons.platform.domain.Scenario;
import br.org.ons.platform.domain.enumeration.ScenarioType;

/**
 * Spring Data JPA repository for the Scenario entity.
 */
public interface ScenarioRepository extends JpaRepository<Scenario,String> {

	List<Scenario> findByType(ScenarioType type);
	
	List<Scenario> findByAggregateId(String aggregateId);
}
