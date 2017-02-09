package br.org.ons.geracao.cadastro;

/**
 * Tipos de usina
 */
public enum TipoUsina {
	
	UHE("UHE", "Hidroelétrica"),
	UTE("UTE", "Térmica"),
	EOLICA("EOLICA", "Eólica"),
	SOLARBIO("SOLARBIO", "Solar Biomassa"),
	PCE("PCE", "Pequena Central Hidrelétrica");

	private final String codigo;
	private final String descricao;
	
	private TipoUsina(String codigo, String descricao) {
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
