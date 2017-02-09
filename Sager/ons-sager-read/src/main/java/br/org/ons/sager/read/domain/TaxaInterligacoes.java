package br.org.ons.sager.read.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@KeySpace("taxaInterligacoes")
public class TaxaInterligacoes {

	@Id
	private String id;

	private String tipo;

	private String cos;

	private String usiid;

	private String usi;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dtRef;

	private String teif;

	private String ip;

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o campo cos
	 */
	public String getCos() {
		return cos;
	}

	/**
	 * @param cos
	 *            o campo cos a ser definido
	 */
	public void setCos(String cos) {
		this.cos = cos;
	}

	/**
	 * @return o campo usiid
	 */
	public String getUsiid() {
		return usiid;
	}

	/**
	 * @param usiid
	 *            o campo usiid a ser definido
	 */
	public void setUsiid(String usiid) {
		this.usiid = usiid;
	}

	/**
	 * @return o campo usi
	 */
	public String getUsi() {
		return usi;
	}

	/**
	 * @param usi
	 *            o campo usi a ser definido
	 */
	public void setUsi(String usi) {
		this.usi = usi;
	}

	/**
	 * @return o campo dtRef
	 */
	public LocalDate getDtRef() {
		return dtRef;
	}

	/**
	 * @param dtRef
	 *            o campo dtRef a ser definido
	 */
	public void setDtRef(LocalDate dtRef) {
		this.dtRef = dtRef;
	}

	/**
	 * @return o campo teif
	 */
	public String getTeif() {
		return teif;
	}

	/**
	 * @param teif
	 *            o campo teif a ser definido
	 */
	public void setTeif(String teif) {
		this.teif = teif;
	}

	/**
	 * @return o campo ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            o campo ip a ser definido
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
		TaxaInterligacoes other = (TaxaInterligacoes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaxaInterligacoes [id=" + id + ", tipo=" + tipo + ", cos=" + cos + ", usiid=" + usiid + ", usi=" + usi
				+ ", dtRef=" + dtRef + ", teif=" + teif + ", ip=" + ip + "]";
	}
}
