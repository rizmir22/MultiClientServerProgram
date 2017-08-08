# MultiClientServerProgram

This is a Multi Client Server Program.

* Multi Client Can be connect to the server using this program
* In this Server program has a thread pool. maximum number of client to connect to the server is defined by this thread pool Size.
  in this code thread pool Size is 10.
* Exceeding client connections are added to the Queue and it will be retrieved when one connected client is diconnected



* To Run the program you should run the Server first using mvn clean install.
* Then You need to Run the Client same as the above.
* make sure the Sevrver host is correct and Port which is going to connect is same as Server listening Port.
* you will receive a massage that which port you are connected and welcome message.
* if you want to disconnect then type as quit client sever connection will be disconnected
