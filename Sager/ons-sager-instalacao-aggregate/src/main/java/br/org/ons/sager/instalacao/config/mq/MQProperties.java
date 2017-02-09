package br.org.ons.sager.instalacao.config.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades de configuração da conexão com o IBM WebSphere MQ
 */
@ConfigurationProperties(prefix = "mq")
public class MQProperties {

	/**
	 * Nome da queue manager
	 */
	private String queueManager;
	
	private String host;
	private int port;
	
	/**
	 * Nome do canal do tipo Server-Connection
	 */
	private String channel;
	
	/**
	 * Nome de um usuário com permissão de conexão ao canal
	 */
	private String username;
	
	/**
	 * Senha do usuário
	 */
	private String password;

	/**
	 * @return o campo queueManager
	 */
	public String getQueueManager() {
		return queueManager;
	}

	/**
	 * @param queueManager
	 *            o campo queueManager a ser definido
	 */
	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

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
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            o campo port a ser definido
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return o campo channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            o campo channel a ser definido
	 */
	public void setChannel(String channel) {
		this.channel = channel;
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