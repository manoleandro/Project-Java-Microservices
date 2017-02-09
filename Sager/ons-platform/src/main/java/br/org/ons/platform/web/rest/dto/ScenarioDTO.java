package br.org.ons.platform.web.rest.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

import br.org.ons.platform.domain.enumeration.ScenarioStatus;
import br.org.ons.platform.domain.enumeration.ScenarioType;

/**
 * DTO para a entidade Scenario
 */
public class ScenarioDTO {

	private String id;

	private String aggregateId;

	private String aggregateName;

	private String timelineId;

	private String description;

	private ScenarioType type;

	private ScenarioStatus status;

	private ZonedDateTime startDate;

	private ZonedDateTime endDate;

	private ZonedDateTime creationDate;

	private String userId;
	
	private Long majorVersion;
	
	private Long firstMinorVersion;
	
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getAggregateName() {
		return aggregateName;
	}

	public void setAggregateName(String aggregateName) {
		this.aggregateName = aggregateName;
	}

	public String getTimelineId() {
		return timelineId;
	}

	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ScenarioType getType() {
		return type;
	}

	public void setType(ScenarioType type) {
		this.type = type;
	}

	public ScenarioStatus getStatus() {
		return status;
	}

	public void setStatus(ScenarioStatus status) {
		this.status = status;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}

	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	public Long getFirstMinorVersion() {
		return firstMinorVersion;
	}

	public void setFirstMinorVersion(Long firstMinorVersion) {
		this.firstMinorVersion = firstMinorVersion;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ScenarioDTO scenarioDTO = (ScenarioDTO) o;

		if (!Objects.equals(id, scenarioDTO.id)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

    @Override
    public String toString() {
        return "ScenarioDTO{" +
            "id=" + id +
            ", aggregateId='" + aggregateId + "'" +
            ", aggregateName='" + aggregateName + "'" +
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
