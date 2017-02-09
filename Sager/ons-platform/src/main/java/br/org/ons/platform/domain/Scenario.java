package br.org.ons.platform.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;

/**
 * Representa um cenário paralelo de eventos de uma instância de aggregate
 */
@Entity
@Table(name = "tb_scenario", schema="platform")
public class Scenario {

	public static final String DEFAULT_DESCRIPTION = "Principal";
	
    @Id
    @Column(name = "id_scenario")
    private String id;

    /**
     * ID da instância de aggregate
     */
    @Column(name = "id_aggregate")
    private String aggregateId;

    /**
     * Nome da instância de aggregate
     */
    @Column(name = "nom_aggregate")
    private String aggregateName;

    /**
     * ID da timeline representada pelo cenário
     */
    @Column(name = "id_timeline")
    private String timelineId;

    /**
     * Descrição do cenário
     */
    @Column(name = "dsc_description")
    private String description;

    /**
     * Tipo de cenário
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tip_scenario")
    private ScenarioType type;

    /**
     * Status do cenário
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sts_scenario")
    private ScenarioStatus status;

    /**
     * Data de início do cenário
     */
    @Column(name = "din_start")
    @Type(type = "br.org.ons.platform.domain.util.ZonedDateTimeConverter")
    private ZonedDateTime startDate;

    /**
     * Data de encerramento do cenário
     */
    @Column(name = "din_end")
    @Type(type = "br.org.ons.platform.domain.util.ZonedDateTimeConverter")
    private ZonedDateTime endDate;

    /**
     * Data de criação do cenário
     */
    @Column(name = "din_creation")
    @Type(type = "br.org.ons.platform.domain.util.ZonedDateTimeConverter")
    private ZonedDateTime creationDate;

    /**
     * ID do usuário criador do cenário
     */
    @Column(name = "lgn_user")
    private String userId;

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
	 * @return o campo aggregateName
	 */
	public String getAggregateName() {
		return aggregateName;
	}

	/**
	 * @param aggregateName o campo aggregateName a ser definido
	 */
	public void setAggregateName(String aggregateName) {
		this.aggregateName = aggregateName;
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

	/**
	 * @return o campo description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description o campo description a ser definido
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return o campo type
	 */
	public ScenarioType getType() {
		return type;
	}

	/**
	 * @param type o campo type a ser definido
	 */
	public void setType(ScenarioType type) {
		this.type = type;
	}

	/**
	 * @return o campo status
	 */
	public ScenarioStatus getStatus() {
		return status;
	}

	/**
	 * @param status o campo status a ser definido
	 */
	public void setStatus(ScenarioStatus status) {
		this.status = status;
	}

	/**
	 * @return o campo startDate
	 */
	public ZonedDateTime getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate o campo startDate a ser definido
	 */
	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return o campo endDate
	 */
	public ZonedDateTime getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate o campo endDate a ser definido
	 */
	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
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
	 * @return o campo userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId o campo userId a ser definido
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Scenario scenario = (Scenario) o;
        if(scenario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, scenario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Scenario{" +
            "id=" + id +
            ", aggregateId='" + aggregateId + "'" +
            ", timelineId='" + timelineId + "'" +
            ", description='" + description + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", creationDate='" + creationDate + "'" +
            ", userId='" + userId + "'" +
            '}';
    }
}
