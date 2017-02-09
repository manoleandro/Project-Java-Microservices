package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

//import br.org.ons.sager.read.repository.EstadoOperativoRepository;

@QueryEntity
@KeySpace("ArqDispVer")
public class ArqDispVer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String data;
	private String hora;
	private String tipoUsina = "MT";
	private String nomeUnidadeGeradora;
	private String codDPP;
	private String idUGE;
	private String valorDisponibilidade;
	private String descEstadoOperativo;
	private String codEstadoOperativo;
	// private String codCondicaoOperativa;
	private String descCondicaoOperativa;
	private String dadoIndefinido = "";

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getData() {
		return data;
	}

	public String getHora() {
		return hora;
	}

	public void setDataHora(Date dataVerificada) {
	    SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
    	
    	this.data = sdfData.format(dataVerificada);
	    this.hora = sdfHora.format(dataVerificada);
	}
	
	public String getTipoUsina() {
		return tipoUsina;
	}

	public String getnomeUnidadeGeradora() {
		return nomeUnidadeGeradora;
	}

	public void setNomeUnidadeGeradora(String nomeUnidadeGeradora) {
		this.nomeUnidadeGeradora = nomeUnidadeGeradora;
	}

	public String getCodDPP() {
		return codDPP;
	}

	public void setCodDPP(String codid_dpp) {
		this.codDPP = codid_dpp;
	}

	public String getIdUGE() {
		return idUGE;
	}

	public void setIdUGE(String idEquipamento) {
		this.idUGE = idEquipamento;
	}	
	
	public String getValorDisponibilidade() {
		return valorDisponibilidade;
	}

	public void setValorDisponibilidade(String valorPotenciaDisponivel) {
		this.valorDisponibilidade = valorPotenciaDisponivel;
	}
	
	public String getDescEstadoOperativo() {
		return descEstadoOperativo;
	}

	public void setDescEstadoOperativo(String descEstadoOperativo) {
		this.descEstadoOperativo = descEstadoOperativo;
	}
	
	public String getCodEstadoOperativo() {
		return codEstadoOperativo;
	}

	public void setCodEstadoOperativo(String estadoOperativo) {
		this.codEstadoOperativo = estadoOperativo;
	}

	public String getDescCondicaoOperativa() {
		return descCondicaoOperativa;
	}

	public void setDescCondicaoOperativa(String descCondicaoOperativa) {
		// this.codCondicaoOperativa = condicaoOperativa;
		this.descCondicaoOperativa = descCondicaoOperativa;
	}

	public String getDadoIndefinido() {
		return dadoIndefinido;
	}
	
	@Override
	public String toString() {
		return "ArqDispVer [data=" + data + ", nomeUnidadeGeradora=" + nomeUnidadeGeradora + 
				", idUGE=" + idUGE	+ ", valorDisponibilidade=" + valorDisponibilidade + 
				", codEstadoOperativo=" + codEstadoOperativo	+ ", descEstadoOperativo=" + descEstadoOperativo + 
				", descCondicaoOperativa=" + descCondicaoOperativa + 
				", codDPP=" + codDPP	+ ", valorDisponibilidade=" + valorDisponibilidade + 
				", idUGE=" + idUGE	+ ", tipoUsina=" + tipoUsina + "]";
	}

}
