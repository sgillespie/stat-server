package group3.server;

import group3.domain.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class StatServer {
	protected ServerConfig config;
	
	public StatServer(ServerConfig config) {
		this.config = config;
	}
	
	public abstract void start() throws IOException;
	public abstract void stop() throws IOException;

	/*
	 * Getters and setters
	 */
	public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}
}