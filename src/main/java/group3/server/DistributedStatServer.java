package group3.server;

import group3.domain.ServerCommand;
import group3.domain.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

	public static class DistributedSocketRunner implements Runnable {
		Socket clientSocket;
		
		public DistributedSocketRunner(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			try {
				handleSocket();
			} catch (IOException e) {
				// Print a warning and move on
				System.err.println("Warning: can't handle request" + e.getMessage());
			}
		}
		
		private void handleSocket() throws IOException {
			try {
				System.out.println(String.format(
		    			"Accepting a new connection from: %s", clientSocket.getInetAddress().getHostAddress()));
		    	
		    	// Open read/write streams from the client socket
		    	PrintWriter out =
			            new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			            new InputStreamReader(clientSocket.getInputStream()));
			    
			    // Read the input from the client
			    String clientInput;
			    while ((clientInput = in.readLine()) != null) {
			    	System.out.println("Received message from " + clientSocket.getInetAddress().getHostAddress() + ": " + clientInput);
			    	
			    	// Lookup the command to run
				    ServerCommand cmd = null;
				    if ("uptime".equals(clientInput)) {
				    	cmd = ServerCommand.UPTIME;
				    } else if ("date".equals(clientInput)) {
				    	cmd = ServerCommand.DATE_TIME;
				    } else if ("memory".equals(clientInput)) {
				    	cmd = ServerCommand.MEMORY_USAGE;
				    } else if ("netstat".equals(clientInput)) {
				    	cmd = ServerCommand.NETSTAT;
				    } else if ("users".equals(clientInput)) {
				    	cmd = ServerCommand.CURRENT_USERS;
				    } else if ("procs".equals(clientInput)) {
				    	cmd = ServerCommand.RUNNING_PROCS;
				    }
				    
				    // Guard against NullPointerExceptions
				    if (cmd == null) {
				    	System.err.println("Received a null request from " + clientSocket.getInetAddress().getHostAddress() + "!");
				    	clientSocket.close();
				    	break;
				    }
				    
				    try {
				    	// Run the command
				    	Process proc = Runtime.getRuntime().exec(cmd.getCommand());
				    	int exitCode = proc.waitFor();
				    	
				    	if (exitCode == 0) {
				    		// Send the results to the client
				    		BufferedReader reader = 
				    		         new BufferedReader(new InputStreamReader(proc.getInputStream()));
				    		
				    		String line = null;
				    		while ((line = reader.readLine()) != null) {
				    			out.println(line);
				    		}
				    		out.println();
				    	} else {
				    		System.err.println("Error running command: " + cmd.getCommand() + "!");
				    	}
				    } catch (InterruptedException e) {
				    	throw new IOException(e);
				    }
			    }
			} finally {
				if (clientSocket != null) clientSocket.close();
			}
		}

		public Socket getClientSocket() {
			return clientSocket;
		}
	}
}
