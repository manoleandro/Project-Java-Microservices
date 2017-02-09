package br.org.ons.sager.instalacao.command;

import java.util.Date;

import br.org.ons.platform.common.Command;

/**
 * Comando para calcular taxas de um período
 */
public class CalcularTaxasCommand extends Command {

	private Date dataInicio;
	private Date dataFim;

	/**
	 * @return o campo dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            o campo dataInicio a ser definido
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return o campo dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            o campo dataFim a ser definido
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularTaxasCommand [dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}
}
