package br.org.ons.sager.read.web.rest.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;


public class DisponibilidadesDTO {

	private List<DispDTO> disponibilidades;
	private Map<String, String> colunas;
	private boolean hasExcel;

	@JsonCreator
    public DisponibilidadesDTO() {
    }    

	public List<DispDTO> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<DispDTO> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	public Map<String, String> getColunas() {
		return colunas;
	}

	public void setColunas(Map<String, String> colunas) {
		this.colunas = colunas;
	}

	public boolean isHasExcel() {
		return hasExcel;
	}

	public void setHasExcel(boolean hasExcel) {
		this.hasExcel = hasExcel;
	}
}
