package br.org.ons.sager.agendamento.config.wxs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 *
 */
@ConfigurationProperties(prefix = "wxs")
public class WXSProperties {

	private String catalogServerAddresses;
	private String objectGridName;

	/**
	 * @return o campo catalogServerAddresses
	 */
	public String getCatalogServerAddresses() {
		return catalogServerAddresses;
	}

	/**
	 * @param catalogServerAddresses
	 *            o campo catalogServerAddresses a ser definido
	 */
	public void setCatalogServerAddresses(String catalogServerAddresses) {
		this.catalogServerAddresses = catalogServerAddresses;
	}

	/**
	 * @return o campo objectGridName
	 */
	public String getObjectGridName() {
		return objectGridName;
	}

	/**
	 * @param objectGridName
	 *            o campo objectGridName a ser definido
	 */
	public void setObjectGridName(String objectGridName) {
		this.objectGridName = objectGridName;
	}

}
