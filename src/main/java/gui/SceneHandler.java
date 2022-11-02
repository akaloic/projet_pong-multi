package gui;

import gui.entities.Player;
import gui.views.GameRobotView;
import gui.views.GameView;
import gui.views.MenuView;
import gui.views.PlayerNumber;
import gui.views.SettingsView;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        court = new CourtMulti(players[0], players[1], 1000, 600); // Extrait du code qu'il y avait dans App.java pour afficher
                                                            // le jeu.
        var gameView = new GameView(court, root, 1.0, this);
       
        stage.setScene(scene);
        stage.show();
        gameView.animate();
        
    }
  public void switchToGame(Pane menuRoot,Stage s,int n) {
	  s.hide();
	  this.setPlayers(n);
	  switch (n) {
		  case 1:this.switchToGameRobot(menuRoot);
		  break;
		  case 2:this.switchToGame2(menuRoot);
		  break;
		  default:;
	  
	  }
	  
  }



    public void switchToGame2(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        
        court = new CourtMulti(players[0], players[1], 1000, 600);
        ControlHandler controlHandler = new ControlHandler(players,this);
        controlHandler.getInput2();
        
        view = new GameView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
        ((GameView) view).animate();
    }

    public void switchToGameRobot(Pane menuRoot) {
        menuRoot.getChildren().clear(); // On enlève tous les éléments qu'on a pu attribuer au Pane pour pouvoir ensuite
                                        // afficher le jeu sans problèmes.
        court = new CourtRobot (this.players[0], 1000, 600);
        var gameView = new GameRobotView(court, root, 1.0, this);
       // scene.getStylesheets().add(getClass().getResource("stylesheet1.css").toExternalForm());

        ControlHandler controlHandler = new ControlHandler(players, this);
        controlHandler.getInput1();
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
        root.setStyle(null);
        court = new Court(1000, 600);
        view = new MenuView(court, root, 1.0, this);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMenu(Pane settingsRoot,Stage s) { // Méthode permettant de passer de Settings à menu
        switchToMenu(settingsRoot);
        s.hide();
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
    	Stage stage2=new Stage();
    	stage2.initStyle(StageStyle.UNDECORATED);
    	 var court = new Court(450, 300);
    	 SceneHandler scenehandle2=new SceneHandler(stage2);
    	 var view=new PlayerNumber(court,root,1.0,this,scenehandle2);
    	 stage2.setScene(scene);
    	 stage2.show();
    	 stage2.centerOnScreen();
    	 view.animate();
    	
    }
    public void setPlayers(int n) {
    	this.players=new Player[n];
    	for(int i=0;i<n;i++) {
    		this.players[i]=new Player();
    	}
    }

    


}

