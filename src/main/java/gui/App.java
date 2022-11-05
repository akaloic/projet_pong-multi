package gui;



import gui.entities.Player;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ControlHandler;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("JeuPong");
    	primaryStage.setResizable(false);
    	//primaryStage.getIcons().add(new Image("file:ressource/icon.png"));  
    	SceneHandler sceneHandler = new SceneHandler(primaryStage);
        sceneHandler.setMenuScene();
        
       
    }
}
