package br.org.ons.sager.read.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.read.domain.Notificacao;

public interface NotificacaoRepository extends CrudRepository<Notificacao, String>, QueryDslPredicateExecutor<Notificacao> {
	
}
