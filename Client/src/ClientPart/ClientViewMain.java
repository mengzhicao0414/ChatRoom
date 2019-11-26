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
