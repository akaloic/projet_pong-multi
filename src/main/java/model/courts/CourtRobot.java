package model.courts;

import model.Court;
import model.RacketController;

public class CourtRobot extends Court{
	private RacketController playerA;
    private RacketController.State botDirection;//direction du bot
    private double directionPoint;//coordonee en y ou la balle se dirige
    public CourtRobot(RacketController playerA, double width, double height) {
        super(width, height);
        this.playerA=playerA;
        reset();
    }

    public void update(double deltaT) {
    	super.SpeedUp(deltaT);

        switch (this.playerA.getState()) {
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
                setRacketXA(getRacketXA() + getRacketSpeed() * deltaT  * getCoefA());
                if (getRacketXA() >getWidth() / 2)
                    setRacketXA(getWidth() / 2);
                break;

        }


        setCoefB(0.8);

        int xDirection = sensX(deltaT);

        if (xDirection == 1 && getWidth()/2 < getBallX()){
            if (getBallY() - 20 > getRacketB()) {
                setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
            }
            if (getBallY() - 60 < getRacketB()) {
                setRacketB(getRacketB() - getRacketSpeed() * deltaT * getCoefB());
            }
        }else{
            if (getRacketB() > getHeight()/2){                                              //racketB dessus de milieuY
                setRacketB(getRacketB() - getRacketSpeed() * deltaT * getCoefB());
            }
            if (getRacketB() < getHeight()/2){                                              //racketB dessous de milieuY
                setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
            }
        }

        if (updateBall(deltaT))
            reset();
    }

    private int sensX(double deltaT) {
        if ( (getBallX() + deltaT * (-getBallSpeedX() + 100)) > getBallX() ) return -1;
        if ( (getBallX() + deltaT * (-getBallSpeedX() + 100)) < getBallX() ) return 1;
        return 0;
    }


}
