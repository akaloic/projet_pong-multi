package model.courts;

import gui.entities.Player;
import model.Court;
import model.RacketController;

public class CourtMulti extends Court{
    // instance parameters

	private final RacketController []players;
    private boolean[]AI=new boolean[4];


    
    
    
    public CourtMulti(RacketController[]p, double width, double height,boolean S) {
        super(width, height);
        this.players=p;
        super.setNbrejoueur(p.length);
        reset();
    }

    public CourtMulti(RacketController[]p,double width, double height, double racketSize,boolean[]AI,boolean S) { //nouveau constructeur pour qu'on puisse modifier la taille de la raquette
        super(width,height,racketSize,S);
        this.players=p;
        this.AI=AI;
        super.setNbrejoueur(p.length);
        reset();
    }


    

    public void update(double deltaT) {
        SpeedUp(deltaT); // fonction qui augmente la coeff de vitesse
        if (!AI[0]) {
            switch (this.players[0].getState()) {
                case GOING_UP:
                    setRacketA(getRacketA() - getRacketSpeed() * deltaT * getCoefA());
                    if (getRacketA() < 50.0)
                        setRacketA(50.0);
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
                    setRacketXA(getRacketXA() - getRacketSpeed() * deltaT * getCoefA());
                    if (getRacketXA() < 0.0)
                        setRacketXA(0.0);
                    break;
                case GOING_RIGHT:
                    setRacketXA(getRacketXA() + getRacketSpeed() * deltaT * getCoefA());
                    if (getRacketXA() > getWidth() / 2 - 20)
                        setRacketXA(getWidth() / 2 - 20);
                    break;

            }
        } else {
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
        }

        if (!AI[1] && getNbrej()>=2) {
            switch (players[1].getState()) {
                case GOING_UP:
                    setRacketB(getRacketB() - getRacketSpeed() * deltaT * getCoefB());
                    if (getRacketB() < 50.0)
                        setRacketB(50.0);
                    break;
                case IDLE:
                    setCoefB(0.2);// lorsque la raquette devient immobile, la vitesse est aussi réinitialiser;
                    break;
                case GOING_DOWN:
                    setRacketB(getRacketB() + getRacketSpeed() * deltaT * getCoefB());
                    if (getRacketB() + getRacketSize() > getHeight())
                        setRacketB(getHeight() - getRacketSize());
                    break;
                case GOING_LEFT:
                    setRacketXB(getRacketXB() - getRacketSpeed() * deltaT * getCoefB());
                    if (getRacketXB() < -(getWidth() / 2 - 20)) {
                        setRacketXB(-(getWidth() / 2 - 20));
                    }
                    break;
                case GOING_RIGHT:
                    setRacketXB(getRacketXB() + getRacketSpeed() * deltaT * getCoefB());
                    if (getRacketXB() > 0.0) {
                        setRacketXB(0.0);
                    }

            }

        } else {
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

        }



        if(getNbrej()>=3) {
            if (!AI[2]) {
                switch (players[2].getState()) {
                    case GOING_LEFT:
                        setRacketXC(getRacketXC() - getRacketSpeed() * deltaT);
                        if ((getRacketXC() < -(super.getWidth() / 2 - 20.0))) {
                            setRacketXC(-(super.getWidth() / 2 - 20.0));
                        }
                        break;
                    case GOING_RIGHT:
                        setRacketXC(getRacketXC() + getRacketSpeed() * deltaT);
                        if (getRacketXC() > getWidth() / 2 - 20.0) {
                            setRacketXC(getWidth() / 2 - 20.0);
                        }
                    default:
                        break;

                }

            } else {
                setCoefC(0.7);
                if (getBallX() > getRacketXC() + getWidth() / 2) {
                    setRacketXC(getRacketXC() + getRacketSpeed() * deltaT * getCoefC());
                    if (getRacketXC() + getRacketSpeed() * deltaT * getCoefC() > (getWidth() / 2) - 30.0) {
                        setRacketXC((getWidth() / 2) - 30.0);
                    }
                } else if (getBallX() < getRacketXC() + getWidth()) {
                    setRacketXC(getRacketXC() - getRacketSpeed() * deltaT * getCoefC());
                    if (getRacketXC() < (-getWidth() / 2) + 30.0) {
                        setRacketXC((-getWidth() / 2) + 30.0);
                    }
                }

            }
        }


         if(getNbrej()>=4) {
             if (!AI[3]) {
                 switch (players[3].getState()) {
                     case GOING_LEFT:
                         setRacketXD(getRacketXD() - getRacketSpeed() * deltaT );
                         if ((getRacketXD() <-(super.getWidth()/2-20.0))) {
                             setRacketXD(-(super.getWidth()/2-20.0));
                         }
                         break;
                     case GOING_RIGHT:
                         setRacketXD(getRacketXD() + getRacketSpeed() * deltaT);
                         if (getRacketXD() > getWidth()/2-20.0) {
                             setRacketXD(getWidth()/2-20.0);
                         }
                     default:
                         break;

                 }
             }else {
                 setCoefD(0.7);
                 if (getBallX() > getRacketXD() + getWidth() / 2) {
                     setRacketXD(getRacketXD() + getRacketSpeed() * deltaT * getCoefD());
                     if (getRacketXD() + getRacketSpeed() * deltaT * getCoefD() > (getWidth() / 2) - 30.0) {
                         setRacketXD((getWidth() / 2) - 30.0);
                     }
                 } else if (getBallX() < getRacketXD() + getWidth()) {
                     setRacketXD(getRacketXD() - getRacketSpeed() * deltaT * getCoefD());
                     if (getRacketXD() < (-getWidth() / 2) + 30.0) {
                         setRacketXD((-getWidth() / 2) + 30.0);
                     }
                 }

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
