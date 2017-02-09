package br.org.ons.sager.read.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("reprodutibilidade")
public class Reprodutibilidade {
	
	@Id
	private String id;
	
	private String correlationId;
	
	private String instalacao;
	
	private Date dataApuracao;
	
	private String taxaMemoriaCalculo;
	
	private List<UGParam> memoriaCalculo = new ArrayList<UGParam>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstalacao() {
		return instalacao;
	}

	public void setInstalacao(String instalacao) {
		this.instalacao = instalacao;
	}

	public Date getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(Date dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	public String getTaxaMemoriaCalculo() {
		return taxaMemoriaCalculo;
	}

	public void setTaxaMemoriaCalculo(String taxaMemoriaCalculo) {
		this.taxaMemoriaCalculo = taxaMemoriaCalculo;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public List<UGParam> getMemoriaCalculo() {
		return memoriaCalculo;
	}

	public void setMemoriaCalculo(List<UGParam> memoriaCalculo) {
		this.memoriaCalculo = memoriaCalculo;
	}

}
