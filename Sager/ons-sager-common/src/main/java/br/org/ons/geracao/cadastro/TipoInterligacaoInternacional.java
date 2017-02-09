package br.org.ons.geracao.cadastro;

/**
 * Tipos de interligação internacional
 */
public enum TipoInterligacaoInternacional {
	
	IMPORTACAO("IMPORTACAO", "Importação de Energia Elétrica do Exterior"),
	EXPORTACAO("EXPORTACAO", "Exportação de Energia Elétrica para o Exterior");

	private final String codigo;
	private final String descricao;
	
	private TipoInterligacaoInternacional(String codigo, String descricao) {
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
