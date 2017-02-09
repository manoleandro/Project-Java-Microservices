package br.org.ons.platform.domain.enumeration;

/**
 * Os possíveis tipos de cenário
 */
public enum ScenarioType {
	
    /**
     * Cenário padrão
     */
    DEFAULT,
    /**
     * Cenário paralelo criado a partir de um cenário padrão
     */
    PARALLEL,
    /**
     * Cenário temporário de teste
     */
    TEST
}
