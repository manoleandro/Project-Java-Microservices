package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("ArqIndAcum")
public class ArqIndAcum implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mesAno;
	private String tipoUsina = "MP";
	private String nomeUnidadeGeradora;
	private String codDPP;
	private String teifaMes;
	private String teifaAcumulado;
	private String teipMes;
	private String teipAcumulado;
	private String dadoIndefinido = "";


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
	    this.mesAno = mesAno;
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

	public String getTeifaMes() {
		return teifaMes;
	}

	public void setTeifaMes(String teifaMes) {
		this.teifaMes = teifaMes;
	}

	public String getTeifaAcumulado() {
		return teifaAcumulado;
	}

	public void setTeifaAcumulado(String teifaAcumulado) {
		this.teifaAcumulado = teifaAcumulado;
	}

	public String getTeipAcumulado() {
		return teipAcumulado;
	}

	public void setTeipAcumulado(String teipAcumulado) {
		this.teipAcumulado = teipAcumulado;
	}

	public String getTeipMes() {
		return teipMes;
	}

	public void setTeipMes(String teipMes) {
		this.teipMes = teipMes;
	}

	public String getDadoIndefinido() {
		return dadoIndefinido;
	}
	
	@Override
	public String toString() {
		return "ArqTipSinc [nomeUnidadeGeradora=" + nomeUnidadeGeradora + 
				", mesAno=" + mesAno + ", tipoUsina=" + tipoUsina + ", codDPP=" + codDPP +
				", teifaMes=" + teifaMes + ", teifaAcumulado=" + teifaAcumulado + ", teipAcumulado=" + teipAcumulado +
				", teipMes=" + teipMes + "]";
	}

}
