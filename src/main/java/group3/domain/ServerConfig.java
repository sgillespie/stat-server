package group3.domain;

public class ServerConfig {
	private String host = "127.0.0.1";
	private Integer port = 9000;
	
	public ServerConfig() {
	}
	
	public ServerConfig(String host, Integer port) {
		setHost(host);
		setPort(port);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
