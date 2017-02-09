package br.org.ons.geracao.modelagem;

import java.util.ArrayList;
import java.util.List;
import br.org.ons.geracao.evento.franquia.Franquia;

/**
 * Representa os contadores (ex. franquia) em um determinado perÃ­odo.
 *
 */
public class ContadoresPeriodo extends Periodo {

	private static final long serialVersionUID = 1L;
	
	private List<Franquia> franquias = new ArrayList<>(); // franquias do equipamento vigentes no período

	/**
	 * @return o campo franquias
	 */
	public List<Franquia> getFranquias() {
		return franquias;
	}

	/**
	 * @param franquias
	 *            o campo franquias a ser definido
	 */
	public void setFranquias(List<Franquia> franquias) {
		this.franquias = franquias;
	}
	
	public Franquia franquia(String codigo) {
		java.util.Iterator<Franquia> it = franquias.iterator();
		while(it.hasNext()) {
			Franquia franquia = it.next();
			if(franquia.getCodigo().equals(codigo))
				return franquia;
		}
		return null;
	}
}