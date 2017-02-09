package br.org.ons.sager.instalacao.event;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.platform.common.Event;

/**
 * Evento que indica que um novo equipamento foi cadastrado em uma instalação.
 */
public class EquipamentoCadastradoEvent extends Event {

	/**
	 * Equipamento adicionado à instalação
	 */
	private Equipamento equipamento;

	/**
	 * @return o campo equipamento
	 */
	public Equipamento getEquipamento() {
		return equipamento;
	}

	/**
	 * @param equipamento o campo equipamento a ser definido
	 */
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EquipamentoCadastradoEvent [equipamento=" + equipamento + "]";
	}

}
