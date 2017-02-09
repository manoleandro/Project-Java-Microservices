package br.org.ons.sager.instalacao.config.odm;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades de configuração da conexão com o IBM ODM
 */
@ConfigurationProperties(prefix = "odm")
public class ODMProperties {

	private String host;
	private Integer port;
	private String username;
	private String password;

	/**
	 * @return o campo host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            o campo host a ser definido
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return o campo port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port
	 *            o campo port a ser definido
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return o campo username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            o campo username a ser definido
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return o campo password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            o campo password a ser definido
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
