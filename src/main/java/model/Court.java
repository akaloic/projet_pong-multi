package model;

import java.util.Random;
import javafx.scene.media.AudioClip;
import sound.AudioBank;

public class Court {
    // instance parameters
    private final double width, height; // m

    private final double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state

    private double racketXB;
    private double racketYB;
    private double racketXA;
    private double racketYA;
    private double racketXC;
    private double racketXD;
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY; // m
    private int scoreA, scoreB, scoreC, scoreD;
    private int nbrejoueur;
    private boolean agetscore = true;// true pour playerA a marqué le point sinon false pour PlayerB a marqué le
                                     // point;
    private double coeffSpeedA = 0.3; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement
                                      // de raquette;
    private double coeffSpeedB = 0.3;
    private double coeffSpeedC = 0.3;
    private double coeffSpeedD = 0.3;
    private double coeefSpeedBall = 1;
    //private double nextBallX, nextBallY;

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
        // next, see if the ball would meet some obstacle
        if (UPDowndevientMur(nextBallY) || DowndevientMur(nextBallY)) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }
        if (LeftdevientMur(nextBallX) || RightdevientMur(nextBallX)) {  //Rebonds gauche/droite
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        if (LeftdevientMur(nextBallX) || RightdevientMur(nextBallX)) {  //Rebonds gauche/droite
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }

        /*
        if ((nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA
                && nextBallY < racketYA + racketSize) // Rebond raquette gauche
                || (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB
                && nextBallY < racketYB + racketSize)) // Rebond raquette droite
        {
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall + 100;
            nextBallX = ballX + deltaT * ballSpeedX;*/
        if (nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA && nextBallY < racketYA + racketSize) {
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall + 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB && nextBallY < racketYB + racketSize){
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall - 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        }else if ((nextBallY < 55.0 + 20.0 && nextBallX > width / 2 + racketXC - 55
                && nextBallX < width / 2 + racketXC + racketSize - 55) // rebond de raquette haut ,//55.0 c'est la
                // valeur de la marge + epaisseur de bordure.
                || (nextBallY > height - 25.00 && nextBallX > width / 2 + racketXD - 55
                && nextBallX < width / 2 + racketXD + racketSize - 55)) {

            ballSpeedY = -ballSpeedY * this.coeefSpeedBall - 100;
            nextBallY = ballY + deltaT * ballSpeedY;
        }else if(perdUnPoint(nextBallX,nextBallY,this.nbrejoueur)) {
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;

        /*
        if (nextBallX<racketXA && nextBallX>racketXA-30.0 && nextBallY > racketYA && nextBallY < racketYA + racketSize){
            ballSpeedX = -ballSpeedX*this.coeefSpeedBall + 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        }
        if (nextBallX>racketXB+width && nextBallX<racketXB+width+30.0 && nextBallY > racketYB && nextBallY < racketYB + racketSize){
            ballSpeedX = -ballSpeedX*this.coeefSpeedBall - 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        }


        ballX = nextBallX;
        ballY = nextBallY;
        return false;

         */
    }

    public boolean perdUnPoint(double nextBallX,double nextBallY,int nbrejoueur) {
    	 if (nextBallX < -20) {
             agetscore = false;
             scoreA--;
             return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
         } else if (nextBallX > width+20) {
        	 AudioBank.score.play();
             agetscore = true;
             scoreB--;
             return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
         }else if (nextBallY < 55.0 && nbrejoueur>2) {
             scoreC--;
             return true;
         } else if (nextBallY > height - 5.00 && nbrejoueur==4) {
             scoreD--;
             return true;
         }
    	 return false; 	
    }
  /*  public boolean rebondSol_Ou_Plafond(double nextBallY,int nbrejoueur) {
    	switch(nbrejoueur) {
    	case 4: return false;
    	case 3: return (nextBallY > height-5.00);
    	default: return (nextBallY < 55.00 || nextBallY > height - 5.00);
    	}
    }*/
    public boolean LeftdevientMur(double nextBallX) {
    	if(scoreA==0) {
    		return (nextBallX<-20);
    	}
    /*	if(scoreB==0) {
    		return(nextBallX>width+20);
    	}*/
    	return false;
    }
    public boolean RightdevientMur(double nextBallX) {
    	if(scoreB==0) {
    		return(nextBallX>width+20);
    	}
    	return false;
    }
    public boolean UPDowndevientMur(double nextBallY) {
    	if(scoreC==0) {
    		return (nextBallY < 55.00);
    	}
    	/*if(scoreD==0) {
    	   return (nextBallY > height-25.00);
    	}*/
    	return false;
    }
    public boolean DowndevientMur(double nextBallY) {
    	if(scoreD==0) {
     	   return (nextBallY > height-5.00);
     	}
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
    public void setNbrejoueur(int n) {  
    	this.nbrejoueur=n;
    	this.setScore(n);
    }
    private  void setScore(int n) {    //methode qui initialise vie de player3 et player4
    	scoreA=3;scoreB=3;   //par defaut il y a toujour playerA et playerB sur le terrain 
    	                     // chaque joueur a 3 vies

    	if(n>2) {
    		this.scoreC=3;
    	}
    	if(n>3) {
    		this.scoreD=3;
    	}
    }
    public int getNbrej() {
    	return this.nbrejoueur;
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

    public double getBallSpeedY() {
        return ballSpeedY;
    }

    public double getBallSpeedX() {
        return ballSpeedX;
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
    public double getCoefC(){
        return this.coeffSpeedC;
    }
    public double getCoefD(){
        return this.coeffSpeedD;
    }
    public void setCoefC(double coef){
        this.coeffSpeedC=coef;
    }
    public void setCoefD(double coef){
        this.coeffSpeedD=coef;
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

	public int getScoreC() {
		return scoreC;
	}

	public int getScoreD() {
		return scoreD;
	}

}
