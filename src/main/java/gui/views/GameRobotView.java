package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;
import model.courts.CourtRobot;

public class GameRobotView extends View {

    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text score;
    private Button continu;
    private AnimationTimer timer =
    	new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                ((CourtRobot) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(getCourt().getRacketA() * getScale());
                racketA.setX(getXMargin() - getRacketThickness()+getCourt().getRacketXA());
                racketB.setY(getCourt().getBallY() * getScale() + getXMargin() - 65);
                racketB.setX(getCourt().getWidth() * getScale() + getXMargin());
                /*
                racketB.setY(court.getRacketB() * scale);
                racketB.setX(court.getWidth() * scale + xMargin + court.getRacketXB());
               
                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                */

                ball.setCenterX(getCourt().getBallX() * getScale() + getXMargin());
                ball.setCenterY(getCourt().getBallY() * getScale());
                score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à animate() pour que
                                                                              // le texte s'actualise quand un des
                                                                              // joueurs marque
                
                if(getPause()) {     // si le champs boolean pause est vrai 
                	this.stop();            //on arrete le timer pour faire une pause du scene
                	this.last=0;            // comme le temps continue de s'avancer , il faut réunitialiser last en 0 pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au meme moment que là où on arrete
                	getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
                	
                }
            }};
  

    public GameRobotView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);

        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX((court.getWidth() / 2) * scale + getXMargin() / 2); // Petite modification pour mieux placer le score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(Color.BLACK);
        score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());
       

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(getRacketThickness());
        racketA.setFill(Color.RED); //joueur 
        
        racketA.setX(getXMargin() - getRacketThickness()+court.getRacketXA());
        racketA.setY(court.getRacketA() * scale);
        
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.BLUE); //le robot

        racketB.setX(court.getWidth() * scale + getXMargin());
        racketB.setY(court.getBallY() * scale);

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + getXMargin());
        ball.setCenterY(court.getBallY() * scale);
        continu=new Button("Continue");
        continu.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        continu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        continu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        continu.setOnAction(event->animate());
        getRoot().getChildren().addAll(racketA, racketB, ball, score); // On ajoute le score aux éléments du Pane
    }

    public void animate() {
    	if(getRoot().getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
        	getRoot().getChildren().remove(continu); // si oui , on enleve le bouton 
     	    pauseORcontinue();  // et on met aussi le champs boolean pause en false pour préparer à la prochaine demande de pause
        }
        timer.start();
    }
}
