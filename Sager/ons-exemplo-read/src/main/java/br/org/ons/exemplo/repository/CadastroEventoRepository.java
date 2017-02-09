package br.org.ons.exemplo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.org.ons.exemplo.domain.CadastroEvento;

/**
 * Spring Data JPA repository for the CadastroEvento entity.
 */
public interface CadastroEventoRepository extends CrudRepository<CadastroEvento, String> {
	
	List<CadastroEvento> findByAggregateIdOrderByDataVerificada(String aggregateId);
}
