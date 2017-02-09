package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;

import br.org.ons.platform.common.Command;

/**
 * Comando para calcular taxas de um período
 */
public class CalcularTaxasCommand extends Command {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;

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

	@Override
    public String toString() {
        return "CalcularTaxasCommand{" +
            "dataInicio='" + dataInicio + "'" +
            ", dataFim='" + dataFim + "'" +
            '}';
    }
}
