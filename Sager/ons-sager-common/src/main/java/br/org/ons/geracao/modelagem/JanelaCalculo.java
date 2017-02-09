package br.org.ons.geracao.modelagem;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Taxa;

/**
 * Representa um periodo apura??o de eventos de mudan?a de estado operativo
 *
 */

public class JanelaCalculo extends Periodo {

	private static final long serialVersionUID = 1L;
	
	private String idInstalacao;
	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
	private List<ParametroTaxa> parametros = new ArrayList<>();
	private List<Taxa> taxas = new ArrayList<>();

	/**
	 * @return o campo idInstalacao
	 */
	public String getIdInstalacao() {
		return idInstalacao;
	}

	/**
	 * @param idInstalacao
	 *            o campo idInstalacao a ser definido
	 */
	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}
	
	/**
	 * @return o campo eventos
	 */
	public List<EventoMudancaEstadoOperativo> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos
	 *            o campo eventos a ser definido
	 */
	public void setEventos(List<EventoMudancaEstadoOperativo> eventos) {
		this.eventos = eventos;
	}

	/**
	 * @return o campo parametros
	 */
	public List<ParametroTaxa> getParametros() {
		return parametros;
	}

	/**
	 * @param parametros
	 *            o campo parametros a ser definido
	 */
	public void setParametros(List<ParametroTaxa> parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return o campo taxas
	 */
	public List<Taxa> getTaxas() {
		return taxas;
	}

	/**
	 * @param taxas
	 *            o campo taxas a ser definido
	 */
	public void setTaxas(List<Taxa> taxas) {
		this.taxas = taxas;
	}
}
