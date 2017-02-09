package br.org.ons.geracao.evento;

import java.io.Serializable;
import java.util.Date;

/**
 * Indicador de disponibilidade da instalação.
 *
 */
public class Disponibilidade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String versao;
	private TipoDisponibilidade tipoDisponibilidade;
	private Double valor;
	private Date dataInicio;
	private String idEquipamento;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo versao
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            o campo versao a ser definido
	 */
	public void setVersao(String versao) {
		this.versao = versao;
	}

	/**
	 * @return o campo tipoDisponibilidade
	 */
	public TipoDisponibilidade getTipoDisponibilidade() {
		return tipoDisponibilidade;
	}

	/**
	 * @param tipoDisponibilidade
	 *            o campo tipoDisponibilidade a ser definido
	 */
	public void setTipoDisponibilidade(TipoDisponibilidade tipoDisponibilidade) {
		this.tipoDisponibilidade = tipoDisponibilidade;
	}

	/**
	 * @return o campo valor
	 */
	public Double getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            o campo valor a ser definido
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}

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

	public String getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Disponibilidade [id=" + id + ", versao=" + versao + ", tipoDisponibilidade=" + tipoDisponibilidade
				+ ", valor=" + valor + ", dataInicio=" + dataInicio + ", idEquipamento=" + idEquipamento + "]";
	}
	
}
