package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;

public class WinView extends View{

    private final Text gagnant;
    private final Button again;
    private final Button menu;

    public WinView(Court court, Pane root, double scale, SceneHandler sceneHandler, String joueur, String typePartie) {

        super(court, root, scale, sceneHandler);
        root.setMinWidth(court.getWidth() * scale + 2 * getMargin());
        root.setMinHeight(court.getHeight() * scale);

        String qui = "";
        switch (joueur){
            case "A": qui="le joueur A"; break;
            case "B": qui="le joueur B"; break;
            default: qui="le robot";
        }

        this.gagnant = new Text();
        this.gagnant.setX(((court.getWidth() / 2) * scale) - 200);
        this.gagnant.setY((court.getWidth() / 10) * scale);
        this.gagnant.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        this.gagnant.setFill(Color.BLACK);
        this.gagnant.setText("Le gagnant est: "+qui+" !");  //bug ne s'affiche qu'apres quelques secondes

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

        /*Image image=new Image(MenuView.class.getResourceAsStream("./onepicebg.jpg"));
        ImageView backg=new ImageView(image);
        backg.setFitWidth(root.getMinWidth());
        backg.setFitHeight(root.getMinHeight());

         */

        getRoot().getChildren().addAll(gagnant, again, menu);


    }
}

