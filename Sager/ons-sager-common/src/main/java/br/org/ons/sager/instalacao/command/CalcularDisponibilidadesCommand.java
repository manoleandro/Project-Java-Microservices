package br.org.ons.sager.instalacao.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.platform.common.Command;

/**
 * Comando para calcular as disponibilidades de uma instalação
 */
public class CalcularDisponibilidadesCommand extends Command {

	private Date dataInicio;
	private Date dataFim;
	private List<TipoDisponibilidade> tiposDisponibilidade = new ArrayList<>();
	private String idUnidadeGeradora;

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

	/**
	 * @return o campo tiposDisponibilidade
	 */
	public List<TipoDisponibilidade> getTiposDisponibilidade() {
		return tiposDisponibilidade;
	}

	/**
	 * @param tiposDisponibilidade
	 *            o campo tiposDisponibilidade a ser definido
	 */
	public void setTiposDisponibilidade(List<TipoDisponibilidade> tiposDisponibilidade) {
		this.tiposDisponibilidade = tiposDisponibilidade;
	}

	/**
	 * @return o campo idUnidadeGeradora
	 */
	public String getIdUnidadeGeradora() {
		return idUnidadeGeradora;
	}

	/**
	 * @param idUnidadeGeradora o campo idUnidadeGeradora a ser definido
	 */
	public void setIdUnidadeGeradora(String idUnidadeGeradora) {
		this.idUnidadeGeradora = idUnidadeGeradora;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalcularDisponibilidadesCommand [dataInicio=" + dataInicio + ", dataFim=" + dataFim
				+ ", tiposDisponibilidade=" + tiposDisponibilidade + ", idUnidadeGeradora=" + idUnidadeGeradora + "]";
	}

}
