package br.org.ons.exemplo.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsula os dados de uma resposta de execução de regra de negócio
 */
public class RuleResponse {

	/**
	 * Parâmetros de saída da regra
	 */
	private Map<String, Object> outputParameters = new HashMap<>();

	/**
	 * @param outputParameters Parâmetros de saída da regra
	 */
	public RuleResponse(Map<String, Object> outputParameters) {
		super();
		this.outputParameters = outputParameters;
	}

	/**
	 * @return o campo outputParameters
	 */
	public Map<String, Object> getOutputParameters() {
		return outputParameters;
	}

	/**
	 * @param outputParameters o campo outputParameters a ser definido
	 */
	public void setOutputParameters(Map<String, Object> outputParameters) {
		this.outputParameters = outputParameters;
	}
}
