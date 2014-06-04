package group3.client;

import group3.domain.ClientConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatStressTester implements Runnable {
	private static final Integer MAX_ITERATIONS = 100;
	private ClientConfig config;
	private Load load;
	private List<Long> responseTimes = null;
	
	public static enum Load {
		LIGHT("date"), 
		HEAVY("netstat"),
		;
		
		private final String command;
		
		private Load(String command) {
			this.command = command;
		}
		
		public String getCommand() {
			return command;
		}
	}
	
	public StatStressTester(ClientConfig config, Load load) {
		this.config = config;
		this.load = load;
	}

	public void run() {
		try {
			test();
		} catch (IOException e) {
			System.out.println("Error running stress test!");
			System.err.print(e);
		}
	}
	
	public void test() throws IOException {
		StatClient client = new StatClient(config);
		List<Long> responseTimes = new ArrayList<Long>();
		
		for (Integer i = 0; i < MAX_ITERATIONS; i++) {
			Long startTime = System.currentTimeMillis();
			
			client.connect();
			client.sendMessage(load.getCommand());
			client.disconnect();
			
			Long endTime = System.currentTimeMillis();
			Long responseTime = endTime - startTime;
			
			responseTimes.add(responseTime);
		}
		
		setResponseTimes(responseTimes);
	}
	
	public static Double mean(List<? extends Number> ls) {
		Double sum = 0.0;
		
		for (Number x : ls) {
			sum += x.doubleValue();
		}
		
		return sum / (ls.size());
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}

	public List<Long> getResponseTimes() {
		return responseTimes;
	}

	public synchronized void setResponseTimes(List<Long> responseTimes) {
		this.responseTimes = responseTimes;
	}
}
