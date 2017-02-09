package br.org.ons.sager.read.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.read.domain.NotificacoesLidas;

public interface NotificacoesLidasRepository extends CrudRepository<NotificacoesLidas, String>, QueryDslPredicateExecutor<NotificacoesLidas> {

}
