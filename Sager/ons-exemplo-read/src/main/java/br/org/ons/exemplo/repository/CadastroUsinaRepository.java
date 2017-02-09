package br.org.ons.exemplo.repository;

import org.springframework.data.repository.CrudRepository;

import br.org.ons.exemplo.domain.CadastroUsina;

/**
 * Spring Data JPA repository for the CadastroUsina entity.
 */
public interface CadastroUsinaRepository extends CrudRepository<CadastroUsina,String> {

}
