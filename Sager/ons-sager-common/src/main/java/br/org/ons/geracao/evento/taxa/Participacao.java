package br.org.ons.geracao.evento.taxa;

import java.math.BigDecimal;

import br.org.ons.geracao.modelagem.Variavel;

/**
 * Participação (variável = percentual) de um participante (ex. equipamento)
 * em uma variável (ex. taxa).
 */
public class Participacao extends Variavel {

	private static final long serialVersionUID = 1L;
	
	private String idParticipante;
	
	private BigDecimal  potencia = BigDecimal.ZERO;
	
	private String codigoONS = "";

	/**
	 * @return o campo idParticipante
	 */
	public String getIdParticipante() {
		return idParticipante;
	}

	/**
	 * @param tipo
	 *            o campo idParticipante a ser definido
	 */
	void setIdParticipante(String idParticipante) {
		this.idParticipante = idParticipante;
	}

	public BigDecimal getPotencia() {
		return potencia;
	}

	public void setPotencia(BigDecimal potencia) {
		this.potencia = potencia;
	}
	
	public String getCodigoONS() {
		return codigoONS;
	}

	public void setCodigoONS(String codigoONS) {
		this.codigoONS = codigoONS;
	}	
}
