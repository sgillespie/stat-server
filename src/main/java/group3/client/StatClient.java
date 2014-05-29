package group3.client;

import java.io.IOException;

public interface StatClient {
	// TODO[sgillespie]: This is a mess! Refactor when possible.
	public void connect() throws IOException;
	public void disconnect() throws IOException;
	public Boolean isConnected() throws IOException;
	
	/**
	 * precondition: the client must be connected to the server
	 * 
	 * @return the response from the server 
	 * @throws IllegalStateException if the server is not connected
	 */
	public String sendMessage(String msg) throws IOException;
}
