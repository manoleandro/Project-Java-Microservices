package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.taxa.TaxaAcumuladaEstendida;

public class CalcularTaxasAcumuladasResponseV2 {

	private List<TaxaAcumuladaEstendida> taxasAcumuladas = new ArrayList<>();

	public List<TaxaAcumuladaEstendida> getTaxasAcumuladas() {
		return taxasAcumuladas;
	}

	public void setTaxasAcumuladas(List<TaxaAcumuladaEstendida> taxasAcumuladas) {
		this.taxasAcumuladas = taxasAcumuladas;
	}
	
/*	public boolean temTodasTaxasPassadas(int qtdeAlvo) {
		java.util.Iterator<TaxaAcumulada> it = taxasAcumuladas.iterator();
		while (it.hasNext())
			if (!it.next().temTodasTaxasPassadas(qtdeAlvo))
				return false;
		return true;
	} */
}