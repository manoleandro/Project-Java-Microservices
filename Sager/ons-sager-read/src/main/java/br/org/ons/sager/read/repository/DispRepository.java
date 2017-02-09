package br.org.ons.sager.read.repository;

import java.util.List;

import br.org.ons.sager.read.domain.Disp;

/**
 * Spring Data MongoDB repository for the TaxaRef entity.
 */
public interface DispRepository extends ECJCompiled<Disp, String> {

	List<Disp> findDistinctEquipamentoByInstalacao(String instalacao);

	// List<Disp> findByDataAfter(Date dtInicio);
	// List<Disp> findByDataBetween(Date dtInicio, Date dtFim);
	//
	//
	// List<String> findDistinctEquipamentosNomeByInstalacao(String instalacao);

}