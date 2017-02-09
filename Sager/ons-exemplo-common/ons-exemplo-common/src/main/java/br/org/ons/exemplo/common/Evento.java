package br.org.ons.exemplo.common;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Representa um evento de mudança de estado operativo de uma usina
 */
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private ZonedDateTime dataVerificada;
	private String estadoOperativo;
	private String condicaoOperativa;
	private String classificacaoOrigem;
	private Double potenciaDisponivel;
	
	public Evento() {
		super();
	}
	
	public Evento(String id, ZonedDateTime dataVerificada, String estadoOperativo, String condicaoOperativa,
			String classificacaoOrigem, Double potenciaDisponivel) {
		this.id = id;
		this.dataVerificada = dataVerificada;
		this.estadoOperativo = estadoOperativo;
		this.condicaoOperativa = condicaoOperativa;
		this.classificacaoOrigem = classificacaoOrigem;
		this.potenciaDisponivel = potenciaDisponivel;
	}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evento evento = (Evento) o;
        if(evento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
    public String toString() {
        return "Evento{'" + id + 
        	"','" + dataVerificada + "'" +
            ", '" + estadoOperativo + "'" +
            ", '" + condicaoOperativa + "'" +
            ", '" + classificacaoOrigem + "'" +
            ", '" + potenciaDisponivel + "'" +
            '}';
    }
}
