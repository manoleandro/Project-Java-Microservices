package br.org.ons.geracao.modelagem;

import java.io.Serializable;

/**
 * Representa um aviso ou um erro.
 *
 */
public class AvisoErro implements Serializable {
	
	private static final long serialVersionUID = 1L;


	private String mensagem;
	public enum Tipo { Aviso, Erro };
	private Tipo tipo;

	/**
	 * @return o campo mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem
	 *            o campo mensagem a ser definido
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return o campo tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
