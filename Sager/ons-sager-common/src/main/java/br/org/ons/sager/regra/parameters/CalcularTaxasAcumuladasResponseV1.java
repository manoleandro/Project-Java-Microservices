package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.taxa.TaxaAcumulada;

public class CalcularTaxasAcumuladasResponseV1 {
	private List<TaxaAcumulada> taxasAcumuladas = new ArrayList<TaxaAcumulada>();

	public List<TaxaAcumulada> getTaxasAcumuladas() {
		return taxasAcumuladas;
	}

	public void setTaxasAcumuladas(List<TaxaAcumulada> taxasAcumuladas) {
		this.taxasAcumuladas = taxasAcumuladas;
	}
	
	public boolean temTodasTaxasPassadas(int qtdeAlvo) {
		java.util.Iterator<TaxaAcumulada> it = taxasAcumuladas.iterator();
		while(it.hasNext())
			if(it.next().temTodasTaxasPassadas(qtdeAlvo) == false)
				return false;
		return true;
	}
}
