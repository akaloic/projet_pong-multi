package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;
import model.courts.CourtMulti;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View{

    private final Rectangle racketA, racketB,racketC,racketD;
 
    private final Circle ball;
    private final Text score;
    private Button continu;
    private Line separateur;
    private Region border;
    private final Text timeDisplay; //pour afficher le timer
    private final Timer t;
    private static int timeLeft  = 5;

    private Button menu;
    private Button restart;

    private final AnimationTimer timer=new AnimationTimer() {
        long last = 0;
        
        @Override
        public void handle(long now) {
            if (last == 0) { // ignore the first tick, just compute the first deltaT
                last = now;
                return;
            }
            ((CourtMulti) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
            last = now;
            ((CourtMulti) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
            last = now;
            racketA.setY(getCourt().getRacketA() * getScale());
            racketA.setX(getMargin() - getRacketThickness() + getCourt().getRacketXA());
            racketB.setY(getCourt().getRacketB() * getScale());
            racketB.setX(getCourt().getWidth() * getScale() + getMargin() + getCourt().getRacketXB());
            if(racketC!=null) {
            	racketC.setX(getCourt().getWidth()/2*getScale()+getCourt().getRacketXC());
            }
            if(racketD!=null) {
            	racketD.setX(getCourt().getWidth()/2*getScale()+getCourt().getRacketXD());
            }
           
            ball.setCenterX(getCourt().getBallX() * getScale() + getMargin());
            ball.setCenterY(getCourt().getBallY() * getScale());
            score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à animate() pour que
                                                                          // le texte s'actualise quand un des
                                                                          // joueurs marque
            
            if(getPause()) {     // si le champs boolean pause est vrai 
            	this.stop();            //on arrete le timer pour faire une pause du scene
            	this.last=0;            // comme le temps continue de s'avancer , il faut réunitialiser last en 0 pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au meme moment que là où on arrete
            	getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
                getRoot().getChildren().add(menu);
            }
            
            else if(timeDisplay.getText().equals("FIN DU JEU !")) {
            	getRoot().getChildren().add(menu);
            	getRoot().getChildren().add(restart);
            }
                        
        }
        
    };
   
    public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler,int nbreracket) {
        super(court, root, scale, sceneHandler);
        this.timeDisplay = new Text();
        this.t = new Timer();
        final TimerTask task = new TimerTask() {
            public void run() {
                if(timeLeft > 0) {
                    timeDisplay.setText(String.valueOf(timeLeft));
                    if(View.getPause()) { //ICI ON A PLUS GETPAUSE
                    	 timeDisplay.setText(String.valueOf(timeLeft));
                    }
                    else {
                    	timeLeft--;
                    }
                }
                else{
                	timeDisplay.setText("FIN DU JEU !");
                    t.cancel();
                }
            }
        };
      

        Image image=new Image(MenuView.class.getResourceAsStream("./onepicebg.jpg"));
        ImageView backg=new ImageView(image);
        backg.setFitWidth(root.getMinWidth());
        backg.setFitHeight(root.getMinHeight());
        double milieu=court.getWidth()/2+getMargin();
        separateur=new Line(milieu,45,milieu,court.getHeight()-25);
        separateur.getStrokeDashArray().addAll(35d,30d);
        border=new Region();
        border.setMinHeight(court.getHeight()-getMargin());
        border.setMinWidth(court.getWidth()+2*super.getRacketThickness());
        border.setLayoutX(getMargin()-super.getRacketThickness());
        border.setLayoutY(getMargin());
        border.setStyle("-fx-border-style:solid;-fx-border-color:black;-fx-border-width:4");
        getRoot().getChildren().addAll(backg,border);
        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX((court.getWidth() / 2) * scale + getMargin() / 2-10); // Petite modification pour mieux placer le score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(Color.BLACK);
        score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());
        if(nbreracket>=1) {
        	racketA = new Rectangle();
            racketA.setHeight(court.getRacketSize() * scale);
            racketA.setWidth(getRacketThickness());
            racketA.setFill(Color.BLACK);
            racketA.setStroke(Color.CYAN);
            racketA.setStrokeWidth(2);
            
            racketA.setX(getMargin() - getRacketThickness() + court.getRacketXA());
            racketA.setY(court.getRacketA() * scale);
            getRoot().getChildren().addAll(racketA,separateur);
        }else {
        	racketA=null;
        }
        if(nbreracket>=2) {
        	 racketB = new Rectangle();
             racketB.setHeight(court.getRacketSize() * scale);
             racketB.setWidth(getRacketThickness());
             racketB.setFill(Color.GREY);
             racketB.setStroke(Color.CYAN);
             racketB.setStrokeWidth(2);
             racketB.setX(court.getWidth() * scale + getMargin() + court.getRacketXB());
             racketB.setY(court.getRacketB() * scale);
             getRoot().getChildren().add(racketB);
        }else {
        	racketB=null;
        	
        }
        if(nbreracket>=3) {                      //creation de racket C lorsque le nobre de joueur est 3 ou plus 
        	racketC= new Rectangle();
            racketC.setHeight(super.getRacketThickness());
            racketC.setWidth(court.getRacketSize() * scale);
            racketC.setFill(Color.BLUE);
            racketC.setX(court.getWidth()/2*scale+court.getRacketXC());
            racketC.setY(getMargin());
            getRoot().getChildren().add(racketC);
            getRoot().getChildren().remove(separateur);
       }else {
       	racketC=null;
       	
       }
        if(nbreracket>=4) {   //creation de racket D lorsque le nobre de joueur est 4
       	 racketD= new Rectangle();
            racketD.setHeight(super.getRacketThickness());
            racketD.setWidth(court.getRacketSize() * scale);
            racketD.setFill(Color.RED);
            racketD.setX(court.getWidth()/2 * scale + court.getRacketXD());
            racketD.setY(court.getHeight()-super.getRacketThickness());
            getRoot().getChildren().add(racketD);
       }else {
       	racketD=null;
       	
       }

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);
        ball.setCenterX(court.getBallX() * scale + getMargin());
        ball.setCenterY(court.getBallY() * scale);
        
        continu=new Button("Continue");
        continu.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        continu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        continu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        continu.setOnAction(event->animate());
        
        restart = new Button("Restart");
        restart.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        restart.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        restart.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        restart.setOnAction(event-> sceneHandler.switchToGame(getRoot(), nbreracket, PlayerNumber.getRacketSmall(),PlayerNumber.getRacketMedium(),PlayerNumber.getRacketLarge()));        

        menu=new Button("Menu");
        menu.setLayoutX(((court.getWidth() / 2) * scale) + 120);
        menu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        menu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        menu.setOnAction(event-> sceneHandler.switchToMenu(getRoot()));
                
        timeDisplay.setX((court.getWidth() / 3) * scale + getMargin() / 2);
        timeDisplay.setY(35);
        timeDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        timeDisplay.setFill(Color.RED);
        
        getRoot().getChildren().addAll(ball, score, timeDisplay); // On ajoute le score aux éléments du Pane
        timerStart(task);

    }
    public void animate() {
        if(getRoot().getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
        	getRoot().getChildren().remove(continu); // si oui , on enleve les boutons
            getRoot().getChildren().remove(menu);
            pauseORcontinue();  // et on met aussi le champs boolean pause en false pour préparer à la prochaine demande de pause
        }
        else if(getRoot().getChildren().contains(restart)) {
        	getRoot().getChildren().remove(restart); // si oui , on enleve les boutons
            getRoot().getChildren().remove(menu);
            timer.start();  // on lance le timer 
        }
        timer.start();  // on lance le timer 
     }

    public void timerStart(TimerTask task) {
    	t.scheduleAtFixedRate(task, 1000, 1000);
    }

}
