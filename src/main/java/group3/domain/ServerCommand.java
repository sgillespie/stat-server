package group3.domain;

public enum ServerCommand {
	DATE_TIME("date"),
	UPTIME("uptime"),
	MEMORY_USAGE("???"),	// TODO[sgillespie]: look this up
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
