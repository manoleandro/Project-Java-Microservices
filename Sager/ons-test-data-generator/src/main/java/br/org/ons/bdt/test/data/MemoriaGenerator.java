package br.org.ons.bdt.test.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MemoriaGenerator extends Generator {

	private static Logger LOG = LogManager.getLogger(MemoriaGenerator.class);

	public void generate() {

		double teipmes = this.calcularTEIPMes("ALUXG", LocalDate.of(2014, 4, 1));
		//double teifames = this.calcularTEIFaMes("ALUXG", LocalDate.of(2014, 4, 1));
		System.out.println("TEIP mes:" + teipmes);
		//System.out.println("TEIFa mes:" + teifames);

	}

	public static void main(String[] args) {
		new MemoriaGenerator().generate();

	}

	public HashMap<String, List<Evento>> getEventos(String usiid, LocalDate mesInicio, LocalDate mesFim) {
		HashMap<String, List<Evento>> eventosUGE = null;
		String query = "select \n" + "d.numons as numons,\n" + "1 as ver, \n" + "rtrim(u.usi_id) as usiid,"
				+ "d.uge_id as ugeid,\n" +
				// "c.nomecurto as cos, \n" +
				// "u.tpusina_id as tipo, \n" +
				// "a.nomecurto as agente,\n" +
				// "u.nomecurto as instalação, \n" +
				"rtrim(ug.nomelongo) as uge,\n" + "d.dtini_verif as datahora, \n" + "d.tpestoper_id as eo,\n"
				+ "d.panocr_id as co, \n" + "d.ogresdes_id as or,\n" + "cast(d.valdisp as int) as disp \n" +
				// "d.din_disponevento as dtCriacao, \n" +
				// "d.dat_ultimaconsolidacao as dtUltimaConsolidacao,\n" +
				// "0 as status, \n" +
				// "'' as statusCorrente, \n" +
				// "d.flcmop as tipoOperacao\n" +
				" from desger d, uge ug, usi u, age a, cos c, ins\n" + " where d.uge_id = ug.uge_id\n"
				+ " and ins.cos_id = c.cos_id\n" + " and u.ins_id = ins.ins_id\n" + " and ug.usi_id = u.usi_id\n"
				+ " and ug.age_id_prop = a.age_id\n" + " and d.dtini_verif between ? and ?\n" + " and ug.usi_id = ?\n"
				+ " order by ug.eqp_id, d.dtini_verif";
		PreparedStatement pstmt = null;
		try {
			pstmt = this.getConnection().prepareStatement(query);
			pstmt.setDate(1, java.sql.Date.valueOf(mesInicio.withDayOfMonth(1)));
			pstmt.setDate(2, java.sql.Date.valueOf(mesFim.withDayOfMonth(mesFim.lengthOfMonth())));
			pstmt.setString(3, usiid);
			ResultSet rs = pstmt.executeQuery();
			eventosUGE = getEventosUGES(rs);

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (this.getConnection() != null)
					this.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eventosUGE;

	}

	public double calcularTEIPMes(String usiid, LocalDate mesCalculo) {

		if (mesCalculo.isBefore(LocalDate.of(2001, 1, 1)) || mesCalculo.isAfter(LocalDate.of(2014, 9, 30))) {
			throw new IllegalArgumentException("Esta taxa é válida apenas de Janeiro/2001 até Setembro/2014");
		}

		double hp = 0;
		double hdp = 0;
		double hedp = 0;

		HashMap<String, List<Evento>> eventos = getEventos(usiid, mesCalculo, mesCalculo);

		for (Iterator iterator = eventos.keySet().iterator(); iterator.hasNext();) {
			String uge = (String) iterator.next();
			System.out.println("Processando UGE" + uge);
			
			hp = hp + calcularHP(mesCalculo);
			hdp = hdp + calcularHDP(eventos.get(uge));
			hedp = hedp + calcularHEDP(eventos.get(uge));
			System.out.println("uge01" + uge + ", hp = " + hp);
			System.out.println("uge01" + uge + ", hdp = " + hdp);
			System.out.println("uge01" + uge + ", hedp = "+ hedp);
		}

		return (hdp + hedp) / hp;

	}

	public double calcularTEIFaMes(String usiid, LocalDate mesCalculo) {

		if (mesCalculo.isBefore(LocalDate.of(2001, 1, 1)) || mesCalculo.isAfter(LocalDate.of(2014, 9, 30))) {
			throw new IllegalArgumentException("Esta taxa é válida apenas de Janeiro/2001 até Setembro/2014");
		}

		double hdf = 0;
		double hedf = 0;
		double hs = 0;
		double hrd = 0;
		double hdce = 0;

		HashMap<String, List<Evento>> eventos = getEventos(usiid, mesCalculo, mesCalculo);

		for (Iterator iterator = eventos.keySet().iterator(); iterator.hasNext();) {
			String uge = (String) iterator.next();
			System.out.println("Processando UGE" + uge);

			hdf = hdf + calcularHDF(eventos.get(uge));
			hedf = hedf + calcularHEDF(eventos.get(uge));
			hs = hs + calcularHSv1(eventos.get(uge));
			hrd = hrd + calcularHRDv1(eventos.get(uge));
			hdce = hdce + calcularHDCE(eventos.get(uge));
		}

		return (hdf + hedf) / (hs + hdf + hrd + hdce);

	}

	private HashMap<String, List<Evento>> getEventosUGES(ResultSet rs) throws SQLException {
		HashMap<String, List<Evento>> eventosUGE = new HashMap<String, List<Evento>>();
		while (rs.next()) {
			String uge = rs.getString("ugeid");
			if (!eventosUGE.containsKey(uge)) {
				eventosUGE.put(uge, new ArrayList());
			}
			Evento evento = new Evento();
			evento.setEo(rs.getString("eo"));
			evento.setCo(rs.getString("co"));
			evento.setOr(rs.getString("or"));
			evento.setDisp(rs.getDouble("disp"));
			evento.setDtVerif(rs.getDate("datahora"));
			eventosUGE.get(uge).add(evento);
		}
		return eventosUGE;
	}

	/*
	 * Parâmetro Horas de Desligamento Programado – HDP:
	 * 
	 * O algoritmo realiza o cálculo das horas em que a Unidade Geradora ou
	 * Interligação Internacional permaneceu desligada em intervenção programada
	 * por indisponibilidades de responsabilidade do empreendimento. Para esse
	 * cálculo, são contabilizadas as horas em que a Unidade Geradora ou
	 * Interligação Internacional permaneceu com os Estados Operativos “DPR”,
	 * “DUR”, “DPA” ou “DCA”, e classificação de Origem identificada como sendo
	 * de responsabilidade do Empreendimento consideradas nas TEIFa e TEIP.
	 * 
	 * Origens para as indisponibilidades de responsabilidade do Empreendimento
	 * consideradas nas taxas TEIFa e TEIP: “GUM”, “GGE”, “GTR”, “GOT”, “GAC”,
	 * “GAG” e “GCB”.
	 * 
	 * Regra Válida desde 01/2000
	 */
	private double calcularHDP(List<Evento> eventos) {
		double dRet = 0d;

		final String[] tipoEO = { "DPR", "DUR", "DPA", "DCA" };
		final String[] tipoOR = { "GUM", "GGE", "GTR", "GOT", "GAC", "GAG", "GCB" };

		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if (Arrays.asList(tipoEO).contains(evento.getEo()) && Arrays.asList(tipoOR).contains(evento.getOr())) {
				Evento proxEvento = eventos.get(i + 1);
				System.out.println("Detectado evento para HDP");
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}

		}

		return dRet / (1000 * 60 * 60);
	}

	/*
	 * Parâmetro Horas Equivalentes de Desligamento Programado – HEDP: É o
	 * cálculo das horas em que a unidade opera com potência nominal limitada,
	 * associada a uma condição programada. É o produto das seguintes parcelas:
	 * 
	 * 1ª Parcela: Número de horas em que a Unidade Geradora ou Interligação
	 * Internacional permaneceu sincronizada ao sistema, operando com uma
	 * limitação de sua potência nominal, associada a uma restrição programada
	 * de responsabilidade da usina, identificada entre as Origens de
	 * responsabilidade do Empreendimento consideradas nas TEIFa e TEIP, no mês
	 * de apuração;
	 * 
	 * 2ª parcela: grau percentual da respectiva restrição programada.
	 * 
	 * Exemplo: Uma Unidade Geradora permaneceu sincronizada durante 10hs,
	 * operando como uma limitação de 50,0% de sua potência nominal, associada a
	 * uma intervenção programada no Ativo de Conexão, logo, HEDP=10 h x 0,50=
	 * 5h.
	 * 
	 * Para esse algoritmo, são consideradas as horas em que a Unidade Geradora
	 * ou Interligação Internacional permaneceu com os Estados Operativos “LIG”,
	 * “LCC”, “LCI” e “LCS”, Condição Operativa “RPR” e classificação de Origem
	 * identificada como sendo de responsabilidade do Empreendimento
	 * consideradas nas TEIFa e TEIP.
	 * 
	 * Origens para as indisponibilidades de responsabilidade do Empreendimento
	 * de Geração consideradas para efeito de cálculo das taxas TEIFa e TEIP:
	 * “GUM”, “GGE”, “GTR”, “GOT”, “GAC”, “GAG”, “GCB”
	 * 
	 * Regra Válida a partir de 01/2000
	 */
	private double calcularHEDP(List<Evento> eventos) {
		double dRet = 0d;

		final String[] tipoEO = { "LIG", "LCC", "LCI", "LCS" };
		final String[] tipoOR = { "GUM", "GGE", "GTR", "GOT", "GAC", "GAG", "GCB" };

		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if (Arrays.asList(tipoEO).contains(evento.getEo()) && Arrays.asList(tipoOR).contains(evento.getOr())) {
				Evento proxEvento = eventos.get(i + 1);
				System.out.println("Detectado evento para HEDP");
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}

		}

		return dRet / (1000 * 60 * 60);
	}

	/*
	 * Parâmetro Total de Horas do Período de Apuração – HP:
	 * 
	 * É o cálculo do número de horas de operação comercial da Unidade Geradora
	 * ou Interligação Internacional, no mês em apuração.
	 * 
	 * Regra Válida a partir de 01/2000
	 * 
	 */
	private double calcularHP(LocalDate mesCalculo) {
		double dRet = 0;
		dRet = mesCalculo.lengthOfMonth() * 24;
		// TODO: Considerar suspenção de unidade geradora e EOC no meio do mês
		return dRet;
	}

	/*
	 * Parâmetro Horas em Serviço – HS:
	 * 
	 * É o cálculo do número de horas em que a Unidade Geradora ou Interligação
	 * Internacional operou ligada ao sistema, no mês de apuração
	 * 
	 * Regra Válida de 01/2000 até 09/2014: Neste cálculo são contabilizadas as
	 * horas em que a Unidade Geradora ou Interligação Internacional permaneceu
	 * com os estados operativos “LIG”, “LCS”, “LCI” e “LCC”, independentemente
	 * de qualquer limitação de potência nominal, no mês de apuração
	 * 
	 * 
	 */
	private double calcularHSv1(List<Evento> eventos) {
		double dRet = 0;

		final String[] tipoEO = { "LIG", "LCC", "LCI", "LCS" };
		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if (Arrays.asList(tipoEO).contains(evento.getEo())) {
				Evento proxEvento = eventos.get(i + 1);
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}
		}
		return dRet;
	}

	/*
	 * Parâmetro Horas em Serviço – HS:
	 * 
	 * É o cálculo do número de horas em que a Unidade Geradora ou Interligação
	 * Internacional operou ligada ao sistema, no mês de apuração
	 * 
	 * Regra Válida a partir de 10/2014: Neste cálculo é considerado o número de
	 * horas equivalentes em serviço da unidade, somado ao nº de horas em que a
	 * unidade operou sincronizada ao sistema sem restrição de potência no mês
	 * de apuração. Neste cálculo são contabilizadas as horas em que a Unidade
	 * Geradora ou Interligação Internacional permaneceu com os estados
	 * operativos LIG, LCS, LCI e LCC, considerando proporcionalmente as
	 * limitações de potência nominal, no mês de apuração. ATENÇÃO: É necessário
	 * criar todo o histórico dos últimos 60 meses do parâmetro HS com base
	 * neste novo critério para calcular a taxa.
	 * 
	 */
	private double calcularHSv2(List<Evento> eventos) {
		double dRet = 0;

		final String[] tipoEO = { "LIG", "LCC", "LCI", "LCS" };
		final String[] tipoOR = { "GUM", "GGE", "GTR", "GOT", "GAC", "GAG", "GCB" };

		return dRet;
	}

	/*
	 * Parâmetro Horas de Reserva Desligada – HRD: É o cálculo do número de
	 * horas de em que a Unidade Geradora ou Interligação Internacional
	 * permaneceu desligada em conveniência operativa ou reserva de prontidão,
	 * no mês de apuração. Também são contabilizadas as horas de
	 * indisponibilidades de responsabilidade do empreendimento, por serem
	 * desconsideráveis para efeito de cálculo das taxas TEIFa e TEIP. Para esse
	 * algoritmo, são contabilizadas duas parcelas:
	 * 
	 * Regras Válidas de 01/2000 até 09/2014 - Cálculo das Parcelas: 1ª Parcela:
	 * Horas em que a Unidade Geradora ou Interligação Internacional permaneceu
	 * com os Estados Operativos “DCO” e “RDP”, independentemente de qualquer
	 * limitação de potência nominal, no mês de apuração;
	 * 
	 * 2ª Parcela: Horas em que a Unidade Geradora ou Interligação Internacional
	 * permaneceu com os Estados Operativos “DPR”, “DPA”, “DUR”, “DEM”, “DAU” e
	 * “DAP”, e Origem classificada como de indisponibilidades de
	 * responsabilidade do Empreendimento, mas desconsideradas nas taxas TEIFa e
	 * TEIP (*), exceto a Origem “GCI”.
	 * 
	 * Origens para indisponibilidades de responsabilidade do Empreendimento,
	 * mas desconsideradas nas taxas TEIFa e TEIP: “GCI”, “GIS”, “GIC”, “GIM”,
	 * “GVO”, “GMP” e “GMT”.
	 * 
	 * 
	 * 
	 */
	private double calcularHRDv1(List<Evento> eventos) {
		double dRet = 0;

		final String[] tipoEO1 = { "DCO", "RDP" };
		final String[] tipoEO2 = { "DPR", "DUR", "DPA", "DEM", "DAU", "DAP" };
		final String[] tipoOR2 = { "GIS", "GIC", "GIM", "GVO", "GMP", "GMT" };
		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if (Arrays.asList(tipoEO1).contains(evento.getEo()) || (Arrays.asList(tipoEO2).contains(evento.getEo())
					&& Arrays.asList(tipoOR2).contains(evento.getOr()))) {
				Evento proxEvento = eventos.get(i + 1);
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}
		}
		return dRet;
	}

	/*
	 * Parâmetro Horas de Reserva Desligada – HRD: É o cálculo do número de
	 * horas de em que a Unidade Geradora ou Interligação Internacional
	 * permaneceu desligada em conveniência operativa ou reserva de prontidão,
	 * no mês de apuração. Também são contabilizadas as horas de
	 * indisponibilidades de responsabilidade do empreendimento, por serem
	 * desconsideráveis para efeito de cálculo das taxas TEIFa e TEIP. Para esse
	 * algoritmo, são contabilizadas duas parcelas:
	 * 
	 * 
	 * Regras Válidas a partir de 10/2014 - Cálculo das Parcelas: 1ª Parcela:
	 * Horas em que a Unidade Geradora ou Interligação Internacional permaneceu
	 * com os Estados Operativos “DCO” e “RDP”, ou seja, nº de horas
	 * equivalentes em reserva desligada do equipamento somado ao nº de horas de
	 * reserva desligada sem restrição de potência, no mês de apuração;
	 * 
	 * 2ª Parcela: Horas em que a Unidade Geradora ou Interligação Internacional
	 * permaneceu com os Estados Operativos “DPR”, “DPA”, “DUR”, “DEM”, “DAU” e
	 * “DAP”, e Origem classificada como de indisponibilidades de
	 * responsabilidade do Empreendimento, mas desconsideradas nas taxas TEIFa e
	 * TEIP (*), exceto a Origem “GCI”.
	 * 
	 * ATENÇÂO: É necessário criar todo o histórico das taxas dos últimos 60
	 * meses com base neste novo critério para poder realizar o cálculo.
	 * 
	 * (*) Origens para indisponibilidades de responsabilidade do
	 * Empreendimento, mas desconsideradas nas taxas TEIFa e TEIP: “GCI”, “GIS”,
	 * “GIC”, “GIM”, “GVO”, “GMP” e “GMT”
	 * 
	 */
	private double calcularHRDv2(List<Evento> eventos) {
		double dRet = 0;

		return dRet;

	}

	/*
	 * Parâmetro Horas de Desligamento Forçado – HDF: É o cálculo das horas em
	 * que a Unidade Geradora ou Interligação Internacional permaneceu desligada
	 * em decorrência de um desligamento de emergência ou automático, associado
	 * a uma indisponibilidade de responsabilidade do empreendimento,
	 * identificadas através das classificações das Origens, no mês de apuração.
	 * 
	 * Para esse cálculo, são contabilizadas as horas em que Unidade Geradora ou
	 * Interligação Internacional permaneceu com os Estados Operativos “DEM” ou
	 * “DAU” e classificação de Origem identificada como sendo de
	 * responsabilidade do Empreendimento consideradas nas taxas TEIFa e TEIP.
	 * 
	 * Origens para as indisponibilidades de responsabilidade do Empreendimento
	 * consideradas nas taxas TEIFa e TEIP: “GUM”, “GGE”, “GTR”, “GOT”, “GAC”,
	 * “GAG” e “GCB”
	 * 
	 * Regra Válida a partir de 01/2000
	 * 
	 */
	private double calcularHDF(List<Evento> eventos) {
		double dRet = 0;
		final String[] tipoEO = { "DEM", "DAU" };
		final String[] tipoOR = { "GUM", "GGE", "GTR", "GOT", "GAC", "GAG", "GCB" };
		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if (Arrays.asList(tipoEO).contains(evento.getEo()) && Arrays.asList(tipoOR).contains(evento.getOr())) {
				Evento proxEvento = eventos.get(i + 1);
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}
		}
		return dRet;

	}

	/*
	 * Parâmetro Horas Equivalentes de Desligamento Forçado – HEDF: É o cálculo
	 * das horas em que a unidade opera com potência nominal limitada, associada
	 * a uma condição forçada. É o produto das seguintes parcelas:
	 * 
	 * 1ª Parcela: Número de horas em que a Unidade Geradora ou Interligação
	 * Internacional permaneceu sincronizada ao sistema, operando com uma
	 * limitação de sua potência nominal, associada a uma restrição forçada de
	 * responsabilidade do empreendimento, identificada entre as origens que são
	 * de responsabilidade do Empreendimento consideradas nas taxas TEIFa e
	 * TEIP, no mês de apuração;
	 * 
	 * 2ª Parcela: - Grau percentual da restrição forçada por causa interna.
	 * 
	 * Exemplo: Uma Unidade Geradora Termelétrica, despachada por mérito de
	 * custo, permaneceu sincronizada durante 10h, operando como uma limitação
	 * de 90,0% de sua potência nominal, associada a uma súbita restrição no
	 * fornecimento de combustível. Logo, HEDF = 10 h x 0,90 = 9h.
	 * 
	 * Para esse algoritmo, são consideradas as horas em que a Unidade Geradora
	 * ou Interligação Internacional permaneceu com os estados operativos “LIG”,
	 * “LCC”, “LCI” e “LCS”, condição operativa “RFO” e classificação entre as
	 * origens que são de responsabilidade do Empreendimento consideradas nas
	 * taxas TEIFa e TEIP.
	 * 
	 * Origens para as indisponibilidades de responsabilidade do Empreendimento
	 * de Geração consideradas para efeito de cálculo das taxas TEIFa e TEIP:
	 * “GUM”, “GGE”, “GTR”, “GOT”, “GAC”, “GAG”, “GCB”
	 * 
	 * Regra Válida a partir de 01/2000
	 */
	private double calcularHEDF(List<Evento> eventos) {
		double dRet = 0;

		return dRet;

	}

	/*
	 * Parâmetro Horas Desligada por condições externas – HDCE: É o cálculo das
	 * horas em que a Unidade Geradora ou Interligação Internacional permaneceu
	 * desligada em aproveitamento a causas externas, ou desligado em
	 * decorrência de indisponibilidades não consideradas como de
	 * responsabilidade do empreendimento, identificadas através das
	 * classificações das Origens, no mês de apuração.
	 * 
	 * Para esse cálculo, são contabilizadas as horas em que a Unidade Geradora
	 * ou Interligação Internacional permaneceu com o Estado Operativo “DAP” ou
	 * com os Estados Operativos “DPR”, “DUR”, “DEM”, “DPA” ou “DAU” e
	 * classificação de Origem identificada como não sendo de responsabilidade
	 * do Empreendimento, e Estado Operativo “DCA” com Origem “GCI”.
	 * 
	 * Origens para indisponibilidades que não são de responsabilidade do
	 * Empreendimento desconsideradas para efeito de cálculo das taxas TEIFa e
	 * TEIP: “GHN”, “GHT”, “GHI”, “GHC”, “GRE”, “GRB”, “GOU”, “GOO” e “GHM”
	 * 
	 * Regra Válida a partir de 01/2000
	 * 
	 */
	private double calcularHDCE(List<Evento> eventos) {
		double dRet = 0;
		// final String[] tipoEO = { "DAP" };
		final String[] tipoEO2 = { "DPR", "DUR", "DEM", "DPA", "DAU" };
		final String[] tipoOR2 = { "GUM", "GGE", "GTR", "GOT", "GAC", "GAG", "GCB" };
		// final String[] tipoEO3 = { "DCA" };
		// final String[] tipoOR3 = { "GCI" };

		for (int i = 0; i < eventos.size(); i++) {
			Evento evento = eventos.get(i);

			if ("DAP".equals(evento.getEo())
					|| (Arrays.asList(tipoEO2).contains(evento.getEo())
							&& Arrays.asList(tipoOR2).contains(evento.getOr()))
					|| ("DCA".equals(evento.getEo()) && "GCI".equals(evento.getOr()))) {
				Evento proxEvento = eventos.get(i + 1);
				dRet = dRet + (proxEvento.getDtVerif().getTime() - evento.getDtVerif().getTime());
			}
		}
		return dRet;

	}

}
