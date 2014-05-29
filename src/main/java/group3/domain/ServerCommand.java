package group3.domain;

public enum ServerCommand {
	DATE_TIME("date"),
	UPTIME("uptime"),
	NETSTAT("netstat -an"),
	MEMORY_USAGE("free"),
	CURRENT_USERS("who"),
	RUNNING_PROCS("ps aux"),
	QUIT(null)
	;
	
	private final String systemCommand;
	
	private ServerCommand(String command) {
		this.systemCommand = command;
	}
	
	public String getCommand() {
		return systemCommand;
	}
}
