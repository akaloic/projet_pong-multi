package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;
import model.courts.CourtMulti;

public class GameView extends View{

    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text score;
   
    public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
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
        racketA.setFill(Color.BLACK);
        
        racketA.setX(getXMargin() - getRacketThickness() + court.getRacketXA());
        racketA.setY(court.getRacketA() * scale);
        
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.BLACK);

        racketB.setX(court.getWidth() * scale + getXMargin() + court.getRacketXB());
        racketB.setY(court.getRacketB() * scale);

        ball = new Circle();
        ball.setRadius(((CourtMulti) court).getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(((CourtMulti) court).getBallX() * scale + getXMargin());
        ball.setCenterY(((CourtMulti) court).getBallY() * scale);

        getRoot().getChildren().addAll(racketA, racketB, ball, score); // On ajoute le score aux éléments du Pane

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
                ((CourtMulti) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(getCourt().getRacketA() * getScale());
                racketA.setX(getXMargin() - getRacketThickness() + getCourt().getRacketXA());
                racketB.setY(getCourt().getRacketB() * getScale());
                racketB.setX(getCourt().getWidth() * getScale() + getXMargin() + getCourt().getRacketXB());
               
                ball.setCenterX(((CourtMulti) getCourt()).getBallX() * getScale() + getXMargin());
                ball.setCenterY(((CourtMulti) getCourt()).getBallY() * getScale());
                score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à animate() pour que
                                                                              // le texte s'actualise quand un des
                                                                              // joueurs marque
            }
        }.start();
    }
}
