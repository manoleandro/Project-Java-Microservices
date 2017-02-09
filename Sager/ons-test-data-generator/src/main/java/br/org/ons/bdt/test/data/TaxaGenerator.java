package br.org.ons.bdt.test.data;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class TaxaGenerator extends Generator {

	private static Logger LOG = LogManager.getLogger(TaxaGenerator.class);

	public void generate() {
		try {
			String query = "select TO_DATE(t.ano_apur || '-' || t.mes_apur, '%Y-%m' ) as dtRef , " +
					"rtrim(u.usi_id) as usiid," + 
					"rtrim(u.nomecurto) as usi," +
					"t.val_teipmes as teipmes, " +
					// "t.val_teifmes as teifmes,"+
					"t.val_teifames as teifames," + 
					"t.val_teipmesfc as teipmesfc," + 
					"t.val_teifamesfc as teifamesfc," +
					// "t.val_numteipmes as num_teip, "+
					// "t.val_denteipmes as den_teip,"+
					// "t.val_numteifames as num_teifa,"+
					// "t.val_denteifames as den_teifa,"+
					// "t.val_numteipmesfc as num_teip_fc,"+
					// "t.val_denteipmesfc as den_teip_fc,"+
					// "t.val_numteifamesfc as num_teifa_fc, "+
					// "t.val_denteifamesfc as den_teifa_fc,"+
					"p.val_teipopermes as teipopermes, " + 
					"p.val_teifopermes as teifopermes,"	+ 
					"t.val_teipacum as teip, " +
					// "t.val_teifacum as teif,"+
					"t.val_teifaacum as teifa," + 
					"t.val_teipacumfc as teipfc," + 
					"t.val_teifaacumfc as teifafc,"
					+ "p.val_teipoperacum as teipoper, " + "p.val_teifoperacum teifoper"
					+ " from tb_mrausimes t, tb_pmousimes p, usi u" + " where u.usi_id = t.usi_id"
					+ " and p.usi_id = u.usi_id"
					+ " and u.usi_id in ('BAUSB', 'RJUSCP', 'PRGBM', 'RSUTEC', 'RJUTME', 'ROUHJI', 'AMBA', 'APSAJ', 'CEUTFO', 'CEUTPC', 'CEUTPD', 'RSUTCH', 'ALUXG', 'RSUPME', 'ROTNE1')"
					+ " and t.ano_apur > 2000" + " and t.ano_apur = p.ano_apur" + " and t.mes_apur = p.mes_apur"
					+ " order by t.ano_apur, t.mes_apur, u.nomecurto";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = Converter.convertToJSON(rs,true);

			FileOutputStream fos = new FileOutputStream("/tmp/taxa.json");
			fos.write(result.toString().getBytes());
			fos.flush();
			fos.close();

			LOG.info(result);

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

		new TaxaGenerator().generate();
	}
}
