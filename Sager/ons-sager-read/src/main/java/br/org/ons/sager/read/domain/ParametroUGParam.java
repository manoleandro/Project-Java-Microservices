package br.org.ons.sager.read.domain;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("parametroUGParam")
public class ParametroUGParam extends UGParam {

	private String equipamentoNome;
	
	private Parametro param;

	public String getEquipamentoNome() {
		return equipamentoNome;
	}

	public void setEquipamentoNome(String equipamentoNome) {
		this.equipamentoNome = equipamentoNome;
	}

	public Parametro getParam() {
		return param;
	}

	public void setParam(Parametro param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "ParametroUGParam [equipamentoNome=" + equipamentoNome + ", param=" + param + ", getEquipamentoNome()="
				+ getEquipamentoNome() + ", getParam()=" + getParam() + ", getData()=" + getData() + ", getTaxa()="
				+ getTaxa() + ", getTipoTaxa()=" + getTipoTaxa() + "]";
	}
	
}
