package br.org.ons.geracao.cadastro;

import java.io.Serializable;

/**
 * Conjunto de equipamentos destinados � gera��o de energia el�trica.
 */
public class UnidadeGeradora extends Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TipoFonteEnergetica tipoFonteEnergetica;
	private String codigoIdDpp;
	//OBS - jcardoso - potenciaEfetiva retirada pelo Jerome dia 03/01/17
//	private Double potenciaEfetiva;
	private String codigoOns;

	/**
	 * O atributo codCCEE indica se a unidade geradora est� cadastrada para
	 * receber encargo de servi�o de sistema por compensador s�ncrono
	 * 
	 * Informa��o a utilizar na gera��o do relat�rio TipSinc no SAGER e nas
	 * regras de neg�cio no ODM.
	 * 
	 * Este dado n�o consta no SAMUG (entrada manual na planilha Excel de massa
	 * de dados para testes).
	 * 
	 */
	private String codigoCcee;
	private String idVisaoPlanejamentoOpEnergetica;
	private String idDpp;
	
	/**
	 * @return o campo tipoFonteEnergetica
	 */
	public TipoFonteEnergetica getTipoFonteEnergetica() {
		return tipoFonteEnergetica;
	}

	/**
	 * @param tipoFonteEnergetica
	 *            o campo tipoFonteEnergetica a ser definido
	 */
	public void setTipoFonteEnergetica(TipoFonteEnergetica tipoFonteEnergetica) {
		this.tipoFonteEnergetica = tipoFonteEnergetica;
	}

	/**
	 * @return o campo codigoIdDpp
	 */
	public String getCodigoIdDpp() {
		return codigoIdDpp;
	}

	/**
	 * @param codigoIdDpp
	 *            o campo codigoIdDpp a ser definido
	 */
	public void setCodigoIdDpp(String codigoIdDpp) {
		this.codigoIdDpp = codigoIdDpp;
	}

	/**
	 * @return o campo codigoOns
	 */
	public String getCodigoOns() {
		return codigoOns;
	}

	/**
	 * @param codigoOns
	 *            o campo codigoOns a ser definido
	 */
	public void setCodigoOns(String codigoOns) {
		this.codigoOns = codigoOns;
	}

	/**
	 * @return o campo codigoCcee
	 */
	public String getCodigoCcee() {
		return codigoCcee;
	}

	/**
	 * @param codigoCcee
	 *            o campo codigoCcee a ser definido
	 */
	public void setCodigoCcee(String codigoCcee) {
		this.codigoCcee = codigoCcee;
	}

	/**
	 * @return o campo idVisaoPlanejamentoOpEnergetica
	 */
	public String getIdVisaoPlanejamentoOpEnergetica() {
		return idVisaoPlanejamentoOpEnergetica;
	}

	/**
	 * @param idVisaoPlanejamentoOpEnergetica
	 *            o campo idVisaoPlanejamentoOpEnergetica a ser definido
	 */
	public void setIdVisaoPlanejamentoOpEnergetica(String idVisaoPlanejamentoOpEnergetica) {
		this.idVisaoPlanejamentoOpEnergetica = idVisaoPlanejamentoOpEnergetica;
	}

	/**
	 * @return o campo idDpp
	 */
	public String getIdDpp() {
		return idDpp;
	}

	/**
	 * @param idDpp
	 *            o campo idDpp a ser definido
	 */
	public void setIdDpp(String idDpp) {
		this.idDpp = idDpp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnidadeGeradora [tipoFonteEnergetica=" + tipoFonteEnergetica + ", codigoIdDpp=" + codigoIdDpp
				+ ", codigoOns=" + codigoOns + ", codigoCcee=" + codigoCcee
				+ ", idVisaoPlanejamentoOpEnergetica=" + idVisaoPlanejamentoOpEnergetica + ", idDpp=" + idDpp
				+ ", getId()=" + getId() + ", getVersao()=" + getVersao() + ", getNome()=" + getNome()
				+ ", getTipoInstalacao()=" + getTipoInstalacao() + ", getIdInstalacao()=" + getIdInstalacao()
				+ ", getDataEventoEOC()=" + getDataEventoEOC() + ", getDataEntradaOperacaoComercial()="
				+ getDataEntradaOperacaoComercial() + ", getDataDesativacao()=" + getDataDesativacao()
				+ ", getQuantidadeHorasServico()=" + getQuantidadeHorasServico() + ", getDataImplantacao()="
				+ getDataImplantacao() + ", getDataRenovacaoProrrogacaoConcessao()="
				+ getDataRenovacaoProrrogacaoConcessao() + ", getPotenciasCalculo()=" + getPotenciasCalculo()
				+ ", getFranquias()=" + getFranquias() + ", getSuspensoes()=" + getSuspensoes() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
