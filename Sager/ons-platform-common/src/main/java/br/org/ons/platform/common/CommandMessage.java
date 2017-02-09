package br.org.ons.platform.common;

/**
 * Mensagem que encapsula um comando e seus metadados
 *
 * @param <C> Comando encapsulado
 */
public class CommandMessage<C extends Command> {

	/**
	 * Metadados do comando
	 */
	private CommandMetadata metadata = new CommandMetadata();
	
	/**
	 * Comando
	 */
	private C command;

	/**
	 * Modificação a ser aplicada ao comando. Utilizado em caso de
	 * reprocessamento ou processamento de cenário
	 */
	private CommandModification modification;

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
	public C getCommand() {
		return command;
	}

	/**
	 * @param command o campo command a ser definido
	 */
	public void setCommand(C command) {
		this.command = command;
	}

	/**
	 * @return o campo modification
	 */
	public CommandModification getModification() {
		return modification;
	}

	/**
	 * @param modification o campo modification a ser definido
	 */
	public void setModification(CommandModification modification) {
		this.modification = modification;
	}

	@Override
    public String toString() {
        return "CommandMessage{" +
            "metadata=" + metadata +
            ", command='" + command + "'" +
            ", modification='" + modification + "'" +
            '}';
    }
}
