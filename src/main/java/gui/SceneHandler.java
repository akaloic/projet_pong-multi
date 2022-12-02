package gui;

import gui.entities.Player;
import gui.views.GameRobotView;
import gui.views.GameView;
import gui.views.MenuView;
import gui.views.PlayerNumber;
import gui.views.SettingsView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ControlHandler;
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
    private Player[] players;
    private boolean[]AI=new boolean[4];
    public static String bgcolor = "#FFFFFF"; // Couleur du background en hexadecimal en blanc par défaut
    public static Color itemcolor = Color.BLACK;

    public SceneHandler(Stage stage) {

        this.root = new Pane();
        this.scene = new Scene(root);
        this.stage = stage;

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
        court = new CourtMulti(players, 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher
                                                    // le jeu.
        var gameView = new GameView(court, root, 1.0, this, players.length,AI);

        stage.setScene(scene);
        stage.show();
        gameView.animate();

    }

    public void switchToGame(Pane menuRoot, int n, boolean small, boolean medium, boolean large,boolean[]AI) { // methode qui passe
                                                                                                   // de
                                                                                                   // ViewplayerNumber à
                                                                                                   // GameView
        this.setPlayers(n);
        this.switchToGameRbis(menuRoot,small,medium,large,AI);
      /*  switch (n) {
            case 1:
                this.switchToGameRobot(menuRoot);
                break;
            default:
                this.switchToGameRbis(menuRoot, small, medium, large);

        }
        */

    }

    public void paneColor() {
        if (SettingsView.darkthemetest) {
            bgcolor = "#000000";
        } else {
            bgcolor = "#FFFFFF";
        }
        root.setStyle("-fx-background-color: " + bgcolor);
    }

    public void switchToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        this.paneColor();
        court = new CourtMulti(players, 1000, 600);
        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput();
        view = new GameView(court, root, 1.0, this, players.length,AI);
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameR(Pane menuRoot, double racketSize) { // on utilise cette methode pour qu'on puisse modifier
                                                                  // la taille de la raquette
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
        // afficher le jeu sans problèmes.
        court = new CourtMulti(players, 1000, 600, racketSize,AI);
        view = new GameView(court, root, 1.0, this, 1,AI);
        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput();
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameRbis(Pane menuRoot, boolean small, boolean medium, boolean large, boolean[] AI) { // on utilise cette
                                                                                                // methode pour qu'on
                                                                                                // puisse modifier la
                                                                                                // taille de la raquette
                                                                                                // + sauvgarde la
                                                                                                // derniere taille
                                                                                                // choisie si le jouer
                                                                                                // revient au start
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
        // afficher le jeu sans problèmes.
        if (small)
            court = new CourtMulti(players, 1000, 600, 75.0,AI);
        else if (medium)
            court = new CourtMulti(players, 1000, 600,100,AI);
        else if (large)
            court = new CourtMulti(players, 1000, 600, 150.0,AI);
        view = new GameView(court, root, 1.0, this, players.length,AI);
        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput();
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameRobot(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        this.paneColor();
        court = new CourtRobot(this.players[0], 1000, 600);
        var gameView = new GameRobotView(court, root, 1.0, this);
        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput();
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }

    public void switchToSettings(Pane menuRoot) { // Méthode permettant de passer de menu à Settings
        menuRoot.getChildren().clear();
        this.paneColor();
        var court = new Court(1000, 600);
        view = new SettingsView(court, root, 1.0, this);

        stage.setScene(scene);

        stage.show();
    }

    public void refreshSettings(Pane settingsRoot) { // SettingsToSettings
        settingsRoot.getChildren().clear();
        var court = new Court(1000, 600);
        this.paneColor();
        view = new SettingsView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(Pane settingsRoot) { // Méthode permettant de passer de Settings à menu
        settingsRoot.getChildren().clear();
        this.paneColor();
        court = new Court(1000, 600);
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public String switchSonButton(Button b) {
        if (b.getText().equals("On"))
            return "Off";
        return "On";
    }

    public void setMenuScene() {
        court = new Court(1000, 600);
        // root.setStyle("-fx-background-color: #FF0000"); //Changement couleure bg
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoNbreJoueur(Pane menuRoot) {
        menuRoot.getChildren().clear();
        var court = new Court(1000, 600);
        var view = new PlayerNumber(court, root, 1.0, this);
        view.animate();

    }

    public void setPlayers(int n) {
        this.players = new Player[n];
        for (int i = 0; i < n; i++) {
            this.players[i] = new Player();
        }
    }

}
