package br.org.ons.geracao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.modelagem.Periodo;

/**
 * Instala��o destinada � produ��o e disponibiliza��o de energia
 * el�trica ao sistema.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, 
		include = JsonTypeInfo.As.PROPERTY, 
		property = "_class", 
		visible = false)
@JsonSubTypes({
	@Type(value = UnidadeGeradora.class, 
			name = "br.org.ons.geracao.cadastro.UnidadeGeradora"),
	@Type(value = EquipamentoInterligacaoInternacional.class, 
			name = "br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional")
})
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String versao;
	private String nome;

	private TipoInstalacao tipoInstalacao;
	private String idInstalacao;

	/**
	 * Persistir neste atributo a data do (primeiro) evento com condi��o
	 * operativa igual a "EOC". S� pode existir uma �nica data de entrada em
	 * opera��o comercial por equipamento. A persist�ncia deve ocorrer na
	 * ocasi�o da carga dos eventos de mudan�a operativa do equipamento.
	 * 
	 */
	private Date dataEventoEOC;
	private Date dataDesativacao;

	/**
	 * Horas em servi�o acumuladas desde a entrada em opera��o do
	 * equipamento
	 * 
	 */
	private Integer quantidadeHorasServico;

	/**
	 * A data de implanta��o � (= dtEntrada no SAMUG). Utilizada no
	 * per�odo de vig�ncia das franquias do equipamento. Foi chamado data de
	 * implantação para não confundir com a data do evento EOC.
	 * 
	 */
	private Date dataImplantacao;

	/**
	 * Atributo com valor: data de renova��o da concess�o ou data de
	 * prorroga��o da concess�o (duas verbaliza��es de navega��o
	 * no IBM ODM)
	 */
	private Date dataRenovacaoProrrogacaoConcessao;

	private List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
	private List<Franquia> franquias = new ArrayList<Franquia>();
	private List<Periodo> suspensoes = new ArrayList<Periodo>();

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
	 * @return o campo versao
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            o campo versao a ser definido
	 */
	public void setVersao(String versao) {
		this.versao = versao;
	}

	/**
	 * @return o campo nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            o campo nome a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return o campo tipoInstalacao
	 */
	public TipoInstalacao getTipoInstalacao() {
		return tipoInstalacao;
	}

	/**
	 * @param tipoInstalacao
	 *            o campo tipoInstalacao a ser definido
	 */
	public void setTipoInstalacao(TipoInstalacao tipoInstalacao) {
		this.tipoInstalacao = tipoInstalacao;
	}

	/**
	 * @return o campo idInstalacao
	 */
	@JsonIgnore
	public String getIdInstalacao() {
		return idInstalacao;
	}

	/**
	 * @param idInstalacao o campo idInstalacao a ser definido
	 */
	@JsonSetter
	public void setIdInstalacao(String idInstalacao) {
		this.idInstalacao = idInstalacao;
	}

	/**
	 * @return o campo dataEventoEOC
	 */
	public Date getDataEventoEOC() {
		return dataEventoEOC;
	}

	/**
	 * @param dataEventoEOC
	 *            o campo dataEventoEOC a ser definido
	 */
	public void setDataEventoEOC(Date dataEventoEOC) {
		this.dataEventoEOC = dataEventoEOC;
	}

	/**
	 * @return o campo dataEventoEOC se não for null, senão retorna o campo
	 *         dataImplantacao
	 */
	@JsonIgnore
	public Date getDataEntradaOperacaoComercial() {
		return (dataEventoEOC != null) ? dataEventoEOC : dataImplantacao;
	}

	/**
	 * @return o campo dataDesativacao
	 */
	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	/**
	 * @param dataDesativacao
	 *            o campo dataDesativacao a ser definido
	 */
	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	/**
	 * @return o campo quantidadeHorasServico
	 */
	public Integer getQuantidadeHorasServico() {
		return quantidadeHorasServico;
	}

	/**
	 * @param quantidadeHorasServico
	 *            o campo quantidadeHorasServico a ser definido
	 */
	public void setQuantidadeHorasServico(Integer quantidadeHorasServico) {
		this.quantidadeHorasServico = quantidadeHorasServico;
	}

	/**
	 * @return o campo dataImplantacao
	 */
	public Date getDataImplantacao() {
		return dataImplantacao;
	}

	/**
	 * @param dataImplantacao
	 *            o campo dataImplantacao a ser definido
	 */
	public void setDataImplantacao(Date dataImplantacao) {
		this.dataImplantacao = dataImplantacao;
	}

	/**
	 * @return o campo dataRenovacaoProrrogacaoConcessao
	 */
	public Date getDataRenovacaoProrrogacaoConcessao() {
		return dataRenovacaoProrrogacaoConcessao;
	}

	/**
	 * @param dataRenovacaoProrrogacaoConcessao
	 *            o campo dataRenovacaoProrrogacaoConcessao a ser definido
	 */
	public void setDataRenovacaoProrrogacaoConcessao(Date dataRenovacaoProrrogacaoConcessao) {
		this.dataRenovacaoProrrogacaoConcessao = dataRenovacaoProrrogacaoConcessao;
	}

	/**
	 * @return o campo potenciasCalculo
	 */
	public List<PotenciaCalculo> getPotenciasCalculo() {
		return potenciasCalculo;
	}

	/**
	 * @param potenciasCalculo
	 *            o campo potenciasCalculo a ser definido
	 */
	public void setPotenciasCalculo(List<PotenciaCalculo> potenciasCalculo) {
		this.potenciasCalculo = potenciasCalculo;
	}

	/**
	 * @return o campo franquias
	 */
	public List<Franquia> getFranquias() {
		return franquias;
	}

	/**
	 * @param franquias
	 *            o campo franquias a ser definido
	 */
	public void setFranquias(List<Franquia> franquias) {
		this.franquias = franquias;
	}

	/**
	 * @return o campo suspensoes
	 */
	public List<Periodo> getSuspensoes() {
		return suspensoes;
	}

	/**
	 * @param franquias
	 *            o campo suspensoes a ser definido
	 */
	public void setSuspensoes(List<Periodo> suspensoes) {
		this.suspensoes = suspensoes;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", versao=" + versao + ", nome=" + nome + ", tipoInstalacao=" + tipoInstalacao
				+ ", idInstalacao=" + idInstalacao + ", dataEventoEOC=" + dataEventoEOC + ", dataDesativacao="
				+ dataDesativacao + ", quantidadeHorasServico=" + quantidadeHorasServico + ", dataImplantacao="
				+ dataImplantacao + ", dataRenovacaoProrrogacaoConcessao=" + dataRenovacaoProrrogacaoConcessao
				+ ", potenciasCalculo=" + potenciasCalculo + ", franquias=" + franquias + ", suspensoes=" + suspensoes
				+ "]";
	}

	public boolean suspensoNoPeriodo(Periodo periodo) {
		java.util.Iterator<Periodo> it = suspensoes.iterator();
		while (it.hasNext()) {
			Periodo suspensao = it.next();
			if (periodo != null && suspensao.getDataFim()!= null && suspensao.getDataInicio() != null &&
				suspensao.getDataInicio().compareTo(periodo.getDataInicio()) <= 0 &&
				suspensao.getDataFim().compareTo(periodo.getDataFim()) >= 0)
				return true;
			if(periodo != null && suspensao.getDataFim() == null && suspensao.getDataInicio() != null &&
			   suspensao.getDataInicio().compareTo(periodo.getDataInicio()) <= 0)
				return true;
		}
		return false;
	}

	/**
	 * @param periodo
	 *            uma janela de cálculo
	 * @return o período de suspensão que inclui a janela de cálculo
	 */
	public Periodo suspensao(Periodo periodo) {
		try {
			
			Iterator<Periodo> itS = suspensoes.iterator();
			while (itS.hasNext()) {
				Periodo p = itS.next();				
				if (p.contem(periodo)){					
					return p;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PotenciaCalculo potenciaCalculo(Date data) {
		java.util.Iterator<PotenciaCalculo> it = potenciasCalculo.iterator();
		while (it.hasNext()) {
			PotenciaCalculo pc = it.next();
			if(pc.getVigencia().contem(data))
				return pc;
		}		
		return null;
	}
	
	public PotenciaCalculo potenciaCalculo(Periodo periodo) {
		return potenciaCalculo(periodo.getDataInicio());
	}
	
	public void adicionarPotenciaCalculo(Date dataInicio, Double valor) {
		adicionarPotenciaCalculo(dataInicio, null, valor);
	}
	
	public void adicionarPotenciaCalculo(Date dataInicio, Date dataFim, Double valor) {
		// Procura uma potência que inicia na data de início do período
		PotenciaCalculo potencia = null;
		java.util.Iterator<PotenciaCalculo> it = potenciasCalculo.iterator();
		while (it.hasNext()) {
			PotenciaCalculo pc = it.next();
			if(pc.getVigencia().getDataInicio().compareTo(dataInicio) == 0) {
				potencia = pc;
				break;
			}
		}
		if(potencia != null) {
			potencia.getVigencia().setDataFim(dataFim);
			potencia.setValor(valor);
		}
		else {
			potencia = new PotenciaCalculo();
			potencia.setIdEquipamento(id);
			Periodo vigencia = new Periodo();
			vigencia.setDataInicio(dataInicio);
			vigencia.setDataFim(dataFim);
			potencia.setVigencia(vigencia);
			potencia.setValor(valor);
			potenciasCalculo.add(potencia);
		}
	}
	
	public Equipamento clone(String tag) {
		br.org.ons.geracao.cadastro.Equipamento eq = new br.org.ons.geracao.cadastro.Equipamento();
		eq.dataDesativacao = this.dataDesativacao;
		eq.dataEventoEOC = this.dataEventoEOC;
		eq.dataImplantacao = this.dataImplantacao;
		eq.dataRenovacaoProrrogacaoConcessao = this.dataRenovacaoProrrogacaoConcessao;
		eq.franquias = new ArrayList<Franquia>();
		Iterator<Franquia> itF = this.franquias.iterator();
		while(itF.hasNext()) {
			Franquia f = itF.next();
			Franquia franquia = new Franquia();
			franquia.setCodigo(f.getCodigo());
			franquia.setIdEquipamento(id);
			franquia.setPeriodoValidade(f.getPeriodoValidade());
			franquia.setQtMinutosServicoParaUso(f.getQtMinutosServicoParaUso());
			franquia.setPeriodoVigencia(f.getPeriodoVigencia());
			franquia.setValorLimite(f.getValorLimite());
			franquia.setValorDisponivel(f.getValorDisponivel());
			franquia.setVersao(f.getVersao());
			eq.franquias.add(franquia);
		}
		eq.id = this.id;
		eq.idInstalacao = this.idInstalacao;
		eq.nome = this.nome + tag;
		eq.potenciasCalculo = new ArrayList<PotenciaCalculo>();
		Iterator<PotenciaCalculo> itP = this.potenciasCalculo.iterator();
		while(itP.hasNext()) {
			PotenciaCalculo p = itP.next();
			PotenciaCalculo potencia = new PotenciaCalculo();
			potencia.setIdEquipamento(id);
			potencia.setValor(p.getValor());
			potencia.setVigencia(p.getVigencia());
			eq.potenciasCalculo.add(potencia);
		}
		eq.quantidadeHorasServico = this.quantidadeHorasServico;
		eq.suspensoes = this.suspensoes;
		eq.tipoInstalacao = this.tipoInstalacao;
		eq.versao = this.versao;
		return eq;
	}
}
