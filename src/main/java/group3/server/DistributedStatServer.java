package group3.server;

import group3.domain.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DistributedStatServer extends StatServer {
	private ServerSocket serverSocket;
	
	public DistributedStatServer(ServerConfig config) {
		super(config);
	}
		
	@Override
	public void start() throws IOException {
		System.out.println(String.format("Starting stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		try {
			// Create the server socket
			serverSocket = new ServerSocket(config.getPort());
		    
		    while (true) {
		    	Socket clientSocket = null;
		    	
		    	// Accept a client connection
		    	clientSocket = serverSocket.accept();
		    	new Thread(new DistributedSocketRunner(clientSocket)).start();
		    }
		} finally {
			stop();
		}
	}

	@Override
	public void stop() throws IOException {
		System.out.println(String.format("Stopping stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		// Close the socket
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
	
	public class DistributedSocketRunner implements Runnable {
		Socket clientSocket;
		
		public DistributedSocketRunner(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			try {
				handleSocket(clientSocket);
			} catch (IOException e) {
				// Print a warning and move on
				System.err.println("Warning: can't handle request" + e.getMessage());
			}
		}

		public Socket getClientSocket() {
			return clientSocket;
		}
	}
}
