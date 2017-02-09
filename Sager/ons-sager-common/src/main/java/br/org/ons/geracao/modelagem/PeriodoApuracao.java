package br.org.ons.geracao.modelagem;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Taxa;

/**
 * Representa um periodo apura��o de eventos de mudan�a de estado operativo
 *
 */
public class PeriodoApuracao extends Periodo {

	private static final long serialVersionUID = 1L;

	private List<EventoMudancaEstadoOperativo> eventos = new ArrayList<>();
	private List<ParametroTaxa> parametrosV1 = new ArrayList<>();
	private List<ParametroTaxa> parametrosV2 = new ArrayList<>();
	private List<Taxa> taxas = new ArrayList<>();
	private String handlerVersion;

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

	public List<ParametroTaxa> getParametrosV1() {
		return parametrosV1;
	}

	public void setParametrosV1(List<ParametroTaxa> parametrosV1) {
		this.parametrosV1 = parametrosV1;
	}

	public List<ParametroTaxa> getParametrosV2() {
		return parametrosV2;
	}

	public void setParametrosV2(List<ParametroTaxa> parametrosV2) {
		this.parametrosV2 = parametrosV2;
	}

	public String getHandlerVersion() {
		return handlerVersion;
	}

	public void setHandlerVersion(String handlerVersion) {
		this.handlerVersion = handlerVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parametrosV1 == null) ? 0 : parametrosV1.hashCode());
		result = prime * result + ((parametrosV2 == null) ? 0 : parametrosV2.hashCode());
		result = prime * result + ((taxas == null) ? 0 : taxas.hashCode());
		result = prime * result + ((this.getDataInicio() == null ) ? 0 : this.getDataInicio().hashCode());
		result = prime * result + ((handlerVersion == null) ? 0 : handlerVersion.hashCode());
		result = prime * result + ((eventos == null) ? 0 : eventos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PeriodoApuracao other = (PeriodoApuracao) obj;
		
		if (parametrosV1 == null) {
			if (other.parametrosV1 != null)
				return false;
		} else if (!parametrosV1.equals(other.parametrosV1))
			return false;
		
		if (parametrosV2 == null) {
			if (other.parametrosV2 != null)
				return false;
		} else if (!parametrosV2.equals(other.parametrosV2))
			return false;	
		
		if (handlerVersion == null) {
			if (other.handlerVersion != null)
				return false;
		} else if (!handlerVersion.equals(other.handlerVersion))
			return false;	
	
		if (eventos == null) {
			if (other.eventos != null)
				return false;
		} else if (!eventos.equals(other.eventos))
			return false;		
		
		if (taxas == null) {
			if (other.taxas != null)
				return false;
		} else if (!taxas.equals(other.taxas))
			return false;
		if (this.getDataInicio() == null) {
			if (other.getDataInicio() != null)
				return false;
		} else if (!this.getDataInicio().equals(other.getDataInicio()))
			return false;

		return true;
	}
	
	
	
}
