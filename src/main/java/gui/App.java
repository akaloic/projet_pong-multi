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
                case Z:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case S:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case Q:
                    playerA.state = RacketController.State.GOING_LEFT;
                    break;
                case D:
                    playerA.state = RacketController.State.GOING_RIGHT;
                    break;
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;
                case RIGHT:
                    playerB.state = RacketController.State.GOING_RIGHT;
                    break;
                case LEFT:
                    playerB.state = RacketController.State.GOING_LEFT;
                    break;
                case SPACE:
                	Player.pauseORcontinue();
                	break;

            }
        });
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {

                case Z:
                    if (playerA.state == RacketController.State.GOING_UP)
                        playerA.state = RacketController.State.IDLE;
                    break;
                case S:
                    if (playerA.state == RacketController.State.GOING_DOWN)
                        playerA.state = RacketController.State.IDLE;
                    break;
                case Q:
                    if (playerA.state == RacketController.State.GOING_LEFT)
                        playerA.state = RacketController.State.IDLE;
                    break;
                case D:
                    if (playerA.state == RacketController.State.GOING_RIGHT)
                        playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (playerB.state == RacketController.State.GOING_UP)
                        playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN)
                        playerB.state = RacketController.State.IDLE;
                    break;
                case LEFT:
                    if (playerB.state == RacketController.State.GOING_LEFT)
                        playerB.state = RacketController.State.IDLE;
                    break;
                case RIGHT:
                    if (playerB.state == RacketController.State.GOING_RIGHT)
                        playerB.state = RacketController.State.IDLE;
                    break;
                	

            }
        });
        sceneHandler.setMenuScene();
    }
}
