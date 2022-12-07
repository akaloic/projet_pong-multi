package gui.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import gui.SceneHandler;
import gui.View;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;


public class OnlineMenuView extends View{ // Classe similaire à GameView.java & MenuView.java avec des éléments différents

    // Base attributes
    private final Text online;
    private final Button exit;

    // Joining / Creating a server
    private final Button createButton, joinButton;

    public OnlineMenuView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);
        
        root.setMinWidth(court.getWidth() * scale + 2 * getMargin());
        root.setMinHeight(court.getHeight() * scale);

        online = new Text();
        online.setX(((court.getWidth() / 2) * scale) - 80);
        online.setY((court.getWidth() / 10) * scale);
        online.setTextAlignment(TextAlignment.CENTER);
        online.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        online.setFill(Color.BLACK);
        online.setText("Online");

        exit = new Button("Exit");
        exit.setLayoutX(((court.getWidth() / 2) * scale) - 5);
        exit.setLayoutY(((court.getHeight() / 2) * scale) + 100);
        exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        exit.setOnAction(event -> sceneHandler.switchToMenu(getRoot())); 

        createButton = new Button();
        createButton.setText("Create server");
        createButton.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        createButton.setLayoutY(250);
        createButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        createButton.setOnAction(event -> {sceneHandler.switchToOnlineGame("localhost", "22222", getRoot(),true);});

        joinButton = new Button();
        joinButton.setText("Join server");
        joinButton.setLayoutX(((court.getWidth() / 2) * scale) + 120);
        joinButton.setLayoutY(250);
        joinButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        joinButton.setOnAction(event -> {connectPopUp();});

 
        getRoot().getChildren().addAll(online, exit, createButton, joinButton); // On ajoute le title et les boutons aux éléments
        // du Pane

    }

    private void connectPopUp() {

      // Ip adress
      TextField ipField = new TextField("IP adress");
      ipField.setLayoutX(((getCourt().getWidth() / 2) * getScale()) + 120);
      ipField.setLayoutY(250);

    // Port
      TextField portField = new TextField("Port");
      portField.setLayoutX(((getCourt().getWidth() / 2) * getScale()) + 120);
      portField.setLayoutY(275);
    // Connect button
      Button connectButton = new Button("Connect");
      connectButton.setText("Join server");
      connectButton.setLayoutX(((getCourt().getWidth() / 2) * getScale()) + 120);
      connectButton.setLayoutY(300);
      connectButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
      connectButton.setOnAction(event -> {getSceneHandler().switchToOnlineGame(ipField.getText(), portField.getText(), getRoot(), false);});
      getRoot().getChildren().remove(joinButton);
      getRoot().getChildren().addAll(connectButton,ipField,portField);
    
    } 


}
