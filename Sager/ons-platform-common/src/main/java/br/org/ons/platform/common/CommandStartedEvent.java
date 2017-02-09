package br.org.ons.platform.common;

/**
 * Sinaliza o início do processamento de um comando pela plataforma
 */
public class CommandStartedEvent extends Event {
	
	private String commandName;
	
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	

}
