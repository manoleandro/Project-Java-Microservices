package br.org.ons.sager.regra.parameters;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.geracao.evento.taxa.Taxa;

public class CalcularTaxasMensaisResponse {

	private List<Taxa> taxasMensais = new ArrayList<>();

	public List<Taxa> getTaxasMensais() {
		return taxasMensais;
	}

	public void setTaxasMensais(List<Taxa> taxasMensais) {
		this.taxasMensais = taxasMensais;
	}
	
	
}
