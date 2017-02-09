package br.org.ons.platform.common;

/**
 * Comando para criação de um novo cenário paralelo
 */
public class CreateScenarioCommand extends Command {

	/**
	 * Nome da instância do aggregate. Utilizada para fins infomativos na UI
	 */
	private String aggregateName;

	/**
	 * Descrição do cenário
	 */
	private String scenarioDescription;

	/**
	 * DEFAULT, PARALLEL ou TEST
	 */
	private String scenarioType;

	/**
	 * @return o campo aggregateName
	 */
	public String getAggregateName() {
		return aggregateName;
	}

	/**
	 * @param aggregateName
	 *            o campo aggregateName a ser definido
	 */
	public void setAggregateName(String aggregateName) {
		this.aggregateName = aggregateName;
	}

	/**
	 * @return o campo scenarioDescription
	 */
	public String getScenarioDescription() {
		return scenarioDescription;
	}

	/**
	 * @param scenarioDescription
	 *            o campo scenarioDescription a ser definido
	 */
	public void setScenarioDescription(String scenarioDescription) {
		this.scenarioDescription = scenarioDescription;
	}

	/**
	 * @return o campo scenarioType
	 */
	public String getScenarioType() {
		return scenarioType;
	}

	/**
	 * @param scenarioType
	 *            o campo scenarioType a ser definido
	 */
	public void setScenarioType(String scenarioType) {
		this.scenarioType = scenarioType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateScenarioCommand [aggregateName=" + aggregateName + ", scenarioDescription=" + scenarioDescription
				+ ", scenarioType=" + scenarioType + "]";
	}
}
