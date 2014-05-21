package group3.server;

import group3.domain.ServerCommand;


public interface StatService {
	/**
	 * Runs the requested command and returns the result
	 * 
	 * @param command the command to run
	 * @return the standard output of the command result
	 */
	public String getStat(ServerCommand command);
}
