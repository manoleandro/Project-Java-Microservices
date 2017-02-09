package br.org.ons.sager.parametrizacao.domain;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

/**
 * Representa os Parâmetros do Processo de Retificacao de Eventos
 * 
 */
@QueryEntity
@KeySpace("RetificacoesParam")
public class RetificacoesParam {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private int dia;
	private LocalTime hora;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "RetificacoesParam [id=" + id + ", dia=" + dia + ", hora=" + hora + "]";
	}
}
