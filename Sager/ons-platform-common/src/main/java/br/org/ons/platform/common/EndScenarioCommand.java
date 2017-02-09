package br.org.ons.platform.common;

/**
 * Comando para encerrar um cenário paralelo
 */
public class EndScenarioCommand extends Command {

	/**
	 * ID do cenário a ser encerrado
	 */
	private String scenarioId;

	/**
	 * Indica se o cenário deve se tornar o principal
	 */
	private Boolean makeDefault;

	/**
	 * @return o campo scenarioId
	 */
	public String getScenarioId() {
		return scenarioId;
	}

	/**
	 * @param scenarioId
	 *            o campo scenarioId a ser definido
	 */
	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

	/**
	 * @return o campo makeDefault
	 */
	public Boolean getMakeDefault() {
		return makeDefault;
	}

	/**
	 * @param makeDefault
	 *            o campo makeDefault a ser definido
	 */
	public void setMakeDefault(Boolean makeDefault) {
		this.makeDefault = makeDefault;
	}

	@Override
	public String toString() {
		return "EndScenarioCommand [scenarioId=" + scenarioId + ", makeDefault=" + makeDefault + "]";
	}
}
