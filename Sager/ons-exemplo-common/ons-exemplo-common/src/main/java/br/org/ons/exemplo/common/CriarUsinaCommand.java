package br.org.ons.exemplo.common;

import br.org.ons.platform.common.CreateAggregateCommand;

/**
 * Comando para criar uma nova usina. Este comando cria novas instâncias de
 * aggregate
 */
public class CriarUsinaCommand extends CreateAggregateCommand {

	private Usina usina;

	public Usina getUsina() {
		return usina;
	}

	public void setUsina(Usina usina) {
		this.usina = usina;
	}

	@Override
	public String toString() {
		return "CriarUsinaCommand{" + "usina='" + usina + "'" + '}';
	}
}
