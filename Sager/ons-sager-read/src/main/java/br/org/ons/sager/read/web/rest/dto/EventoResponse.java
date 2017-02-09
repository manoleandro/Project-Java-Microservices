package br.org.ons.sager.read.web.rest.dto;

import org.springframework.data.domain.Page;

import br.org.ons.sager.read.domain.Evento;

public class EventoResponse {
	
	private Page<Evento> eventos;

	public Page<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Page<Evento> eventos) {
		this.eventos = eventos;
	}


}
