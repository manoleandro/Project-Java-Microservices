package br.org.ons.bdt.test.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class TaxaDeReferenciaGenerator extends Generator{
	
	private static Logger LOG = LogManager.getLogger(TaxaDeReferenciaGenerator.class);
	
	public void generate() {
		try {
			String query = "select c.nomecurto as cos, u.usi_id as id, rtrim(u.nomecurto) as inst, TO_DATE(ano_ref || '-' || mes_ref, '%Y-%m' ) as dtRef, val_teifref as teif, val_ipref as ip from tb_indisprefusi i, usi u, ins, cos c where u.usi_id = i.usi_id and ins.cos_id = c.cos_id and u.ins_id = ins.ins_id and u.usi_id in ('BAUSB', 'RJUSCP', 'PRGBM', 'RSUTEC', 'RJUTME', 'ROUHJI', 'AMBA', 'APSAJ', 'CEUTFO', 'CEUTPC', 'CEUTPD', 'RSUTCH', 'ALUXG', 'RSUPME', 'ROTNE1')order by u.nomecurto, ano_ref, mes_ref";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = Converter.convertToJSON(rs,true);
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

		new TaxaDeReferenciaGenerator().generate();
	}
}
