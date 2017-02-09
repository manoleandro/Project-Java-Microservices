package br.org.ons.sager.agendamento.domain;

import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

/**
 * Representa o agendamento da construção de um cenário paralelo de cálculo para
 * uma instalação.
 */
@QueryEntity
@KeySpace("AgendamentoCenario")
public class AgendamentoCenario extends Agendamento {

	private String idCenario;
	private String nomeCenario;
	private String justificativa;

	/**
	 * @return o campo idCenario
	 */
	public String getIdCenario() {
		return idCenario;
	}

	/**
	 * @param idCenario
	 *            o campo idCenario a ser definido
	 */
	public void setIdCenario(String idCenario) {
		this.idCenario = idCenario;
	}

	/**
	 * @return o campo nomeCenario
	 */
	public String getNomeCenario() {
		return nomeCenario;
	}

	/**
	 * @param nomeCenario
	 *            o campo nomeCenario a ser definido
	 */
	public void setNomeCenario(String nomeCenario) {
		this.nomeCenario = nomeCenario;
	}

	/**
	 * @return o campo justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa
	 *            o campo justificativa a ser definido
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

}
