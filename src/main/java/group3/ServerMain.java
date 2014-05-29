package group3;

import group3.domain.ServerConfig;
import group3.server.StatServer;
import group3.server.StatServerImpl;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		ServerConfig config = new ServerConfig("127.0.0.1", 9000);	// TODO: get this from args ^^^
		StatServer server = new StatServerImpl(config);
		server.start();
	}
}
