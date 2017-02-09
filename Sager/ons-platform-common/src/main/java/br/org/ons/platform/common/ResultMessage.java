package br.org.ons.platform.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.NoClass;

/**
 * Mensagem que encapsula o resultado da execução de um comando. Utiliza
 * códigos e status do protocolo HTTP para indicação de sucesso ou erro na
 * execução do comando.
 *
 * @param <R>
 *            Tipo de objeto retornado como resultado
 */

public class ResultMessage<R> {

	/**
	 * Código de status HTTP da execução do comando
	 */
	private Integer statusCode;
	
	/**
	 * Mensagem de status HTTP da execução do comando
	 */
	private String statusMessage;
	
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
	 * Construtor padr�o utilizado pelo serializador JSON
	 */
	public ResultMessage() {
		super();
	}

	/**
	 * @param statusCode C�digo de status HTTP
	 * @param statusMessage Mensagem de status HTTP
	 */
	public ResultMessage(Integer statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	/**
	 * @param statusCode C�digo de status HTTP
	 * @param statusMessage Mensagem de status HTTP
	 * @param results Resultados da execu��o do comando
	 */
	public ResultMessage(Integer statusCode, String statusMessage, List<R> results) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.results = results;
	}

	/**
	 * @return o campo statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode o campo statusCode a ser definido
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return o campo statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage o campo statusMessage a ser definido
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
        return "ResultMessage{" +
            "statusCode='" + statusCode + "'" +
            ", statusMessage='" + statusMessage + "'" +
            ", results='" + results + "'" +
            '}';
    }
}
