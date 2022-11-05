package model;

import java.awt.*;
import java.util.Random;
public class Court {
    // instance parameters
    private final RacketController playerA;
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
    private double coeffSpeedA=1.0; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement de raquette;
    private double coeffSpeedB=1.0;
    public static double incremente = 100.0;

    public Court(RacketController playerA, double width, double height) {
        this.playerA = playerA;

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.width = dimension.getWidth();
        this.height = dimension.getHeight();
    }

    private double eitherInt(double a, double b) {
        Random rd = new Random();
        int c = rd.nextInt(2);
        if (c == 1) {
            return a;
        } else {
            return b;
        }
    }

    public void SpeedUp(double time) { // on augmente la vitesse par rapport le temps
        this.coeffSpeedA+=time;
        this.coeffSpeedB+=time;
    }

    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;

        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height - 110) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
        }

        if (nextBallX>racketXA && nextBallX<racketXA+10.0 && nextBallY > racketA && nextBallY < racketA + racketSize) {
            ballSpeedX = -ballSpeedX + incremente;
        }
        if (nextBallX > width - 125 && (nextBallY <= racketB + racketSize && nextBallY >= racketB)){
            ballSpeedX = -ballSpeedX - incremente;
        }

        if (nextBallX < 0) {
        	agetscore=false; 
            scoreB++;
            return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        }
        if (nextBallX > width - 50) {
            System.out.println(nextBallY+" ballYCourt");
            System.out.println(racketB+" B");
        	agetscore=true;
            scoreA++;
            return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        }

        ballX = nextBallX;
        ballY = nextBallY;

        return false;
    }

    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.ballSpeedX = (agetscore)?-200.0:200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa trajectoire entre vers le haut ou vers le bas.
        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketXB=0;
        this.racketXA=0;  
    }


    public double getBallRadius() {
        return ballRadius;
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

    public RacketController getPlayerA() {
        return playerA;
    }

    public double getRacketSpeed() {
        return racketSpeed;
    }

    public void setRacketA(double racketA) {
        this.racketA = racketA;
    }

    public void setRacketXA(double racketXA) {
        this.racketXA = racketXA;
    }

    public void setRacketB(double racketB) {
        this.racketB = racketB;
    }

    public void setRacketXB(double racketXB) {
        this.racketXB = racketXB;
    }

    public double getCoefB() {
        return coeffSpeedB;
    }

    public void setCoefB(double coef) {
        coeffSpeedB = coef;
    }

    public double getCoefA() {
        return coeffSpeedA;
    }

    public void setCoefA(double coef) {
        coeffSpeedA = coef;
    }



}
