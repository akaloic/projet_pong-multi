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

public class TimeOut extends View{
	private final Text timeDisplay;
    private final Button again;
    private final Button menu;
    private final Timer t;
    private static int timeLeft  = 5;
    
    public TimeOut(Court court, Pane root, double scale, SceneHandler sceneHandler, String joueur, String typePartie) {

        super(court, root, scale, sceneHandler);
        root.setMinWidth(court.getWidth() * scale + 2 * getMargin());
        root.setMinHeight(court.getHeight() * scale);
        
        this.menu = new Button("Menu");
        this.menu.setLayoutX(((court.getWidth() / 2) * scale) - 5);
        this.menu.setLayoutY(((court.getHeight() / 2) * scale) + 100);
        this.menu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        this.menu.setOnAction(event -> sceneHandler.switchToMenu(getRoot()));

        this.again = new Button();
        this.again.setText("Rejouer");
        this.again.setLayoutX(((court.getWidth() / 2) * scale) - 20);
        this.again.setLayoutY(((court.getHeight() / 2) * scale) + 200);
        this.again.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        if (typePartie.equals("Robot"))  this.again.setOnAction(event -> sceneHandler.switchToGameRobot(getRoot()));
        else  this.again.setOnAction(event -> sceneHandler.switchToGame(getRoot()));
        
        timeDisplay.setX((court.getWidth() / 3) * scale + getMargin() / 2);
        timeDisplay.setY(35);
        timeDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        timeDisplay.setFill(Color.RED);
        
        getRoot().getChildren().addAll(ball, score, timeDisplay, restart); // On ajoute le score aux éléments du Pane
        timerStart(task);

        /*Image image=new Image(MenuView.class.getResourceAsStream("./onepicebg.jpg"));
        ImageView backg=new ImageView(image);
        backg.setFitWidth(root.getMinWidth());
        backg.setFitHeight(root.getMinHeight());

         */
    }
        public void animate() {
	        if(getRoot().getChildren().contains(again)) {
	        	getRoot().getChildren().remove(again); // si oui , on enleve les boutons
	            getRoot().getChildren().remove(menu);
	            timer.start();  // on lance le timer 
	        }
	        timer.start();  // on lance le timer
        }

        public void timerStart(TimerTask task) {
        	t.scheduleAtFixedRate(task, 1000, 1000);
        }

	
}
