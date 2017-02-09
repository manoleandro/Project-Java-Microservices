package br.org.ons.sager.instalacao.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsula os dados de uma requisição a regra de negócio
 */
public class RuleRequest {

	/**
	 * Nome da rule application
	 */
	private String ruleAppName;
	/**
	 * Versão da rule application
	 */
	private String ruleAppVersion;
	/**
	 * Nome do rule set
	 */
	private String ruleSetName;
	/**
	 * Versão do rule set
	 */
	private String ruleSetVersion;

	/**
	 * Parâmetros de saída da regra
	 */
	private Map<String, Object> inputParameters = new HashMap<>();

	/**
	 * @param ruleAppName
	 *            Nome da rule application (not null)
	 * @param ruleAppVersion
	 *            Versão da rule application
	 * @param ruleSetName
	 *            Nome do rule set (not null)
	 * @param ruleSetVersion
	 *            Versão do rule set
	 */
	public RuleRequest(String ruleAppName, String ruleAppVersion, String ruleSetName, String ruleSetVersion) {
		assert ruleAppName != null;
		assert ruleSetName != null;
		this.ruleAppName = ruleAppName;
		this.ruleAppVersion = ruleAppVersion;
		this.ruleSetName = ruleSetName;
		this.ruleSetVersion = ruleSetVersion;
	}

	/**
	 * @return Caminho da regra a ser invocada: /ruleAppName/ruleAppVersion/ruleSetName/ruleSetVersion
	 */
	public String getRulePath() {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(this.ruleAppName);
		if (this.ruleAppVersion != null && this.ruleAppVersion.length() > 0)
			sb.append("/").append(this.ruleAppVersion);
		sb.append("/").append(this.ruleSetName);
		if (this.ruleSetVersion != null && this.ruleSetVersion.length() > 0)
			sb.append("/").append(this.ruleSetVersion);
		return sb.toString();
	}

	/**
	 * @return o campo ruleAppName
	 */
	public String getRuleAppName() {
		return ruleAppName;
	}

	/**
	 * @return o campo ruleAppVersion
	 */
	public String getRuleAppVersion() {
		return ruleAppVersion;
	}

	/**
	 * @return o campo ruleSetName
	 */
	public String getRuleSetName() {
		return ruleSetName;
	}

	/**
	 * @return o campo ruleSetVersion
	 */
	public String getRuleSetVersion() {
		return ruleSetVersion;
	}

	/**
	 * @return o campo inputParameters
	 */
	public Map<String, Object> getInputParameters() {
		return inputParameters;
	}

	/**
	 * @param inputParameters
	 *            o campo inputParameters a ser definido
	 */
	public void setInputParameters(Map<String, Object> inputParameters) {
		this.inputParameters = inputParameters;
	}
}
