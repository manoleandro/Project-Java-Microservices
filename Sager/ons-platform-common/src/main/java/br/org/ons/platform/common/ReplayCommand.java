package br.org.ons.platform.common;

/**
 * Comando para reprodução de um comando de uma timeline de um aggregate.
 * Opcionalmente, pode ser especificada uma modificação para o comando.
 */
public class ReplayCommand<M extends CommandModification> extends Command {

	/**
	 * ID de correlação do comando a ser reproduzido
	 */
	private String correlationId;
	/**
	 * Modificação opcional a ser aplicada ao comando
	 */
	private M modification;

	/**
	 * @return o campo correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId
	 *            o campo correlationId a ser definido
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return o campo modification
	 */
	public M getModification() {
		return modification;
	}

	/**
	 * @param modification
	 *            o campo modification a ser definido
	 */
	public void setModification(M modification) {
		this.modification = modification;
	}
}
