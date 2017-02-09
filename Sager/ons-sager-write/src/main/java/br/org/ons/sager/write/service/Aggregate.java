package br.org.ons.sager.write.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandModification;
import br.org.ons.platform.common.Event;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "aggregateType")
public abstract class Aggregate {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String id;
	private Long majorVersion;
	private Long minorVersion;
	private String name;
	private List<Event> eventsToSave = new ArrayList<Event>();
	private String scenarioName;

	public String getAggregateType() {
		return getClass().getName();
	}

	@SuppressWarnings("unchecked")
	public <R> R apply(Command command) {
		try {
			Method handle = getClass().getDeclaredMethod("handle", command.getClass());
			handle.setAccessible(true);
			return (R) handle.invoke(this, command);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			} else {
				throw new RuntimeException(e.getTargetException());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <R> R modifyAndApply(Command command, CommandModification modification) {
		try {
			Method modify = getClass().getDeclaredMethod("modify", command.getClass(), modification.getClass());
			modify.setAccessible(true);
			modify.invoke(this, command, modification);
		} catch (NoSuchMethodException e) {
			log.debug("No modifier for {}, {}. Nothing to do.", command.getClass(), modification.getClass());
		} catch (IllegalAccessException e) {
			// Should not happen
			log.debug("Illegal access to modifier: {}, {}. Nothing to do.", command.getClass(), modification.getClass());
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			} else {
				throw new RuntimeException(e.getTargetException());
			}
		}
		return (R) apply(command);
	}

	protected void play(Event event) {
		redirectToWhen(event);
		eventsToSave.add(event);
	}

	public void replay(Event event) {
		redirectToWhen(event);
		minorVersion++;
	}

	private void redirectToWhen(Event event) {
		try {
			Method when = getClass().getDeclaredMethod("when", event.getClass());
			when.setAccessible(true);
			when.invoke(this, event);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			} else {
				throw new RuntimeException(e.getTargetException());
			}
		}
	}

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

	public List<Event> getEventsToSave() {
		return eventsToSave;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
}
