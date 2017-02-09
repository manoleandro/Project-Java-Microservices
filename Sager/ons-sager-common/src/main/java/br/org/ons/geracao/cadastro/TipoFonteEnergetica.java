package br.org.ons.geracao.cadastro;

/**
 * Classificação da unidade geradora de energia elétrica quanto à sua natureza
 * (hidráulica, termoelétrica, eólica, etc.).
 */
public enum TipoFonteEnergetica {

	UHE("UHE", "Hidrelétrica"), 
	UTE("UTE", "Térmica"), 
	EOLICA("EOLICA", "Eólica");

	private final String codigo;
	private final String descricao;

	private TipoFonteEnergetica(String codigo, String descricao) {
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
