package br.org.ons.sager.read.domain;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("equipamentoUGParam")
public class EquipamentoUGParam extends UGParam {

	Equipamento equipamento;
	
	String codigo;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	@Override
	public String toString() {
		return "EquipamentoUGParam [equipamento=" + equipamento + ", codigo=" + codigo + ", getCodigo()=" + getCodigo()
				+ ", getEquipamento()=" + getEquipamento() + ", getData()=" + getData() + ", getTaxa()=" + getTaxa()
				+ ", getTipoTaxa()=" + getTipoTaxa() + "]";
	}
	
}
