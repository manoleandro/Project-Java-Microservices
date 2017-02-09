package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.Disponibilidade;

public class CalcularDisponibilidadeResponse {

	private List<Comentario> comentarios = new ArrayList<>();

	private List<Disponibilidade> disponibilidades = new ArrayList<>();

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Disponibilidade> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularDisponibilidadeResponse [comentarios=" + comentarios + ", disponibilidades=" + disponibilidades
				+ "]";
	}

}
