package br.org.ons.geracao.cadastro;

import java.util.Date;

/**
 * Instala��o que une os sistemas el�tricos de duas concession�rias ou empresas
 * de energia el�trica situadas em pa�ses distintos.
 */
public class InterligacaoInternacional extends Instalacao {

	private static final long serialVersionUID = 1L;

	private String id;
	private String versao;
	private String idDpp;
	private String cintDpp;
	private String codigoOns;
	private TipoInterligacaoInternacional tipo;
	private String cosr;

	/**
	 * Encapsula as propriedades e m�todos da Interliga��o Internacional quando
	 * esta � tratada com um tipo de Equipamento.
	 */
	private EquipamentoInterligacaoInternacional equipamento;

	private Agente agenteResponsavel;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id o campo id a ser definido
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
	 * @param versao o campo versao a ser definido
	 */
	public void setVersao(String versao) {
		this.versao = versao;
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

	/**
	 * @return o campo cintDpp
	 */
	public String getCintDpp() {
		return cintDpp;
	}

	/**
	 * @param cintDpp
	 *            o campo cintDpp a ser definido
	 */
	public void setCintDpp(String cintDpp) {
		this.cintDpp = cintDpp;
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
	 * @return o campo equipamento
	 */
	public EquipamentoInterligacaoInternacional getEquipamento() {
		return equipamento;
	}

	/**
	 * @param equipamento
	 *            o campo equipamento a ser definido
	 */
	public void setEquipamento(EquipamentoInterligacaoInternacional equipamento) {
		this.equipamento = equipamento;
	}

	/**
	 * @return o campo agenteResponsavel
	 */
	public Agente getAgenteResponsavel() {
		return agenteResponsavel;
	}

	/**
	 * @param agenteResponsavel
	 *            o campo agenteResponsavel a ser definido
	 */
	public void setAgenteResponsavel(Agente agenteResponsavel) {
		this.agenteResponsavel = agenteResponsavel;
	}

	/**
	 * @return o campo tipo
	 */
	public TipoInterligacaoInternacional getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(TipoInterligacaoInternacional tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o campo cosr
	 */
	public String getCosr() {
		return cosr;
	}

	/**
	 * @param cosr o campo cosr a ser definido
	 */
	public void setCosr(String cosr) {
		this.cosr = cosr;
	}

	/**
	 * @return a data de desativação da interligação internacional
	 */
	public Date dataDesativacao() {
		return equipamento.getDataDesativacao();
	}
		
	/**
	 * @return a data de implantação de this
	 */
	public Date dataImplantacao() {
		return equipamento.getDataImplantacao();
	}
}
