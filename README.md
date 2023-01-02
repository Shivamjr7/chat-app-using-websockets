# chat-app-using-websockets
This is a simple group chat application using Server Client model in Java.

* ServerSocket creates a socket at server end which listens on a port 
* When Server accepts a client connect it creates a socket for the client and new thread where it will communicate to the client
* So multiple clients : multiple threads 
* When one of the clients send a message , it broadcasts the message to all threads by writing to all clitn sockets 
