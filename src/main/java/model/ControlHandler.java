package model;

import gui.SceneHandler;
import gui.View;
import gui.entities.Player;

public class ControlHandler {
    private Player[]players;
    private SceneHandler sceneHandler;

    public ControlHandler(Player []players, SceneHandler sceneHandler) {
        this.players=players;
        this.sceneHandler = sceneHandler;
    }
    public void getInput1() {
    	sceneHandler.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default:;
                case Z:
                    players[0].state = RacketController.State.GOING_UP;
                    break;
                case S:
                    players[0].state = RacketController.State.GOING_DOWN;
                    break;
                case Q:
                    players[0].state = RacketController.State.GOING_LEFT;
                    break;
                case D:
                    players[0].state = RacketController.State.GOING_RIGHT;
                    break;
                case SPACE:
                	View.pauseORcontinue();
                	break;
            }
        });
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                default:;
                case Z:
                    if (players[0].state == RacketController.State.GOING_UP)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case S:
                    if (players[0].state == RacketController.State.GOING_DOWN)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case Q:
                    if (players[0].state == RacketController.State.GOING_LEFT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
                case D:
                    if (players[0].state == RacketController.State.GOING_RIGHT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
            }
        });
    }

    public void getInput2() {
        sceneHandler.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default:;
                case Z:
                    players[0].state = RacketController.State.GOING_UP;
                    break;
                case S:
                    players[0].state = RacketController.State.GOING_DOWN;
                    break;
                case Q:
                    players[0].state = RacketController.State.GOING_LEFT;
                    break;
                case D:
                    players[0].state = RacketController.State.GOING_RIGHT;
                    break;
                case UP:
                    players[1].state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    players[1].state = RacketController.State.GOING_DOWN;
                    break;
                case RIGHT:
                    players[1].state = RacketController.State.GOING_RIGHT;
                    break;
                case LEFT:
                    players[1].state = RacketController.State.GOING_LEFT;
                    break;
                case SPACE:
                	View.pauseORcontinue();
                	break;
            }
        });
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                default:;
                case Z:
                    if (players[0].state == RacketController.State.GOING_UP)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case S:
                    if (players[0].state == RacketController.State.GOING_DOWN)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case Q:
                    if (players[0].state == RacketController.State.GOING_LEFT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
                case D:
                    if (players[0].state == RacketController.State.GOING_RIGHT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (players[1].state == RacketController.State.GOING_UP)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (players[1].state == RacketController.State.GOING_DOWN)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case LEFT:
                    if (players[1].state == RacketController.State.GOING_LEFT)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case RIGHT:
                    if (players[1].state == RacketController.State.GOING_RIGHT)
                    	players[1].state = RacketController.State.IDLE;
                    break;

            }
        });

    }
    

}
