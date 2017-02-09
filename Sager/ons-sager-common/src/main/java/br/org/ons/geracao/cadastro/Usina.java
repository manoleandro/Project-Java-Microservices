package br.org.ons.geracao.cadastro;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;

import br.org.ons.geracao.modelagem.Periodo;

/**
 * Conjunto de instala��es destinadas � produ��o e disponibiliza��o de energia
 * el�trica ao sistema. Os Empreendimentos de Gera��o t�m como principais
 * componentes as Unidades Geradoras, Transformadores Elevadores de Tens�o e
 * Ativos de Conex�o ao sistema.
 * 
 */
public class Usina extends Instalacao {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String versao;
	private TipoUsina tipo;
	private String cosr;
	private Date dataProrrogacao;
	private String codigoVisaoApuracaoGeracao;
	private String codigoAneel;

	private List<UnidadeGeradora> unidadesGeradoras = new ArrayList<>();

	private Agente agenteProprietario;
	
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
	 * @return o campo tipo
	 */
	public TipoUsina getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            o campo tipo a ser definido
	 */
	public void setTipo(TipoUsina tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o campo cosr
	 */
	public String getCosr() {
		return cosr;
	}

	/**
	 * @param cosr
	 *            o campo cosr a ser definido
	 */
	public void setCosr(String cosr) {
		this.cosr = cosr;
	}

	/**
	 * @return o campo dataProrrogacao
	 */
	public Date getDataProrrogacao() {
		return dataProrrogacao;
	}

	/**
	 * @param dataProrrogacao
	 *            o campo dataProrrogacao a ser definido
	 */
	public void setDataProrrogacao(Date dataProrrogacao) {
		this.dataProrrogacao = dataProrrogacao;
	}

	/**
	 * @return o campo codigoVisaoApuracaoGeracao
	 */
	public String getCodigoVisaoApuracaoGeracao() {
		return codigoVisaoApuracaoGeracao;
	}

	/**
	 * @param codigoVisaoApuracaoGeracao
	 *            o campo codigoVisaoApuracaoGeracao a ser definido
	 */
	public void setCodigoVisaoApuracaoGeracao(String codigoVisaoApuracaoGeracao) {
		this.codigoVisaoApuracaoGeracao = codigoVisaoApuracaoGeracao;
	}

	/**
	 * @return o campo codigoAneel
	 */
	public String getCodigoAneel() {
		return codigoAneel;
	}

	/**
	 * @param codigoAneel
	 *            o campo codigoAneel a ser definido
	 */
	public void setCodigoAneel(String codigoAneel) {
		this.codigoAneel = codigoAneel;
	}

	/**
	 * @return o campo unidadesGeradoras
	 */
	public List<UnidadeGeradora> getUnidadesGeradoras() {
		return unidadesGeradoras;
	}

	/**
	 * @param unidadesGeradoras
	 *            o campo unidadesGeradoras a ser definido
	 */
	public void setUnidadesGeradoras(List<UnidadeGeradora> unidadesGeradoras) {
		this.unidadesGeradoras = unidadesGeradoras;
	}
	
	/**
	 * @return o campo agenteProprietario
	 */
	public Agente getAgenteProprietario() {
		return agenteProprietario;
	}

	/**
	 * @param agenteProprietario o campo agenteProprietario a ser definido
	 */
	public void setAgenteProprietario(Agente agenteProprietario) {
		this.agenteProprietario = agenteProprietario;
	}

	/**
	 * @param periodo
	 *            uma janela de cálculo
	 * @return a maior data de desativação das unidades geradoras, se todas fossem desativadas
	 */
	public Date dataDesativacao() {
		Iterator<UnidadeGeradora> it = unidadesGeradoras.iterator();
		Date dtDesativacao = null;
		int n = 0;
		System.out.println(" dt desativacao 1231244 ");
		while(it.hasNext()) {
			UnidadeGeradora ug = it.next();
			System.out.println(" dt desativacao "+ug.getDataDesativacao());
			if(ug.getDataDesativacao() != null) {
							if(dtDesativacao == null || dtDesativacao.before(ug.getDataDesativacao()))
					dtDesativacao = ug.getDataDesativacao();
				n++;
			}
		}
		System.out.println(" dt desativacao 2 "+dtDesativacao);
		//Caso não tenha data retornar um valor fake ultima data do seculo, 
		//onde está situação não irá disparar regras.
		Calendar c = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
		c.set(2999, 12, 31);
		
		return (n == unidadesGeradoras.size()) ? dtDesativacao : c.getTime();
	}
	
	/**
	 * @param periodo
	 *            uma janela de cálculo
	 * @return o menor período de suspensão se todas as unidades geradoras de this fossem suspensas no período
	 */
	public Periodo suspensao(Periodo periodo) {
		Iterator<UnidadeGeradora> itUG = unidadesGeradoras.iterator();
		Periodo primeiraSuspensao = null;
		while(itUG.hasNext()) {
			UnidadeGeradora uge = itUG.next();
			Periodo p = uge.suspensao(periodo);
			if(p != null &&  (primeiraSuspensao == null ||	
				(primeiraSuspensao != null &&
				p.getDataInicio().getTime() > primeiraSuspensao.getDataInicio().getTime()))){
			 	primeiraSuspensao = p;				 
			}	
			if(suspensoNoPeriodo(periodo)){
				return primeiraSuspensao;
			}
		}
		return null;
		//return (numEqp == unidadesGeradoras.size()) ? primeiraSuspensao : null;
	}
	
	/**
	 * @return a menor data de implantação das unidades geradoras de this
	 */
	public Date dataImplantacao() {
		Iterator<UnidadeGeradora> itUG = unidadesGeradoras.iterator();
		Date menorDataImplantacao = null;
		while(itUG.hasNext()) {
			UnidadeGeradora uge = itUG.next();
			if(menorDataImplantacao == null || uge.getDataImplantacao().before(menorDataImplantacao))
				menorDataImplantacao = uge.getDataImplantacao();
		}
		return menorDataImplantacao;
	}
	
	public boolean suspensoNoPeriodo(Periodo periodo) {
		Iterator<UnidadeGeradora> itUG = unidadesGeradoras.iterator();
		while(itUG.hasNext()) {
			if(periodo != null && itUG.next().suspensoNoPeriodo(periodo) == false)
				return false;
		}
		return true;
	}
}
