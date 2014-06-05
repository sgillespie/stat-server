package group3;

import group3.client.StatClient;
import group3.domain.ClientConfig;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Entry point for Client
 */
public class ClientMain {
	public static void main(String[] args) throws IOException {
		// Verify args
		if (args.length < 1) {
			usage();
			System.exit(1);
		}
		
		String ip = args[0];
		
		ClientConfig config = new ClientConfig(ip, 9000);
		StatClient client = new StatClient(config);
		try {
			// Run the menu loop
			menuLoop(client);
		} catch (UnknownHostException e) {
			System.err.println("Invalid host: " + e.getMessage() + "!");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error connecting to server: " + e.getMessage());
			System.exit(1);
		} catch (IllegalStateException e) {
			System.err.println("Error connecting to server: " + e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Returns the menu as a string
	 */
	private static String prompt() {
		StringBuffer prompt = new StringBuffer();
		
		prompt.append("1.	Host Current Date and Time\n");
		prompt.append("2.	Host Uptime\n");
		prompt.append("3.	Host Memory Use\n");
		prompt.append("4.	Host Netstat\n");
		prompt.append("5.	Host Current Users\n");
		prompt.append("6.	Host Running Processes\n");
		prompt.append("7.	Quit!\n");
		prompt.append("\nEnter a number: ");
		return prompt.toString();
	}
	
	/**
	 * Prints usage information 
	 */
	private static void usage() {
		System.err.println("Error: invalid usage!");
		System.err.println("Usage: group3.ClientMain <host>");
	}
	
	/**
	 * Print out the menu and take input from user
	 */
	private static void menuLoop(StatClient client) throws IOException {
		while (true) {
			// Print the menu
			System.out.print(prompt());
			
			// Get the input
			String userInput = System.console().readLine();
			if (!userInput.matches("[1-7]")) {
				System.err.println("Incorrect input! Enter a number between 1-7");
				continue;
			}
			
			// Connect to the client
			client.connect();
			
			// Determine the message to send
			Integer entry = Integer.parseInt(userInput);
			String message = "";
			switch(entry) {
				case 1:
					message = "date";
					break;
				case 2:
					message = "uptime";
					break;
				case 3:
					message = "memory";
					break;
				case 4:
					message = "netstat";
					break;
				case 5:
					message = "users";
					break;
				case 6:
					message = "procs";
					break;
				case 7:
					System.out.println("Goodbye!");
					client.disconnect();
					System.exit(0);
					break;
				default: // This should never happen!
					break;
			}
			
			// Send the message to the server
			String serverResponse = client.sendMessage(message);
			
			// Print the results
			System.out.println("Sent message: " + message);
			System.out.println("Got response: " + serverResponse);
			
			// Disconnect from the server
			client.disconnect();
		}
	}
}
