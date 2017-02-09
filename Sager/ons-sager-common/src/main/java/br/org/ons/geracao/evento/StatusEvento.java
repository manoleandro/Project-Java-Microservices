package br.org.ons.geracao.evento;

/**
 * 
 *
 */
public enum StatusEvento {

	/**
	 * Evento da base histórica não retificado
	 */
	NORMAL(0, "Normal"),
	RETIFICADO_INSERIDO(1, "Inserido na retificação"),
	RETIFICADO_ALTERADO(2, "Alterado na retificação"),
	RETIFICADO_REMOVIDO(3, "Removido na retificação");

	
	private final int codigo;
	private final String descricao;
	
	private StatusEvento(int codigo, String descricao) {
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
