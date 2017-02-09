package br.org.ons.platform.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.NoClass;

/**
 * Sinaliza o fim do processamento de um comando pela plataforma
 */
public class CommandFinishedEvent<R> extends Event {

	public enum Status {
		SUCCESS, FAILED
	}

	private String commandName;

	/**
	 * Status da execução do comando
	 */
	private Status status;

	/**
	 * Mensagem descritiva do status
	 */
	private String message;

	/**
	 * Os objetos de resultado retornados pela execução do comando. Podem ser
	 * retornados mais de um resultado caso haja mais de um cenário paralelo para
	 * o aggregate
	 */
	@JsonTypeInfo(
			use = JsonTypeInfo.Id.CLASS, 
			include = JsonTypeInfo.As.PROPERTY, 
			property = "_class", 
			defaultImpl = NoClass.class)
	private List<R> results = new ArrayList<>();
	
	/**
	 * @return o campo commandName
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * @param commandName o campo commandName a ser definido
	 */
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	/**
	 * @return o campo status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            o campo status a ser definido
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return o campo message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            o campo message a ser definido
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return o campo results
	 */
	public List<R> getResults() {
		return results;
	}

	/**
	 * @param results o campo results a ser definido
	 */
	public void setResults(List<R> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "CommandFinishedEvent [commandName=" + commandName + ", status=" + status + ", message=" + message
				+ ", results=" + results + "]";
	}
}
