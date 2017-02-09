package br.org.ons.platform.common;

/**
 * Conjunto de metadados específico para comandos
 */
public class CommandMetadata extends Metadata {

	/**
	 * Nome do serviço que executou o comando
	 */
	private String handlerName;
	
	/**
	 * Versão do serviço que executou o comando
	 */
	private String handlerVersion;

    /**
	 * @return o campo handlerName
	 */
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * @param handlerName o campo handlerName a ser definido
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * @return o campo handlerVersion
	 */
	public String getHandlerVersion() {
		return handlerVersion;
	}

	/**
	 * @param handlerVersion o campo handlerVersion a ser definido
	 */
	public void setHandlerVersion(String handlerVersion) {
		this.handlerVersion = handlerVersion;
	}

	@Override
    public String toString() {
        return "CommandMetadata{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", majorVersion='" + getMajorVersion() + "'" +
            ", minorVersion='" + getMinorVersion() + "'" +
            ", timelineDate='" + getTimelineDate() + "'" +
            ", isReplay='" + getIsReplay() + "'" +
            ", handlerName='" + handlerName + "'" +
            ", handlerVersion='" + handlerVersion + "'" +
            ", properties='" + getProperties() + "'" +
            '}';
    }
}
