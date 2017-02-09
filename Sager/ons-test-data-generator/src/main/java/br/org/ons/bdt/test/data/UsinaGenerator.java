package br.org.ons.bdt.test.data;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class UsinaGenerator extends Generator {

	private static Logger LOG = LogManager.getLogger(UsinaGenerator.class);

	public void generate() {
		try {
			String query = 
					"select distinct \n" + 
					" u.tpusina_id as tipo,\n" + 
					"rtrim(i.ins_id) as id,\n" + 
					"a.nomecurto as agente, \n" + 
					"rtrim(i.cos_id) as cosr,\n" + 
					"rtrim(u.nomecurto) as nome\n" + 
					"from eqp eq, uge ug, usi u, age a ,ins i\n" + 
					"where eq.eqp_id = ug.eqp_id \n" + 
					"and u.ins_id = i.ins_id\n" + 
					"and ug.usi_id = u.usi_id \n" + 
					"and ug.usi_id in ('BAUSB', 'RJUSCP', 'PRGBM', 'RSUTEC', 'RJUTME', 'ROUHJI', 'AMBA', 'APSAJ', 'CEUTFO', 'CEUTPC', 'CEUTPD', 'RSUTCH', 'ALUXG', 'RSUPME', 'ROTNE1') \n" + 
					"and eq.age_id_prop = a.age_id";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = Converter.convertToJSON(rs,true);
			
			
			FileOutputStream fos = new FileOutputStream("/tmp/usinas.json");
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

		new UsinaGenerator().generate();
	}
}
