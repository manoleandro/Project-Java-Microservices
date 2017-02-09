package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.modelagem.Periodo;


public class CalcularTaxasAcumuladasRequestV1 {
	
	private Periodo janelaCalculo;
	
	private Instalacao instalacao;
	
	/**
	 * Todas as taxas calculadas nos ultimos 60 meses "ativos".
	 * - Se não tiver 60 meses de taxas calculadas, mandar o que tiver
	 * - Pular os periodos de suspensão da instalação inteira (nestes meses, não existem taxas).
	 */
	private List<Taxa> taxas = new ArrayList<Taxa>();
	
	public Periodo getJanelaCalculo() {
		return janelaCalculo;
	}
	
	public void setJanelaCalculo(Periodo janelaCalculo) {
		this.janelaCalculo = janelaCalculo;
	}

	public Instalacao getInstalacao() {
		return instalacao;
	}
	
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
	}
	
	public List<Taxa> getTaxas() {
		return taxas;
	}

	public void setTaxas(List<Taxa> taxas) {
		this.taxas = taxas;
	}
}
