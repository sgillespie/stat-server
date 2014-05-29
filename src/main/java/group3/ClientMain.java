package group3;

import group3.client.StatClient;
import group3.client.StatClientImpl;
import group3.domain.ClientConfig;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain {
	
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			usage();
			System.exit(1);
		}
		
		String ip = args[0];
		
		ClientConfig config = new ClientConfig(ip, 9000);
		StatClient client = new StatClientImpl(config);
		try {
			serverLoop(client);
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
	
	private static void usage() {
		System.err.println("Error: invalid usage!");
		System.err.println("Usage: group3.ClientMain <host>");
	}
	
	private static void serverLoop(StatClient client) throws IOException {
		while (true) {
			System.out.print(prompt());
			String userInput = System.console().readLine();
			if (!userInput.matches("[1-7]")) {
				System.err.println("Incorrect input! Enter a number between 1-7");
				continue;
			}
			
			client.connect();
			
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
			
			String serverResponse = client.sendMessage(message);
			System.out.println("Sent message: " + message);
			System.out.println("Got response: " + serverResponse);
			client.disconnect();
		}
	}
}
