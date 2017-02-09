package br.org.ons.geracao.evento;

import java.util.Date;

public class ComentarioSituacao extends Comentario {

	private static final long serialVersionUID = 1L;

	public enum StatusObjeto {
		DESATIVADO, SUSPENSO, FORA_OPERACAO_COMERCIAL
	}

	public enum TipoObjeto {
		USINA, INTERLIGACAO_INTERNACIONAL, UNIDADE_GERADORA
	}

	private Date dataInicio;
	private Date dataFim;
	private StatusObjeto statusObjeto;
	private TipoObjeto tipoObjeto;
	private String nomeObjeto;

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
	 * @return o campo statusObjeto
	 */
	public StatusObjeto getStatusObjeto() {
		return statusObjeto;
	}

	/**
	 * @param statusObjeto
	 *            o campo statusObjeto a ser definido
	 */
	public void setStatusObjeto(StatusObjeto statusObjeto) {
		this.statusObjeto = statusObjeto;
	}

	/**
	 * @return o campo tipoObjeto
	 */
	public TipoObjeto getTipoObjeto() {
		return tipoObjeto;
	}

	/**
	 * @param tipoObjeto
	 *            o campo tipoObjeto a ser definido
	 */
	public void setTipoObjeto(TipoObjeto tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	/**
	 * @return o campo nomeObjeto
	 */
	public String getNomeObjeto() {
		return nomeObjeto;
	}

	/**
	 * @param nomeObjeto
	 *            o campo nomeObjeto a ser definido
	 */
	public void setNomeObjeto(String nomeObjeto) {
		this.nomeObjeto = nomeObjeto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ComentarioSituacao [dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", statusObjeto="
				+ statusObjeto + ", tipoObjeto=" + tipoObjeto + ", nomeObjeto=" + nomeObjeto + ", getTipo()="
				+ getTipo() + ", getDescricao()=" + getDescricao() + ", getDataInsercao()=" + getDataInsercao()
				+ ", getOrigem()=" + getOrigem() + "]";
	}
}
