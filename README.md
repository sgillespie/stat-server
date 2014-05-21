stat-server
===========

A simple client-server program that returns information about the host

Building and running
====================

1) This project uses the gradle build system (gradle.org).  To build the entire
   project on a unix system, run the command:

        ./gradlew jar

   To build it on a Windows system, run the command:

	gradlew.bat jar

2) Once built, you can run the server or client using the jar file:

	java -jar build/lib/stat-server.jar group3.ServerMain
   or
        java -jar build/lib/stat-server.jar group3.ClientMain

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

 * The "Main" classes are all in the group3 package (ServerMain, ClientMain)
 * Client related code is in the group3.client package
 * Server related code is in the group3.server package
 * Data-type classes are all in the group3.domain classes
   - Client configuration
   - Server configuration
   - Other misc utilities
 * The server and clients are all interfaces
 * The implementations are all called *Impl
