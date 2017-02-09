package br.org.ons.exemplo.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

/**
 * Representa um registro de Cadastro de Evento do modelo de leitura exposto aos
 * usuários através do portal Web. Este repositório deve ser consumido apenas
 * para leitura pelos serviços REST, sendo atualizado somente através dos
 * EventHandlers
 */
@KeySpace("CadastroEvento")
public class CadastroEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private ZonedDateTime dataVerificada;

    private String estadoOperativo;

    private String condicaoOperativa;

    private String classificacaoOrigem;

    private Double potenciaDisponivel;

    private String aggregateId;

    private Long majorVersion;

    private Long minorVersion;

    private ZonedDateTime timelineDate;

    private String correlationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDataVerificada() {
        return dataVerificada;
    }

    public void setDataVerificada(ZonedDateTime dataVerificada) {
        this.dataVerificada = dataVerificada;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public String getCondicaoOperativa() {
        return condicaoOperativa;
    }

    public void setCondicaoOperativa(String condicaoOperativa) {
        this.condicaoOperativa = condicaoOperativa;
    }

    public String getClassificacaoOrigem() {
        return classificacaoOrigem;
    }

    public void setClassificacaoOrigem(String classificacaoOrigem) {
        this.classificacaoOrigem = classificacaoOrigem;
    }

    public Double getPotenciaDisponivel() {
        return potenciaDisponivel;
    }

    public void setPotenciaDisponivel(Double potenciaDisponivel) {
        this.potenciaDisponivel = potenciaDisponivel;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public Long getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	public Long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(Long minorVersion) {
		this.minorVersion = minorVersion;
	}

    public ZonedDateTime getTimelineDate() {
        return timelineDate;
    }

    public void setTimelineDate(ZonedDateTime timelineDate) {
        this.timelineDate = timelineDate;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CadastroEvento cadastroEvento = (CadastroEvento) o;
        if(cadastroEvento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cadastroEvento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CadastroEvento{" +
            "id=" + id +
            ", dataVerificada='" + dataVerificada + "'" +
            ", estadoOperativo='" + estadoOperativo + "'" +
            ", condicaoOperativa='" + condicaoOperativa + "'" +
            ", classificacaoOrigem='" + classificacaoOrigem + "'" +
            ", potenciaDisponivel='" + potenciaDisponivel + "'" +
            ", aggregateId='" + aggregateId + "'" +
            ", majorVersion='" + majorVersion + "'" +
            ", minorVersion='" + minorVersion + "'" +
            ", timelineDate='" + timelineDate + "'" +
            ", correlationId='" + correlationId + "'" +
            '}';
    }
}
