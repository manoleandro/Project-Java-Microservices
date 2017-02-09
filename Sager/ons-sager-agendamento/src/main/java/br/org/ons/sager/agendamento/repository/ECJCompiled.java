package br.org.ons.sager.agendamento.repository;

import java.io.Serializable;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 *
 */
@NoRepositoryBean
public interface ECJCompiled<T, I extends Serializable> extends PagingAndSortingRepository<T, I>, QueryDslPredicateExecutor<T> {

}
