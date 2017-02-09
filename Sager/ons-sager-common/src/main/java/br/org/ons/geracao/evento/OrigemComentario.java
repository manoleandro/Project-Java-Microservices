package br.org.ons.geracao.evento;

/**
 * 
 *
 */
public enum OrigemComentario {

	AGENTE(0, "Agente"), ONS(1, "ONS"), SISTEMA(2, "Sistema");

	private final int codigo;
	private final String descricao;

	private OrigemComentario(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	/**
	 * @return o campo codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @return o campo descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}
