package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.CourtRobot;

public class GameRobotView {
    // class parameters
    private final CourtRobot courtRobot;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0; // pixels

    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text score;

    // bouton son
    private final Button son;
    private final SceneHandler sh;

    /**
     * @param courtRobot le "modèle" de cette vue (le terrain de jeu de raquettes et
     *                   tout
     *                   ce qu'il y a dessus)
     * @param root       le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *                   affiché
     * @param scale      le facteur d'échelle entre les distances du modèle et le
     *                   nombre
     *                   de pixels correspondants dans la vue
     */
    public GameRobotView(CourtRobot courtRobot, Pane root, double scale, SceneHandler sh) {
        this.courtRobot = courtRobot;
        this.gameRoot = root;
        this.scale = scale;
        this.sh = sh;

        root.setMinWidth(courtRobot.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(courtRobot.getHeight() * scale);

        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX((courtRobot.getWidth() / 2) * scale + xMargin / 2); // Petite modification pour mieux placer le
                                                                       // score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(SceneHandler.itemcolor);
        score.setText(this.courtRobot.getScoreA() + " - " + this.courtRobot.getScoreB());

        racketA = new Rectangle();
        racketA.setHeight(courtRobot.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.RED); // joueur

        racketA.setX(xMargin - racketThickness + courtRobot.getRacketXA());
        racketA.setY(courtRobot.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(courtRobot.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.BLUE); // le robot

        racketB.setX(courtRobot.getWidth() * scale + xMargin);

        racketB.setY(courtRobot.getBallY() * scale);

        ball = new Circle();
        ball.setRadius(courtRobot.getBallRadius());
        ball.setFill(SceneHandler.itemcolor);

        ball.setCenterX(courtRobot.getBallX() * scale + xMargin);
        ball.setCenterY(courtRobot.getBallY() * scale);

        son = new Button();
        son.setText("On");
        son.setLayoutX(1050);
        son.setLayoutY(10);
        son.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        son.setOnAction(event -> son.setText(sh.switchSonButton(son)));

        gameRoot.getChildren().addAll(racketA, racketB, ball, score, son); // On ajoute le score aux éléments du Pane

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
                courtRobot.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(courtRobot.getRacketA() * scale);
                racketA.setX(xMargin - racketThickness + courtRobot.getRacketXA());
                racketB.setY(courtRobot.getBallY() * scale + xMargin - 65);
                racketB.setX(courtRobot.getWidth() * scale + xMargin);
                /*
                 * racketB.setY(court.getRacketB() * scale);
                 * racketB.setX(court.getWidth() * scale + xMargin + court.getRacketXB());
                 * 
                 * ball.setCenterX(court.getBallX() * scale + xMargin);
                 * ball.setCenterY(court.getBallY() * scale);
                 */

                ball.setCenterX(courtRobot.getBallX() * scale + xMargin);
                ball.setCenterY(courtRobot.getBallY() * scale);
                score.setText(courtRobot.getScoreA() + " - " + courtRobot.getScoreB()); // On ajoute le score à
                                                                                        // animate() pour que
                // le texte s'actualise quand un des
                // joueurs marque
            }
        }.start();
    }
}
