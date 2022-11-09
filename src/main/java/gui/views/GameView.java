package gui.views;

import gui.SceneHandler;
import gui.View;
import gui.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private Button continu;
    private final Label[] commande = new Label[7];
    private static double opacity = 1;

    private final AnimationTimer timer=new AnimationTimer() {
        long last = 0;

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
            racketB.setX(getCourt().getWidth() * getScale() - getXMargin() + getCourt().getRacketXB());

            ball.setCenterX(getCourt().getBallX() * getScale() + getXMargin());
            ball.setCenterY(getCourt().getBallY() * getScale());
            score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à animate() pour que
            // le texte s'actualise quand un des
            // joueurs marque

            if (Player.getPause()) {     // si le champs boolean pause est vrai
                this.stop();            //on arrete le timer pour faire une pause du scene
                this.last = 0;            // comme le temps continue de s'avancer , il faut réunitialiser last en 0 pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au meme moment que là où on arrete
                getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu

            }
            if (getCourt().getScoreA() == 10 || getCourt().getScoreB() == 10) {
                if (getCourt().getScoreA() == 10) getSceneHandler().switchToPageWin(getRoot(), "A", "Multi");
                else getSceneHandler().switchToPageWin(getRoot(), "B", "Multi");
                stop();
            }
        }
    };


   
    public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);

        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX(getCourt().getWidth()/2 * getScale() - 20); // Petite modification pour mieux placer le score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(Color.BLACK);
        score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());
       

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(getRacketThickness());
        racketA.setFill(Color.BLACK);
        
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.BLACK);

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

        commande[0] = new Label(" z : Monter ");
        commande[0].setLayoutX(200);
        commande[0].setLayoutY(450);
        commande[0].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[1] = new Label(" s : Descendre pour jouer gauche ");
        commande[1].setLayoutX(200);
        commande[1].setLayoutY(500);
        commande[1].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[2] = new Label(" ↑ : Monter ");
        commande[2].setLayoutX(1100);
        commande[2].setLayoutY(450);
        commande[2].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[3] = new Label(" ↓ : Descendre ");
        commande[3].setLayoutX(1100);
        commande[3].setLayoutY(500);
        commande[3].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[4] = new Label(" INSTRUCTIONS: ");
        commande[4].setLayoutX(court.getWidth()/2 - 90);
        commande[4].setLayoutY(120);
        commande[4].setStyle("-fx-text-fill:grey; -fx-font-size: 20;");
        commande[5] = new Label(" p : Faire pause ");
        commande[5].setLayoutX(court.getWidth()/2 - 80);
        commande[5].setLayoutY(200);
        commande[5].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");
        commande[6] = new Label(" ESC : Quitter le jeu ");
        commande[6].setLayoutX(court.getWidth()/2 - 90);
        commande[6].setLayoutY(250);
        commande[6].setStyle("-fx-border-color: grey; -fx-text-fill:grey; -fx-font-size: 20;");

        getRoot().getChildren().addAll(racketA, racketB, ball, score, commande[0], commande[1], commande[2], commande[3], commande[4], commande[5], commande[6]);

    }

    public void animate() {
        if(getRoot().getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
            getRoot().getChildren().remove(continu); // si oui , on enleve le bouton
            Player.pauseORcontinue();  // et on met aussi le champs boolean pause en false pour préparer à la prochaine demande de pause
        }

       timer.start();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                opacity -= 0.002;
                commande[0].opacityProperty().set(opacity);
                commande[1].opacityProperty().set(opacity);
                commande[2].opacityProperty().set(opacity);
                commande[3].opacityProperty().set(opacity);
                commande[4].opacityProperty().set(opacity);
                commande[5].opacityProperty().set(opacity);
                commande[6].opacityProperty().set(opacity);

                if (opacity <= 0){
                    stop();
                }
            }
        }.start();
    }
}

