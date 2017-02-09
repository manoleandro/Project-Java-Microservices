package br.org.ons.geracao.evento;

/**
 * 
 *
 */
public enum TipoOperacao {

	O("O", "Operação Comercial"),
	C("C", "Comissionamento");
	
	private final String codigo;
	private final String descricao;
	
	private TipoOperacao(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	/**
	 * @return o campo codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return o campo descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}
