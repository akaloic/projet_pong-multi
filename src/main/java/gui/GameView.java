package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;
import java.util.Timer;
import java.util.TimerTask;

public class GameView {
    // class parameters
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0; // pixels
    
    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    private final Text score;
    private final Text timeDisplay; //pour afficher le timer
    private final Timer t;
    private static int timeLeft  = 10;
    private Button continu;
    private final AnimationTimer timer=new AnimationTimer() {
        long last = 0;

        @Override
        public void handle(long now) {
            if (last == 0) { // ignore the first tick, just compute the first deltaT
                last = now;
                return;
            }
            court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
            last = now;
            racketA.setY(court.getRacketA() * scale);
            racketA.setX(xMargin - racketThickness+court.getRacketXA());
            racketB.setY(court.getRacketB() * scale);
            racketB.setX(court.getWidth() * 	scale + xMargin + court.getRacketXB());
           
            ball.setCenterX(court.getBallX() * scale + xMargin);
            ball.setCenterY(court.getBallY() * scale);
            score.setText(court.getScoreA() + " - " + court.getScoreB()); // On ajoute le score à animate() pour que
                                                                    // le texte s'actualise quand un des
                                                                          // joueurs marque
            
            if(Player.getPause()) {     // si le champs boolean pause est vrai 
            	this.stop();            //on arrete le timer pour faire une pause du scene
            	this.last=0;            // comme le temps continue de s'avancer , il faut réunitialiser last en 0 pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au meme moment que là où on arrete
            	gameRoot.getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
            }                    
        }
    };


    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout
     *              ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre
     *              de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;
        this.timeDisplay = new Text();
        
        this.t = new Timer();

        final TimerTask task = new TimerTask() {
            public void run() {
                if(timeLeft > 0) {
                    timeDisplay.setText(String.valueOf(timeLeft));
                    if(Player.getPause()) {
                    	 timeDisplay.setText(String.valueOf(timeLeft));
                    }
                    else {
                    	timeLeft--;
                    }
                }
                else {
                    timeDisplay.setText("Fin du jeu !");
                    t.cancel();
                }
            }  
        };

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX((court.getWidth() / 2) * scale + xMargin / 2); // Petite modification pour mieux placer le score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(Color.BLACK);
        score.setText(this.court.getScoreA() + " - " + this.court.getScoreB());
       
        timeDisplay.setX((court.getWidth() / 3) * scale + xMargin / 2);
        timeDisplay.setY(35);
        timeDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        timeDisplay.setFill(Color.RED);

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.BLACK);
        
        racketA.setX(xMargin - racketThickness+court.getRacketXA());
        racketA.setY(court.getRacketA() * scale);
        
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.BLACK);

        racketB.setX(court.getWidth() * scale + xMargin + court.getRacketXB());
        racketB.setY(court.getRacketB() * scale);

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);
        continu=new Button("Continue");
        continu.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        continu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        continu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        continu.setOnAction(event->animate());
        gameRoot.getChildren().addAll(racketA, racketB, ball, score, timeDisplay); // On ajoute le score aux éléments du Pane
        timerStart(task);
    }
   

    public void animate() {
       if(gameRoot.getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
    	   gameRoot.getChildren().remove(continu); // si oui , on enleve le bouton 
    	   Player.pauseORcontinue();  // et on met aussi le champs boolean pause en false pour préparer à la prochaine demande de pause
       }
       timer.start();  // on lance le timer    
    }
    
    public void timerStart(TimerTask task) {
    	t.scheduleAtFixedRate(task, 1000, 1000);
    }
}
