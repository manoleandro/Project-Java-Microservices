package br.org.ons.sager.instalacao.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
import br.org.ons.platform.common.Event;

/**
 * Evento que indica o resultado de um processo de c�lculo de taxas 
 */
public class TaxasAcumuladasCalculadasV1Event extends Event {


	
	private Date dataInicioApuracao;
	private List<TaxaAcumulada> taxasAcumuladas = new ArrayList<>();
	private List<ParametroTaxa> parametros = new ArrayList<>();
	private List<Taxa> taxas = new ArrayList<>();

	public Date getDataInicioApuracao() {
		return dataInicioApuracao;
	}

	public void setDataInicioApuracao(Date dataInicioApuracao) {
		this.dataInicioApuracao = dataInicioApuracao;
	}

	public List<TaxaAcumulada> getTaxasAcumuladas() {
		return taxasAcumuladas;
	}
	
	public void addTaxasAcumuladas(List<TaxaAcumulada> taxasAcumuladas){
		this.taxasAcumuladas.addAll(taxasAcumuladas);
	}

	public List<ParametroTaxa> getParametros() {
		return parametros;
	}
	
	public void addParametros(List<ParametroTaxa> parametros){
		this.parametros.addAll(parametros);
	}

	public List<Taxa> getTaxas() {
		return taxas;
	}
	
	public void addTaxas(List<Taxa> taxas){
		this.taxas.addAll(taxas);
	}
	
	public void setTaxas(List<Taxa> taxas){
		this.taxas = taxas;
	}
	
}
