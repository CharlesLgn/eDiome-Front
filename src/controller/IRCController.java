package controller;

import Utils.XMLDataFinder;
import com.jfoenix.controls.JFXButton;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import inter.ServerInterface;
import javafx.stage.Stage;
import metier.Message;
import resource.lang.Translate;
import resource.lang.typetrad.ButonName;
import resource.lang.typetrad.LabelName;
import start.Main;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class IRCController implements Initializable {

    private String tabEmoji[] = {"1","2","3","4","5","6","7","9","10","11","12","13","14","15","16","17","18","19","20","21"};

    @FXML
    private JFXButton btnSend;

    @FXML
    private Pane PaneEmoji;

    @FXML
    private JFXButton btnemoji;

    @FXML
    private Label lblPseudo;

    @FXML
    private ScrollPane paneChat;

    @FXML
    private TextField textMessage;

    @FXML
    private TextField textPseudo;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox VboxMere;

    ServerInterface obj;

    private int nbServ;

    @SuppressWarnings("unused")
    private static Scanner sc;

    public IRCController(int nbServ){
        this.nbServ = nbServ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc = new Scanner(System.in);
        int port = 8000;
        try {
            obj = (ServerInterface) Naming.lookup("rmi://localhost:" + port + "/serv"+nbServ);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        textPseudo.setText(XMLDataFinder.getPseudo());

        textPseudo.setOnKeyReleased(e -> XMLDataFinder.setPseudo(textPseudo.getText()) );

        paneChat.setVvalue(paneChat.getVmax());

        textMessage.setOnKeyPressed(event -> {
            if(event.getCode().getName().equalsIgnoreCase("enter")){
                send();
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                printChat();
                translate();
            }
        }.start();

        loadEmoji();
    }

    private void translate(){
        lblPseudo.setText(Translate.haveIt(LabelName.PSEUDO, Main.getLangue().label) + " : ");
        btnSend.setText(Translate.haveIt(ButonName.SEND, Main.getLangue().butonName));
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
        try {
            if(!textMessage.getText().equalsIgnoreCase("")) {
                obj.send(textPseudo.getText(), textMessage.getText());
                textMessage.setText("");
                paneChat.setVvalue(paneChat.getVmax());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void attachments(){
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void displayEmoji(){
        if(PaneEmoji.isVisible()){
            PaneEmoji.setVisible(false);
        }else{
            PaneEmoji.setVisible(true);
        }
    }

    public void loadEmoji(){
        HBox hbox = new HBox();
        //hbox.getStyleClass().add("menu-bar-2");
        for(int i = 0; i <= 15; i++) {
            JFXButton jfxb = new JFXButton(tabEmoji[i]);
            jfxb.setPrefSize(20, 20);
            System.out.println(PaneEmoji);
            hbox.getChildren().add(jfxb);
        }

        PaneEmoji.getChildren().add(hbox);
    }
}
