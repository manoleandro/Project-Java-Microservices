package br.org.ons.bdt.test.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe pai de todos os geradores
 *
 */
public abstract class Generator {

	private static Logger LOG = LogManager.getLogger(Generator.class);
	protected Connection conn = null;
	private String server = "10.25.231.12";
	// String server = "172.20.249.41";
	private String port = "5200";
	private String user = "scalaapp";
	private String passwd = "teste123";
	private String database = "bd_tecndes_samug2_new";
	private String ifxserver = "ifx_des_tcp_01";
	private String url = "jdbc:informix-sqli://" + server + ":" + port + "/" + database + ":INFORMIXSERVER=" + ifxserver
			+ ";user=" + user + ";password=" + passwd;

	public Generator() {

	}

	public Connection getConnection() {

		try {
			if (this.conn == null || conn.isClosed()) {
				LOG.info("String de conexão: " + url);

				Class.forName("com.informix.jdbc.IfxDriver");
				conn = DriverManager.getConnection(url);
				LOG.info("Conexão bem sucedida");
			}
		} catch (ClassNotFoundException e) {
			LOG.error("Erro na configuração do JDBC", e);
		} catch (SQLException e) {
			LOG.error("Erro na conexão", e);
		}
		
		return this.conn;

	}

	public abstract void generate();
}
