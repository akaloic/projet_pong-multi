package gui;

import model.RacketController;

public class Player implements RacketController { // Classe qui était dans App.java passée en classe publique pour des
                                                  // problèmes de visibilité
    State state = State.IDLE;

    @Override
    public State getState() {
        return state;
    }
}
