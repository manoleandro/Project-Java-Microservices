package br.org.ons.geracao.evento.taxa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.TimeZone;

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.modelagem.Periodo;

/**
 * Taxa acumulada estendida
 * 
 */
public class TaxaAcumuladaEstendida extends Taxa {
	private static final long serialVersionUID = 873565927676533381L;
	private List<HorasIndisponibilidade> horasIndisponibilidade = new ArrayList<HorasIndisponibilidade>();
	
	/**
	 * @return o campo horasIndisponibilidade
	 */
	public List<HorasIndisponibilidade> getHorasIndisponibilidade() {
		return horasIndisponibilidade;
	}

	/**
	 * @param horasIndisponibilidade
	 *            o campo horasIndisponibilidade a ser definido
	 */
	public void setHorasIndisponibilidade(List<HorasIndisponibilidade> horasIndisponibilidade) {
		this.horasIndisponibilidade = horasIndisponibilidade;
	}
	
	public void adicionarHorasIndisponibilidade(HorasIndisponibilidade horasIndisp) {
		horasIndisponibilidade.add(horasIndisp);
	}
	
	public List<HorasIndisponibilidade> horasIndisponibilidadeDoEquipamento(Equipamento equipamento) {
		List<HorasIndisponibilidade> horasIndisponibilidadeDoEquipamento = new ArrayList<HorasIndisponibilidade>();
		Iterator<HorasIndisponibilidade> it = horasIndisponibilidade.iterator();
		while(it.hasNext()) {
			HorasIndisponibilidade h = it.next();
			if(h.getIdEquipamento().equals(equipamento.getId()))
				horasIndisponibilidadeDoEquipamento.add(h);
		}
		return horasIndisponibilidadeDoEquipamento;		
	}
	
	public HorasIndisponibilidade horasIndisponibilidadeDoEquipamentoNoPeriodo(Equipamento equipamento, Periodo periodo) {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.setTime(periodo.getDataInicio());
		Iterator<HorasIndisponibilidade> it = horasIndisponibilidade.iterator();
		while(it.hasNext()) {
			HorasIndisponibilidade h = it.next();
			if(h.getIdEquipamento().equals(equipamento.getId()) &&
			   cal.get(Calendar.MONTH) == h.getMes()-1 //0..11
			   && cal.get(Calendar.YEAR) == h.getAno())
				return h;
		}
		HorasIndisponibilidade h = new HorasIndisponibilidade();
		h.atribuir(BigDecimal.ZERO);
		return h;
	}
			
	public void criarHorasIndisponibilidade(int qtdePeriodos, Equipamento equipamento, Periodo periodoCorrente,
											java.util.HashMap<String, List> indisponibilidades) {
		// Cria qtdePeriodos horas de indisponibilidade, anteriores a periodoCorrente se pelo menos um equipamento estiver disponível no período
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		Periodo periodo = periodoCorrente;
		System.out.println("# indisponibilidades = " + indisponibilidades.size());
		int qtdeEquipamentosComIndisponibilidade = indisponibilidades.keySet().size();
		int n = 0;
		do {
			Iterator<List> itEquip = indisponibilidades.values().iterator();
			int equipamentosIndisponiveis = 0;
			while(itEquip.hasNext()) {
				Iterator<Periodo> itIndisp = ((List<Periodo>)itEquip.next()).iterator();
				while(itIndisp.hasNext())
					if(itIndisp.next().contem(periodo)){
						equipamentosIndisponiveis++;
						break;
					}
			}
			
			cal.setTime(periodo.getDataInicio());
//			System.out.println(qtdePeriodos + " periodos a partir de " + periodo.getDataInicio());

			// Se a instalação inteira estiver indisponível, não criamos nenhuma objeto HorasIndisponibilidade
			if(qtdeEquipamentosComIndisponibilidade == 0 || equipamentosIndisponiveis < qtdeEquipamentosComIndisponibilidade) {
				HorasIndisponibilidade h = new HorasIndisponibilidade();
				h.setId(br.org.ons.platform.common.util.IdGenerator.newId());
				//Nota: null é o código padrão
				h.setMes(cal.get(Calendar.MONTH)+1); // 1..12
				h.setAno(cal.get(Calendar.YEAR));
				h.setIdEquipamento(equipamento.getId());
				horasIndisponibilidade.add(h);
				n++;
			}
			
			// Volta um mês atrás
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date dataInicio = cal.getTime();
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			periodo = new Periodo(dataInicio, cal.getTime());
		} while(n < qtdePeriodos);
	}
}