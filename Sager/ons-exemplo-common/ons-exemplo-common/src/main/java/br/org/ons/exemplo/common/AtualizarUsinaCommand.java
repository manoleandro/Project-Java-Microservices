package br.org.ons.exemplo.common;

import br.org.ons.platform.common.Command;

/**
 * Comando para atualizar os dados de uma usina
 */
public class AtualizarUsinaCommand extends Command {

	private Usina usina;

	public Usina getUsina() {
		return usina;
	}

	public void setUsina(Usina usina) {
		this.usina = usina;
	}

	@Override
    public String toString() {
        return "AtualizarUsinaCommand{" +
            "usina='" + usina + "'" +
            '}';
    }
}
