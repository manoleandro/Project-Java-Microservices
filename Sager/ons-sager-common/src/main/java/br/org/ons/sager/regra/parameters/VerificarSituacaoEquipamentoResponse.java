package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.Comentario;

public class VerificarSituacaoEquipamentoResponse {
	
	

	private List<Comentario> comentarios = new ArrayList<>();
	
	
	private boolean apto;
	

	/**
	 * @return the comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the apto
	 */
	public boolean isApto() {
		return apto;
	}

	/**
	 * @param apto the apto to set
	 */
	public void setApto(boolean apto) {
		this.apto = apto;
	}
	
	

}
