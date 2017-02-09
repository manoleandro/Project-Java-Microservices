package br.org.ons.platform.domain.enumeration;

/**
 * Os possíveis estados de cenário
 */
public enum ScenarioStatus {
    
	/**
	 * Cenário ativo: habilitado para receber novos registros
	 */
	ACTIVE,
    /**
     * Cenário inativo: persistido somente para histórico
     */
    INACTIVE
}
