package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;

public class SettingsView extends View{ // Classe similaire à GameView.java & MenuView.java avec des éléments différents

    private final Text reglages;
    private final Button exit;
    private final Button son;

    //Raquette moduable
    private final Text tailleRaquette;
    private final Button raquetteSmall;
    private final Button raquetteMedium;
    private final Button raquetteLarge;




    public SettingsView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);

        root.setMinWidth(court.getWidth() * scale + 2 * getXMargin());
        root.setMinHeight(court.getHeight() * scale);

        reglages = new Text();
        reglages.setX(((court.getWidth() / 2) * scale) - 80);
        reglages.setY((court.getWidth() / 10) * scale);
        reglages.setTextAlignment(TextAlignment.CENTER);
        reglages.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        reglages.setFill(Color.BLACK);
        reglages.setText("Réglages");

        exit = new Button("Exit");
        exit.setLayoutX(((court.getWidth() / 2) * scale) - 5);
        exit.setLayoutY(((court.getHeight() / 2) * scale) + 100);
        exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        exit.setOnAction(event -> sceneHandler.switchToMenu(getRoot()));

        son = new Button();
        son.setText("On");
        son.setLayoutX(1050);
        son.setLayoutY(10);
        son.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        son.setOnAction(event -> son.setText(sceneHandler.switchSonButton(son)));

        //Raquette Taille
        tailleRaquette = new Text();
        tailleRaquette.setX(((court.getWidth() / 2) * scale) - 80);
        tailleRaquette.setY((court.getWidth() / 5) * scale);
        tailleRaquette.setTextAlignment(TextAlignment.CENTER);
        tailleRaquette.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        tailleRaquette.setFill(Color.BLACK);
        tailleRaquette.setText("Racket Size");

        raquetteSmall = new Button();
        raquetteSmall.setText("Small");
        raquetteSmall.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        raquetteSmall.setLayoutY(250);
        raquetteSmall.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        raquetteSmall.setOnAction(event -> sceneHandler.switchToGameR(getRoot(), 75.0));

        raquetteMedium = new Button();
        raquetteMedium.setText("Medium");
        raquetteMedium.setLayoutX(((court.getWidth() / 2) * scale) + 11);
        raquetteMedium.setLayoutY(250);
        raquetteMedium.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        raquetteMedium.setOnAction(event -> sceneHandler.switchToGame(getRoot()));

        raquetteLarge = new Button();
        raquetteLarge.setText("Large");
        raquetteLarge.setLayoutX(((court.getWidth() / 2) * scale) + 120);
        raquetteLarge.setLayoutY(250);
        raquetteLarge.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        raquetteLarge.setOnAction(event -> sceneHandler.switchToGameR(getRoot(), 150.0));



        getRoot().getChildren().addAll(reglages, exit, son, tailleRaquette, raquetteSmall, raquetteMedium, raquetteLarge); // On ajoute le title et les boutons aux éléments
        // du Pane

    }

}
