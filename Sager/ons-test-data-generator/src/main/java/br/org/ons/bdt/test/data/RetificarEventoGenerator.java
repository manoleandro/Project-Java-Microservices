package br.org.ons.bdt.test.data;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class RetificarEventoGenerator extends Generator {

	private static Logger LOG = LogManager.getLogger(RetificarEventoGenerator.class);

	public void generate() {
		try {
			this.conn = getConnection();
			String query = "select " + 
					"d.numons as numons," + 
					"1 as ver, " +
					//"rtrim(u.usi_id) as usiid," +
					"trim(e.ido_eqp) as eqp," +
					//"c.nomecurto as cos, \n" + 
					//"u.tpusina_id as tipo, \n" + 
					//"a.nomecurto as agente,\n" + 
					//"u.nomecurto as instalação, \n" + 
					//"rtrim(ug.nomelongo) as uge,\n" +
					"'' as obs," + 
					"d.dtini_verif as datahora," + 
					"d.tpestoper_id as eo," + 
					"d.panocr_id as co," + 
					"d.ogresdes_id as or," +
					"d.age_id as age," +
					"cast(d.valdisp as int) as disp," +
					"case when (d.coment is null or LENGTH(d.coment) = 0) then 'N' else 'S' end as temComONS," +
					"case when (d.cmn_agente is null or LENGTH(d.cmn_agente) = 0) then 'N' else 'S' end as temComAgente," +
					"d.coment as com_ons," +
					"d.cmn_agente as com_age," +
					//"d.din_disponevento as dtCriacao, \n" + 
					//"d.dat_ultimaconsolidacao as dtUltimaConsolidacao,\n" + 
					//"0 as status, \n" + 
					//"'' as statusCorrente, \n" + 
					"d.flcmop as tipo_op" + 
					" from desger d, eqp e, uge ug, usi u, age a, ins\n" + 
					" where d.uge_id = ug.uge_id\n" +
					" and e.eqp_id = ug.eqp_id" +
					" and e.tpeqp_id = 'UGE'" +
					//" and ins.cos_id = c.cos_id\n" + 
					" and u.ins_id = ins.ins_id\n" + 
					" and ug.usi_id = u.usi_id\n" + 
					" and ug.age_id_prop = a.age_id\n" + 
					" and d.dtini_verif >= date('2001-01-01')\n" + 
					" and ug.usi_id in ('ALUXG')\n" + 
					" and d.flg_eventoconsistido <> 3" + // remove eventos espelho
					" order by ug.eqp_id, d.dtini_verif";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = Converter.convertToJSON(rs,false);

			FileOutputStream fos = new FileOutputStream("/tmp/evento.json");
			fos.write(result.toString().getBytes());
			fos.flush();
			fos.close();

			//LOG.info(result);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					LOG.info("Conexão fechada.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {

		new RetificarEventoGenerator().generate();
	}
}
