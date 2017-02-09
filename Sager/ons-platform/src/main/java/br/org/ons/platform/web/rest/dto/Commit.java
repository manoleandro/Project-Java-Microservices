package br.org.ons.platform.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.domain.model.GenericAggregate;
import br.org.ons.platform.domain.model.GenericCommand;

/**
 * Representa a efetivação da execução de um comando, persistindo os eventos
 * gerados na(s) timeline(s) main de um aggregate. Pode haver mais de uma
 * timeline main caso existam cenários paralelos para o aggregate
 */
public class Commit {

	/**
	 * Comando executado sobre o aggregate
	 */
	private GenericCommand command;
	/**
	 * Metadados da mensagem de comando
	 */
	private CommandMetadata metadata;
	/**
	 * Um ou mais estados do aggregate após a execução do comando
	 */
	private List<GenericAggregate> aggregates = new ArrayList<>();

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
	 * @return o campo aggregates
	 */
	public List<GenericAggregate> getAggregates() {
		return aggregates;
	}

	/**
	 * @param aggregates o campo aggregates a ser definido
	 */
	public void setAggregates(List<GenericAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	@Override
    public String toString() {
        return "GenericCommand{" +
            "command='" + command + "'" +
            ", metadata='" + metadata + "'" +
            ", aggregates='" + aggregates + "'" +
            '}';
    }
}
