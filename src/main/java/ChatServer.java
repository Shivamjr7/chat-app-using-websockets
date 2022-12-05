import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {


    private static final int PORT= 5000;

    Set<UserThread> userThreads   = new HashSet<>();
    Set<String> users = new HashSet<>();

    public static void main(String[] args) {
        // starting the server

        ChatServer server = new ChatServer();
        server.startServer();

    }

    public void startServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat application started .. listening on port 5000");

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New User Joined ....");

                UserThread userThread = new UserThread(clientSocket,this);
                userThreads.add(userThread);
                userThread.start();
            }
        }
        catch(Exception e )
        {
            e.printStackTrace();
        }

    }

    void addUserName(String userName) {
        users.add(userName);
    }
    void removeUser(String userName, UserThread aUser) {
        boolean removed = users.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.users;
    }

    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }


}
