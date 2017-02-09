package br.org.ons.sager.regra.parameters;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.modelagem.Periodo;

public class VerificarSituacaoEquipamentoRequest {

	
	private  Equipamento equipamento;
	
	
	private Periodo janelaCalculo;

	
	private String atividade;
	

	/**
	 * @return the equipamento
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}

	/**
	 * @param equipamento the equipamento to set
	 */
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	/**
	 * @return the janelaCalculo
	 */
	public Periodo getJanelaCalculo() {
		return janelaCalculo;
	}

	/**
	 * @param janelaCalculo the janelaCalculo to set
	 */
	public void setJanelaCalculo(Periodo janelaCalculo) {
		this.janelaCalculo = janelaCalculo;
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return atividade;
	}
	
	/**
	 * @param atividade the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	
}
