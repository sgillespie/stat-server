package group3.domain;

/**
 * Enum representation of commands to run
 */
public enum ServerCommand {
	DATE_TIME("date"),
	UPTIME("uptime"),
	NETSTAT("netstat -an"),
	MEMORY_USAGE("free"),
	CURRENT_USERS("who"),
	RUNNING_PROCS("ps aux"),
	;
	
	private final String systemCommand;
	
	private ServerCommand(String command) {
		this.systemCommand = command;
	}
	
	public String getCommand() {
		return systemCommand;
	}
}
