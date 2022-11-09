package model.courts;

import model.Court;
import model.RacketController;

public class CourtRobot extends Court{
    
    public CourtRobot(RacketController playerA, double width, double height) {
        super(playerA, width, height);
        reset();
    }

    public void update(double deltaT) {

        SpeedUp(deltaT); // fonction qui augmente le coeff de vitesse
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

        if (getBallY() > getRacketB()){
            setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
            setCoefB(0.4);
            if (getRacketB() + getRacketSpeed() * deltaT * getCoefB() > getHeight()){
                setRacketB(getHeight() - 100);
            }
        }
        else if (getBallY() < getRacketB()){
            setRacketB(getRacketB() - getRacketSpeed() * deltaT * getCoefB()) ;
            setCoefB(0.4);
            if (getRacketB() < 0.0){
                setRacketB(0.0);

            }
        }
        else {
            setCoefB(0.4);
        }

        if (updateBall(deltaT))
            reset();
    }

}
