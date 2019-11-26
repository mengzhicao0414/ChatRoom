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
