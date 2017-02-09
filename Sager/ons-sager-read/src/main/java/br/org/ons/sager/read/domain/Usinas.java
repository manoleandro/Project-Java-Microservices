package br.org.ons.sager.read.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("usinas")
public class Usinas {
	 private static final long serialVersionUID = 1L;
	
    @Id
    private String id;
    
    private String codaneel;
    
    private String codvisaoapuracaogeracao;
    
    private String cosr;
	
    private Date dtprorrogacaorenovacaoconcessao;
	
    private String nome;
    
    private String tipo_id;
    
    private String tipo_nome;
    
    private Date dtoutorgaimplantacao;
    
    private String centroopid;
    
    private String agente;
        
    private List<Equipamento> equipamentos = new ArrayList<>();
    
    private String minorVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodaneel() {
		return codaneel;
	}

	public void setCodaneel(String codaneel) {
		this.codaneel = codaneel;
	}

	public String getCodvisaoapuracaogeracao() {
		return codvisaoapuracaogeracao;
	}

	public void setCodvisaoapuracaogeracao(String codvisaoapuracaogeracao) {
		this.codvisaoapuracaogeracao = codvisaoapuracaogeracao;
	}

	public String getCosr() {
		return cosr;
	}

	public void setCosr(String cosr) {
		this.cosr = cosr;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo_id() {
		return tipo_id;
	}

	public void setTipo_id(String tipo_id) {
		this.tipo_id = tipo_id;
	}

	public String getTipo_nome() {
		return tipo_nome;
	}

	public void setTipo_nome(String tipo_nome) {
		this.tipo_nome = tipo_nome;
	}

	public String getCentroopid() {
		return centroopid;
	}

	public void setCentroopid(String centroopid) {
		this.centroopid = centroopid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
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

	public Date getDtprorrogacaorenovacaoconcessao() {
		return dtprorrogacaorenovacaoconcessao;
	}

	public void setDtprorrogacaorenovacaoconcessao(Date dtprorrogacaorenovacaoconcessao) {
		this.dtprorrogacaorenovacaoconcessao = dtprorrogacaorenovacaoconcessao;
	}

	public Date getDtoutorgaimplantacao() {
		return dtoutorgaimplantacao;
	}

	public void setDtoutorgaimplantacao(Date dtoutorgaimplantacao) {
		this.dtoutorgaimplantacao = dtoutorgaimplantacao;
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
		Usinas other = (Usinas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usinas [id=" + id + ", codaneel=" + codaneel + ", codvisaoapuracaogeracao=" + codvisaoapuracaogeracao
				+ ", cosr=" + cosr + ", dtprorrogacaorenovacaoconcessao=" + dtprorrogacaorenovacaoconcessao + ", nome="
				+ nome + ", tipo_id=" + tipo_id + ", tipo_nome=" + tipo_nome + ", dtoutorgaimplantacao="
				+ dtoutorgaimplantacao + ", centroopid=" + centroopid  + "]";
	}
    
    
    
    
}
