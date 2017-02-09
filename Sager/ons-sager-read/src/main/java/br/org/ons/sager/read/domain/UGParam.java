package br.org.ons.sager.read.domain;

import java.util.Date;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("ugParam")
public class UGParam implements Comparable<UGParam>{
	
	private Integer ident;
	
	private Integer idOrdenacao;

	private Date data;
	
	private String idTaxa;
	
	private String taxa;
	
	private String tipoTaxa;
	
	public int getIdent() {
		return ident;
	}

	public void setIdent(Integer ident) {
		this.ident = ident;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTaxa() {
		return taxa;
	}

	public void setTaxa(String taxa) {
		this.taxa = taxa;
	}

	public String getTipoTaxa() {
		return tipoTaxa;
	}

	public void setTipoTaxa(String tipoTaxa) {
		this.tipoTaxa = tipoTaxa;
	}

	public Integer getIdOrdenacao() {
		return idOrdenacao;
	}

	public String getIdTaxa() {
		return idTaxa;
	}

	public void setIdTaxa(String idTaxa) {
		this.idTaxa = idTaxa;
	}

	public void setIdOrdenacao(Integer idOrdenacao) {
		this.idOrdenacao = idOrdenacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOrdenacao == null) ? 0 : idOrdenacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UGParam other = (UGParam) obj;
		if (idOrdenacao == null) {
			if (other.idOrdenacao != null)
				return false;
		} else if (!idOrdenacao.equals(other.idOrdenacao))
			return false;
		return true;
	}

	@Override
	public int compareTo(UGParam o) {
		return this.idOrdenacao.compareTo(o.getIdOrdenacao());
	}

	@Override
	public String toString() {
		return "UGParam [idOrdenacao=" + idOrdenacao + "]";
	}

}
