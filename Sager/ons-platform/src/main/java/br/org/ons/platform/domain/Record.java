package br.org.ons.platform.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.org.ons.platform.domain.enumeration.RecordType;

/**
 * Representa registros de comandos, eventos e snapshots em uma timeline.
 */
@Entity
@Table(name = "tb_record", schema="platform")
public class Record {

    @Id
    @Column(name = "id_record")
    private String id;

    /**
	 * Identifica a versão do aggregate dentro da timeline. A versão é
	 * incrementada a cada registro de evento
	 */
    @Column(name = "ver_minor")
    private Long minorVersion;

    /**
     * ID do registro pai, em caso de registros derivados de outros
     */
    @Column(name = "id_correlation")
    private String correlationId;

    /**
     * Data de criação do registro
     */
    @Column(name = "din_creation")
    @Type(type = "br.org.ons.platform.domain.util.ZonedDateTimeConverter")
    private ZonedDateTime creationDate;

    /**
     * Data do registro na linha do tempo
     */
    @Column(name = "din_record")
    @Type(type = "br.org.ons.platform.domain.util.ZonedDateTimeConverter")
    private ZonedDateTime recordDate;

    /**
     * Tipo de registro
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tip_record")
    private RecordType type;

    /**
     * Nome da classe dos dados serializados (comando, evento ou aggregate)
     */
    @Column(name = "tip_payload")
    private String payloadType;

    /**
     * Metadados da mensagem de comando ou evento registrada
     */
    @Lob
    @Column(name = "arq_metadata")
    private byte[] metadata;

    /**
     * Dados serializados do comando, evento ou snapshot do aggregate
     */
    @Lob
    @Column(name = "arq_payload")
    private byte[] payload;

    /**
     * ID da timeline que contém o registro
     */
    @Column(name="id_timeline")
    private String timelineId;

    /**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo minorVersion
	 */
	public Long getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @param minorVersion o campo minorVersion a ser definido
	 */
	public void setMinorVersion(Long minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @return o campo correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId o campo correlationId a ser definido
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return o campo creationDate
	 */
	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate o campo creationDate a ser definido
	 */
	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return o campo recordDate
	 */
	public ZonedDateTime getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordDate o campo recordDate a ser definido
	 */
	public void setRecordDate(ZonedDateTime recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * @return o campo type
	 */
	public RecordType getType() {
		return type;
	}

	/**
	 * @param type o campo type a ser definido
	 */
	public void setType(RecordType type) {
		this.type = type;
	}

	/**
	 * @return o campo payloadType
	 */
	public String getPayloadType() {
		return payloadType;
	}

	/**
	 * @param payloadType o campo payloadType a ser definido
	 */
	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	/**
	 * @return o campo metadata
	 */
	public byte[] getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata o campo metadata a ser definido
	 */
	public void setMetadata(byte[] metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return o campo payload
	 */
	public byte[] getPayload() {
		return payload;
	}

	/**
	 * @param payload o campo payload a ser definido
	 */
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	/**
	 * @return o campo timelineId
	 */
	public String getTimelineId() {
		return timelineId;
	}

	/**
	 * @param timelineId o campo timelineId a ser definido
	 */
	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        if(record.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, record.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Record{" +
            "id=" + id +
            ", minorVersion='" + minorVersion + "'" +
            ", correlationId='" + correlationId + "'" +
            ", creationDate='" + creationDate + "'" +
            ", recordDate='" + recordDate + "'" +
            ", type='" + type + "'" +
            ", name='" + payloadType + "'" +
            ", metadata='" + metadata + "'" +
            ", payload='" + payload + "'" +
            '}';
    }
}
