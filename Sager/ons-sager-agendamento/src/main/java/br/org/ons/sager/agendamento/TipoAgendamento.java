package br.org.ons.sager.agendamento;

/**
 * Tipo de Agendamento
 */
public enum TipoAgendamento { 

	AGENDADO("AGENDADO"), 
	EM_EXECUCAO("EM EXECUCAO"), 
	FINALIZADO("FINALIZADO"),
	ERRO("ERRO"),
	SUCESSO("SUCESSO"),
	CANCELADO("CANCELADO");

	private final String descricao;

	private TipoAgendamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
