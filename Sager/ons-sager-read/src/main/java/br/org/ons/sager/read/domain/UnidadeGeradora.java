package br.org.ons.sager.read.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("unidadeGeradora")
public class UnidadeGeradora implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String versao;

	private String codid_dpp;

	private String iddpp;

	private String codccee;

	private String codons;

	private String idusina;

	private String idvisaoplanejamentoopenergetica;

	private String tipofonteenergetica;

	private String dtdesativacao;

	private String dteventoeoc;

	private String dtimplantacao;

	private String dtprorrogacaorenovacaoconcessao;

	private String nome;

	private String idagente;

	private String nomeagente;

	private String siglaagente;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getCodid_dpp() {
		return codid_dpp;
	}

	public void setCodid_dpp(String codid_dpp) {
		this.codid_dpp = codid_dpp;
	}

	public String getIddpp() {
		return iddpp;
	}

	public void setIddpp(String iddpp) {
		this.iddpp = iddpp;
	}

	public String getCodccee() {
		return codccee;
	}

	public void setCodccee(String codccee) {
		this.codccee = codccee;
	}

	public String getCodons() {
		return codons;
	}

	public void setCodons(String codons) {
		this.codons = codons;
	}

	public String getIdusina() {
		return idusina;
	}

	public void setIdusina(String idusina) {
		this.idusina = idusina;
	}

	public String getIdvisaoplanejamentoopenergetica() {
		return idvisaoplanejamentoopenergetica;
	}

	public void setIdvisaoplanejamentoopenergetica(String idvisaoplanejamentoopenergetica) {
		this.idvisaoplanejamentoopenergetica = idvisaoplanejamentoopenergetica;
	}

	public String getTipofonteenergetica() {
		return tipofonteenergetica;
	}

	public void setTipofonteenergetica(String tipofonteenergetica) {
		this.tipofonteenergetica = tipofonteenergetica;
	}

	public String getDtdesativacao() {
		return dtdesativacao;
	}

	public void setDtdesativacao(String dtdesativacao) {
		this.dtdesativacao = dtdesativacao;
	}

	public String getDteventoeoc() {
		return dteventoeoc;
	}

	public void setDteventoeoc(String dteventoeoc) {
		this.dteventoeoc = dteventoeoc;
	}

	public String getDtimplantacao() {
		return dtimplantacao;
	}

	public void setDtimplantacao(String dtimplantacao) {
		this.dtimplantacao = dtimplantacao;
	}

	public String getDtprorrogacaorenovacaoconcessao() {
		return dtprorrogacaorenovacaoconcessao;
	}

	public void setDtprorrogacaorenovacaoconcessao(String dtprorrogacaorenovacaoconcessao) {
		this.dtprorrogacaorenovacaoconcessao = dtprorrogacaorenovacaoconcessao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdagente() {
		return idagente;
	}

	public void setIdagente(String idagente) {
		this.idagente = idagente;
	}

	public String getNomeagente() {
		return nomeagente;
	}

	public void setNomeagente(String nomeagente) {
		this.nomeagente = nomeagente;
	}

	public String getSiglaagente() {
		return siglaagente;
	}

	public void setSiglaagente(String siglaagente) {
		this.siglaagente = siglaagente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadeGeradora other = (UnidadeGeradora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usinas [id=" + id + ", versao=" + versao + ", codid_dpp=" + codid_dpp + ", iddpp=" + iddpp
				+ ", codccee=" + codccee + ", codons=" + codons + ", idusina=" + idusina
				+ ", idvisaoplanejamentoopenergetica=" + idvisaoplanejamentoopenergetica + ", tipofonteenergetica="
				+ tipofonteenergetica + ", dtdesativacao=" + dtdesativacao + ", dteventoeoc=" + dteventoeoc
				+ ", dtimplantacao=" + dtimplantacao + ", dtprorrogacaorenovacaoconcessao="
				+ dtprorrogacaorenovacaoconcessao + ", nome=" + nome + ", idagente=" + idagente + ", nomeagente="
				+ nomeagente + ", siglaagente=" + siglaagente + "]";
	}

}
