package br.org.ons.sager.instalacao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida;
import br.org.ons.platform.common.Event;

/**
 * Evento que indica o resultado de um processo de c�lculo de taxas 
 */
public class TaxasAcumuladasCalculadasV2Event extends Event {

	private Date dataInicioApuracao;
	private List<TaxaAcumuladaEstendida> taxas = new ArrayList<>();
	private List<ParametroTaxa> parametrosV1 = new ArrayList<>();
	private List<ParametroTaxa> parametrosV2 = new ArrayList<>();

	public Date getDataInicioApuracao() {
		return dataInicioApuracao;
	}

	public void setDataInicioApuracao(Date dataInicioApuracao) {
		this.dataInicioApuracao = dataInicioApuracao;
	}

	public List<TaxaAcumuladaEstendida> getTaxas() {
		return taxas;
	}

	public void addTaxas(List<TaxaAcumuladaEstendida> taxas) {
		this.taxas.addAll(taxas);
	}

	public List<ParametroTaxa> getParametrosV1() {
		return parametrosV1;
	}

	public void addParametrosV1(List<ParametroTaxa> parametrosV1) {
		this.parametrosV1.addAll(parametrosV1);
	}
	
	public void setParametrosV1(List<ParametroTaxa> parametrosV1){
		this.parametrosV1 = parametrosV1;
	}
	

	public List<ParametroTaxa> getParametrosV2() {
		return parametrosV2;
	}

	public void addParametrosV2(List<ParametroTaxa> parametrosV2) {
		this.parametrosV2.addAll(parametrosV2);
	}
	
	public void setParametrosV2(List<ParametroTaxa> parametrosV2){
		this.parametrosV2 = parametrosV2;
	}	
	
	
}
