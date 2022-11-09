package model;

import gui.SceneHandler;
import gui.View;
import gui.entities.Player;

public class ControlHandler {
    private Player playerA, playerB;
    private SceneHandler sceneHandler;

    public ControlHandler(Player playerA, Player playerB, SceneHandler sceneHandler) {
        this.playerA = playerA; this.playerB = playerB;
        this.sceneHandler = sceneHandler;
    }

    public void getInput() {
        sceneHandler.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default:;
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
                case SPACE:
                	View.pauseORcontinue();
                	break;
            }
        });
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                default:;
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

    }
    

}
