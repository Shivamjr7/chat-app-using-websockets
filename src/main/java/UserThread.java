import java.io.*;
import java.net.Socket;
import java.util.stream.IntStream;

public class UserThread  extends   Thread{

    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    UserThread(Socket socket , ChatServer server)
    {
            this.socket = socket;
            this.server  = server;
    }
    @Override
    public void run() {

        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }


    }



    void sendMessage(String message) {
        writer.println(message);
    }
}
