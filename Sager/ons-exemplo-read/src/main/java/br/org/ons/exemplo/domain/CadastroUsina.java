package br.org.ons.exemplo.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

/**
 * Representa um registro de Cadastro de Usina do modelo de leitura exposto aos
 * usuários através do portal Web. Este repositório deve ser consumido apenas
 * para leitura pelos serviços REST, sendo atualizado somente através dos
 * EventHandlers
 */
@KeySpace("CadastroUsina")
public class CadastroUsina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String version;

    private String nomeCurto;

    private String tipoUsina;

    private Double potenciaCalculo;

    private String apuracoes;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNomeCurto() {
        return nomeCurto;
    }

    public void setNomeCurto(String nomeCurto) {
        this.nomeCurto = nomeCurto;
    }

    public String getTipoUsina() {
        return tipoUsina;
    }

    public void setTipoUsina(String tipoUsina) {
        this.tipoUsina = tipoUsina;
    }

    public Double getPotenciaCalculo() {
        return potenciaCalculo;
    }

    public void setPotenciaCalculo(Double potenciaCalculo) {
        this.potenciaCalculo = potenciaCalculo;
    }

    public String getApuracoes() {
		return apuracoes;
	}

	public void setApuracoes(String apuracoes) {
		this.apuracoes = apuracoes;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CadastroUsina cadastroUsina = (CadastroUsina) o;
        if(cadastroUsina.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cadastroUsina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CadastroUsina{" +
            "id=" + id +
            ", version='" + version + "'" +
            ", nomeCurto='" + nomeCurto + "'" +
            ", tipoUsina='" + tipoUsina + "'" +
            ", potenciaCalculo='" + potenciaCalculo + "'" +
            ", apuracoes=" + apuracoes + 
            '}';
    }
}
