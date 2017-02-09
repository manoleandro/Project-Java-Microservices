package br.org.ons.sager.read.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.annotations.QueryInit;

@QueryEntity
@KeySpace("instalacaoTaxas")
public class InstalacaoTaxas {
	
	@Id
	private String id;
	
	private String instalacao;

	@QueryInit("valores")
	private List<Taxa> taxas;

	public InstalacaoTaxas() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InstalacaoTaxas(String instalacao, List<Taxa> taxas) {
		super();
		this.instalacao = instalacao;
		this.taxas = taxas;
	}

	public String getInstalacao() {
		return instalacao;
	}

	public void setInstalacao(String instalacao) {
		this.instalacao = instalacao;
	}

	public List<Taxa> getTaxas() {
		return taxas;
	}
	
	public void setTaxas(List<Taxa> taxas) {
		this.taxas = taxas;
	}	
	
	public void addTaxas(List<Taxa> taxas){
		if(this.taxas == null){
			this.taxas = new ArrayList<Taxa>();
		}
		this.taxas.addAll(taxas);
	}
	
	public void addTaxa(Taxa taxa){
		if(this.taxas == null){
			this.taxas = new ArrayList<Taxa>();
		}		
		this.taxas.add(taxa);
	}

	@Override
	public String toString() {
		return "UsinaTaxas [instalacao=" + instalacao + ", taxas=" + taxas + "]";
	}

}
