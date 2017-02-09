package br.org.ons.platform.common;

/**
 * Mensagem que encapsula um evento e seus metadados
 *
 * @param <E> Evento encapsulado
 */
public class EventMessage<E extends Event> {

	/**
	 * Metadados do evento
	 */
	private EventMetadata metadata = new EventMetadata();
	
	/**
	 * Evento
	 */
	private E event;

    /**
	 * @return o campo metadata
	 */
	public EventMetadata getMetadata() {
		return metadata;
	}


	/**
	 * @param metadata o campo metadata a ser definido
	 */
	public void setMetadata(EventMetadata metadata) {
		this.metadata = metadata;
	}


	/**
	 * @return o campo event
	 */
	public E getEvent() {
		return event;
	}


	/**
	 * @param event o campo event a ser definido
	 */
	public void setEvent(E event) {
		this.event = event;
	}

	@Override
    public String toString() {
        return "EventMessage{" +
            "metadata=" + metadata +
            ", event='" + event + "'" +
            '}';
    }
}
