package br.org.ons.geracao.evento.franquia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.ons.geracao.modelagem.Periodo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * 
 *
 */
public class Franquia implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String versao;
	private Integer valorLimite;
	private Integer qtMinutosServicoParaUso;
	private Integer valorDisponivel;
	private Periodo periodoVigencia;
	private Periodo periodoValidade = null;
	private String idEquipamento;

	/**
	 * @return o campo codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            o campo codigo a ser definido
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return o campo valorLimite
	 */
	public Integer getValorLimite() {
		return valorLimite;
	}

	/**
	 * @param valorLimite
	 *            o campo valorLimite a ser definido
	 */
	public void setValorLimite(Integer valorLimite) {
		this.valorLimite = valorLimite;
	}

	/**
	 * @return o campo qtMinutosServicoParaUso
	 */
	public Integer getQtMinutosServicoParaUso() {
		return qtMinutosServicoParaUso;
	}

	/**
	 * @param qtMinutosServicoParaUso
	 *            o campo qtMinutosServicoParaUso a ser definido
	 */
	public void setQtMinutosServicoParaUso(Integer qtMinutosServicoParaUso) {
		this.qtMinutosServicoParaUso = qtMinutosServicoParaUso;
	}

	/**
	 * @return o campo valorDisponivel
	 */
	public Integer getValorDisponivel() {
		return valorDisponivel;
	}

	/**
	 * @param valorDisponivel
	 *            o campo valorDisponivel a ser definido
	 */
	public void setValorDisponivel(Integer valorDisponivel) {
		this.valorDisponivel = valorDisponivel;
	}

	/**
	 * @return o campo periodoVigencia
	 */
	public Periodo getPeriodoVigencia() {
		return periodoVigencia;
	}

	/**
	 * @param periodoValidade
	 *            o campo periodoVigencia a ser definido
	 */
	public void setPeriodoVigencia(Periodo periodoVigencia) {
		this.periodoVigencia = periodoVigencia;
	}

	/**
	 * @return o campo periodoValidade
	 */
	public Periodo getPeriodoValidade() {
		return periodoValidade;
	}

	/**
	 * @param periodoValidade
	 *            o campo periodoValidade a ser definido
	 */
	public void setPeriodoValidade(Periodo periodoValidade) {
		this.periodoValidade = periodoValidade;
	}

	/**
	 * @return o campo idEquipamento
	 */
	@JsonIgnore
	public String getIdEquipamento() {
		return idEquipamento;
	}

	/**
	 * @param idEquipamento o campo idEquipamento a ser definido
	 */
	@JsonSetter
	public void setIdEquipamento(String idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
	
	public void atualizarPeriodoValidade(Date dataInicio, Date dataFim) {
		if(periodoValidade == null)
			periodoValidade = new Periodo();
		periodoValidade.setDataInicio(dataInicio);
		periodoValidade.setDataFim(dataFim);
	}
	
	public void atualizarInicioPeriodoValidade(Date dataInicio) {
		if(periodoValidade == null)
			periodoValidade = new Periodo();
		periodoValidade.setDataInicio(dataInicio);
		periodoValidade.setDataFim(null);
	}
	
	public List<String> comparar(Franquia franquia) {
		List<String> diferencas = new ArrayList<String>();
		if(franquia != null && franquia.idEquipamento.equals(idEquipamento) && franquia.codigo.equals(codigo) && franquia.versao.equals(versao) && franquia.periodoVigencia.igual(periodoVigencia)) {
			if(franquia.valorLimite != valorLimite)
				diferencas.add(idEquipamento + "-" + franquia.periodoVigencia.getDataInicio() + "-" + franquia.codigo + ": Valor limite diferente (" + franquia.valorLimite + ")");
			if(franquia.qtMinutosServicoParaUso != qtMinutosServicoParaUso)
				diferencas.add(idEquipamento + "-" + franquia.periodoVigencia.getDataInicio() + "-" + franquia.codigo + ": Quantidade de minutos de serviço para uso diferente (" + franquia.qtMinutosServicoParaUso + ")");
			if(franquia.valorDisponivel != valorDisponivel)
				diferencas.add(idEquipamento + "-" + franquia.periodoVigencia.getDataInicio() + "-" + franquia.codigo + ": Valor disponível diferente (" + franquia.valorDisponivel + ")");
			if(franquia.periodoValidade.igual(periodoValidade))
				diferencas.add(idEquipamento + "-" + franquia.periodoVigencia.getDataInicio() + "-" + franquia.codigo + ": Periodo de validade diferente (" + franquia.periodoValidade + ")");
		}
		return diferencas;
	}
}
