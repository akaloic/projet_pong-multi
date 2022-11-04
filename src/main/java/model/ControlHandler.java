package model;

import gui.SceneHandler;
import gui.View;
import gui.entities.Player;
import javafx.scene.shape.Line;

public class ControlHandler {
    private Player[]players;
    private SceneHandler sceneHandler;

    public ControlHandler(Player []players, SceneHandler sceneHandler) {
        this.players=players;
        this.sceneHandler = sceneHandler;
    }

    public void getInput() {
        sceneHandler.getScene().setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case Z:
                    players[0].state = RacketController.State.GOING_UP;
                    break;
                case S:
                    players[0].state = RacketController.State.GOING_DOWN;
                    break;
                case Q:
                	if(players.length==3) {
                		players[2].state=RacketController.State.GOING_LEFT;
                		break;
                	}
                	
                   players[0].state = RacketController.State.GOING_LEFT;
                   break;
                case D:
                	if(players.length==3) {
                		players[2].state=RacketController.State.GOING_RIGHT;
                		break;
                	}
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
                default:;
            }
           
        });
        System.out.println(players[2].state);
        sceneHandler.getScene().setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
               
                case Z:
                    if (players[0].state == RacketController.State.GOING_UP)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case S:
                    if (players[0].state == RacketController.State.GOING_DOWN)
                        players[0].state = RacketController.State.IDLE;
                    break;
                case Q:
                	if(players.length==3) {
                		 if (players[2].state == RacketController.State.GOING_LEFT)
                         	players[2].state = RacketController.State.IDLE;
                         break;
                	}
                    if (players[0].state == RacketController.State.GOING_LEFT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
                case D:   
                	if(players.length==3) {
               		 if (players[2].state == RacketController.State.GOING_RIGHT)
                        	players[2].state = RacketController.State.IDLE;
                        break;
               	}
                    if (players[0].state == RacketController.State.GOING_RIGHT)
                    	players[0].state = RacketController.State.IDLE;
                    break;
                case UP:
                	if(players.length<=1) break;
                    if (players[1].state == RacketController.State.GOING_UP)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case DOWN:
                	if(players.length<=1) break;
                    if (players[1].state == RacketController.State.GOING_DOWN)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case LEFT:
                	if(players.length<=1) break;
                    if (players[1].state == RacketController.State.GOING_LEFT)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                case RIGHT:
                	if(players.length<=1) break;
                    if (players[1].state == RacketController.State.GOING_RIGHT)
                    	players[1].state = RacketController.State.IDLE;
                    break;
                default:;

            }
        });
    }

    

}
