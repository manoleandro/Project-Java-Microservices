package br.org.ons.sager.instalacao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.platform.common.Event;

/**
 * Evento publicando quando as disponibilidades para um período são calculadas
 */
public class DisponibilidadesCalculadasEvent extends Event {

	private Date dataInicio;
	private Date dataFim;
	private String idEquipamento;
	private List<Disponibilidade> disponibilidades = new ArrayList<>();
	private List<Comentario> comentarios = new ArrayList<>();

	/**
	 * @return o campo dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            o campo dataInicio a ser definido
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return o campo dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            o campo dataFim a ser definido
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return o campo idEquipamento
	 */
	public String getIdEquipamento() {
		return idEquipamento;
	}

	/**
	 * @param idEquipamento
	 *            o campo idEquipamento a ser definido
	 */
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	/**
	 * @return o campo disponibilidades
	 */
	public List<Disponibilidade> getDisponibilidades() {
		return disponibilidades;
	}

	/**
	 * @param disponibilidades
	 *            o campo disponibilidades a ser definido
	 */
	public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	/**
	 * @return o campo comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            o campo comentarios a ser definido
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DisponibilidadesCalculadasEvent [dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", idEquipamento="
				+ idEquipamento + ", disponibilidades=" + disponibilidades + ", comentarios=" + comentarios + "]";
	}
}
