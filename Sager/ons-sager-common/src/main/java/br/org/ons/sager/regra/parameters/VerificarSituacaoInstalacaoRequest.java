package br.org.ons.sager.regra.parameters;

import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.modelagem.Periodo;

public class VerificarSituacaoInstalacaoRequest {

	
	
	private  Instalacao instalacao;
	
	
	private Periodo janelaCalculo;

	
	private String atividade;
	
	/**
	 * @return the instalacao
	 */
	public Instalacao getInstalacao() {
		return instalacao;
	}

	/**
	 * @param instalacao the instalacao to set
	 */
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
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
