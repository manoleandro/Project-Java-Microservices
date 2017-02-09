package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.modelagem.Periodo;

public class CalcularTaxasMensaisRequest {	
	
	
	
	
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();
	
	private Periodo janelaCalculo;	
	
	private List<ParametroTaxa> listaParametros = new ArrayList<ParametroTaxa>();

	

	/**
	 * @return the equipamentos
	 */
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}


	/**
	 * @param equipamentos the equipamentos to set
	 */
	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}


	public Periodo getJanelaCalculo() {
		return janelaCalculo;
	}


	public void setJanelaCalculo(Periodo janelaCalculo) {
		this.janelaCalculo = janelaCalculo;
	}


	public List<ParametroTaxa> getListaParametros() {
		return listaParametros;
	}


	public void setListaParametros(List<ParametroTaxa> listaParametros) {
		this.listaParametros = listaParametros;
	}
	
	
	
	

}
