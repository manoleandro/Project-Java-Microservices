package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("disp")
public class Disp implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 
	 @Id
	 private String id;
	 
	 private String tipoInstalacao;
	 
	 private String tipo;
	 
	 private String agente;
	 
	 private String centroOperacao;
	 
	 private String instalacao;			
	 
	 private String equipamento;
	 
	 private Double num_do;
	 
	 private Double num_dc;
	 
	 private Double num_de;
	 
	 private Date data;	 
	 
	 private Date hora;
	 
	 private boolean isDispConsolidada;
	 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoInstalacao() {
		return tipoInstalacao;
	}

	public void setTipoInstalacao(String tipoInstalacao) {
		this.tipoInstalacao = tipoInstalacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getCentroOperacao() {
		return centroOperacao;
	}

	public void setCentroOperacao(String centroOperacao) {
		this.centroOperacao = centroOperacao;
	}

	public String getInstalacao() {
		return instalacao;
	}

	public void setInstalacao(String instalacao) {
		this.instalacao = instalacao;
	}

	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	public Double getNum_do() {
		return num_do;
	}

	public void setNum_do(Double num_do) {
		this.num_do = num_do;
	}

	public Double getNum_dc() {
		return num_dc;
	}

	public void setNum_dc(Double num_dc) {
		this.num_dc = num_dc;
	}

	public Double getNum_de() {
		return num_de;
	}

	public void setNum_de(Double num_de) {
		this.num_de = num_de;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}	

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public boolean isDispConsolidada() {
		return isDispConsolidada;
	}

	public void setDispConsolidada(boolean isDispConsolidada) {
		this.isDispConsolidada = isDispConsolidada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Disp other = (Disp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Disp [id=" + id + ", tipoInstalacao=" + tipoInstalacao + ", agente=" + agente + ", centroOperacao=" + centroOperacao
				+ ", instalacao=" + instalacao + ", equipamento="
				+ equipamento + ", num_do=" + num_do + ", num_dc=" + num_dc
				+ ", num_de=" + num_de + ", data=" + data + ", hora=" + hora 
				+ "]";
	}
	
}
