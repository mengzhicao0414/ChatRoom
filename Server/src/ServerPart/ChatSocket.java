package ServerPart;/**
 * @author: mengzhicao
 * @date: 2019-11-22
 * @version: 1.0.0
 * @description:
 */

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @program: CS513Project
 * @Description: This class is designed for creating a new thread for each user connected. Then it will keep listening the command from users
 * @author: Mengzhi Cao
 * @create: 2019-11-22
 **/
public class ChatSocket extends Thread {

    private final Socket chatSocket;
    private final ServerListener server;
    private OutputStream outputStream;
    private String nowUser = null;

    public ChatSocket(ServerListener serverlistener, Socket clientSocket) {
        this.server = serverlistener;
        this.chatSocket = clientSocket;
    }

    public String getNowUser() {
        return nowUser;
    }

    public void publish(String message) throws IOException {
        outputStream.write(message.getBytes());
    }

    @Override
    public void run() {
        try {
            serverHandleSocket();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
    * @Description: This function to handle the coming commands
    * @param: []
    * @return: void
    */
    private void serverHandleSocket() throws IOException {
        InputStream inputStream = chatSocket.getInputStream();
        this.outputStream = chatSocket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String nowLine;
        //for different commands use different function to handdle the command
        while ((nowLine = br.readLine()) != null) {
            String[] lines = nowLine.split(" ");
            if (lines != null && lines.length > 0) {
                String cmd = lines[0];
                switch (lines[0].toLowerCase()) {
                    case "register":
                        if (lines.length == 3) {
                            serverHandleRegister(lines);
                        }
                        break;
                    case "signin":
                        if (lines.length == 3) {
                            serverHandleSignIn(lines);
                        }
                        break;
                    case "signout":
                    case "exit":
                        serverHandleOff();
                        return;
                    case "sendto":
                        String[] msgLines = nowLine.split(" ", 3);
                        serverHandleSendMsg(msgLines);
                        break;
                    default:
                        publish("Command not found\n");

                }
            }
        }
        chatSocket.close();
        System.out.println("Client "+ chatSocket.getPort()+" disconnected\n");
    }

    /**
    * @Description: This function is designed to handle the coming send message command, if it is correct, this client will send message to the target client
    * @param: [msgLines]
    * @return: void
    */
    private void serverHandleSendMsg(String[] msgLines) throws IOException {
        String userName = msgLines[1];
        String message = msgLines[2];

        if (server.getUserMap().containsKey(userName)) {
            ArrayList<ChatSocket> list = server.getSocketList();
            //check the online user list and send the mesage to the target client
            for (ChatSocket socket : list) {
                if (userName.equals(socket.getNowUser())) {
                    socket.publish("Receive from " + nowUser + " " + message + "  " +"("+ new Date()+ ")" + "\n");
                }
            }

        } else {
            publish(userName + " " + "not exist\n");
        }
    }

    /**
    * @Description: This function is designed to handle the coming sign out commad, it will close the chatSocket.
    * @param: []
    * @return: void
    */
    private void serverHandleOff() throws IOException {
        server.removeSocket(this);

        ArrayList<ChatSocket> list = server.getSocketList();
        offlineInform(list);
        chatSocket.close();
        System.out.println("Client "+ chatSocket.getPort()+" disconnected\n");
    }

    /**
    * @Description: This function is design to handle the sign in command, it will check if the user is registered or the password is correct or not
    * @param: [lines]
    * @return: void
    */
    private void serverHandleSignIn(String[] lines) throws IOException {
        String userName = lines[1];
        String password = lines[2];
        String msg;
        ArrayList<ChatSocket> list = server.getSocketList();

        HashMap userMap = server.getUserMap();
        if (userMap.containsKey(userName)) {

            if (password.equals(userMap.get(userName))) {

                for (ChatSocket socket : list) {
                    if (userName.equals(socket.getNowUser())) {
                        socket.serverHandleOff();
                    }
                }
                offlineInform(list);
                nowUser = userName;

                msg = userName + " " + "successfully log in " + "("+new Date() + ")\n";
                outputStream.write(msg.getBytes());
                System.out.println(userName + " " + "is online now " + new Date());
                for (ChatSocket socket : list) {
                    if (socket.getNowUser() != null) {
                        publish(socket.getNowUser() + " " + "online\n");
                    }
                }
                String message = nowUser + " " + "online " + "(" + new Date() + ")" + "\n";
                for (ChatSocket socket : list) {
                    if (!nowUser.equals(socket.getNowUser()) && socket.getNowUser() != null) {
                        socket.publish(message);
                    }
                }
            } else {
                msg = "Wrong password\n";
                outputStream.write(msg.getBytes());
            }
        } else {
            msg = "User not exist\n";
            outputStream.write(msg.getBytes());
        }


    }

    private void offlineInform(ArrayList<ChatSocket> list) throws IOException {
        if (nowUser != null) {
            String message = nowUser + " " + "offline " + "(" + new Date() + ")" + "\n";
            System.out.print(message);
            for (ChatSocket socket : list) {
                if (!nowUser.equals(socket.getNowUser()) && socket.getNowUser() != null) {
                    socket.publish(message);
                }
            }
        }
    }

    /**
    * @Description: This function is designed to handle the register command, then it will check if the user is duplicated or not
    * @param: [lines]
    * @return: void
    */
    private void serverHandleRegister(String[] lines) throws IOException {
        String userName = lines[1];
        String password = lines[2];
        String msg;
        HashMap userMap = server.getUserMap();

        if (userMap.containsKey(userName)) {
            msg = "Sorry, user name is duplicate\n";
        } else {
            userMap.put(userName, password);
            msg = "Congratulation! Register Succeed\n";
            System.out.println(userName + " " + "register successfully " + new Date());
        }

        outputStream.write(msg.getBytes());

    }
}
