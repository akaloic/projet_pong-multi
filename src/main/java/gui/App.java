package gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("JeuPong");
    	Image image=new Image(App.class.getResourceAsStream("./icon.png"));
    	primaryStage.getIcons().add(image);
    	primaryStage.setMinHeight(600);
    	primaryStage.setMinWidth(1000);
    	primaryStage.setResizable(false);
    	SceneHandler sceneHandler = new SceneHandler(primaryStage);
        sceneHandler.setMenuScene();
        
       
    }
}
