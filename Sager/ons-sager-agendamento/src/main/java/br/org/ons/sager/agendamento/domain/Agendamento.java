package br.org.ons.sager.agendamento.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * Superclasse abstrata com atributos comuns a todos os tipos de agendamento
 */
public abstract class Agendamento implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private List<Instalacao> instalacoesCenarios = new ArrayList<>();
	private ZonedDateTime dataAgendamento;
	private String situacao;
	private String resultado;
	private String solicitante;
	private ZonedDateTime dataCriacao;
	private ZonedDateTime dataTermino;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo dataAgendamento
	 */
	public ZonedDateTime getDataAgendamento() {
		return dataAgendamento;
	}

	/**
	 * @param dataAgendamento
	 *            o campo dataAgendamento a ser definido
	 */
	public void setDataAgendamento(ZonedDateTime dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	/**
	 * @return o campo situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao o campo situacao a ser definido
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return o campo resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @param resultado o campo resultado a ser definido
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return o campo solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}

	/**
	 * @param solicitante o campo solicitante a ser definido
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public ZonedDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(ZonedDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public ZonedDateTime getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(ZonedDateTime dataTermino) {
		this.dataTermino = dataTermino;
	}

	public List<Instalacao> getInstalacoesCenarios() {
		return instalacoesCenarios;
	}

	public void setInstalacoesCenarios(List<Instalacao> instalacoesCenarios) {
		this.instalacoesCenarios = instalacoesCenarios;
	}

}
