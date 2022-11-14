package gui.entities;

import model.RacketController;

public class Player implements RacketController { // Classe qui était dans App.java passée en classe publique pour des
                                                  // problèmes de visibilité


    public State state = State.IDLE;
    private static boolean pause=false; //si vrai on met le timer stop sinon le jeu continue de jouer
    // donc false par défaut par conséquent le jeu marche normalement;
    @Override
    public State getState() {
        return state;
    }
    public static void pauseORcontinue() {
    	pause=!pause;
    }
    public static boolean getPause() {
    	return pause;
    }

}
