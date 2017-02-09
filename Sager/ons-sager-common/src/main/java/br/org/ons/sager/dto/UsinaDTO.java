package br.org.ons.sager.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UsinaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private long minorVersion;
	private String nome;
	private List<EquipamentoDTO> Equipamentos;
	
	
	@JsonCreator
    public UsinaDTO() {
		
    }
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<EquipamentoDTO> getEquipamentos() {
		return Equipamentos;
	}
	public void setEquipamentos(List<EquipamentoDTO> equipamentos) {
		Equipamentos = equipamentos;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(long minorVersion) {
		this.minorVersion = minorVersion;
	}

	@Override
    public String toString() {
        return "InstalacaoDTO{" +
            "nome='" + nome + '\'' +
            ", Equipamentos='" + Equipamentos + '\'' +
            '}';
    }

}
