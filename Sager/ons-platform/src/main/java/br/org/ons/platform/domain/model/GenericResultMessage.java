package br.org.ons.platform.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma mensagem genérica de resultado processada pela plataforma
 */
public class GenericResultMessage {

	private Integer statusCode;
	private String statusMessage;
	private List<GenericResult> results = new ArrayList<>();

	public GenericResultMessage() {
		super();
	}

	public GenericResultMessage(Integer statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public GenericResultMessage(Integer statusCode, String statusMessage, List<GenericResult> results) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.results = results;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<GenericResult> getResults() {
		return results;
	}

	public void setResults(List<GenericResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "GenericResultMessage [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", results="
				+ results + "]";
	}

}
