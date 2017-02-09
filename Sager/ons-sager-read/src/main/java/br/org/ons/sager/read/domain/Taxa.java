package br.org.ons.sager.read.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import com.querydsl.core.annotations.QueryEntity;

import br.org.ons.geracao.evento.taxa.Participacao;

@QueryEntity
@KeySpace("taxa")
public class Taxa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	protected String id;
	
	protected String jobId;
	
	protected String nomeTaxa;

	protected String idInstalacao;
	
	protected String nomeInstalacao;
	
	protected Date dataApuracao;
	
	protected Integer ident;

	protected Integer ano;

	protected Integer mes;

	protected Integer versaoCenario;

	protected Integer versaoTaxa;
	
	protected Date dataCalculo;
	
	protected String nomeCenario;

	protected String outraVersaoTaxa;
	
	protected String outraVersaoCenario;
	
	protected String regulamentacao;
	
	protected Long majorVersion;
	
	protected List<String> memoriaCalculo = new ArrayList<>();
	
	protected List<Valores> valores = new ArrayList<Valores>();
	
	protected List<Participacao> participacoesEquipamentos = new ArrayList<Participacao>();

	
	
	
	public String getNomeInstalacao() {
		return nomeInstalacao;
	}

	public void setNomeInstalacao(String nomeInstalacao) {
		this.nomeInstalacao = nomeInstalacao;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getIdInstalacao() {
		return idInstalacao;
	}

	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public int getVersaoTaxa() {
		return versaoTaxa;
	}

	public void setVersaoTaxa(Integer versaoTaxa) {
		this.versaoTaxa = versaoTaxa;
	}
	
	public List<Valores> getValores() {
		return valores;
	}

	public void setValores(List<Valores> valores) {
		this.valores = valores;
	}
	
	public void addValor(Valores valor){
		valores.add(valor);
	}

	public void addValores(List<Valores> valores){
		this.valores.addAll(valores);
	}	
	

	public int getIdent() {
		return ident;
	}

	public void setIdent(Integer ident) {
		this.ident = ident;
	}

	public int getVersaoCenario() {
		return versaoCenario;
	}

	public void setVersaoCenario(Integer versaoCenario) {
		this.versaoCenario = versaoCenario;
	}

	public Date getDataCalculo() {
		return dataCalculo;
	}

	public void setDataCalculo(Date dataCalculo) {
		this.dataCalculo = dataCalculo;
	}

	public String getNomeCenario() {
		return nomeCenario;
	}

	public void setNomeCenario(String nomeCenario) {
		this.nomeCenario = nomeCenario;
	}

	public String isOutraVersaoTaxa() {
		return outraVersaoTaxa;
	}

	public void setOutraVersaoTaxa(String outraVersaoTaxa) {
		this.outraVersaoTaxa = outraVersaoTaxa;
	}

	public String isOutraVersaoCenario() {
		return outraVersaoCenario;
	}

	public void setOutraVersaoCenario(String outraVersaoCenario) {
		this.outraVersaoCenario = outraVersaoCenario;
	}

	public String getRegulamentacao() {
		return regulamentacao;
	}

	public void setRegulamentacao(String regulamentacao) {
		this.regulamentacao = regulamentacao;
	}

	public Date getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(Date dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	public List<Participacao> getParticipacoesEquipamentos() {
		return participacoesEquipamentos;
	}

	public void setParticipacoesEquipamentos(List<Participacao> participacoesEquipamentos) {
		this.participacoesEquipamentos = participacoesEquipamentos;
	}
	
	public String getNomeTaxa() {
		return nomeTaxa;
	}

	public void setNomeTaxa(String nomeTaxa) {
		this.nomeTaxa = nomeTaxa;
	}

	public String getOutraVersaoTaxa() {
		return outraVersaoTaxa;
	}

	public String getOutraVersaoCenario() {
		return outraVersaoCenario;
	}

	public List<String> getMemoriaCalculo() {
		return memoriaCalculo;
	}

	public void setMemoriaCalculo(List<String> memoriaCalculo) {
		this.memoriaCalculo = memoriaCalculo;
	}

	public Long getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + mes;
		result = prime * result + ((nomeCenario == null) ? 0 : nomeCenario.hashCode());
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
		Taxa other = (Taxa) obj;
		if (!ano.equals(other.ano))
			return false;
		if (!mes.equals(other.mes))
			return false;
		if (nomeCenario == null) {
			if (other.nomeCenario != null)
				return false;
		} else if (!nomeCenario.equals(other.nomeCenario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Taxa [id=" + id + ", jobId=" + jobId + ", nomeTaxa=" + nomeTaxa + ", idInstalacao=" + idInstalacao
				+ ", dataApuracao=" + dataApuracao + ", ident=" + ident + ", ano=" + ano + ", mes=" + mes
				+ ", versaoCenario=" + versaoCenario + ", versaoTaxa=" + versaoTaxa + ", dataCalculo=" + dataCalculo
				+ ", nomeCenario=" + nomeCenario + ", outraVersaoTaxa=" + outraVersaoTaxa + ", outraVersaoCenario="
				+ outraVersaoCenario + ", regulamentacao=" + regulamentacao + ", memoriaCalculo=" + memoriaCalculo
				+ ", valores=" + valores + ", participacoesEquipamentos=" + participacoesEquipamentos + "]";
	}

	
	
}
