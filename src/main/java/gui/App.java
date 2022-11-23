package gui;

import gui.entities.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.ControlHandler;
import sound.AudioBank;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        AudioBank.menuSongPlayer.setAutoPlay(true);

        var playerA = new Player();
        var playerB = new Player();
        SceneHandler sceneHandler = new SceneHandler(primaryStage, playerA, playerB);

        ControlHandler controlHandler = new ControlHandler(playerA, playerB, sceneHandler);
        controlHandler.getInput();
        sceneHandler.setMenuScene();
    }
}
