package group3.client;

import group3.domain.ClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StatClient {
	private ClientConfig config;
	Socket clientSocket = null;

	public StatClient(ClientConfig config) {
		this.config = config;
	}

	public void connect() throws IOException {
		clientSocket = new Socket(config.getRemoteHost(), config.getRemotePort());
		// Remove this (I think?)
		// System.out.println(String.format("Connected to %s:%s", config.getRemoteHost(), config.getRemotePort()));
	}

	public void disconnect() throws IOException {
		clientSocket.close();
		// Remove this (I think?)
		// System.out.println(String.format("Disconnected from %s:%s", config.getRemoteHost(), config.getRemotePort()));
	}

	public Boolean isConnected() {
		return clientSocket != null && clientSocket.isConnected();
	}

	public String sendMessage(String msg) throws IOException {
		if (!isConnected()) {
			throw new IllegalStateException(String.format("Error: Could not connect to %s:%s", config.getRemoteHost(), config.getRemotePort()));
		}
		
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		out.println(msg);
		out.flush();
		
    	String line = null;
    	StringBuffer output = new StringBuffer();
    	while ((line = in.readLine()) != null) {
    		if (line.isEmpty()) break;
    		output.append(line + "\n");
    	}
    	
    	return output.toString();
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}
}
