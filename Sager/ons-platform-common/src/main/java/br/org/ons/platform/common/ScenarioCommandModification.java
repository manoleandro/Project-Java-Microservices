package br.org.ons.platform.common;

/**
 * Representa uma modificação a ser aplicada a um comando de acordo com a regra
 * de um cenário paralelo.
 */
public class ScenarioCommandModification extends CommandModification {

	/**
	 * Nome do cenário, a ser identificado pela regra
	 */
	private String scenarioName;

	/**
	 * @return o campo scenarioName
	 */
	public String getScenarioName() {
		return scenarioName;
	}

	/**
	 * @param scenarioName o campo scenarioName a ser definido
	 */
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((scenarioName == null) ? 0 : scenarioName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		ScenarioCommandModification other = (ScenarioCommandModification) obj;
		if (scenarioName == null) {
			if (other.scenarioName != null) {
				return false;
			}
		} else if (!scenarioName.equals(other.scenarioName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ScenarioCommandModification{" + "scenarioName=" + scenarioName + "}";
	}
}
