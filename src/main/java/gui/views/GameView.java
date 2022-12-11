package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class GameView extends View {
        private final Rectangle racketA, racketB, racketC, racketD;
        private  boolean[]AI=new boolean[4];   //tab pour indiquer si la raquette est controlé par machine ou joueur
        private int nbreRacket;
        private final Circle ball;
    //    private final Text score;
        private final Text lifePlayerLeft;
        private final Text lifePlayerR;
        private final Text lifePlayerU;
        private final Text lifePlayerD;
        private final Text timeDisplay;
        private final Text timeOut;
        private static int timeLeft = 5;
        private Timer temps;
        private Button continu;
        private Line separateur;
        private Region border;
        private Button menu;
        private Button replay;
        private final Label[] commande = new Label[5];
        private static double opacity = 1;
        
        //FAIS CA STP : public PlayerNumber(Court court,Pane root,double scale,SceneHandler scenehandler)
        private final AnimationTimer timer = new AnimationTimer() {
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
                        if(!AI[0]){
                            racketA.setY(getCourt().getRacketA() * getScale());
                            racketA.setX(getMargin() - getRacketThickness() + getCourt().getRacketXA());
                        }else{
                            racketA.setX(getMargin() - getRacketThickness());
                            if (getCourt().getRacketA() * getScale() + getMargin() - 65 > 500) racketA.setY(500);
                            else racketA.setY(getCourt().getRacketA() * getScale() + getMargin() - 65);

                        }
                         if(!AI[1] && nbreRacket>=2){
                               racketB.setY(getCourt().getRacketB() * getScale());
                               racketB.setX(getCourt().getWidth() * getScale() + getMargin() + getCourt().getRacketXB());
                         }else{
                               racketB.setX(getCourt().getWidth() * getScale() + getMargin());
                               if (getCourt().getRacketB() * getScale() + getMargin() - 65 > 500) racketB.setY(500);
                               else racketB.setY(getCourt().getRacketB() * getScale() + getMargin() - 65);  //ici racketB est AI

                         }

                        if (racketC != null) {
                                racketC.setX(getCourt().getWidth() / 2 * getScale() + getCourt().getRacketXC());
                        }
                        if (racketD != null) {
                                racketD.setX(getCourt().getWidth() / 2 * getScale() + getCourt().getRacketXD());
                             
                        }

                        ball.setCenterX(getCourt().getBallX() * getScale() + getMargin());
                        ball.setCenterY(getCourt().getBallY() * getScale());
                        // score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à                                                                      // pour que
                                                                                       // le texte s'actualise quand un des

                        lifePlayerLeft.setText("PlayerA : "+getCourt().getScoreA());
                        lifePlayerR.setText("PlayerB : "+getCourt().getScoreB());
                        lifePlayerU.setText("PlayerC : "+getCourt().getScoreC());
                        lifePlayerD.setText("PlayerD : "+getCourt().getScoreD());
                        if(getCourt().getScoreA()==0) {
                            if(getRoot().getChildren().contains(lifePlayerLeft)) {
                                getRoot().getChildren().removeAll(lifePlayerLeft,racketA);
                                getCourt().setRacketA(-30);
                            }
                        }
                        if(getCourt().getScoreB()==0) {
                            if(getRoot().getChildren().contains(lifePlayerR)) {
                                getRoot().getChildren().removeAll(lifePlayerR,racketB);
                                getCourt().setRacketB(-30);
                            }
                        }
                        if(getCourt().getScoreC()==0) {
                            if(getRoot().getChildren().contains(lifePlayerU)) {
                                getRoot().getChildren().removeAll(lifePlayerU,racketC);
                                getCourt().setRacketXC(-30);
                            }
                        }
                        if(getCourt().getScoreD()==0) {
                            if(getRoot().getChildren().contains(lifePlayerD)) {
                                getRoot().getChildren().removeAll(lifePlayerD,racketD);
                                getCourt().setRacketXD(-30);
                            }
                        }


                        /*
                        if (getCourt().getScoreA() == 0 && getCourt().getScoreB() == 0 && getCourt().getScoreC() == 0 && getCourt().getScoreD() == 0) {
                                timer.stop();
                                getRoot().getChildren().removeAll(racketA, racketB, racketC, racketD, ball, lifePlayerLeft, lifePlayerR, lifePlayerU, lifePlayerD);
                                getRoot().getChildren().addAll(continu, menu);
                                continu.setOpacity(opacity);
                                menu.setOpacity(opacity);
                                opacity -= 0.01;
                                if (opacity <= 0) {
                                        opacity = 1;
                                        timer.stop();
                                }
                        }

                         */
                        /*
                        if (getCourt().getScoreA() == 10 || getCourt().getScoreB() == 10){
                            if (getCourt().getScoreA() == 10) getSceneHandler().switchToPageWin(getRoot(), "A", "Robot");
                            else getSceneHandler().switchToPageWin(getRoot(), "Robot", "Robot");
                            stop();
                        }

                         */


                        if (getPause()) { // si le champs boolean pause est vrai
                                    this.stop(); // on arrete le timer pour faire une pause du scene
                                    this.last = 0; // comme le temps continue de s'avancer , il faut réunitialiser last en 0
                                                   // pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au moment que là où on arrete
                                    getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
                                    getRoot().getChildren().add(menu);
                        }
                        
                        else if(timeLeft == (-1)) { //Si le temps est 0, on affiche les boutons Restart et Menu
                        	this.stop();
                        	this.last = 0;
                        	getRoot().getChildren().add(replay); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
                            getRoot().getChildren().add(menu);
                        }
                }
        };
        
        public void decrementeNbreRacket() {
        	
        }


        public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler, int nbreracket,boolean[]AI) {
                super(court, root, scale, sceneHandler);
                this.timeDisplay = new Text();
                this.timeOut = new Text();
                this.temps = new Timer(); 	//Initialiser le timer

                final TimerTask task = new TimerTask() {
                    public void run() {
                        if(timeLeft >= 0) {	//Tant qu'on a un temps positif : on décrémente ce temps
                            timeDisplay.setText(String.valueOf(timeLeft));
                            timeLeft--;
                        }
                        else {
                        	timeOut.setText("TIME OUT"); //Arrivant à zéro, le temps s'arrête
                        	temps.cancel();
                        }
                    }
                };
                
                this.nbreRacket=nbreracket;
                this.AI=AI;  // initialiser l'attribut avec le tab qui est passée par PlayerNumberView
                Image image = new Image(MenuView.class.getResourceAsStream("./onepicebg.jpg"));
                ImageView backg = new ImageView(image);
                backg.setFitWidth(root.getMinWidth());
                backg.setFitHeight(root.getMinHeight());
                double milieuX = court.getWidth() / 2 + getMargin();
                double milieuY=court.getHeight()/2;
                separateur = new Line(milieuX, 45, milieuX, court.getHeight() - 25);
                separateur.getStrokeDashArray().addAll(35d, 30d);
                border = new Region();
                border.setMinHeight(court.getHeight() - getMargin());
                border.setMinWidth(court.getWidth() + 2 * super.getRacketThickness());
                border.setLayoutX(getMargin() - super.getRacketThickness());
                border.setLayoutY(getMargin());
                border.setStyle("-fx-border-style:solid;-fx-border-color:black;-fx-border-width:4");
                getRoot().getChildren().addAll(backg, border);
        /*        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
                score.setX((court.getWidth() / 2) * scale + getMargin() / 2 - 10); // Petite modification pour mieux
                                                                                   // placer le score.
                score.setY(35);
                score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                score.setFill(SceneHandler.itemcolor);
                score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());*/
                lifePlayerLeft=new Text("PlayerA : "+getCourt().getScoreA());
                lifePlayerLeft.setLayoutY(milieuY-20);
                lifePlayerLeft.setLayoutX(100);
                lifePlayerLeft.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
                lifePlayerR=new Text("PlayerB : "+getCourt().getScoreB());
                lifePlayerR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
                lifePlayerR.setFill(Color.GREY);
                lifePlayerR.setLayoutX(getCourt().getWidth()-100);
                lifePlayerR.setLayoutY(milieuY-20);
                lifePlayerU=new Text("PlayerC : "+getCourt().getScoreC());
                lifePlayerU.setLayoutX(milieuX);
                lifePlayerU.setLayoutY(100);
                lifePlayerU.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
                lifePlayerU.setFill(Color.BLUE);
                lifePlayerD=new Text("PlayerD : "+getCourt().getScoreD());
                lifePlayerD.setLayoutX(milieuX);
                lifePlayerD.setLayoutY(getCourt().getHeight()-50);
                lifePlayerD.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
                lifePlayerD.setFill(Color.RED);
                racketA = new Rectangle();
                racketA.setHeight(court.getRacketSize() * scale);
                racketA.setWidth(getRacketThickness());
                racketA.setFill(Color.BLACK);

               racketA.setX(getMargin() - getRacketThickness() + court.getRacketXA());
               racketA.setY(court.getRacketA() * scale);
               racketB = new Rectangle();
               racketB.setHeight(court.getRacketSize() * scale);
               racketB.setWidth(getRacketThickness());
               racketB.setFill(Color.GREY);
               racketB.setX(court.getWidth() * scale + getMargin() + court.getRacketXB());
               racketB.setY(court.getRacketB() * scale);
               getRoot().getChildren().addAll(racketA,racketB, separateur);
                if (nbreracket >= 3) { // creation de racket C lorsque le nobre de joueur est 3 ou plus
                        racketC = new Rectangle();
                        racketC.setHeight(super.getRacketThickness());
                        racketC.setWidth(court.getRacketSize() * scale);
                        racketC.setFill(Color.BLUE);
                        racketC.setX(court.getWidth() / 2 * scale + court.getRacketXC());
                        racketC.setY(getMargin());
                        getRoot().getChildren().addAll(racketC,lifePlayerU);
                        getRoot().getChildren().remove(separateur);
                } else {
                        racketC = null;

                }
                if (nbreracket >= 4) { // creation de racket D lorsque le nobre de joueur est 4
                        racketD = new Rectangle();
                        racketD.setHeight(super.getRacketThickness());
                        racketD.setWidth(court.getRacketSize() * scale);
                        racketD.setFill(Color.RED);
                        racketD.setX(court.getWidth() / 2 * scale + court.getRacketXD());
                        racketD.setY(court.getHeight() - super.getRacketThickness());
                        getRoot().getChildren().addAll(racketD,lifePlayerD);
                } else {
                        racketD = null;

                }

                ball = new Circle();
                ball.setRadius(court.getBallRadius());
                ball.setFill(Color.BLACK);
                ball.setCenterX(court.getBallX() * scale + getMargin());
                ball.setCenterY(court.getBallY() * scale);
                continu = new Button("Continue");
                continu.setLayoutX(((court.getWidth() / 2) * scale) - 120);
                continu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
                continu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                continu.setOnAction(event -> animate());

                menu = new Button("Menu");
                menu.setLayoutX(((court.getWidth() / 2) * scale) + 120);
                menu.setLayoutY(((court.getHeight() / 2) * scale));
                menu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                menu.setOnAction(event -> sceneHandler.switchToMenu(getRoot()));
                
                timeDisplay.setX((court.getWidth() / 3) * scale + getMargin());
                timeDisplay.setY(35);
                timeDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                timeDisplay.setFill(Color.BLACK);
                timeDisplay.setStrokeWidth(2); 
                timeDisplay.setStroke(Color.CYAN);
                
                timeOut.setLayoutX(((court.getWidth() / 2) * scale) - 80 );
                timeOut.setLayoutY(((court.getHeight() / 2) * scale) - 60);
                timeOut.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
                timeOut.setFill(Color.BLACK);
                timeOut.setStrokeWidth(2); 
                timeOut.setStroke(Color.CYAN);
                
                replay = new Button("Replay");
                replay.setLayoutX(((court.getWidth() / 2) * scale) - 180);
                replay.setLayoutY(((court.getHeight() / 2) * scale));
                replay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                replay.setOnAction(event -> sceneHandler.switchToGame(getRoot()));
               // replay.setOnAction(event -> animate());


                commande[0] = new Label(" z : Monter pour joueur gauche ");
                commande[0].setLayoutX(court.getWidth()/2 - 85);
                commande[0].setLayoutY(200);
                commande[0].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
                commande[1] = new Label(" s : Descendre pour joueur gauche ");
                commande[1].setLayoutX(court.getWidth()/2 - 100);
                commande[1].setLayoutY(250);
                commande[1].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
                commande[2] = new Label(" p : Faire pause ");
                commande[2].setLayoutX(court.getWidth()/2 - 40);
                commande[2].setLayoutY(300);
                commande[2].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
                commande[3] = new Label(" ESC : Quitter le jeu ");
                commande[3].setLayoutX(court.getWidth()/2 - 50);
                commande[3].setLayoutY(350);
                commande[3].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
                commande[4] = new Label(" INSTRUCTIONS: ");
                commande[4].setLayoutX(court.getWidth()/2 - 40);
                commande[4].setLayoutY(120);
                commande[4].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
                
                

                getRoot().getChildren().addAll(ball,lifePlayerLeft,lifePlayerR, timeDisplay, timeOut,  replay, commande[0], commande[1], commande[2], commande[3], commande[4]); // On ajoute le score aux éléments du Pane
                compteARebour(task);
        }
        
        public void compteARebour(TimerTask task) {
            temps.scheduleAtFixedRate(task, 1000, 1000); // used to schedule the task for execution at the given time
       }

    public void animate() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                opacity -= 0.002;
                commande[0].opacityProperty().set(opacity);
                commande[1].opacityProperty().set(opacity);
                commande[2].opacityProperty().set(opacity);
                commande[3].opacityProperty().set(opacity);
                commande[4].opacityProperty().set(opacity);
                
                if (opacity <= 0) {
                    stop();
                }
            }
        }.start();
        timer.start();
        if (getRoot().getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
            getRoot().getChildren().remove(continu); // si oui , on enleve les boutons
            getRoot().getChildren().remove(menu);
            pauseORcontinue();
        }
        if(getRoot().getChildren().contains(replay)) {
        	getRoot().getChildren().remove(replay);
        	getRoot().getChildren().remove(menu);
        }
    }
}
