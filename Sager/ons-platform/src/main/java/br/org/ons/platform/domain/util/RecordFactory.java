package br.org.ons.platform.domain.util;

import java.time.ZonedDateTime;

import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.platform.domain.Record;
import br.org.ons.platform.domain.enumeration.RecordType;
import br.org.ons.platform.web.rest.dto.Commit;
import br.org.ons.platform.domain.model.GenericAggregate;

/**
 * Classe utilitária que gera instâncias de Record a partir de diversos tipos de objetos
 */
public class RecordFactory {
	
	private RecordFactory() {
		
	}

	/**
	 * Gera uma cópia de um Record
	 * @param record Record a ser copiado
	 * @return Cópia
	 */
	public static Record fromRecord(Record record) {
		Record newRecord = new Record();
		newRecord.setId(IdGenerator.newId());
		newRecord.setCorrelationId(record.getCorrelationId());
		newRecord.setCreationDate(ZonedDateTime.now());
		newRecord.setPayloadType(record.getPayloadType());
		newRecord.setMinorVersion(record.getMinorVersion());
		newRecord.setRecordDate(record.getRecordDate());
		newRecord.setType(record.getType());
		newRecord.setTimelineId(record.getTimelineId());
		newRecord.setMetadata(record.getMetadata());
		newRecord.setPayload(record.getPayload());
		return newRecord;
	}
	
	/**
	 * Gera um registro de comando a partir de um Commit
	 * @param commit Commit
	 * @return Registro de comando representado pelo Commit
	 */
	public static Record fromCommit(Commit commit) {
		Record record = new Record();
		record.setId(commit.getMetadata().getId());
		record.setCorrelationId(commit.getMetadata().getCorrelationId());
		record.setCreationDate(ZonedDateTime.now());
		record.setPayloadType(commit.getCommand().getName());
		record.setMinorVersion(commit.getMetadata().getMinorVersion());
		record.setRecordDate(commit.getMetadata().getTimelineDate());
		record.setType(RecordType.COMMAND);
		return record;
	}
	
	/**
	 * Gera um registro de evento a partir de uma mensagem de evento
	 * @param message Mensagem de evento
	 * @return Registro de evento
	 */
	public static Record fromEvent(EventMessage<?> message) {
		Record record = new Record();
		record.setId(message.getMetadata().getId());
		record.setCorrelationId(message.getMetadata().getCorrelationId());
		record.setCreationDate(ZonedDateTime.now());
		record.setPayloadType(message.getEvent().getName());
		record.setMinorVersion(message.getMetadata().getMinorVersion());
		record.setRecordDate(message.getMetadata().getTimelineDate());
		record.setType(RecordType.EVENT);
		return record;
	}
	
	/**
	 * Gera um registro de snapshot a partir de um aggregate
	 * @param aggregate Estado do aggregate a ser persistido
	 * @param recordDate Data de registro do aggregate
	 * @return Registro de snapshot
	 */
	public static Record fromAggregate(GenericAggregate aggregate, ZonedDateTime recordDate) {
		Record record = new Record();
		record.setId(IdGenerator.newId());
		record.setCorrelationId(null);
		record.setCreationDate(ZonedDateTime.now());
		record.setPayloadType(aggregate.getAggregateType());
		record.setMinorVersion(aggregate.getMinorVersion());
		record.setRecordDate(recordDate);
		record.setType(RecordType.SNAPSHOT);
		return record;
	}
}
