package group3;

import group3.domain.ServerConfig;
import group3.server.StatServer;

/**
 * Main class for the server
 */
public class ServerMain {
	public static void main(String[] args) throws Exception {
		// Create server config
		ServerConfig config = new ServerConfig("127.0.0.1", 9000);
		StatServer server = new StatServer(config);
		
		// Start the server
		server.start();
	}
}
