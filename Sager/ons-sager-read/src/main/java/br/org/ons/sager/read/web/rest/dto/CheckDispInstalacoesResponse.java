package br.org.ons.sager.read.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CheckDispInstalacoesResponse {
		 
	private List<DispInstalacoesDTO> instalacoes;
	private boolean hasExcelGeral;
	 
	@JsonCreator
    public CheckDispInstalacoesResponse() {
    }

	public List<DispInstalacoesDTO> getInstalacoes() {
		return instalacoes;
	}

	public void setInstalacoes(List<DispInstalacoesDTO> instalacoes) {
		this.instalacoes = instalacoes;
	}

	public boolean isHasExcelGeral() {
		return hasExcelGeral;
	}

	public void setHasExcelGeral(boolean hasExcelGeral) {
		this.hasExcelGeral = hasExcelGeral;
	}
}
