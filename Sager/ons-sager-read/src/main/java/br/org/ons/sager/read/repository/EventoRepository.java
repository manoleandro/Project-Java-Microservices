package br.org.ons.sager.read.repository;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.read.domain.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>, QueryDslPredicateExecutor<Evento> {

	public List<Evento> findAllByIdEvento(String idEvento);
}
