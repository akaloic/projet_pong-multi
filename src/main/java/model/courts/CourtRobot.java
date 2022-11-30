package model.courts;

import model.Court;
import model.RacketController;

public class CourtRobot extends Court{
	private RacketController playerA;
    
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
        setCoefB(0.7);
        if (getBallY() > getRacketB()){
            setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
            if (getRacketB() + getRacketSpeed() * deltaT * getCoefB() > getHeight()){
                setRacketB(getHeight() - 100);
            }
        }
        else if (getBallY() < getRacketB()){
            setRacketB(getRacketB() - getRacketSpeed() * deltaT * getCoefB()) ;
            if (getRacketB() < 0.0){
                setRacketB(0.0);
            }
        }


        if (updateBall(deltaT))
            reset();
    }

}
