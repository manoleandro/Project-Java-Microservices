package br.org.ons.platform.domain.enumeration;

/**
 * Os tipos possíveis de registros
 */
public enum RecordType {
    
	/**
	 * Registro de comando
	 */
	COMMAND,
    /**
     * Registro de evento derivado de um comando
     */
    EVENT,
    /**
	 * Registro de snapshot, que armazena o estado do aggregate em um
	 * determinado instante
	 */
    SNAPSHOT
}
