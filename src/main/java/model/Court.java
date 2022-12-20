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
   // private boolean agetscore = true;// true pour playerA a marqué le point sinon false pour PlayerB a marqué le
                                     // point;
    private double coeffSpeedA = 0.3; // variable qui permet ralentir la vitesse de raquetteA avant chaque déplacement
                                      // de raquette;
    private double coeffSpeedB = 0.3;
    private double coeffSpeedC = 0.3;
    private double coeffSpeedD = 0.3;
    private double coeefSpeedBall = 1;
    // lastPlayer enregistre le dernier joueur qui a touché la balle
    private int lastPlayer = 0; // 0:aucun raquette touche la balle
                                // 1:Rackette A
                                // 2:Rackette B
                                // 3:Rackette C
                                // 4:Rackette D
    private boolean SystemdeVie;

    // Mort Subite
    private double leftwall = 0;
    private double rightwall = 0;
    private boolean tie = true;
    private boolean suddenDeath = false;

    public Court(double width, double height) {
        this.width = width;
        this.height = height;

    }

    public Court(double width, double height, double racketSize, boolean S) { // nouveau constructeur pour qu'on puisse
                                                                              // modifier la
        // taille de la raquette
        this.width = width;
        this.height = height;
        this.racketSize = racketSize;
        this.SystemdeVie = S;

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
        if (UPDowndevientMur(nextBallY) || DowndevientMur(nextBallY)) { // Rebonds plafond / sol
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }

        if (LeftdevientMur(nextBallX) || RightdevientMur(nextBallX)) { // Rebonds gauche/droite
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        }


        if (nextBallX < racketXA && nextBallX > racketXA - 30.0 && nextBallY > racketYA
                && nextBallY < racketYA + racketSize) { // rebond de raquette gauche
            lastPlayer = 1;
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall + 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX > racketXB + width && nextBallX < racketXB + width + 30.0 && nextBallY > racketYB
                && nextBallY < racketYB + racketSize) { // rebond de raquette droite
            lastPlayer = 2;
            ballSpeedX = -ballSpeedX * this.coeefSpeedBall - 100;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nbrejoueur > 2 && nextBallY < 55.0 + 20.0 && nextBallX > width / 2 + racketXC - 55 //// rebond de
                                                                                                      //// raquette haut
                                                                                                      //// ,//55.0 c'est
                                                                                                      //// la valeur de
                                                                                                      //// la marge +
                                                                                                      //// epaisseur de
                                                                                                      //// bordure.
                && nextBallX < width / 2 + racketXC + racketSize - 55) {
            lastPlayer = 3;
            ballSpeedY = -ballSpeedY * this.coeefSpeedBall - 100;
            nextBallY = ballY + deltaT * ballSpeedY;
        } else if (nbrejoueur == 4 && nextBallY > height - 25.00 && nextBallX > width / 2 + racketXD - 55 // rebond de
                                                                                                          // raquette
                                                                                                          // bas
                && nextBallX < width / 2 + racketXD + racketSize - 55) {
            lastPlayer = 4;
            ballSpeedY = -ballSpeedY * this.coeefSpeedBall - 100;
            nextBallY = ballY + deltaT * ballSpeedY;
        } else if (this.SystemdeVie && perdUnVie(nextBallX, nextBallY)) {
            return true;
        } else if (!this.SystemdeVie && marquerUnPoint(nextBallX, nextBallY)) {
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;

    }

    public boolean marquerUnPoint(double nextBallX, double nextBallY) {
        if (nextBallX < -15 || nextBallX < leftwall - 20 || nextBallX > width + 15 || nextBallX > rightwall + width + 20
                || (nextBallY < 55.0 && nbrejoueur > 2) || (nextBallY > height - 5.00 && nbrejoueur == 4)) {
            switch (this.lastPlayer) {
                case 0:
                    break;
                case 1:
                    scoreA++;
                    break;
                case 2:
                    scoreB++;
                    break;
                case 3:
                    scoreC++;
                    break;
                case 4:
                    scoreD++;
                    break;
                default:
                    ;
            }
            return true;
        }
        return false;
    }


    public boolean perdUnVie(double nextBallX,double nextBallY) {
    	 if (nextBallX < -15.00) {
    		 AudioBank.score.play();
             //agetscore = false;
             scoreA--;
             return true; // Quand la balle sort du jeu du côté droit, on donne un point au joueur B
         } else if (nextBallX > width+15.00) {
        	 AudioBank.score.play();
            // agetscore = true;
             scoreB--;
             return true; // Quand la balle sort du jeu du côté gauche, on donne un point au joueur A
         }else if (nextBallY < 55.00 && nbrejoueur>2) {
        	 AudioBank.score.play();
        	 scoreC--;
             return true;
         } else if (nextBallY > height-5.00 && nbrejoueur==4) {
        	 AudioBank.score.play();
        	 scoreD--;
             return true;
         }
    	 return false; 	
    }

    public boolean LeftdevientMur(double nextBallX) {
        if (this.SystemdeVie) {
            if (scoreA <= 0) {
                this.racketXA = -20;
                return (nextBallX < -15.00);

            }
        }
        return false; // Si on n'est pas en System de Vie , le côté gauche devient jamais un mur

    }

    public boolean RightdevientMur(double nextBallX) {
        if (this.SystemdeVie) {
            if (scoreB <= 0) {
                this.racketXB = width + 20;
                return (nextBallX > width + 15.00);
            }
        }
        return false; // Si on n'est pas en System de Vie , le côté Droite devient jamais un mur

    }

    public boolean UPDowndevientMur(double nextBallY) {
        if (this.SystemdeVie) {
            if (scoreC <= 0) {
                this.racketXC = -20 - width;
                return (nextBallY < 55.00);
            }

        } else if (this.nbrejoueur < 3) {
            return (nextBallY < 55.00);
        }
        return false;
    }

    public boolean DowndevientMur(double nextBallY) {
        if (scoreD <= 0) {
            this.racketXD = -20 - width;
            return (nextBallY > height - 5.00);
        } else if (this.nbrejoueur == 4) {
            return (nextBallY > height - 5.00);
        }
        return false;
    }

    public void reset() {
        this.racketYA = height / 2;
        this.racketYB = height / 2;
        
       // this.ballSpeedX = (agetscore) ? -200.0 : 200; // la balle va dirigé vers celui qui a marqué le point
        this.ballSpeedX= eitherInt(-200.0, 200.0);
        this.ballSpeedY = eitherInt(-200.0, 200.0); // A chaque reset de la balle, on détermine aléatoirement sa
                                                    // trajectoire entre vers le haut ou vers le bas.
       
        this.ballX = width / 2;
        this.ballY = height / 2;
        this.racketXB = 0;
        this.racketXA = 0;
        this.racketXC = 0;
        this.racketXD = 0;
        this.lastPlayer = 0;
        if(this.nbrejoueur==2) {
        	if(ballSpeedX==-200) {
        		lastPlayer=2;
        	}else {
        		lastPlayer=1;
        	}
        }
        
    }

    public void setNbrejoueur(int n) {
        this.nbrejoueur = n;
        if (this.SystemdeVie) {
            this.setScore(n);
        }

    }

    private void setScore(int n) { // methode qui initialise vie de player3 et player4
        scoreA = 3;
        scoreB = 3; // par defaut il y a toujour playerA et playerB sur le terrain
                    // chaque joueur a 3 vies

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

    public double getCoefC() {
        return this.coeffSpeedC;
    }

    public double getCoefD() {
        return this.coeffSpeedD;
    }

    public void setCoefC(double coef) {
        this.coeffSpeedC = coef;
    }

    public void setCoefD(double coef) {
        this.coeffSpeedD = coef;
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

    public int getScoreC() {
        return scoreC;
    }

    public int getScoreD() {
        return scoreD;
    }

}
