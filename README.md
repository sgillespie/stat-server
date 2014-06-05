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

2) Once built, you can run the server or client using the jar file:

	java -jar build/lib/stat-server.jar group3.ServerMain
   or
        java -jar build/lib/stat-server.jar group3.ClientMain <host-ip>
        
   If you do not specify a host-ip, the program will print an error message
   and exit.
        
Testing
=======
Once built, you can run the stress tester by using the jar file:

	java -jar build/lib/stat-server.jar group3.StresserMain <host-ip>
	
If you do not specify a host-ip, the program will print an error message
and exit.
	
The stresser will run "light" and then "heavy load" (date and netstat, respectively),
starting with 1 thread, then 5, 10, 15, 20, 25, 30, 35, 40, 45, 50. It will print the
results to standard output for each run.

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

The stat-server code is in src/main/java. Test directories exist also,
in case you want to use JUnit to write tests (src/test/java).  

A few notes on the organization of the project

 * The "Main" classes are all in the group3 package (ServerMain, ClientMain, StresserMain)
 * Client related code is in the group3.client package
 * Server related code is in the group3.server package
 * Data-type classes are all in the group3.domain classes
   - Client configuration
   - Server configuration
   - Other misc utilities
