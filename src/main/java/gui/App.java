package gui;



import java.nio.file.Paths;

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
    	Image image=new Image(App.class.getResourceAsStream("./icon.png"));
    	primaryStage.getIcons().add(image);
    	SceneHandler sceneHandler = new SceneHandler(primaryStage);
        sceneHandler.setMenuScene();
        
       
    }
}
