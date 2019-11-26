package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @program: CS513Project
 * @Description: a class to connect the server and client GUI
 * @author: Mengzhi Cao
 * @create: 2019-11-23
 **/
public class ChatClient {
    private final String serverIP;
    private final int serverPort;
    public Socket socket;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedIn;
    private ObservableList<String> onlineUserList;

    private ChatRoomController GUI;


    public ChatClient(ServerInfo serverDetail) {
        this.serverIP = serverDetail.getIp();
        this.serverPort = serverDetail.getPort();


    }


    /**
    * @Description: when the user correctly connect the server, create a new socket and connect with server
    * @param: []
    * @return: boolean
    */
    public boolean connect() {
        try {
            this.socket = new Socket(serverIP, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
    * @Description: When the user register a new user, send the command to server
    * @param: [reName, rePassword]
    * @return: java.lang.String
    */
    public String register(String reName, String rePassword) throws IOException {
        String msg = "register" + " " + reName + " " + rePassword + "\n";
        serverOut.write(msg.getBytes());

        return bufferedIn.readLine();

    }

    /**
    * @Description: When the user signin, send the command to server
    * @param: [signName, signPassword]
    * @return: java.lang.String
    */
    public String signIn(String signName, String signPassword) throws IOException {
        String msg = "signin" + " " + signName + " " + signPassword + "\n";
        serverOut.write(msg.getBytes());

        String response = bufferedIn.readLine();
        if (response.startsWith(signName + " " + "successfully log in")) {
            this.onlineUserList = FXCollections.observableArrayList();
            //after the user log in, start a new thread to listening the server response
            startListener();

        }
        return response;

    }
    /**
    * @Description:  a listener keeps listening the response from the server, and change the content of chat room GUI
    * @param: []
    * @return: void
    */
    private void startListener() {
        Thread t = new Thread() {
            @Override
            public void run() {
                String line;
                try {
                    while ((line = bufferedIn.readLine()) != null) {
                        String[] lines = line.split(" ");
                        if (lines != null && lines.length > 0) {
                            String cmd = lines[1];
                            switch (cmd.toLowerCase()) {
                                case "online":
                                    handleOnlineUser(onlineUserList, lines);
                                    break;
                                case "offline":
                                    handleOfflineUser(onlineUserList, lines);
                                    break;
                                case "from":
                                    String[] msgLines = line.split(" ", 4);
                                    handleMessage(msgLines);
                                    break;

                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }


        };
        t.start();
    }

    /**
    * @Description: When received user's offline message, change the GUI in a runlater way
    * @param: [onlineUserList, lines]
    * @return: void
    */
    private void handleOfflineUser(ObservableList<String> onlineUserList, String[] lines) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlineUserList.remove(lines[0]);
                GUI.chatLog.appendText("[Server Log] " + lines[0] + " " + "is offline now" + "(" + new Date() + ")\n");
            }
        });


    }

    /**
    * @Description: When reveived other users' messages, print them on the chat room GUI in a runlater way
    * @param: [msgLines]
    * @return: void
    */
    private void handleMessage(String[] msgLines) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GUI.chatLog.appendText(msgLines[2] + ": " + msgLines[3] + "\n");
            }
        });

    }

    /**
    * @Description: When received user's online message, change the listview of the chat room
    * @param: [onlineUserList, lines]
    * @return: void
    */
    private void handleOnlineUser(ObservableList<String> onlineUserList, String[] lines) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlineUserList.add(lines[0]);
                GUI.chatLog.appendText("[Server Log] " + lines[0] + " " + "online\n");
            }
        });


    }

    public ObservableList<String> getOnlineUserList() {
        return this.onlineUserList;
    }

    public boolean sendTo(String chatTarget, String message) throws IOException {
        if (chatTarget != null) {
            String msg = "sendto" + " " + chatTarget + " " + message + "\n";
            serverOut.write(msg.getBytes());
            return true;
        } else {
            return false;
        }
    }

    public void setGUI(ChatRoomController gui) {
        this.GUI = gui;
    }

    public ChatRoomController getGUI() {
        return GUI;
    }

    public void signOut() throws IOException {
        String msg = "signout\n";
        serverOut.write(msg.getBytes());

    }
}
