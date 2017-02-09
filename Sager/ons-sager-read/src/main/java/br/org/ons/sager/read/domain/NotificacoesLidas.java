package br.org.ons.sager.read.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("NotificacoesLidas")
public class NotificacoesLidas {


	@Id
	private String idLida;
	
	private String id;
	
	private String protocolo;
	
	private String status;
	
	private String tipo;
	
	private String comando;
	
	private String usuario;
	
	private List<String> roles;
	
	private Date data;
	
	private String lida;
	
	private String protocoloID;
	
	private String idBusca;
	
	
	
	
	public String getIdBusca() {
		return idBusca;
	}

	public void setIdBusca(String idBusca) {
		this.idBusca = idBusca;
	}

	public String getProtocoloID() {
		return protocoloID;
	}

	public void setProtocoloID(String protocoloID) {
		this.protocoloID = protocoloID;
	}

	public String getIdLida() {
		return idLida;
	}

	public void setIdLida(String idLida) {
		this.idLida = idLida;
	}

	public String getLida() {
		return lida;
	}

	public void setLida(String lida) {
		this.lida = lida;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	
}
