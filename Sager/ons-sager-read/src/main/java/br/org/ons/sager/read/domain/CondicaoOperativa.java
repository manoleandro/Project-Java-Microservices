package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

/**
 * Condição Operativa.
 */
@QueryEntity
@KeySpace("condicaoOperativa")
public class CondicaoOperativa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	private String id;

    private String descricao;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}   
	
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CondicaoOperativa estadoOperativo = (CondicaoOperativa) o;
        if(estadoOperativo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, estadoOperativo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "CondicaoOperativa [id=" + id + ", descricao=" + descricao + "]";
	}

}
