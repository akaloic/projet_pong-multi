package model;

import java.util.Random;

public class Court {
    // instance parameters
    private final double width, height; // m
    private final double racketSpeed = 300.0; // m/s
    private final double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state
   
    private double racketXB; // m ajouter pour Varier l'echelle X
    private double racketYB; // m
    private double racketXA; // m ajouter pour Varier l'echelle X
    private double racketYA; // m
    private double racketXC;
    private double racketXD;


	private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private int scoreA, scoreB;
    private boolean agetscore=true;//true pour playerA a marqué le point sinon false pour PlayerB a marqué le point;
    private double  coeffSpeedA=0.2; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement de raquette;
    private double coeffSpeedB=0.2;

    public Court( double width, double height) {
        this.width = width;
        this.height = height;
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
        if (nextBallY < 0 || nextBallY > height) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }

        if ((nextBallX>racketXA-50.0 && nextBallX<racketXA && nextBallY > racketYA && nextBallY < racketYA + racketSize)           // Rebond raquette gauche
        || (nextBallX >width+racketXB && nextBallX<width+racketXB+50.0 && nextBallY > racketYB && nextBallY < racketYB + racketSize)) {    // Rebond raquette droite
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

    public void reset() {
        this.racketYA = height / 2;
        this.racketYB = height / 2;
        this.ballSpeedX = (agetscore)?-200.0:200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa trajectoire entre vers le haut ou vers le bas.
        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketXB=0;
        this.racketXA=0; 
        this.racketXC=0;
        this.racketXD=0;
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

	public void setRacketXC(double racketXC) {
		this.racketXC = racketXC;
	}

	public double getRacketXD() {
		return racketXD;
	}

	public void setRacketXD(double racketXD) {
		this.racketXD = racketXD;
	}



}
