package br.org.ons.bdt.test.data;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class InterligacaoInternacionalGenerator extends Generator {

	private static Logger LOG = LogManager.getLogger(InterligacaoInternacionalGenerator.class);

	public void generate() {
		try {
			String query = 
					"select 'cint' as tipo, rtrim(c.ins_id) as id, 1 as versão, c.id_dpp as idDPP, c.cint_dpp_id as cintDPP,\n" + 
							"c.dtdesativa as dtDesativacao, c.dtentrada as dtImplantacao,\n" + 
							"'' as dtProrrogacaoRenovacaoConcessao, rtrim(c.nomecurto) as nome,\n" + 
							"a.age_id as idAgente, a.nomecurto as nomeAgente, a.ido_age as siglaAgente,\n" + 
							"rtrim(cos.cos_id) as cosr\n" + 
							"from cint c, age a, ins i, cos\n" + 
							"where c.age_id_resp = a.age_id\n" + 
							"and i.cos_id = cos.cos_id\n" + 
							"and c.ins_id = i.ins_id\n" + 
							"and c.dtentrada is not null\n" + 
							"order by c.ins_id;";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = Converter.convertToJSON(rs,true);
			
			
			FileOutputStream fos = new FileOutputStream("/tmp/interligacoes.json");
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

		new InterligacaoInternacionalGenerator().generate();
	}
}
