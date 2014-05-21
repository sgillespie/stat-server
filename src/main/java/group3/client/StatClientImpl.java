package group3.client;

import group3.domain.ClientConfig;

public class StatClientImpl implements StatClient {
	private ClientConfig config;

	public StatClientImpl(ClientConfig config) {
		this.config = config;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean isConnected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}
}
