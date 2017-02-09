package br.org.ons.geracao.cadastro;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import br.org.ons.geracao.modelagem.Periodo;

/**
 * Valor da potência para cálculo, vigente durante um período de tempo
 */
public class PotenciaCalculo  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Double valor;
	private Periodo vigencia;

	private String idEquipamento;
	
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
	 * @return o campo vigencia
	 */
	public Periodo getVigencia() {
		return vigencia;
	}

	/**
	 * @param vigencia
	 *            o campo vigencia a ser definido
	 */
	public void setVigencia(Periodo vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * @return o campo idEquipamento
	 */
	@JsonIgnore
	public String getIdEquipamento() {
		return idEquipamento;
	}

	/**
	 * @param idEquipamento o campo idEquipamento a ser definido
	 */
	@JsonSetter
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
}
