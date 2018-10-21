package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import inter.ServerInterface;
import metier.Message;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private ScrollPane paneChat;

    @FXML
    private TextField textMessage;

    ServerInterface obj;

    @SuppressWarnings("unused")
    private static Scanner sc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc = new Scanner(System.in);
        int port = 8000;
        try {
            obj = (ServerInterface) Naming.lookup("rmi://localhost:" + port + "/serv0");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                printChat();
            }
        }.start();
    }

    private void printChat(){
        try {
            ArrayList<Message> messages = obj.getMessages();
            VBox vBox = new VBox();
            vBox.setSpacing(10);

            for (Message message: messages) {
                Label label = new Label(message.toString());
                vBox.getChildren().add(label);
            }
            paneChat.contentProperty().setValue(vBox);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void send(){

    }
}
