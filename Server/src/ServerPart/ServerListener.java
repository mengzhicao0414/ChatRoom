package ServerPart;
/**
 * @author: mengzhicao
 * @date: 2019-11-22
 * @version: 1.0.0
 * @description:
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: CS513Project
 * @Description: A definition of Server class, it will open a new thread and keep listening the request from users
 * @author: Mengzhi Cao
 * @create: 2019-11-22
 **/
public class ServerListener extends Thread {

    private final int port;
    private HashMap<String, String> userMap; //store the online user information
    private ArrayList<ChatSocket> socketList; //store the online client thread


    public ServerListener(int port) {
        this.port = port;
        this.userMap = new HashMap<>();
        this.socketList = new ArrayList<>();
    }

    public HashMap getUserMap() {
        return userMap;
    }

    public ArrayList<ChatSocket> getSocketList() {
        return socketList;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            System.out.println("This Server port is " + this.port);
            System.out.println("Ready to accept connection");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Received connection from " + clientSocket.getPort());
                //Initial the chat socket
                ChatSocket chatSocket = new ChatSocket(this, clientSocket);
                socketList.add(chatSocket);
                chatSocket.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //when the client sign out, remove it from the online user list.
    public void removeSocket(ChatSocket chatSocket) {
        socketList.remove(chatSocket);
    }
}
