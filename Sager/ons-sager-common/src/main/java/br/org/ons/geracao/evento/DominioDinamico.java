package br.org.ons.geracao.evento;

import java.io.Serializable;

/**
 * Classe pai dos tipos alimentados por domínios dinâmicos do IBM ODM
 */
public abstract class DominioDinamico  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codigo;

	/**
	 * @return o campo codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo o campo codigo a ser definido
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
