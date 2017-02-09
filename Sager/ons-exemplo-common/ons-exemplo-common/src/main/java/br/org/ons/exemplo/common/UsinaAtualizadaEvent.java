package br.org.ons.exemplo.common;

import br.org.ons.platform.common.Event;

/**
 * Evento publicado quando uma usina tem seus dados atualizados
 */
public class UsinaAtualizadaEvent extends Event {

	private Usina usina;

	public UsinaAtualizadaEvent() {
		super();
	}
	
	public UsinaAtualizadaEvent(Usina usina) {
		this.usina = usina;
	}
	
	public Usina getUsina() {
		return usina;
	}

	public void setUsina(Usina usina) {
		this.usina = usina;
	}

	@Override
    public String toString() {
        return "UsinaAtualizadaEvent{" +
            "usina='" + usina + "'" +
            '}';
    }
}
