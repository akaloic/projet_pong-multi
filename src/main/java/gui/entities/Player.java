package gui.entities;

import model.RacketController;

public class Player implements RacketController { // Classe qui était dans App.java passée en classe publique pour des
                                                  // problèmes de visibilité
    public State state = State.IDLE;

    public State getState() {
        return state;
    }

    
}
