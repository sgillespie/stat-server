package group3.server;

import group3.domain.ServerCommand;
import group3.domain.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class StatServer {
	protected ServerConfig config;
	
	public StatServer(ServerConfig config) {
		this.config = config;
	}
	
	public abstract void start() throws IOException;
	public abstract void stop() throws IOException;

	public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}
	
	protected void handleSocket(Socket clientSocket) throws IOException {
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
			    		
			    		reader.close();
			    		proc.getOutputStream().close();
			    		proc.getErrorStream().close();
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
}
