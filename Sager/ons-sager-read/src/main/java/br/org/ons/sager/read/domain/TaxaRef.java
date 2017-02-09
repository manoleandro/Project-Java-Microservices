package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.sager.read.service.TaxaRefService;

/**
 * A TaxaRef.
 */
@QueryEntity
@KeySpace("taxa_ref")
public class TaxaRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String cos;

    private String usi;

    private String usiid;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dtRef;

    private BigDecimal teif;

    private BigDecimal ip;
    
    private String tipoInstalacao;


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public String getUsi() {
        return usi;
    }

    public void setUsi(String usi) {
        this.usi = usi;
    }

    public String getUsiid() {
        return usiid;
    }

    public void setUsiid(String usiid) {
        this.usiid = usiid;
    }

    public LocalDate getDtRef() {
        return dtRef;
    }

    public void setDtRef(LocalDate dtRef) {
        this.dtRef = dtRef;
    }

    public BigDecimal getTeif() {
        return teif;
    }

    public void setTeif(BigDecimal teif) {
        this.teif = teif;
    }

    public BigDecimal getIp() {
        return ip;
    }

    public void setIp(BigDecimal ip) {
        this.ip = ip;
    }

    public String getTipoInstalacao() {
		return tipoInstalacao;
	}

	public void setTipoInstalacao(String tipoInstalacao) {
		this.tipoInstalacao = tipoInstalacao;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxaRef taxaRef = (TaxaRef) o;
        if(taxaRef.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, taxaRef.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaxaRef{" +
            "id=" + id +
            ", cos='" + cos + "'" +
            ", usi='" + usi + "'" +
            ", usiid='" + usiid + "'" +
            ", dtRef='" + dtRef + "'" +
            ", teif='" + teif + "'" +
            ", ip='" + ip + "'" +
            '}';
    }
}
