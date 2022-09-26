package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import model.RacketController;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        var playerA = new Player();
        var playerB = new Player();
        // var root = new Pane(); -> Devient sceneHandler.getRoot();
        // var gameScene = new Scene(root); -> Devient sceneHandler.getScene()
        SceneHandler sceneHandler = new SceneHandler(primaryStage, playerA, playerB);

        sceneHandler.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case CONTROL:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case ALT:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;
            }
        });
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case CONTROL:
                    if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                    break;
                case ALT:
                    if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    break;
            }
        });
        sceneHandler.setMenuScene();
    }
}
