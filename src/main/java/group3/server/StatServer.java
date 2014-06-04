package group3.server;

import group3.domain.ServerCommand;
import group3.domain.ServerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class StatServer {
	private ServerConfig config;
	private ServerSocket serverSocket;
	
	public StatServer(ServerConfig config) {
		this.config = config;
	}

	public void start() throws IOException {
		System.out.println(String.format("Starting stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		try {
			serverSocket = new ServerSocket(config.getPort());
		    
		    while (true) {
		    	Socket clientSocket = null;
		    	
		    	try {
			    	clientSocket = serverSocket.accept();
			    	System.out.println(String.format(
			    			"Accepting a new connection from: %s", clientSocket.getInetAddress().getHostAddress()));
			    	
			    	PrintWriter out =
				            new PrintWriter(clientSocket.getOutputStream(), true);
				    BufferedReader in = new BufferedReader(
				            new InputStreamReader(clientSocket.getInputStream()));
				    
				    String clientInput;
				    while ((clientInput = in.readLine()) != null) {
				    	System.out.println("Received message from " + clientSocket.getInetAddress().getHostAddress() + ": " + clientInput);
				    	
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
					    } else if ("quit".equals(clientInput)) {
					    	cmd = ServerCommand.QUIT;
					    }
					    
					    if (cmd == ServerCommand.QUIT) {
					    	System.out.println("Disconnecting client " + clientSocket.getInetAddress().getHostAddress());
					    	clientSocket.close();
					    	break;
					    }
					    
					    if (cmd == null) {
					    	System.err.println("Received a null request from " + clientSocket.getInetAddress().getHostAddress() + "!");
					    	clientSocket.close();
					    	break;
					    }
					    
					    try {
					    	Process proc = Runtime.getRuntime().exec(cmd.getCommand());
					    	int exitCode = proc.waitFor();
					    	
					    	if (exitCode == 0) {
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
		} finally {
			stop();
		}
	}

	public void stop() throws IOException {
		System.out.println(String.format("Stopping stat server @ %s:%s", config.getHost(), 
				config.getPort()));
		
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}
}
