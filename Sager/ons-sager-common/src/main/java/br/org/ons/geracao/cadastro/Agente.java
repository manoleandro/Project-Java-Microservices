package br.org.ons.geracao.cadastro;

import java.io.Serializable;

/**
 * Proprietário ou concessionário de alguma instalação do sistema elétrico
 * nacional.
 */
public class Agente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String sigla;
	private String nomeCurto;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla
	 *            o campo sigla a ser definido
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return o campo nomeCurto
	 */
	public String getNomeCurto() {
		return nomeCurto;
	}

	/**
	 * @param nomeCurto
	 *            o campo nomeCurto a ser definido
	 */
	public void setNomeCurto(String nomeCurto) {
		this.nomeCurto = nomeCurto;
	}
}
