package br.org.ons.sager.read.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.ons.sager.read.domain.TaxaRef;

/**
 * Spring Data MongoDB repository for the TaxaRef entity.
 */
public interface TaxaRefRepository extends ECJCompiled<TaxaRef, String> {

	Page<TaxaRef> findAllByCosIn(Collection<String> cos, Pageable pageable);
}
