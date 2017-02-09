package br.org.ons.platform.domain.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * Representa um resultado genérico processado pela plataforma
 */
public class GenericResult {

	private Map<String, Object> properties = new HashMap<>();

	@JsonGetter("_class")
	public String getClassName() {
		return getClass().getName();
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
		return "GenericResult [properties=" + properties + "]";
	}
}
