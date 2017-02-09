package br.org.ons.exemplo.common;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.platform.common.CommandDataModification;

/**
 * Modificação de um comando ApurarEventosCommand. Pode adicionar, modificar ou
 * excluir eventos do comando.
 */
public class ApurarEventosCommandModification extends CommandDataModification {

	private List<Evento> eventosAdicionados = new ArrayList<>();
	private List<Evento> eventosModificados = new ArrayList<>();
	private List<Evento> eventosExcluidos = new ArrayList<>();

	public List<Evento> getEventosAdicionados() {
		return eventosAdicionados;
	}

	public void setEventosAdicionados(List<Evento> eventosAdicionados) {
		this.eventosAdicionados = eventosAdicionados;
	}

	public List<Evento> getEventosModificados() {
		return eventosModificados;
	}

	public void setEventosModificados(List<Evento> eventosModificados) {
		this.eventosModificados = eventosModificados;
	}

	public List<Evento> getEventosExcluidos() {
		return eventosExcluidos;
	}

	public void setEventosExcluidos(List<Evento> eventosExcluidos) {
		this.eventosExcluidos = eventosExcluidos;
	}

	@Override
	public String toString() {
		return "ApurarEventosCommandModification{" + 
			"eventosAdicionados=" + eventosAdicionados + 
			", eventosModificados=" + eventosModificados + 
			", eventosExcluidos=" + eventosExcluidos + 
			"}";
	}
}
