stat-server
===========

A simple client-server program that returns information about the host

Building and running
====================

1) This project uses the gradle build system.  To build the entire
   project on a unix system, run the command:

	./gradlew jar

   To build it on a Windows system, run the command:

	gradlew.bat jar

2) Once build, you can run the servers or client using the jar file:

2a) Run the server

	java -jar build/libs/stat-server.jar group3.DistributedServerMain
or

	java -jar build/libs/stat-server.jar group3.IterativeServerMain

2b) Run the client

	java -jar build/libs/stat-server.jar group3.ClientMain <host-ip>
        
If you do not specify a host-ip, the program will print an error message
and exit.
        
FOR YOUR CONVENIENCE, all java binaries are included in the build directory,
including the packaged jar.

Testing
=======
Once built, you can run the stress tester by using the jar file:

	java -jar build/libs/stat-server.jar group3.StresserMain <host-ip>
	
If you do not specify a host-ip, the program will print an error message
and exit.
	
The stresser will run "light" and then "heavy load" (date and netstat, respectively),
starting with 1 thread, then 5, 10, 15, 20, 25, 30, 35, 40, 45, 50. It will print the
results to standard output for each run.

Documentation
=============
The docs directory contains results from stress testing (stress-test-results.xlsx), including graphs.

Eclipse
=======

In order to set up the project for eclipse development, run the following
command (on unix):

	./gradlew eclipse

or (on windows):

	gradlew.bat eclipse

Then open up eclipse, and import the stat-server project
(File -> Import -> General -> Existing Projects Into Workspace).

Developing
==========

The stat-server code is in src/main/java.
A few notes on the organization of the project

 * The "Main" classes are all in the group3 package (ServerMain, ClientMain, StresserMain)
 * Client related code is in the group3.client package
 * Server related code is in the group3.server package
 * Data-type classes are all in the group3.domain classes
   - Client configuration
   - Server configuration
   - Other misc utilities
