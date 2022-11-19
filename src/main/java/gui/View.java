package gui;

import java.awt.Color;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import model.Court;


public abstract class View {
    private final SceneHandler sceneHandler;
    private Court court;
    private final Pane root;
    private final double scale, Margin = 50.0, racketThickness = 20.0;
    private static boolean pause=false; //si vrai on met le timer stop sinon le jeu continue de jouer
    // donc false par défaut par conséquent le jeu marche normalement;
    public View (Court court, Pane root, double scale, SceneHandler sceneHandler) {
        this.court = court;
        this.root = root;
        this.scale = scale;
        this.sceneHandler = sceneHandler;
        root.setMinWidth(court.getWidth() * scale+2*Margin );
        root.setMinHeight(court.getHeight() * scale+2*Margin);  
        

    }


    public Court getCourt() {
        return court;
    }

    public double getMargin() {
        return Margin;
    }

    public double getRacketThickness() {
        return racketThickness;
    }

    public Pane getRoot() {
        return root;
    }

    public double getScale() {
        return scale;
    }

    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }
    public void setCourt(Court c) {
    	this.court=c;
    }
    public static void pauseORcontinue() {
    	pause=!pause;
    }
    public static boolean getPause() {
    	return pause;
    }
    
}
