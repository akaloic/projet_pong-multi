package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Court;

public class SceneHandler { // Cette classe permet de manipuler les scènes courrantes sans à avoir besoin de réecrire tout le code à chaque fois
    private Stage stage;
    private Scene scene;
    private Pane root;
    private Player playerA, playerB;

    public SceneHandler(Stage stage, Player playerA, Player playerB) { // On prends les playerA et playerB en argument pour pouvoir les redistribuer sur les menuView / gameView
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

    public  Stage getStage() {
        return stage;
    }

    public void setGameScene() {
        var court = new Court(playerA, playerB, 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher le jeu.
        var gameView = new GameView(court, root, 1.0);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }

    public void setMenuScene() {
        var court = new Court(playerA, playerB, 1000, 600);
        var menuView = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        menuView.animate();
    }

    public void switchToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite afficher le jeu sans problèmes.
        var court = new Court(playerA, playerB, 1000, 600);
        var gameView = new GameView(court, root, 1.0);
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }
    
}
