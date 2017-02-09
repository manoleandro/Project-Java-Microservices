package br.org.ons.sager.read.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("taxaEventosUGParam")
public class TaxaEventosUGParam extends UGParam {

	private BigDecimal valor;
	
	private Set<String> taxaEventosId;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Set<String> getTaxaEventosId() {
		return taxaEventosId;
	}

	public void setTaxaEventosId(Set<String> taxaEventosId) {
		this.taxaEventosId = taxaEventosId;
	}
	
	public void addTaxaEventosIds(Set<String> taxaEventosId){
		
		if( this.taxaEventosId == null){
			this.taxaEventosId = new HashSet<>();
		}
		this.taxaEventosId.addAll(taxaEventosId);		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getIdOrdenacao() == null) ? 0 : this.getIdOrdenacao().hashCode());
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
		if (this.getIdOrdenacao() == null) {
			if (other.getIdOrdenacao() != null)
				return false;
		} else if (!this.getIdOrdenacao().equals(other.getIdOrdenacao()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaxaEventosUGParam [getData()=" + getData() + ", getTaxa()=" + getTaxa() + ", getTipoTaxa()="
				+ getTipoTaxa() + "]";
	}
	
	
	
}
