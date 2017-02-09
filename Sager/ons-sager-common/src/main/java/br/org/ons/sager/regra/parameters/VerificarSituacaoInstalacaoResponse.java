package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.ComentarioSituacao;

public class VerificarSituacaoInstalacaoResponse {

	private List<ComentarioSituacao> comentarios = new ArrayList<>();
	
	private boolean prossegue;

	/**
	 * @return the comentarios
	 */
	public List<ComentarioSituacao> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<ComentarioSituacao> comentarios) {
		this.comentarios = comentarios;
	}

	public boolean isProssegue() {
		return prossegue;
	}

	public void setProssegue(boolean prossegue) {
		this.prossegue = prossegue;
	}
	
	
}
