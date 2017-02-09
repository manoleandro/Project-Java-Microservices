package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.Comentario;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;

public class CalcularParametrosResponse {
	
	private List<ParametroTaxa> parametrosTaxa = new ArrayList<>();
	private List<Comentario> comentarios = new ArrayList<>();
	private List<EventoMudancaEstadoOperativo> eventosAlterados = new ArrayList<>();

	/**
	 * @return o campo parametrosTaxa
	 */
	public List<ParametroTaxa> getParametrosTaxa() {
		return parametrosTaxa;
	}

	/**
	 * @param parametrosTaxa
	 *            o campo parametrosTaxa a ser definido
	 */
	public void setParametrosTaxa(List<ParametroTaxa> parametrosTaxa) {
		this.parametrosTaxa = parametrosTaxa;
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

	/**
	 * @return o campo eventosAlterados
	 */
	public List<EventoMudancaEstadoOperativo> getEventosAlterados() {
		return eventosAlterados;
	}

	/**
	 * @param eventosAlterados
	 *            o campo eventosAlterados a ser definido
	 */
	public void setEventosAlterados(List<EventoMudancaEstadoOperativo> eventosAlterados) {
		this.eventosAlterados = eventosAlterados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularParametrosResponse [parametrosTaxa=" + parametrosTaxa + ", comentarios=" + comentarios
				+ ", eventosAlterados=" + eventosAlterados + "]";
	}
	
	
	

}
