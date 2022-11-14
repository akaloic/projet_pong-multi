package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
<<<<<<< HEAD
import javafx.scene.text.*;
=======
import javafx.scene.control.Label;
import javafx.scene.text.*;
import javafx.scene.text.Font;
>>>>>>> ajoutInstructions
import model.Court;
import model.courts.CourtRobot;

public class GameRobotView extends View {

    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text score;
<<<<<<< HEAD
=======
    private final Label[] commande = new Label[5];
    private static double opacity = 1;
>>>>>>> ajoutInstructions

    public GameRobotView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);


        root.setMinWidth(court.getWidth() * scale + 2 * getXMargin());
        root.setMinHeight(court.getHeight() * scale);

        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
<<<<<<< HEAD
        score.setX((court.getWidth() / 2) * scale + getXMargin() / 2); // Petite modification pour mieux placer le score.
=======
        score.setX(court.getWidth() / 2 - 70); // Petite modification pour mieux placer le score.
>>>>>>> ajoutInstructions
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(Color.BLACK);
        score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());
       

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(getRacketThickness());
<<<<<<< HEAD
        racketA.setFill(Color.RED); //joueur 
        
        racketA.setX(getXMargin() - getRacketThickness()+court.getRacketXA());
        racketA.setY(court.getRacketA() * scale);
=======
        racketA.setFill(Color.RED); //joueur
>>>>>>> ajoutInstructions
        
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.BLUE); //le robot

<<<<<<< HEAD
        racketB.setX(court.getWidth() * scale + getXMargin());
        racketB.setY(court.getBallY() * scale);

=======
>>>>>>> ajoutInstructions
        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

<<<<<<< HEAD
        ball.setCenterX(court.getBallX() * scale + getXMargin());
        ball.setCenterY(court.getBallY() * scale);



        getRoot().getChildren().addAll(racketA, racketB, ball, score); // On ajoute le score aux éléments du Pane

=======
        commande[0] = new Label(" z : Monter pour joueur gauche ");
        commande[0].setLayoutX(court.getWidth()/2 - 135);
        commande[0].setLayoutY(200);
        commande[0].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[1] = new Label(" s : Descendre pour joueur gauche ");
        commande[1].setLayoutX(court.getWidth()/2 - 150);
        commande[1].setLayoutY(250);
        commande[1].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[2] = new Label(" p : Faire pause ");
        commande[2].setLayoutX(court.getWidth()/2 - 90);
        commande[2].setLayoutY(300);
        commande[2].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[3] = new Label(" ESC : Quitter le jeu ");
        commande[3].setLayoutX(court.getWidth()/2 - 100);
        commande[3].setLayoutY(350);
        commande[3].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[4] = new Label(" INSTRUCTIONS: ");
        commande[4].setLayoutX(court.getWidth()/2 - 90);
        commande[4].setLayoutY(120);
        commande[4].setStyle("-fx-text-fill:grey; -fx-font-size: 20;");


        getRoot().getChildren().addAll(racketA, racketB, ball, score, commande[0], commande[1], commande[2], commande[3], commande[4]);


>>>>>>> ajoutInstructions
    }

    public void animate() {
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
<<<<<<< HEAD
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
=======

                racketA.setX(getXMargin() - getRacketThickness()+getCourt().getRacketXA());
                racketA.setY(getCourt().getRacketA() * getScale());

                racketB.setX(getCourt().getWidth() - getXMargin() - getRacketThickness() - getCourt().getRacketXB());
                racketB.setY(getCourt().getRacketB() * getScale() - 65);

                ball.setCenterX(getCourt().getBallX() * getScale() + getXMargin());
                ball.setCenterY(getCourt().getBallY() * getScale());

                score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à animate() pour que
                                                                              // le texte s'actualise quand un des
                                                                              // joueurs marque
                if (getCourt().getScoreA() == 10 || getCourt().getScoreB() == 10){
                    if (getCourt().getScoreA() == 10) getSceneHandler().switchToPageWin(getRoot(), "A", "Robot");
                    else getSceneHandler().switchToPageWin(getRoot(), "Robot", "Robot");
                    stop();
                }
            }
        }.start();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                opacity -= 0.002;
                commande[0].opacityProperty().set(opacity);
                commande[1].opacityProperty().set(opacity);
                commande[2].opacityProperty().set(opacity);
                commande[3].opacityProperty().set(opacity);
                commande[4].opacityProperty().set(opacity);

                if (opacity <= 0){
                    stop();
                }
>>>>>>> ajoutInstructions
            }
        }.start();
    }
}
