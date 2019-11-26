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
