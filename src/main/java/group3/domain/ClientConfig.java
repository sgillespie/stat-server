package group3.domain;

public class ClientConfig {
	private String remoteHost;
	private Integer remotePort;
	
	public ClientConfig(String remoteHost) {
		this(remoteHost, null);
	}
	
	public ClientConfig(String remoteHost, Integer remotePort) {
		setRemoteHost(remoteHost);
		setRemotePort(remotePort);
	}
	
	public String getRemoteHost() {
		return remoteHost;
	}
	
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	
	public Integer getRemotePort() {
		return remotePort;
	}
	
	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}
}
