package br.org.ons.sager.read.web.rest.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.read.domain.Comentario;


public class DispInstalacoesDTO {

	private String id;
	private String nome;    
	private List<EquipamentoDTO> equipamentos;
	private Set<Comentario> comentarios;
	private Set<Comentario> comentariosAviso; 	
	private Set<Comentario> comentariosErro; 	
	private boolean hasDisponibilidades;
	private boolean hasComentarios;
	private boolean hasExcel;
	private long minorVersion;
	private byte[] file;
	
	@JsonCreator
    public DispInstalacoesDTO() {
		super();
    }    

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}
   

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public List<EquipamentoDTO> getEquipamentos() {
		return equipamentos;
	}



	public void setEquipamentos(List<EquipamentoDTO> equipamentos) {
		this.equipamentos = equipamentos;
	}


	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Set<Comentario> getComentariosAviso() {
		return comentariosAviso;
	}

	public void setComentariosAviso(Set<Comentario> comentariosAviso) {
		this.comentariosAviso = comentariosAviso;
	}

	public Set<Comentario> getComentariosErro() {
		return comentariosErro;
	}

	public void setComentariosErro(Set<Comentario> comentariosErro) {
		this.comentariosErro = comentariosErro;
	}

	public boolean isHasDisponibilidades() {
		return hasDisponibilidades;
	}

	public void setHasDisponibilidades(boolean hasDisponibilidades) {
		this.hasDisponibilidades = hasDisponibilidades;
	}

	public boolean isHasComentarios() {
		return hasComentarios;
	}

	public void setHasComentarios(boolean hasComentarios) {
		this.hasComentarios = hasComentarios;
	}

	public long getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(long minorVersion) {
		this.minorVersion = minorVersion;
	}

	public boolean isHasExcel() {
		return hasExcel;
	}

	public void setHasExcel(boolean hasExcel) {
		this.hasExcel = hasExcel;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "DispInstalacoesDTO [id=" + id + ", nome=" + nome + ", equipamentos=" + equipamentos + ", comentarios="
				+ comentarios + ", comentariosAviso=" + comentariosAviso + ", comentariosErro=" + comentariosErro
				+ ", hasDisponibilidades=" + hasDisponibilidades + ", hasComentarios=" + hasComentarios + "]";
	}
}
