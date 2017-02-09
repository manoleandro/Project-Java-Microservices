package br.org.ons.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.ons.platform.domain.Timeline;

/**
 * Spring Data JPA repository for the Timeline entity.
 */
public interface TimelineRepository extends JpaRepository<Timeline,String> {

	/**
	 * Busca as timelines de um aggregate
	 * @param aggregateId
	 *            ID do aggregate
	 * @return As timelines
	 */
	List<Timeline> findByAggregateIdOrderByMajorVersion(String aggregateId);
	
	/**
	 * Busca a primeira timeline de um aggregate
	 * @param aggregateId
	 *            ID do aggregate
	 * @return A timeline
	 */
	Timeline findFirstByAggregateIdOrderByMajorVersion(String aggregateId);

	/**
	 * Busca a primeira timeline principal de uma aggregate
	 * @param aggregateId ID do aggregate
	 * @param main Indica se a timeline deve ser a principal
	 * @return A timeline
	 */
	Timeline findFirstByAggregateIdAndMainOrderByMajorVersion(String aggregateId, Boolean main);

	/**
	 * Busca todas as timelines principais do aggregate
	 * @param aggregateId ID do aggregate
	 * @param main Indica se as timelines devem ser as principais
	 * @return As timelines
	 */
	List<Timeline> findByAggregateIdAndMainOrderByMajorVersion(String aggregateId, Boolean main);
	
	/**
	 * Busca uma timeline correspondente a uma major version
	 * @param aggregateId ID do aggregate
	 * @param majorVersion Versão da timeline
	 * @return A timeline
	 */
	Timeline findFirstByAggregateIdAndMajorVersion(String aggregateId, Long majorVersion);
	
	/**
	 * Busca uma timeline principal correspondente a uma major version
	 * @param aggregateId ID do aggregate
	 * @param majorVersion Versão da timeline
	 * @param main Indica se a timeline deve ser principal
	 * @return A timeline
	 */
	Timeline findFirstByAggregateIdAndMajorVersionAndMain(String aggregateId, Long majorVersion, Boolean main);
}
