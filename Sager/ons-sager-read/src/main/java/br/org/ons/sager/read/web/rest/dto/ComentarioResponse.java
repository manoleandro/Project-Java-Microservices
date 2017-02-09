package br.org.ons.sager.read.web.rest.dto;

import java.util.List;

import br.org.ons.sager.read.domain.Comentario;

public class ComentarioResponse {
	
	private List<Comentario> comentarios;

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

}
