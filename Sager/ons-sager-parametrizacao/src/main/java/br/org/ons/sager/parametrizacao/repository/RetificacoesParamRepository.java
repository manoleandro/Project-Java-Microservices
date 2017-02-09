package br.org.ons.sager.parametrizacao.repository;

import java.time.LocalTime;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.parametrizacao.domain.RetificacoesParam;

/**
 * Spring Data Keyvalue repository for the RetificacoesParam entity.
 */
public interface RetificacoesParamRepository extends CrudRepository<RetificacoesParam, String>, QueryDslPredicateExecutor<RetificacoesParam> {
	
	RetificacoesParam findOneByDiaAndHora(int dia, LocalTime hora);
 
}