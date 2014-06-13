package group3.server;

import group3.domain.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server class - serves connections (iterative)
 */
public class IterativeStatServer extends StatServer {
	private ServerSocket serverSocket;
	
	public IterativeStatServer(ServerConfig config) {
		super(config);
	}

	/* (non-Javadoc)
	 * @see group3.server.StatServer#start()
	 */
	@Override
	public void start() throws IOException {
		System.out.println(String.format("Starting stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		try {
			// Create the server socket
			serverSocket = new ServerSocket(config.getPort());
		    
		    while (true) {
		    	Socket clientSocket = null;
		    	
		    	try {
		    		// Accept a client connection
			    	clientSocket = serverSocket.accept();
			    	handleSocket(clientSocket);

		    	} finally {
		    		// Make sure the socket gets closed
		    		if (clientSocket != null) clientSocket.close();
		    	}
		    }
		} finally {
			stop();
		}
	}

	/* (non-Javadoc)
	 * @see group3.server.StatServer#stop()
	 */
	@Override
	public void stop() throws IOException {
		System.out.println(String.format("Stopping stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		// Close the socket
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
}
