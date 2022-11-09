package gui;

import gui.entities.Player;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ControlHandler;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        var playerA = new Player();
        var playerB = new Player();
        SceneHandler sceneHandler = new SceneHandler(primaryStage, playerA, playerB);

        ControlHandler controlHandler = new ControlHandler(playerA, playerB, sceneHandler);
        controlHandler.getInput();
        sceneHandler.setMenuScene();
    }
}
