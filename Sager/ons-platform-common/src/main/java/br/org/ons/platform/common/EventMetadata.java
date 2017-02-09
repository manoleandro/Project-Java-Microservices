package br.org.ons.platform.common;

/**
 * Conjunto de metadados específico para eventos
 */
public class EventMetadata extends Metadata {

	/**
	 * Tópico onde o evento foi publicado
	 */
	private String topicString;

    /**
	 * @return o campo topicString
	 */
	public String getTopicString() {
		return topicString;
	}

	/**
	 * @param topicString o campo topicString a ser definido
	 */
	public void setTopicString(String topicString) {
		this.topicString = topicString;
	}

	@Override
    public String toString() {
        return "EventMetadata{" +
            "id=" + getId() +
            ", correlationId='" + getCorrelationId() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", majorVersion='" + getMajorVersion() + "'" +
            ", minorVersion='" + getMinorVersion() + "'" +
            ", timelineDate='" + getTimelineDate() + "'" +
            ", isReplay='" + getIsReplay() + "'" +
            ", topicString='" + topicString + "'" +
            ", properties='" + getProperties() + "'" +
            '}';
    }
}
