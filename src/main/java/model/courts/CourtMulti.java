package model.courts;

import model.Court;
import model.RacketController;

public class CourtMulti extends Court{
    // instance parameters
    private final RacketController playerB;



    public CourtMulti(RacketController playerA, RacketController playerB, double width, double height, double racketSize) { //contructeur pour modifier la taille de la raquette
        super(playerA, width, height, racketSize);
        this.playerB = playerB;
        reset();

    }




    

    public void update(double deltaT) { 
    	SpeedUp(deltaT); // fonction qui augmente la coeff de vitesse
        switch (getPlayerA().getState()) {
            case GOING_UP:
                setRacketA(getRacketA() - getRacketSpeed() * deltaT * getCoefA()) ;
                if (getRacketA() < 0.0)
                    setRacketA(0.0);
                break;
            case IDLE:
                setCoefA(0.2);
                break;
            case GOING_DOWN:
                setRacketA(getRacketA() + getRacketSpeed() * deltaT * getCoefA());
                if (getRacketA() + getRacketSize() > getHeight())
                    setRacketA(getHeight() - getRacketSize());
                break;
            case GOING_LEFT:
                setRacketXA(getRacketXA() - getRacketSpeed() * deltaT  * getCoefA());
                if (getRacketXA() < 0.0)
                    setRacketXA(0.0);
                break;
            case GOING_RIGHT:
                setRacketA(getRacketXA() + getRacketSpeed() * deltaT  * getCoefA());
                if (getRacketXA() > getWidth() / 2)
                    setRacketXA(getWidth() / 2);
                break;

        }

        switch (playerB.getState()) {
            case GOING_UP:
                setRacketB(getRacketB() - getRacketSpeed()* deltaT * getCoefB());
                if (getRacketB() < 0.0)
                    setRacketB(0.0);
                break;
            case IDLE:
            	setCoefB(0.2);// lorsque la raquette devient immobile, la vitesse est aussi réinitialiser;
                break;
            case GOING_DOWN:
                setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
                if (getRacketB() + getRacketSize() > getHeight())
                    setRacketB(getHeight() - getRacketSize());
                break;
            case GOING_LEFT:
                setRacketXB(getRacketXB() - getRacketSpeed() * deltaT * getCoefB());
                if (getRacketXB() < -(getWidth() / 2)) {
                    setRacketXB(-(getWidth() / 2));
                }
                break;
            case GOING_RIGHT:
                setRacketXB(getRacketXB() + getRacketSpeed() * deltaT * getCoefB());
                if (getRacketXB() > 0.0) {
                    setRacketXB(0.0);
                }

        }
        if (updateBall(deltaT))
            reset();
    }


}
