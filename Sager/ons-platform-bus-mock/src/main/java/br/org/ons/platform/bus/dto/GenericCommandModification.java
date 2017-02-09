package br.org.ons.platform.bus.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.platform.common.CommandModification;

/**
 * Representa uma modificação de comando genérica processada pela plataforma
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "dummy",
		defaultImpl = GenericCommandModification.class)
public class GenericCommandModification extends CommandModification {

	private String name;

	private Map<String, Object> properties = new HashMap<>();
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonAnyGetter
	public Map<String, Object> getProperties() {
		return properties;
	}

	@JsonAnySetter
	public void putProperty(String name, Object value) {
		properties.put(name, value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GenericCommandModification other = (GenericCommandModification) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
        return "GenericCommandModification{" +
            "name='" + name + "'" +
            ", properties='" + properties + "'" +
            '}';
    }
}
