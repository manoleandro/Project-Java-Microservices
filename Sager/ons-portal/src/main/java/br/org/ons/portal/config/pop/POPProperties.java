package br.org.ons.portal.config.pop;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuração da conexão com o POP
 */
@ConfigurationProperties(prefix = "pop")
public class POPProperties {

	/**
	 * URL do portal do POP
	 */
	private String portalUrl;
	/**
	 * URL do serviço do POP
	 */
	private String serviceUrl;
	/**
	 * Dominio do usuário para bind no POP
	 */
	private String domain;
	/**
	 * Nome de usuário para bind no POP
	 */
	private String username;
	/**
	 * Senha do usuário para bind no POP
	 */
	private String password;

	/**
	 * @return o campo portalUrl
	 */
	public String getPortalUrl() {
		return portalUrl;
	}

	/**
	 * @param portalUrl o campo portalUrl a ser definido
	 */
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}

	/**
	 * @return o campo serviceUrl
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * @param serviceUrl o campo serviceUrl a ser definido
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * @return o campo domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain o campo domain a ser definido
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return o campo username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username o campo username a ser definido
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
	 * @param password o campo password a ser definido
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
