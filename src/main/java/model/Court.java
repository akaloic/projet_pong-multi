package model;

import java.util.Random;

public class Court {
    // instance parameters
    private final double width, height; // m
    private final double racketSpeed = 300.0; // m/s
    private final double racketSize = 100.0; // m

    // instance state
    private double racketA; // m
    private double racketB; // m
    private double racketXB; // m ajouter pour Varier l'echelle X
    private double racketXA; // m ajouter pour Varier l'echelle X

    private int scoreA, scoreB;
    private boolean agetscore=true;//true pour playerA a marqué le point sinon false pour PlayerB a marqué le point;
    private double  coeffSpeedA=0.2; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement de raquette;
    private double coeffSpeedB=0.2;

    public Court(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double eitherInt(double a, double b) {
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

    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.racketXB=0;
        this.racketXA=0;  
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

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
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

    public void setAGetScore(boolean value) {
        agetscore = value;
    }

    public void incrementScoreB() {
        scoreB++;
    }

    public void incrementScoreA() {
        scoreA++;
    }

    public boolean getAGetScore() {
        return agetscore;
    }




}
