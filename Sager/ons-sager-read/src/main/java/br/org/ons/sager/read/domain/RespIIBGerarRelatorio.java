package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("RespIIBGerarRelatorio")
public class RespIIBGerarRelatorio implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private String arquivoDat;
	private String dadosDat;
	private String arquivoXML;
	private String dadosXML;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getArquivoDat() {
		return arquivoDat;
	}

	public String getDadosDat() {
		return dadosDat;
	}

	public void setArquivoDat(String arquivoDat) {
		this.arquivoDat = arquivoDat;
	}

	public void setDadosDat(String dadosDat) {
		this.dadosDat = dadosDat;
	}

	public String getArquivoXML() {
		return arquivoXML;
	}

	public String getDadosXML() {
		return dadosXML;
	}

	public void setArquivoXML(String arquivoXML) {
		this.arquivoXML = arquivoXML;
	}

	public void setDadosXML(String dadosXML) {
		this.dadosXML = dadosXML;
	}

	@Override
	public String toString() {
		return "ArqTipSinc [arquivoDat=" + arquivoDat + ", dadosDat=" + dadosDat + 
				", arquivoXML=" + arquivoXML + ", dadosXML=" + dadosXML + "]";
	}

}
