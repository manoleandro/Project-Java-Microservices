package br.org.ons.exemplo.config.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mq")
public class MQProperties {
    private String queueManager;
    private String host;
    private int port;
    private String channel;
    private String incomingQueue;
    private String outgoingQueue;
    private String username;
    private String password;

    public String getQueueManager() {
        return queueManager;
    }

    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIncomingQueue() {
        return incomingQueue;
    }

    public void setIncomingQueue(String incomingQueue) {
        this.incomingQueue = incomingQueue;
    }

    public String getOutgoingQueue() {
        return outgoingQueue;
    }

    public void setOutgoingQueue(String outgoingQueue) {
        this.outgoingQueue = outgoingQueue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}