package br.org.ons.exemplo.web.rest.dto;

import br.org.ons.exemplo.domain.CadastroEvento;

public class CadastroEventoRetificado extends CadastroEvento {

	private static final long serialVersionUID = 1L;

	private Boolean dirty = false;
	private Boolean deleted = false;

	public Boolean isDirty() {
		return dirty;
	}

	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

    @Override
    public String toString() {
        return "CadastroEvento{" +
            "id=" + getId() +
            ", dataVerificada='" + getDataVerificada() + "'" +
            ", estadoOperativo='" + getEstadoOperativo() + "'" +
            ", condicaoOperativa='" + getCondicaoOperativa() + "'" +
            ", classificacaoOrigem='" + getClassificacaoOrigem() + "'" +
            ", potenciaDisponivel='" + getPotenciaDisponivel() + "'" +
            ", aggregateId='" + getAggregateId() + "'" +
            ", majorVersion='" + getMajorVersion() + "'" +
            ", minorVersion='" + getMinorVersion() + "'" +
            ", timelineDate='" + getTimelineDate() + "'" +
            ", correlationId='" + getCorrelationId() + "'" +
            ", dirty='" + dirty + "'" +
            ", deleted='" + deleted + "'" +
            '}';
    }
}
