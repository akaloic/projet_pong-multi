package gui;

import java.nio.file.Paths;

import gui.entities.Player;
import gui.views.MenuView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ControlHandler;
import sound.AudioBank;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        AudioBank.menuSongPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioBank.menuSongPlayer.play();
        primaryStage.setTitle("JeuPong");
        Image image = new Image(App.class.getResourceAsStream("./icon.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1000);
        primaryStage.setResizable(false);
        SceneHandler sceneHandler = new SceneHandler(primaryStage);
        sceneHandler.setMenuScene();

    }
}
