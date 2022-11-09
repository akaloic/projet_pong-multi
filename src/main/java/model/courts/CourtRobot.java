package model.courts;

import model.Court;
import model.RacketController;

public class CourtRobot extends Court{
    // instance parameters
    private final RacketController playerA;
    private final double ballRadius = 10.0; // m
    // instance state
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    
    
    public CourtRobot(RacketController playerA, double width, double height) {
        super(width, height);
        this.playerA = playerA;
        reset();
    }

    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > getHeight()) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }

        if ((nextBallX > getRacketXA() && nextBallX < getRacketXA() + 10.0 && nextBallY > getRacketA()
                && nextBallY < getRacketA() + getRacketSize()) // Rebond raquette gauche
                || (nextBallX > getWidth() + getRacketXB() && nextBallX < getWidth() + getRacketXB() + 10.0
                        && nextBallY > getRacketB()
                        && nextBallY < getRacketB() + getRacketSize())) { // Rebond raquette droite
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0) {
            setAGetScore(false);
            incrementScoreB();
            return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        } else if (nextBallX > getWidth()) {
            setAGetScore(true);
            incrementScoreA();
            return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    public void update(double deltaT) {

        switch (playerA.getState()) {
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

        if (updateBall(deltaT))
            reset();
    }

    public void reset() {
        super.reset();
        this.ballSpeedX = (getAGetScore()) ? -200.0 : 200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa
                                                        // trajectoire entre vers le haut ou vers le bas.
        this.ballX = getWidth() / 2;
        this.ballY = getHeight() / 2;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

}
