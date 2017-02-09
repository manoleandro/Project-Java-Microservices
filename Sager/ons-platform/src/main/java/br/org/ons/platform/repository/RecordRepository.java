package br.org.ons.platform.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.enumeration.RecordType;

/**
 * Spring Data JPA repository for the Record entity.
 */
public interface RecordRepository extends JpaRepository<Record, String> {

	/**
	 * Busca o registro mais recente anterior a uma dada data limite
	 * @param timelineId Timeline do registro 
	 * @param type Tipo do registro
	 * @param timelineDate Data	limite do registro
	 * @return O registro
	 */
	Record findFirstByTimelineIdAndTypeAndRecordDateLessThanEqualOrderByRecordDateDesc(String timelineId,
			RecordType type, ZonedDateTime timelineDate);

	/**
	 * Busca os registros posteriores a uma dada versão e anteriores a uma dada data limite
	 * @param timelineId Timeline dos registros
	 * @param type Tipo dos registros
	 * @param minorVersion Versão limite
	 * @param recordDate Data limite
	 * @return Os registros
	 */
	List<Record> findByTimelineIdAndTypeAndMinorVersionGreaterThanAndRecordDateBeforeOrderByMinorVersion(
			String timelineId, RecordType type, Long minorVersion, ZonedDateTime recordDate);
	
	/**
	 * Busca os registros posteriores a uma dada versão e anteriores a uma dada
	 * data limite, contendo um determinado ID de correlação
	 * @param timelineId Timeline dos registros
	 * @param type Tipo dos registros
	 * @param minorVersion Versão limite
	 * @param recordDate Data limite
	 * @param correlationId ID de correlação
	 * @return Os registros
	 */
	List<Record> findByTimelineIdAndTypeAndMinorVersionGreaterThanEqualAndRecordDateBeforeAndCorrelationIdOrderByMinorVersion(
			String timelineId, RecordType type, Long minorVersion, ZonedDateTime recordDate, String correlationId);

	List<Record> findByTimelineIdOrderByMinorVersion(String timelineId);

	Record findFirstByTimelineIdOrderByMinorVersion(String timelineId);
}

