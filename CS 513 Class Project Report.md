# CS 513 Class Project Report

CS513 Computer Network

Project Name: A Client-Server Chat Program

Name: Mengzhi Cao

Registration Number: 336482607

Date: 11/25/2019

Signed statement: mengzhicao



##Abstract 

This report outlines the design and development of a program which is a client-server chat program. The program was written in JAVA and tested on MacOs. The design and program are modular in nature and make maximum use of abstract data types and of software re-use. Particular attention is paid to design a server part to receive multiple clients requests and a client part with GUI can send request to server. The report includes a the test cases used to verify the correct operation of the program, as well as the entire code. 



## Contents Page 

 [TOC]



## Project Description

In this project, a server part which can handle multiple clients and a client part with a simple GUI are developed. The server part can handle different requests from clients such as register, sign in and send message to someone. The client part can correctly connect with the server and ask several requests. Besides, after the user sign in their account, they can see the online users and chat with them. Next, we will talk about the details of this program and show some test results.



## Detailed Design

This section should describe your overall design approach and the structure of your code, with emphasis on the more complicated portions of your design and code.

**Server**:

The server part is started by creating a serversocket with a local port. It contains a userMap which is used for storing the registered usernames and passwords and a socketList which is used for storing the running client socket. Then it will listen the coming client and for each of them create a new thread to handle the client request. Here, I design four different commands for the user, they are:

```bash
1. register <username> <password>
2. signin <username> <password>
3. signout
4. sendto <message> <username>
```

When received register command. If the username does't exist in userMap, then server will add this user information into userMap. If the username already exist, it will warn the user. 

When received signin command, server will compare the username and password with the userMap. If it is correct, then server will create a socket and a thread for this user.  Then this new thread will check socketList who are online now and send the online user list back to user. At the same time other online user will receive the notification of this user's online status. Then this socket and thread will keep listening until the user sign out.

When received signout command, server will close the connection between this user. It will close this socket and thread.

When received sendto command, server will check if the user online or not. If it offline, server will warn the user. On the contrast, server will send the message to the target user.



**Client**:

For the client part, I design a login GUI, a chat room GUI for the user and a client class. The client class contains an online user list which will record who are online now. The login window is shown as Figure 1. Here we can input the Server IP and port number to connect a server. After we click the connect button, client will open a new socket connect with Server.

<center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125032044480.png" alt="Figure 1" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 1</div> </center>
If we successfully connect to the server we will get to the chat room page. The chat room GUI design is shown as Figure 2. In this GUI, it contains four buttons. Each one will send the exactly command to server. 	

<center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125034259339.png" alt="Figure 2" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 2</div> </center>
After we fill out the Name textfield and Password textfield.

When we click the register button, client will try to register a new user in server. Then here will appear a server log to notify the users whether they succeed or not.

When we click the sign in button, client will log in the user. Server will also tell them if they succeed or not. Then client will open a new thread  keep listening the output streams from server until we sign out. At the same time,  In the Online Users list view of GUI, it will appear the users who are online now. 

When we click the sign out button, the client program will terminate, and the socket and thread will be closed.

Then we can choose the online user to talk with. After choose the user and fill out the text input area, we can click the send button. The target user will receive our message at the same time.




## Testing and Evaluation

In order to test my program, I design the testing strategy as follows:

* Connect Server test: 
  1. Connect to a correct server
  2. Connect to a non-existing server
* Registration test:
  1. Register a new user
  2. Register  a duplicate user
* Sign in test:
  1. Sign in a existing user, check the online user board and other online user's server log
  2. Sign in a non-existing user
* Message test:
  1. Person A send Message to Person B
  2. Person A send Message to non-existing Person
* Termination test:
  1. Sign out a user
  2. Terminate Client part and report the Server status
  3. Terminate Server part and report the Client status





Then the test report part is as follows:

* Connect Server test:

  1. **Test strategy:** Connect to a correct server

     **Test case:** Firstly we start the server part and run the client part. Then we use server IP as 127.0.0.1 which is a localhost, and port as 8000 to connect Server.

     **Result:** Firstly the client shows exactly as Figure 1, then when we click the button "connect", it jump to the next stage which is exactly as Figure 2.

  2. **Test strategy:** Connect to a non-existing server

     **Test case:** We do the same operation as test 1, but we input the Server IP as 12345 and Port as 10000, which is a non-existing server. 

     **Result:** After we click the connect button, the client warn us as Figure 3.

     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125174510907.png" alt="Figure 3" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 3</div> </center>

* Registration test:

  1. **Test strategy:** Register a new user

     **Test case:** After we successfully connect to the Server and get into the chat room. We input the username as "personA" and password as "12345", then click the register button.

     **Result:** There is a server log showing on the chat log board as Figure 4. It tells us that we successfully register this user.

     

     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125175451956.png" alt="Figure 4" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 4</div> </center>
     
  2. **Test strategy:** Register a duplicate user
  
     **Test case:** We input the username as "personA" and password as "123"
  
     **Result:** There is a server log showing on the chat log board as Figure 5. It warn us the user is already existing, we cannot use this user name. 
     

     
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125180247130.png" alt="Figure 5" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 5</div> </center>
  
* Sign in test:
     1. **Test strategy:** Sign in a existing user, check the online user board and other online user's server log
       
       **Test case:** Here we open other two clients and register two other user "personB" and "personC", then fistly sign in personA and personB. Then we sign personC to see the change of the online user board.
       
       **Result:** After the first step, there are server logs showing on the chat log board of client of personA and client of personB as Figure 6 and Figure 7. After the next step, the online user board of person A and person B increased. Here we show the personA chat room Figure 8 and personC Figure 9 chat room. Then the server log print out is shown as Figure9B
     
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125182042756.png" alt="Figure 6" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 6</div> </center>
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125182224766.png" alt="Figure 7" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 7</div> </center>
     <center>    <img src="/Users/mengzhicao/Desktop/2.png" alt="Figure 8" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 8</div> </center>
     <center>    <img src="/Users/mengzhicao/Desktop/1.png" alt="Figure 9" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 9</div> </center>
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125194642589.png" alt="Figure 9B"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 9B</div> </center>
     2. **Test strategy:** Sign in a non-existing user
        **Test case:** Here we open a new client and sign in a non-existing user "personD"
        **Result:** The chat log board tells us the user does't exist as Figure 10.
     
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125185440049.png" alt="Figure 10" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 10</div> </center>
   
* Message test:
     1. **Test strategy:** Person A send Message to Person B
        **Test case:** Here we use personA client chat with personB back and forward many times. When we need to chat with someone, we need to select the person on online user board. Sometimes the personA or B is typing the text and receive a message.
        **Result:** We show the screenshots Figure 11 and Figure 12 which are person A's and personB's chat room while they are typing. Further more tests such as chat between person A, B  and C are shown in the Appendices.

        <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125191155072.png" alt="Figure 11" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 11</div> </center>
<center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125191302134.png" alt="Figure 12" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 12</div> </center>
 2. **Test strategy:** Person A send Message to non-existing Person
 **Test case:**  We will use personD and send a message without choosing a online user.
 **Result:** The chat room warn us that we have't chosen a person to talk as Figure 13.

   

 <center>    <img src="/Users/mengzhicao/Desktop/3.png" alt="Figure 13" style="zoom:50%;" >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 13</div> </center>


* Termination test:

  1. **Test strategy:** Sign out a user

     **Test case:** Here we will sign out personA, and report the change happen in personB client and server.

     **Result:** Here we show the result after personA sign out. PersonA's client is closed , and the server log is shown as Figure 14. Then the change of online user personB is shown as Figure 15.
     
     
     
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125194506883.png" alt="Figure 14"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 14</div> </center>
     <center>    <img src="/Users/mengzhicao/Desktop/4.png" alt="Figure 15"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 15</div> </center>
     
2. **Test strategy:** Terminate Client part and report the Server status
  
   **Test case:** Here we will terminate the personD's client and check the status of Server and other client.
  
   **Result:** Here we show the server log print out as Figure 16. Besides, personB's client can still send message to online user personC.
  
   <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125194526656.png" alt="Figure 16"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 16</div> </center>


  <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125194603855.png" alt="Figure 17"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 17</div> </center>
  3. **Test strategy:** Terminate Server part and report the Client status

     **Test case:** Here we will terminate the Server and check the personB status.

     **Result:** Here we show the printed log of the client person B which is stable as Figure 18. Although we stay in chat room, personB cannot do any thing this time.
   
     <center>    <img src="/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125194848358.png" alt="Figure 18"  >    <br>    <div style="color:orange; border-bottom: 1px solid #d9d9d9;    display: inline-block;    color: #999;    padding: 2px;">Figure 18</div> </center>
     
     

## Future Development 

As we know from the results above, my program can correctly connect to Server, register users, sign in users, send messages to others and sign out. However after we terminate the server part while the client is still open. We cannot go back to connect to server GUI, so we cannot do anything. I plan to fix this one and make the client can reconnect to server. Besides, in my program we can't send message to a offline user, which is not reasonable in a real situation. So I plan to design a new function to send offline messages to offline users. When they are online, they can check the offline message.

 

## Conclusion – Solution Summary

In this project, I successfully develop a multiple clients chat room program based on the guide line. It correctly pass the designed test and show the correct results without errors. However, there is more functions we can implement into it to make it works more like a real chat room such as offline message function and reconnect function. 



 

## Appendices

### References 

Chat Room design course: https://fullstackmastery.com/courses/MUC

JavaFX tutorials: https://www.youtube.com/watch?v=FLkOX4Eez6o&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG&index=1

Some bug fix:

https://stackoverflow.com/questions/40969389/javafx-listview-chatting

https://stackoverflow.com/questions/39422477/how-do-i-solve-java-lang-illegalstateexception-in-a-gui-created-using-javafx



### Test Cases and Test Results

Addition chat log between personA and personC

  ![image-20191125191700739](/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125191700739.png)

  ![image-20191125191723548](/Users/mengzhicao/Library/Application Support/typora-user-images/image-20191125191723548.png)
### Code 

Server part:

Main function

```java
package ServerPart;/**
 * @author: mengzhicao
 * @date: 2019-11-21
 * @version: 1.0.0
 * @description:
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: CS513Project
 *
 * @Description: Serever main function, it will start a serversocket as server
 *
 * @author: Mengzhi Cao
 *
 * @create: 2019-11-21
 **/


public class MainServer {
    public static void main(String[] args) {
        int port = 8000;
        //start a new server listener to keep listening the request from the clients
        ServerListener server = new ServerListener(port);
        server.start();

    }
}

```

ServerListener:

```java
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

```

ChatSocket:

```java
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

```

Client part:

Main function:

```java
package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @program: CS513Project
 * @Description: Client view main function. It will set the login scene.
 * @author: Mengzhi Cao
 * @create: 2019-11-23
 **/
public class ClientViewMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Connect to Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //set the close request, the program will exit
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

```

loginGUI Design:

```xaml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="281.0" prefWidth="468.0" xmlns="http://javafx.com/javafx/8.0.172-ea" xmlns:fx="http://javafx.com/fxml/1" fx:controller="ClientPart.LoginController">
   <children>
      <Label layoutX="93.0" layoutY="82.0" text="Server IP" />
      <Label layoutX="118.0" layoutY="113.0" text="Port" />
      <TextField fx:id="serverIP" layoutX="162.0" layoutY="77.0" />
      <TextField fx:id="port" layoutX="162.0" layoutY="108.0" />
      <Button layoutX="200.0" layoutY="177.0" mnemonicParsing="false" onAction="#onClick" text="Connect" />
      <Label fx:id="checkConnect" layoutX="189.0" layoutY="147.0" prefHeight="17.0" prefWidth="200.0" textFill="RED" />
   </children>
</AnchorPane>

```

loginGUI Controller:

```java
package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @program: CS513Project
 * @Description: controller of login GUI
 * @author: Mengzhi Cao
 * @create: 2019-11-23
 **/
public class LoginController implements Initializable {

    @FXML
    public TextField serverIP;
    @FXML
    public TextField port;
    @FXML
    public Label checkConnect;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /** 
    * @Description: Function for the click event of login GUI. It will connect to the server and jump to the next stage chat room GUI
    * @param: [actionEvent] 
    * @return: void  
    */ 
    public void onClick(ActionEvent actionEvent) throws IOException {
        String ip = serverIP.getText();
        int portNum = Integer.parseInt(port.getText());

        ServerInfo serverInfo = new ServerInfo(ip, portNum);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ChatRoom.fxml"));
        Parent ChatRoomParent = loader.load();

        Scene ChatRoomScene = new Scene(ChatRoomParent);
        ChatRoomController control = loader.getController();
        //check if the server exists or not
        if (control.initServer(serverInfo)) {

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(ChatRoomScene);
            window.setTitle("Chat Room");
            window.show();
            //set a on close request for chatroom, it will sign out the user and close the socket.
            window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    try {
                        control.client.signOut();
                        control.client.socket.close();
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            checkConnect.setText("Wrong Ip or Port");
        }



    }
}

```

ServerInfo class:

```java
package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */

/**
 * @program: CS513Project
 *
 * @Description: record server information 
 *
 * @author: Mengzhi Cao
 *
 * @create: 2019-11-23
 **/
public class ServerInfo {
    private String ip;
    private int port;

    public ServerInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}

```

Chatroom GUI design:

```xaml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane prefHeight="600.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/8.0.172-ea" xmlns:fx="http://javafx.com/fxml/1" fx:controller="ClientPart.ChatRoomController">
   <children>
      <Label layoutX="31.0" layoutY="44.0" text="Name" />
      <TextField fx:id="userName" layoutX="80.0" layoutY="39.0" prefHeight="27.0" prefWidth="115.0" />
      <Label layoutX="21.0" layoutY="89.0" text="Password" />
      <TextField fx:id="password" layoutX="80.0" layoutY="84.0" prefHeight="27.0" prefWidth="115.0" />
      <Button layoutX="219.0" layoutY="39.0" mnemonicParsing="false" onAction="#Register" text="Register" />
      <Button layoutX="223.0" layoutY="84.0" mnemonicParsing="false" onAction="#signIn" text="Sign In" />
      <ListView fx:id="onlineUsers" layoutX="48.0" layoutY="165.0" prefHeight="367.0" prefWidth="225.0" />
      <Button layoutX="110.0" layoutY="542.0" mnemonicParsing="false" onAction="#signOut" text="Sign Out" />
      <TextField fx:id="messageBoard" alignment="TOP_LEFT" layoutX="318.0" layoutY="443.0" prefHeight="89.0" prefWidth="420.0" />
      <Button layoutX="683.0" layoutY="542.0" mnemonicParsing="false" onAction="#sendMessageTo" text="Send" />
      <TextArea fx:id="chatLog" accessibleRole="TEXT" layoutX="318.0" layoutY="44.0" prefHeight="373.0" prefWidth="420.0" />
      <Label fx:id="chatLabel" layoutX="318.0" layoutY="22.0" prefHeight="17.0" prefWidth="187.0" text="Chat with: " />
      <Label layoutX="49.0" layoutY="148.0" text="Online Users" />
   </children>
</AnchorPane>

```

Chatroom GUI controller:

```java
package ClientPart;/**
 * @author: mengzhicao
 * @date: 2019-11-23
 * @version: 1.0.0
 * @description:
 */

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @program: CS513Project
 * @Description: controller of chat room
 * @author: Mengzhi Cao
 * @create: 2019-11-23
 **/
public class ChatRoomController implements Initializable {
    @FXML
    public TextField userName;
    @FXML
    public TextField password;
    @FXML
    public TextArea chatLog;
    @FXML
    public ListView<String> onlineUsers;
    @FXML
    public TextField messageBoard;
    @FXML
    public Label chatLabel;


    private ServerInfo serverDetail; //record the server information
    public ChatClient client;
    private String chatTarget = null; //record the chat target user after the user click the online user

    /**
    * @Description: When the user successfully connect to server, create a chatclinet class to handle the action event in chatroom scene
    * @param: [serverInfo]
    * @return: boolean
    */
    public boolean initServer(ServerInfo serverInfo) {
        this.serverDetail = serverInfo;
        this.client = new ChatClient(serverDetail);
        boolean checkConnect = client.connect();
        client.setGUI(this);

        return checkConnect;
    }

    /**
    * @Description: intialize this scene, and set the list view of online users
    * @param: [location, resources]
    * @return: void
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        onlineUsers.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String oldVal, String newVal) -> {
                    chatTarget = String.valueOf(ov.getValue());
                    chatLabel.setText("Chat wit: " + chatTarget);

                });

    }

    /**
    * @Description: handle the action event happens in send button, check if there is a target usre or not and show the sended message
    * @param: [actionEvent]
    * @return: void
    */
    public void sendMessageTo(ActionEvent actionEvent) throws IOException {
        if (chatTarget != null) {
            String message = messageBoard.getText();
            if (client.sendTo(chatTarget, message)) {
                chatLog.appendText("You : " + message + " (" + new Date() + ")" + "\n");
                messageBoard.setText("");
            }
        }else{
            chatLog.appendText("Please select a user to chat \n");
        }

    }

    /**
    * @Description: handlet the action happens in sign out button, make sure the user sign out and teh socket is closed
    * @param: [actionEvent]
    * @return: void
    */
    public void signOut(ActionEvent actionEvent) throws IOException {
        client.signOut();
        client.socket.close();
        System.exit(0);
    }

    /**
    * @Description: handle the action happens in sign in button, print out the server log
    * @param: [actionEvent]
    * @return: void
    */
    public void signIn(ActionEvent actionEvent) throws IOException {
        String signName = userName.getText();
        String signPassword = password.getText();

        String response = client.signIn(signName, signPassword);
        if (response.startsWith(signName + " " + "successfully log in ")) {
            System.out.println("connect suceesful");
            onlineUsers.setItems(client.getOnlineUserList());

        }
        chatLog.appendText("[Server Log] " + response + "\n");

    }

    /**
    * @Description: handle the register action when the user click the register button and print out the server log
    * @param: [actionEvent]
    * @return: void
    */
    public void Register(ActionEvent actionEvent) throws IOException {
        String reName = userName.getText();
        String rePassword = password.getText();

        String response = client.register(reName, rePassword);
        chatLog.appendText("[Server Log] " + response + "\n");
    }
}

```

Chat client:

```java
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

```

