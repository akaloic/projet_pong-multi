package model;

import java.util.Random;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // m
    private final double racketSpeed = 300.0; // m/s
    private final double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double racketXB; // m ajouter pour Varier l'echelle X
    private double racketXA; // m ajouter pour Varier l'echelle X
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private int scoreA, scoreB;
    private boolean agetscore=true;//true pour playerA a marqué le point sinon false pour PlayerB a marqué le point;

    public Court(RacketController playerA, RacketController playerB, double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        reset();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }

    public double getRacketA() {
        return racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getRacketXB() {
        return racketXB;
    }

    public double getRacketXA() {
        return racketXA;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    private double eitherInt(double a, double b) { // Fonction permettant de tirer au hasard entre 2 doubles donnés en
                                                   // argument
        Random rd = new Random();
        int c = rd.nextInt(2);
        if (c == 1) {
            return a;
        } else {
            return b;
        }
    }

    public void update(double deltaT) {

        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeed * deltaT;
                if (racketA < 0.0)
                    racketA = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height)
                    racketA = height - racketSize;
                break;
            case GOING_LEFT:
                racketXA -= racketSpeed * deltaT;
                if (racketXA < 0.0)
                    racketXA = 0.0;
                break;
            case GOING_RIGHT:
                racketXA += racketSpeed * deltaT;
                if (racketXA > width / 2)
                    racketXA = width / 2;
                break;

        }

        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeed * deltaT;
                if (racketB < 0.0)
                    racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeed * deltaT;
                if (racketB + racketSize > height)
                    racketB = height - racketSize;
                break;
            case GOING_LEFT:
                racketXB -= racketSpeed * deltaT;
                if (racketXB < -(width / 2)) {
                    racketXB = -(width / 2);
                }
                break;
            case GOING_RIGHT:
                racketXB += racketSpeed * deltaT;
                if (racketXB > 0.0) {
                    racketXB = 0.0;
                }

        }
        if (updateBall(deltaT))
            reset();
    }

    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }


        if ((nextBallX>racketXA && nextBallX<racketXA+10.0 && nextBallY > racketA && nextBallY < racketA + racketSize)           // Rebond raquette gauche
        || (nextBallX >width+racketXB && nextBallX<width+racketXB+10.0 && nextBallY > racketB && nextBallY < racketB + racketSize)) {    // Rebond raquette droite
        	ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        else if (nextBallX < 0) {
        	agetscore=false;
            scoreB++; return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        } else if (nextBallX > width) {
        	agetscore=true;
            scoreA++; return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.ballSpeedX = (agetscore)?-200.0:200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa trajectoire entre vers le haut ou vers le bas.
        this.ballX = width / 2;
        this.ballY = height / 2;
    }
}
