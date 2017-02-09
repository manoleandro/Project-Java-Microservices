package br.org.ons.platform.common;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Representa uma modificação a ser aplicada a um comando durante um
 * reprocessamento ou processamento de cenário
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "name")
public abstract class CommandModification {

	/**
	 * ID de correlação do comando a ser modificado
	 */
	private String correlationId;
	
	/**
	 * Data do comando na timeline
	 */
	private ZonedDateTime timelineDate;

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
	 * @return Nome completo da classe da modificação
	 */
	public String getName() {
		return getClass().getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CommandModification other = (CommandModification) obj;
		if (correlationId == null) {
			if (other.correlationId != null) {
				return false;
			}
		} else if (!correlationId.equals(other.correlationId)) {
			return false;
		}
		return true;
	}
}
