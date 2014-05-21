package group3;

import group3.client.StatClient;
import group3.client.StatClientImpl;
import group3.domain.ClientConfig;

public class ClientMain {
	public static void main(String[] args) {
		ClientConfig config = new ClientConfig("127.0.0.1", 9000);	// TODO: Get this from args ^^^
		StatClient client = new StatClientImpl(config);
		client.connect();
		
		// TODO: Open menu and do stuff!
		
		client.disconnect();
	}
}
