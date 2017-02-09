package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;
import java.util.List;

import br.org.ons.platform.common.Event;

/**
 * Evento publicado quando as taxas de um período de apuraação são calculadas
 */
public class TaxasCalculadasEvent extends Event {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;
	private List<Taxa> taxas;

	public TaxasCalculadasEvent() {
		super();
	}
	
	public TaxasCalculadasEvent(ZonedDateTime dataInicio, ZonedDateTime dataFim, List<Taxa> taxas) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.taxas = taxas;
	}

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

	public List<Taxa> getTaxas() {
		return taxas;
	}

	public void setTaxas(List<Taxa> taxas) {
		this.taxas = taxas;
	}

	@Override
    public String toString() {
        return "TaxasCalculadasEvent{" +
            "dataInicio='" + dataInicio + "'" +
            ", dataFim='" + dataFim + "'" +
            ", taxas='" + taxas + "'" +
            '}';
    }
}
