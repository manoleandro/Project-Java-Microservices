package br.org.ons.exemplo.common;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import br.org.ons.platform.common.Event;

/**
 * Evento publicado quando os eventos de um período são apurados
 */
public class EventosApuradosEvent extends Event {

	private ZonedDateTime dataInicio;
	private ZonedDateTime dataFim;
	private List<Evento> eventos = new ArrayList<>();

	public EventosApuradosEvent() {
		super();
	}
	
	public EventosApuradosEvent(ZonedDateTime dataInicio, ZonedDateTime dataFim, List<Evento> eventos) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.eventos = eventos;
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

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
    public String toString() {
        return "EventosApuradosEvent{" +
                "dataInicio='" + dataInicio + "'" +
                ", dataFim='" + dataFim + "'" +
                ", eventos='" + eventos + "'" +
            '}';
    }
}
