package br.org.ons.platform.bus.dto;

import br.org.ons.platform.common.CommandMetadata;

/**
 * Mensagem que encapsula um comando e seus metadados
 *
 * @param <C> Comando encapsulado
 */
public class GenericCommandMessage {

	/**
	 * Metadados do comando
	 */
	private CommandMetadata metadata = new CommandMetadata();
	
	/**
	 * Comando
	 */
	private GenericCommand command;

	/**
	 * Modifica��o a ser aplicada ao comando. Utilizado em caso de
	 * reprocessamento ou processamento de cen�rio
	 */
	private GenericCommandModification modification;

    /**
	 * @return o campo metadata
	 */
	public CommandMetadata getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata o campo metadata a ser definido
	 */
	public void setMetadata(CommandMetadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return o campo command
	 */
	public GenericCommand getCommand() {
		return command;
	}

	/**
	 * @param command o campo command a ser definido
	 */
	public void setCommand(GenericCommand command) {
		this.command = command;
	}

	/**
	 * @return o campo modification
	 */
	public GenericCommandModification getModification() {
		return modification;
	}

	/**
	 * @param modification o campo modification a ser definido
	 */
	public void setModification(GenericCommandModification modification) {
		this.modification = modification;
	}

	@Override
    public String toString() {
        return "GenericCommandMessage{" +
            "metadata=" + metadata +
            ", command='" + command + "'" +
            ", modification='" + modification + "'" +
            '}';
    }
}
