package group3.client;

public interface StatClient {
	// TODO[sgillespie]: This is a mess! Refactor when possible.
	public void connect();
	public void disconnect();
	public Boolean isConnected();
	
	/**
	 * precondition: the client must be connected to the server
	 * 
	 * @return the response from the server 
	 * @throws IllegalStateException if ther server is not connected
	 */
	public String sendMessage();
}