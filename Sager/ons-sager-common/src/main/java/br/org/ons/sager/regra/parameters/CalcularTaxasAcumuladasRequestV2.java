package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Instalacao;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.modelagem.Periodo;

public class CalcularTaxasAcumuladasRequestV2 {
	
	private Periodo janelaCalculo;
	
	private Instalacao instalacao;
	
	private List<ParametroTaxa> parametrosRes688_2003 = new ArrayList<ParametroTaxa>(); // V1
	private List<ParametroTaxa> parametrosRes614_2014 = new ArrayList<ParametroTaxa>(); // V2

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
		
	public List<ParametroTaxa> getParametrosRes688_2003() {
		return parametrosRes688_2003;
	}

	public void setParametrosRes688_2003(List<ParametroTaxa> parametrosRes688_2003) {
		this.parametrosRes688_2003 = parametrosRes688_2003;
	}
	
	public List<ParametroTaxa> getParametrosRes614_2014() {
		return parametrosRes614_2014;
	}

	public void setParametrosRes614_2014(List<ParametroTaxa> parametrosRes614_2014) {
		this.parametrosRes614_2014 = parametrosRes614_2014;
	}
}