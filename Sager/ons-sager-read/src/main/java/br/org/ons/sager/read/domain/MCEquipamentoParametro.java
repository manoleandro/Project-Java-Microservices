package br.org.ons.sager.read.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("mcequipamentoParametro")
public class MCEquipamentoParametro {
	
	@Id
	private String id;
	
	private String correlationId;
	
	private String instalacao;
	
	private Date dataApuracao;
	
	private String taxaMemoriaCalculo;
	
	private int versaoTaxa;
	
	private int versaoCenario;
	
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

	public List<UGParam> getMemoriaCalculo() {
		Collections.sort(memoriaCalculo);
		return memoriaCalculo;
	}

	public void setMemoriaCalculo(List<UGParam> memoriaCalculo) {
		this.memoriaCalculo = memoriaCalculo;
	}

	public int getVersaoTaxa() {
		return versaoTaxa;
	}

	public void setVersaoTaxa(int versaoTaxa) {
		this.versaoTaxa = versaoTaxa;
	}

	public int getVersaoCenario() {
		return versaoCenario;
	}

	public void setVersaoCenario(int versaoCenario) {
		this.versaoCenario = versaoCenario;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public void addMemoriaCalculo(UGParam memoriaCalculo){
		//Evita adicionar repetido
		if(!this.memoriaCalculo.contains(memoriaCalculo)){
			this.memoriaCalculo.add(memoriaCalculo);
		}
	}


}
