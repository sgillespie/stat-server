package group3;

import group3.domain.ServerConfig;
import group3.server.StatServer;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		ServerConfig config = new ServerConfig("127.0.0.1", 9000);	// TODO: get this from args ^^^
		StatServer server = new StatServer(config);
		server.start();
	}
}
