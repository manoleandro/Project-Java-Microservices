package br.org.ons.sager.agendamento.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.agendamento.domain.AgendamentoCenario;
import br.org.ons.sager.agendamento.domain.AgendamentoRetificacao;

/**
 * 
 *
 */
public interface AgendamentoRetificacaoRepository extends CrudRepository<AgendamentoRetificacao, String>,QueryDslPredicateExecutor<AgendamentoRetificacao>  {

}
