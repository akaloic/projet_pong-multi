package gui;

import java.nio.file.Paths;


import gui.entities.Player;
import gui.views.GameRobotView;
import gui.views.GameView;
import gui.views.MenuView;
import gui.views.PlayerNumber;
import gui.views.SettingsView;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ControlHandler;
import model.Court;
import model.courts.CourtMulti;
import model.courts.CourtRobot;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SceneHandler { // Cette classe permet de manipuler les scènes courrantes sans à avoir besoin de
                            // réecrire tout le code à chaque fois
    private Stage stage;
    private Scene scene;
    private Pane root;
    private View view;
    private Court court;
    private Player []players;

    public SceneHandler(Stage stage) { // On prends les playerA et playerB en argument
                                                                       // pour pouvoir les redistribuer sur les menuView
                                                                       // / gameView
        this.root = new Pane();
        this.scene = new Scene(root);
        this.stage = stage;
      
        
    
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

  public void setGameScene() {
        court = new CourtMulti(players, 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher
                                                            // le jeu.
        var gameView = new GameView(court, root, 1.0, this,players.length);
       
        stage.setScene(scene);
        stage.show();
        gameView.animate();
        
    }
  public void switchToGame(Pane menuRoot,int n) { // methode qui passe de ViewplayerNumber à GameView
	  this.setPlayers(n);
	  switch (n) {
		  case 1:this.switchToGameRobot(menuRoot);
		  break;
		  default:this.switchToGame(menuRoot);
	  
	  }
	  
  }



    public void switchToGame(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        
        court = new CourtMulti(players,1000, 600);
        
        ControlHandler controlHandler = new ControlHandler(players,this);
        controlHandler.getInput();
        view = new GameView(court, root, 1.0, this,players.length);
        stage.setScene(scene);
       
        //Image image =new Image(getClass().getResourceAsStream(""+Paths.get("icon.png")));
       
        
        System.out.println(Paths.get("icon.png"));
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameRobot(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        court = new CourtRobot (this.players[0], 1000, 600);
        var gameView = new GameRobotView(court, root, 1.0, this);
        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput();
        stage.setScene(scene);
        stage.show();
        gameView.animate();
    }    

    public void switchToSettings(Pane menuRoot) { // Méthode permettant de passer de menu à Settings
        menuRoot.getChildren().clear();
        var court = new Court(1000, 600);
        view = new SettingsView(court, root, 1.0, this);
       
        stage.setScene(scene);
      
        stage.show();
    }

    public void switchToMenu(Pane settingsRoot) { // Méthode permettant de passer de Settings à menu
        settingsRoot.getChildren().clear();
        court = new Court(1000, 600);
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }
 

    public String switchSonButton(Button b){
        if (b.getText().equals("On")) return "Off";
        return "On";
    }
  
    public void setMenuScene() {
        court = new Court( 1000, 600);
        //root.setStyle("-fx-background-color: #FF0000"); //Changement couleure bg
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoNbreJoueur(Pane menuRoot) {
    	menuRoot.getChildren().clear();
    	var court = new Court(1000, 600);
    	var view=new PlayerNumber(court,root,1.0,this);
    	view.animate();
    	
    }
    public void setPlayers(int n) {
    	this.players=new Player[n];
    	for(int i=0;i<n;i++) {
    		this.players[i]=new Player();
    	}
    }

    


}

