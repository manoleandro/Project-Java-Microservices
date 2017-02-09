package br.org.ons.sager.read.domain;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("parametro")
public class Parametro {

	private String nomeParametro;
	
	private BigDecimal valorParametro;
	
	private Set<String> paramEventosId;

	public String getNomeParametro() {
		return nomeParametro;
	}

	public void setNomeParametro(String nomeParametro) {
		this.nomeParametro = nomeParametro;
	}

	public BigDecimal getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(BigDecimal valorParametro) {
		this.valorParametro = valorParametro;
	}

	public Set<String> getParamEventosId() {
		return paramEventosId;
	}

	public void setParamEventosId(Set<String> paramEventosId) {
		this.paramEventosId = paramEventosId;
	}
	
	
}
