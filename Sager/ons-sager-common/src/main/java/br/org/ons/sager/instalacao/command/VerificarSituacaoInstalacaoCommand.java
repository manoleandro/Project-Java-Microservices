package br.org.ons.sager.instalacao.command;

import java.util.Date;

import br.org.ons.platform.common.Command;

public class VerificarSituacaoInstalacaoCommand extends Command{

	private Date dataInicio;
	private Date dataFim;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@Override
	public String toString() {
		return "VerificarSituacaoInstalacaoCommand [dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}
	
	
}
