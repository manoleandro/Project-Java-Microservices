package br.org.ons.platform.domain.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.platform.common.Command;

/**
 * Representa um comando genérico processado pela plataforma
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "dummy",
		defaultImpl = GenericCommand.class)
public class GenericCommand extends Command {

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
    public String toString() {
        return "GenericCommand{" +
            "name='" + name + "'" +
            ", properties='" + properties + "'" +
            '}';
    }
}
