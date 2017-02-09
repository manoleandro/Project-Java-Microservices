package br.org.ons.geracao.evento.taxa;

import java.io.Serializable;
import java.util.Date;
import br.org.ons.geracao.cadastro.Instalacao;

/**
 * Taxa de indisponibilidade de uma determinada instala��o em um dado per�odo
 * (m�s).
 */
public class Cenario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nome;
	private Instalacao instalacao;
	private Date dataInicioVigencia, dataFimVigencia;

	/**
	 * @return o campo nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            o campo nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return o campo Instalacao
	 */
	public Instalacao getInstalacao() {
		return instalacao;
	}

	/**
	 * @param instalacao
	 *            o campo instalacao a ser definido
	 */
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
	}
	
	/**
	 * @return o campo dataInicioVigencia
	 */
	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}
	
	/**
	 * @param dataInicioVigencia
	 *            o campo dataInicioVigencia a ser definido
	 */
	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}
		
	/**
	 * @return o campo dataFimVigencia
	 */
	public Date getDataFimVigencia() {
		return dataFimVigencia;
	}
	
	/**
	 * @param dataFimVigencia
	 *            o campo dataFimVigencia a ser definido
	 */
	public void setDataFimVigencia(Date dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}
}