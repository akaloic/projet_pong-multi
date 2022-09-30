package gui;

import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;
import model.CourtRobot;

public class SceneHandler { // Cette classe permet de manipuler les scènes courrantes sans à avoir besoin de
                            // réecrire tout le code à chaque fois
    private Stage stage;
    private Scene scene;
    private Pane root;
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
        var court = new Court(playerA, playerB, 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher
                                                            // le jeu.
        var gameView = new GameView(court, root, 1.0);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }

    public void setMenuScene() {
        var court = new Court(playerA, playerB, 1000, 600);
        //root.setStyle("-fx-background-color: #FF0000"); //Changement couleure bg
        var menuView = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        menuView.animate();
    }

    public void switchToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        var court = new Court(playerA, playerB, 1000, 600);
        var gameView = new GameView(court, root, 1.0);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }

    public void switchToGameRobot(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        var court = new CourtRobot (playerA, 1000, 600);
        var gameView = new GameRobotView(court, root, 1.0);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }    

    public void switchToSettings(Pane menuRoot) { // Méthode permettant de passer de menu à Settings
        menuRoot.getChildren().clear();
        var court = new Court(playerA, playerB, 1000, 600);
        var settingsView = new SettingsView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        settingsView.animate();
    }

    public void switchToMenu(Pane settingsRoot) { // Méthode permettant de passer de Settings à menu
        settingsRoot.getChildren().clear();
        var court = new Court(playerA, playerB, 1000, 600);
        var menuView = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        menuView.animate();
    }

}

