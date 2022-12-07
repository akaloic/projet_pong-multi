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

public class GameView extends View{

    private final Rectangle racketA, racketB,racketC,racketD;
 
    private final Circle ball;
    private final Text score;
    private Button continu;
    private Line separateur;
    private Region border;
 

    private Button menu;
    private final Label[] commande = new Label[5];
    private static double opacity = 1;
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

            if (getCourt().getScoreA() == 10 || getCourt().getScoreB() == 10){
                if (getCourt().getScoreA() == 10) getSceneHandler().switchToPageWin(getRoot(), "A", "2 joueurs");
                else getSceneHandler().switchToPageWin(getRoot(), "B", "2 joueurs");
                stop();
            }
            
            if(getPause()) {     // si le champs boolean pause est vrai 
            	this.stop();            //on arrete le timer pour faire une pause du scene
            	this.last=0;            // comme le temps continue de s'avancer , il faut réunitialiser last en 0 pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au meme moment que là où on arrete
            	getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton qui permet de relancer le jeu
                getRoot().getChildren().add(menu); 	
            }
            //SI TIMEDISPLAY EST FINI => AFFICHER DES BOUTONS
          
            
                        
        }
        
    };
   
    public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler,int nbreracket) {
        super(court, root, scale, sceneHandler);
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




        menu=new Button("Menu");
        menu.setLayoutX(((court.getWidth() / 2) * scale) + 120);
        menu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        menu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        menu.setOnAction(event-> sceneHandler.switchToMenu(getRoot()));
        getRoot().getChildren().addAll(ball, score, commande[0], commande[1], commande[2], commande[3], commande[4]);
        // On ajoute le score aux éléments du Pane
        
        
        
        //TIMEDISPLAY

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
        if(getRoot().getChildren().contains(continu)) {  // si on reprend / commence la partie il faut vérifier si le button continue existe
        	getRoot().getChildren().remove(continu); // si oui , on enleve les boutons
            getRoot().getChildren().remove(menu);
            pauseORcontinue();  // et on met aussi le champs boolean pause en false pour préparer à la prochaine demande de pause
        }
        timer.start();  // on lance le timer 
 
     }
    
    //IL Y A MOYEN QUE J'AILLE A TIMEOUT

    
}
