package br.org.ons.platform.common;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Representa um conjunto de metadados associados a um comando ou evento
 */
public abstract class Metadata {

	/**
	 * ID do comando ou evento
	 */
	private String id;
	
	/**
	 * No caso de um evento, indica o ID do comando de origem.
	 */
	private String correlationId;
	
	/**
	 * Data da criação do comando ou evento
	 */
	private ZonedDateTime creationDate;

	/**
	 * ID do aggregate associado ao comando ou evento
	 */
	private String aggregateId;
	
	/**
	 * Versão da timeline associada ao comando ou evento
	 */
	private Long majorVersion;
	
	/**
	 * No caso de comando, indica a versão do aggregate sobre a qual o mesmo
	 * deve ser executado. No caso de evento, indica a versão do aggregate
	 * gerada após sua aplicação.
	 */
	private Long minorVersion;
	
	/**
	 * Indica a posição do comando ou evento na linha do tempo
	 */
	private ZonedDateTime timelineDate;

	/**
	 * Indica se é um comando ou evento associado a uma reprodutibilidade (sem
	 * persistência na timeline)
	 */
	private Boolean isReplay = false;
	
	/**
	 * Contém propriedades adicionais de metadados
	 */
	private Map<String, Object> properties = new HashMap<>();
	
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
	 * @return o campo aggregateId
	 */
	public String getAggregateId() {
		return aggregateId;
	}

	/**
	 * @param aggregateId o campo aggregateId a ser definido
	 */
	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	/**
	 * @return o campo majorVersion
	 */
	public Long getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @param majorVersion o campo majorVersion a ser definido
	 */
	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
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
	 * @return o campo timelineDate
	 */
	public ZonedDateTime getTimelineDate() {
		return timelineDate;
	}

	/**
	 * @param timelineDate o campo timelineDate a ser definido
	 */
	public void setTimelineDate(ZonedDateTime timelineDate) {
		this.timelineDate = timelineDate;
	}

	/**
	 * @return o campo isReplay
	 */
	public Boolean getIsReplay() {
		return isReplay;
	}

	/**
	 * @param isReplay o campo isReplay a ser definido
	 */
	public void setIsReplay(Boolean isReplay) {
		this.isReplay = isReplay;
	}

	/**
	 * @return Todas as propriedades adicionais
	 */
	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Insere uma nova propriedade adicional
	 * @param name Nome da propriedade
	 * @param value Valor da propriedade
	 */
	@JsonAnySetter
	public void putProperty(String name, Object value) {
		properties.put(name, value);
	}
}
