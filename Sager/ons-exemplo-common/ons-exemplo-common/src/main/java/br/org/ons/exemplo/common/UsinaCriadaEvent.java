package br.org.ons.exemplo.common;

import br.org.ons.platform.common.Event;

/**
 * Evento publicado quando uma nova usina é criada
 */
public class UsinaCriadaEvent extends Event {

	private Usina usina;

	public UsinaCriadaEvent() {
		super();
	}
	
	public UsinaCriadaEvent(Usina usina) {
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
        return "UsinaCriadaEvent{" +
            "usina='" + usina + "'" +
            '}';
    }
}
