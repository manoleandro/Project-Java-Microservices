package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoFonteEnergetica;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.modelagem.Periodo;

@QueryEntity
@KeySpace("equipamento")
public class Equipamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String versao;
	
	private String nome;

	private TipoInstalacao tipoInstalacao;
	
	private String idInstalacao;
	
	private BigDecimal valorPotencia; //não achei no JSON de cadastro de UG

	private BigDecimal participacao; //não achei no JSON de cadastro de UG
	
	private Set<String> equipamentosEventosId; //não achei no JSON de cadastro de UG
	
	private Date dataEventoEOC;
	
	private Date dataDesativacao;

	private Integer quantidadeHorasServico;

	private Date dataImplantacao;

	private Date dataRenovacaoProrrogacaoConcessao;

	private List<PotenciaCalculo> potenciasCalculo; //não achei no JSON de cadastro de UG

	private List<Franquia> franquias; //não achei no JSON de cadastro de UG

	private List<Periodo> suspensoes; //não achei no JSON de cadastro de UG
	
	private String codid_dpp;

	private String iddpp;

	private String codigoCcee;
	
	private String codigoOns;
	
	private String idVisaoPlanejamentoOpEnergetica;
	
	private TipoFonteEnergetica tipoFonteEnergetica;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoInstalacao getTipoInstalacao() {
		return tipoInstalacao;
	}

	public void setTipoInstalacao(TipoInstalacao tipoInstalacao) {
		this.tipoInstalacao = tipoInstalacao;
	}

	public String getIdInstalacao() {
		return idInstalacao;
	}

	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}

	public Date getDataEventoEOC() {
		return dataEventoEOC;
	}

	public void setDataEventoEOC(Date dataEventoEOC) {
		this.dataEventoEOC = dataEventoEOC;
	}

	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	public Integer getQuantidadeHorasServico() {
		return quantidadeHorasServico;
	}

	public void setQuantidadeHorasServico(Integer quantidadeHorasServico) {
		this.quantidadeHorasServico = quantidadeHorasServico;
	}

	public Date getDataImplantacao() {
		return dataImplantacao;
	}

	public void setDataImplantacao(Date dataImplantacao) {
		this.dataImplantacao = dataImplantacao;
	}

	public Date getDataRenovacaoProrrogacaoConcessao() {
		return dataRenovacaoProrrogacaoConcessao;
	}

	public void setDataRenovacaoProrrogacaoConcessao(Date dataRenovacaoProrrogacaoConcessao) {
		this.dataRenovacaoProrrogacaoConcessao = dataRenovacaoProrrogacaoConcessao;
	}

	public List<PotenciaCalculo> getPotenciasCalculo() {
		return potenciasCalculo;
	}

	public void setPotenciasCalculo(List<PotenciaCalculo> potenciasCalculo) {
		this.potenciasCalculo = potenciasCalculo;
	}

	public List<Franquia> getFranquias() {
		return franquias;
	}

	public void setFranquias(List<Franquia> franquias) {
		this.franquias = franquias;
	}

	public List<Periodo> getSuspensoes() {
		return suspensoes;
	}

	public void setSuspensoes(List<Periodo> suspensoes) {
		this.suspensoes = suspensoes;
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

	public String getCodigoCcee() {
		return codigoCcee;
	}

	public void setCodigoCcee(String codigoCcee) {
		this.codigoCcee = codigoCcee;
	}

	public BigDecimal getValorPotencia() {
		return valorPotencia;
	}

	public void setValorPotencia(BigDecimal valorPotencia) {
		this.valorPotencia = valorPotencia;
	}

	public BigDecimal getParticipacao() {
		return participacao;
	}

	public void setParticipacao(BigDecimal participacao) {
		this.participacao = participacao;
	}

	public Set<String> getEquipamentosEventosId() {
		return equipamentosEventosId;
	}

	public void setEquipamentosEventosId(Set<String> equipamentosEventosId) {
		this.equipamentosEventosId = equipamentosEventosId;
	}
	
	public void addEquipamentosEventosIds(Set<String> equipamentosEventosId) {
		if (this.equipamentosEventosId == null) {
			this.equipamentosEventosId = new HashSet<>();
		}
		this.equipamentosEventosId.addAll(equipamentosEventosId);
	}

	public String getCodigoOns() {
		return codigoOns;
	}

	public void setCodigoOns(String codigoOns) {
		this.codigoOns = codigoOns;
	}

	public String getIdVisaoPlanejamentoOpEnergetica() {
		return idVisaoPlanejamentoOpEnergetica;
	}

	public void setIdVisaoPlanejamentoOpEnergetica(String idVisaoPlanejamentoOpEnergetica) {
		this.idVisaoPlanejamentoOpEnergetica = idVisaoPlanejamentoOpEnergetica;
	}

	public TipoFonteEnergetica getTipoFonteEnergetica() {
		return tipoFonteEnergetica;
	}

	public void setTipoFonteEnergetica(TipoFonteEnergetica tipoFonteEnergetica) {
		this.tipoFonteEnergetica = tipoFonteEnergetica;
	}


}
