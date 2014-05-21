package group3.server;

import group3.domain.ServerConfig;

public class StatServerImpl implements StatServer {
	private ServerConfig config;
	
	public StatServerImpl(ServerConfig config) {
		this.config = config;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}
}
