package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import br.org.ons.platform.common.Command;

/**
 * Comando para apurar os eventos de uma usina em um período
 */
public class ApurarEventosCommand extends Command {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;
	private List<Evento> eventos = new ArrayList<>();

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

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
    public String toString() {
        return "ApurarEventosCommand{" +
            "dataInicio='" + dataInicio + "'" +
            ", dataFim='" + dataFim + "'" +
            ", eventos='" + eventos + "'" +
            '}';
    }
}
