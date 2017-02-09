package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Representa um período de apuração de taxas
 */
public class Apuracao {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;
	/**
	 * EVENTOS_APURADOS, PARAMETROS_CALCULADOS ou TAXAS_CALCULADAS
	 */
	private Status status;
	private List<Evento> eventos;
	private List<Parametro> parametros;
	private List<Taxa> taxas;

	public ZonedDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(ZonedDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public ZonedDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(ZonedDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public List<Taxa> getTaxas() {
		return taxas;
	}

	public void setTaxas(List<Taxa> taxas) {
		this.taxas = taxas;
	}

	@Override
    public String toString() {
        return "Apuracao{" +
            "dataInicio='" + dataInicio + "'" +
            ", dataFim='" + dataFim + "'" +
            ", status='" + status + "'" +
            ", eventos='" + eventos + "'" +
            ", parametros='" + parametros + "'" +
            ", taxas='" + taxas + "'" +
            '}';
    }
	
	public enum Status {
		EVENTOS_APURADOS, PARAMETROS_CALCULADOS, TAXAS_CALCULADAS
	}
}
