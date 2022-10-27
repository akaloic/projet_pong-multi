package gui;

import gui.entities.Player;
import gui.views.GameRobotView;
import gui.views.GameView;
import gui.views.MenuView;
import gui.views.SettingsView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;
import model.courts.CourtMulti;
import model.courts.CourtRobot;
import javafx.scene.control.Button;

public class SceneHandler { // Cette classe permet de manipuler les scènes courrantes sans à avoir besoin de
                            // réecrire tout le code à chaque fois
    private Stage stage;
    private Scene scene;
    private Pane root;
    private View view;
    private Court court;
    private Player playerA, playerB;

    public SceneHandler(Stage stage, Player playerA, Player playerB) { // On prends les playerA et playerB en argument
                                                                       // pour pouvoir les redistribuer sur les menuView
                                                                       // / gameView
        this.root = new Pane();
        this.scene = new Scene(root);
        this.stage = stage;
        this.playerA = playerA;
        this.playerB = playerB;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

    public void setGameScene() {
        court = new CourtMulti(playerA, playerB, 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher
                                                            // le jeu.
        var gameView = new GameView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }

    public void setMenuScene() {
        court = new CourtMulti(playerA, playerB, 1000, 600);
        //root.setStyle("-fx-background-color: #FF0000"); //Changement couleure bg
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        court = new CourtMulti(playerA, playerB, 1000, 600);
        view = new GameView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameRobot(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        court = new CourtRobot (playerA, 1000, 600);
        var gameView = new GameRobotView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }    

    public void switchToSettings(Pane menuRoot) { // Méthode permettant de passer de menu à Settings
        menuRoot.getChildren().clear();
        var court = new CourtMulti(playerA, playerB, 1000, 600);
        view = new SettingsView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(Pane settingsRoot) { // Méthode permettant de passer de Settings à menu
        settingsRoot.getChildren().clear();
        court = new CourtMulti(playerA, playerB, 1000, 600);
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public String switchSonButton(Button b){
        if (b.getText().equals("On")) return "Off";
        return "On";
    }

    


}

