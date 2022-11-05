package model.courts;

import gui.entities.Player;
import model.Court;
import model.RacketController;

public class CourtMulti extends Court{
    // instance parameters
	private final RacketController []players;
    
    
    
    public CourtMulti(RacketController[]p, double width, double height) {
        super(width, height);
        this.players=p;
        reset();
        
    }

    

    public void update(double deltaT) { 
    	SpeedUp(deltaT); // fonction qui augmente la coeff de vitesse
        switch (this.players[0].getState()) {
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
                if (getRacketXA() > getWidth() / 2-20)
                    setRacketXA(getWidth() / 2-20);
                break;

        }

        switch (players[1].getState()) {
            case GOING_UP:
                setRacketB(getRacketB() - getRacketSpeed()* deltaT * getCoefB());
                if (getRacketB() < 0.0)
                    setRacketB(0.0);
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
                if (getRacketXB() < -(getWidth() / 2-20)) {
                    setRacketXB(-(getWidth() / 2-20));
                }
                break;
            case GOING_RIGHT:
                setRacketXB(getRacketXB() + getRacketSpeed() * deltaT * getCoefB());
                if (getRacketXB() > 0.0) {
                    setRacketXB(0.0);
                }

        }
        if(players.length>=3) {
        	switch (players[2].getState()) {
            case GOING_LEFT:
                setRacketXC(getRacketXC() - getRacketSpeed() * deltaT );
                if ((getRacketXC() <-(super.getWidth()/2-40.0))) {
                    setRacketXC(-(super.getWidth()/2-40.0));
                }
                break;
            case GOING_RIGHT:
                setRacketXC(getRacketXC() + getRacketSpeed() * deltaT);
                if (getRacketXC() > getWidth()/2-40.0) {
                    setRacketXC(getWidth()/2-40.0);
                }
			default:
				break;
        	
        }
         if(players.length>=4) {
        	 switch (players[3].getState()) {
        	 case GOING_LEFT:
                 setRacketXD(getRacketXD() - getRacketSpeed() * deltaT );
                 if ((getRacketXD() <-(super.getWidth()/2-40.0))) {
                     setRacketXD(-(super.getWidth()/2-40.0));
                 }
                 break;
             case GOING_RIGHT:
                 setRacketXD(getRacketXD() + getRacketSpeed() * deltaT);
                 if (getRacketXD() > getWidth()/2-40.0) {
                     setRacketXD(getWidth()/2-40.0);
                 }
 			default:
 				break;
        	 
        	 }
     
         }
      
    }     
       
        if (updateBall(deltaT))
            reset();
    }


}
