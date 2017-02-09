package br.org.ons.sager.read.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("interligacoes")
public class Interligacoes {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String _class;

	private String versao;

	private String iddpp;

	private String cintdpp;

	private Date dtimplantacao;

	private String nome;

	private String idagente;

	private String nomeagente;

	private String siglaagente;

	private List<Equipamento> equipamentos;
	
	private String minorVersion;
	
	private String cosr;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getIddpp() {
		return iddpp;
	}

	public void setIddpp(String iddpp) {
		this.iddpp = iddpp;
	}

	public String getCintdpp() {
		return cintdpp;
	}

	public void setCintdpp(String cintdpp) {
		this.cintdpp = cintdpp;
	}

	public Date getDtimplantacao() {
		return dtimplantacao;
	}

	public void setDtimplantacao(Date dtimplantacao) {
		this.dtimplantacao = dtimplantacao;
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

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public String getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}

	public String getCosr() {
		return cosr;
	}

	public void setCosr(String cosr) {
		this.cosr = cosr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		Interligacoes other = (Interligacoes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Interligacoes [id=" + id + ", versao=" + versao + ", iddpp=" + iddpp + ", cintdpp=" + cintdpp
				+ ", nome=" + nome + ", idagente=" + idagente + ", nomeagente=" + nomeagente + ", siglaagente=" + siglaagente
				+ ", cosr=" + cosr
			+ "]";
	}

}
