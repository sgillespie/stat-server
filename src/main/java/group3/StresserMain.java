package group3;

import group3.client.StatStressTester;
import group3.client.StatStressTester.Load;
import group3.domain.ClientConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StresserMain {
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 1) {
			usage();
			System.exit(1);
		}
		
		String host = args[0];
		Integer port = 9000;
		List<Integer> numberThreadsList = Arrays.asList(1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50);
		
		ClientConfig config = new ClientConfig(host, port);
		for (Load load : Load.values()) {
			for (Integer numberThreads : numberThreadsList) {
				// Run stresser in specified number of threads
				List<StatStressTester> stressers = new ArrayList<StatStressTester>();
				List<Thread>  threads= new ArrayList<Thread>();
				for (Integer i = 0; i < numberThreads; i++) {
					StatStressTester tester = new StatStressTester(config, load);
					stressers.add(tester);
					
					Thread thread = new Thread(tester);
					threads.add(thread);
					thread.start();
				}
				
				// Wait for the threads to complete
				for (Thread thread : threads) {
					thread.join();
				}
				
				// Calculate mean
				List<Long> responseTimes = new ArrayList<Long>();
				for (StatStressTester stresser : stressers) {
					responseTimes.addAll(stresser.getResponseTimes());
				}
				
				System.out.println(String.format("%s mean response time (%s threads): %s", 
						load.toString().toLowerCase(), numberThreads, StatStressTester.mean(responseTimes)));
			}
		}
	}
	
	private static void usage() {
		System.err.println("Error: invalid usage!");
		System.err.println("Usage: group3.ClientMain <host>");
	}
}
