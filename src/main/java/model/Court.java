package model;

import java.util.Random;
import javafx.scene.media.AudioClip;
import sound.AudioBank;

public class Court {
    // instance parameters
    private double width, height; // m

    private final double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state

    private double racketXB, preXB; // m ajouter pour Varier l'echelle X
    private double racketYB, preYB; // m
    private double racketXA, preXA; // m ajouter pour Varier l'echelle X
    private double racketYA, preYA; // m
    private double racketXC;
    private double racketXD;
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private int scoreA, scoreB, scoreC, scoreD;
    private boolean agetscore = true;// true pour playerA a marqué le point sinon false pour PlayerB a marqué le
                                     // point;
    private double coeffSpeedA = 0.3; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement
                                      // de raquette;
    private double coeffSpeedB = 0.3;
    private double coeefSpeedBall = 1;

    // Mort Subite
    private double leftwall = 0;
    private double rightwall = 0;
    private boolean tie = true;
    private boolean suddenDeath = true;

    public Court(double width, double height) {
        this.width = width;
        this.height = height;

    }

    public Court(double width, double height, double racketSize) { // nouveau constructeur pour qu'on puisse modifier la
                                                                   // taille de la raquette
        this.width = width;
        this.height = height;
        this.racketSize = racketSize;

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
        this.coeffSpeedA += time * 2;
        this.coeffSpeedB += time * 2;
    }

    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // SuddenDeath
        if (tie && suddenDeath) {
            if (leftwall < 300 && rightwall > -300) {
                leftwall += 0.1;
                rightwall -= 0.1;
            }
        }

        // next, see if the ball would meet some obstacle
        if (nextBallY < 55.00 || nextBallY > height - 5.00) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }

        if ((nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA
                && nextBallY < racketYA + racketSize) // Rebond raquette gauche
                || (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB
                        && nextBallY < racketYB + racketSize)) // Rebond raquette droite
        {
            if (nextBallX > width / 2) {
                this.SpeedBallUpOrDown(preXB, racketXB);
            } else {
                this.SpeedBallUpOrDown(preXA, racketXA);
            }
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0 || nextBallX < leftwall - 20) {
            AudioBank.score.play();
            agetscore = false;
            scoreB++;
            return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        } else if (nextBallX > width || nextBallX > rightwall + width + 20) {
            AudioBank.score.play();
            agetscore = true;
            scoreA++;
            return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        }
        ballX = nextBallX;
        ballY = nextBallY;
        preXA = racketXA;
        preXB = racketXB;
        return false;
    }

    public boolean updateBall4(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        if ((nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA
                && nextBallY < racketYA + racketSize) // Rebond raquette gauche
                || (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB
                        && nextBallY < racketYB + racketSize)) // Rebond raquette droite
        {
            if (nextBallX > width / 2) {
                this.SpeedBallUpOrDown(preXB, racketXB);
            } else {
                this.SpeedBallUpOrDown(preXA, racketXA);
            }
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if ((nextBallY < 55.0 + 20.0 && nextBallX > width / 2 + racketXC - 55
                && nextBallX < width / 2 + racketXC + racketSize - 55) // rebond de raquette haut ,//55.0 c'est la
                                                                       // valeur de la marge + epaisseur de bordure.
                || (nextBallY > height - 25.00 && nextBallX > width / 2 + racketXD - 55
                        && nextBallX < width / 2 + racketXD + racketSize - 55)) {

            ballSpeedY = -ballSpeedY * this.coeefSpeedBall;
            nextBallY = ballY + deltaT * ballSpeedY;

        } else if (nextBallX < 0) {
            agetscore = false;
            scoreB++;
            return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        } else if (nextBallX > width) {
            agetscore = true;
            scoreA++;
            return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        } else if (nextBallY < 55.0) {
            scoreD++;
            return true;
        } else if (nextBallY > height - 5.00) {
            scoreC++;
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        preXA = racketXA;
        preXB = racketXB;
        return false;

    }

    public boolean updateBall3(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;

        // next, see if the ball would meet some obstacle
        if (nextBallY > height - 5.00) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
            /*
             * System.out.println(nextBallX);
             * System.out.println(racketXC);
             * System.out.println(width/2);
             * System.out.println(width/2+racketXC);
             * System.out.println(width/2+racketXC+racketSize);
             */

        }

        if ((nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA
                && nextBallY < racketYA + racketSize) // Rebond raquette gauche
                || (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB
                        && nextBallY < racketYB + racketSize)) // Rebond raquette droite
        {
            if (nextBallX > width / 2) {
                this.SpeedBallUpOrDown(preXB, racketXB);
            } else {
                this.SpeedBallUpOrDown(preXA, racketXA);
            }
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallY < 55.0 + 20.0 && nextBallX > width / 2 + racketXC - 55
                && nextBallX < width / 2 + racketXC + racketSize - 55) { // rebond de raquette haut ,//55.0 c'est la
                                                                         // valeur de la marge + epaisseur de bordure.
            ballSpeedY = -ballSpeedY * this.coeefSpeedBall;
            nextBallY = ballY + deltaT * ballSpeedY;
        } else if (nextBallX < 0) {
            agetscore = false;
            scoreB++;
            return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
        } else if (nextBallX > width) {
            agetscore = true;
            scoreA++;
            return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
        } else if (nextBallY < 55.0) {
            scoreD++;
            return true;
        } else if (nextBallY > height - 5.00) {
            scoreC++;
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        preXA = racketXA;
        preXB = racketXB;
        return false;
    }

    public void reset() {
        this.racketYA = height / 2;
        this.racketYB = height / 2;
        this.ballSpeedX = (agetscore) ? -200.0 : 200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa
                                                    // trajectoire entre vers le haut ou vers le bas.
        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketXB = 0;
        this.racketXA = 0;
        this.racketXC = 0;
        this.racketXD = 0;
    }

    public void SpeedBallUpOrDown(double preX, double racketX) {
        if (preX < racketX) {
            if (this.coeefSpeedBall < 2) {
                this.coeefSpeedBall += 0.2;
            }

        } else {
            if (this.coeefSpeedBall > 1) {
                this.coeefSpeedBall -= 0.2;
            }
        }
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
        return racketYA;
    }

    public double getRacketB() {
        return racketYB;
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

    public double getRacketSpeed() {
        return racketSpeed;
    }

    public double getLeftwall() {
        return this.leftwall;
    }

    public double getRightwall() {
        return this.rightwall;
    }

    public void setRacketA(double racketA) {
        this.racketYA = racketA;
    }

    public void setRacketXA(double racketXA) {
        this.racketXA = racketXA;
    }

    public void setRacketB(double racketB) {
        this.racketYB = racketB;
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

    public double getRacketXC() {
        return racketXC;
    }

    public void setRacketXC(double racketYC) {
        this.racketXC = racketYC;
    }

    public double getRacketXD() {
        return racketXD;
    }

    public void setRacketXD(double racketXD) {
        this.racketXD = racketXD;
    }

    public void setRacketSize(double size) {
        this.racketSize = size;
    }

    public void setLeftwall(double leftwall) {
        this.leftwall = leftwall;
    }

    public void setRightwall(double rightwall) {
        this.rightwall = rightwall;
    }

}
