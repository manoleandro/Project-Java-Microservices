package br.org.ons.platform.domain.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa um aggregate genérico processado pela plataforma
 */
public class GenericAggregate {

	private String id;
	private Long majorVersion;
	private Long minorVersion;
	private String name;
	
	private String aggregateType;

	private List<GenericEvent> eventsToSave = new ArrayList<>();
	
	private Map<String, Object> properties = new HashMap<>();
	
	/**
	 * Atributo interno usado pelo EventSourcingRepositoryService
	 */
	private ZonedDateTime snapshotDate;
	
	private String scenarioName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	public Long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(Long minorVersion) {
		this.minorVersion = minorVersion;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAggregateType() {
		return aggregateType;
	}

	public void setAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public List<GenericEvent> getEventsToSave() {
		return eventsToSave;
	}

	public void setEventsToSave(List<GenericEvent> eventsToSave) {
		this.eventsToSave = eventsToSave;
	}

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}

	@JsonAnySetter
	public void putProperty(String name, Object value) {
		properties.put(name, value);
	}

	@JsonIgnore
	public ZonedDateTime getSnapshotDate() {
		return snapshotDate;
	}

	@JsonIgnore
	public void setSnapshotDate(ZonedDateTime snapshotDate) {
		this.snapshotDate = snapshotDate;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	@Override
    public String toString() {
        return "GenericAggregate{" +
            "id='" + id + "'" +
            ", majorVersion='" + majorVersion + "'" +
            ", minorVersion='" + minorVersion + "'" +
            ", aggregateType='" + aggregateType + "'" +
            ", eventsToSave='" + eventsToSave + "'" +
            ", properties='" + properties + "'" +
            ", snapshotDate='" + snapshotDate + "'" +
            ", scenarioName='" + scenarioName + "'" +
            '}';
    }
}
