package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("ArqTipSinc")
public class ArqTipSinc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String data;
	private String hora;
	private String tipoUsina = "MP";
	private String nomeUnidadeGeradora;
	private String codDPP;
	private String idUGE;
	private String codCCEE;
	private String flgSincrono;
	private String Comentario;


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
	
	public String getCodCCEE() {
		return codCCEE;
	}

	public void setCodCCEE(String CodPontoMedicao) {
		this.codCCEE = CodPontoMedicao;
	}
	
	public String getFlgSincrono() {
		return flgSincrono;
	}

	public void setFlgSincrono(String flgSincrono) {
		this.flgSincrono = flgSincrono;
	}
	
	public String getComentario() {
		return Comentario;
	}

	public void setComentario(String Comentario) {
		this.Comentario = Comentario;
	}
	
	@Override
	public String toString() {
		return "ArqTipSinc [data=" + data + ", nomeUnidadeGeradora=" + nomeUnidadeGeradora + 
				", idUGE=" + idUGE	+ ", codDPP=" + codDPP	+ ", idUGE=" + idUGE	+ ", tipoUsina=" + tipoUsina + "]";
	}

}
