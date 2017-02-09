package br.org.ons.sager.agendamento.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.agendamento.domain.AgendamentoCenario;

/**
 * 
 *
 */
public interface AgendamentoCenarioRepository extends CrudRepository<AgendamentoCenario, String>,QueryDslPredicateExecutor<AgendamentoCenario> {

}
