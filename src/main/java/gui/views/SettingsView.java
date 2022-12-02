package gui.views;

import java.io.File;

import gui.SceneHandler;
import gui.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sound.AudioBank;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class SettingsView extends View { // Classe similaire à GameView.java & MenuView.java avec des éléments
                                         // différents

        private final Text reglages;
        private final Button exit;
        private final Button son;

        private Slider slVolume;
        private final Button playButton;

        //choisir le fond
        private final Text background;
        private final Button background1;

        // Darktheme
        private final Button darktheme;
        public static boolean darkthemetest = false; // Couleur du fond blanc

        // Raquette moduable
        private final Text tailleRaquette;
        private final Button raquetteSmall;
        private final Button raquetteMedium;
        private final Button raquetteLarge;

        public SettingsView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
                super(court, root, scale, sceneHandler);

                root.setMinWidth(court.getWidth() * scale + 2 * getMargin());
                root.setMinHeight(court.getHeight() * scale);

                reglages = new Text();
                reglages.setX(((court.getWidth() / 2) * scale) - 80);
                reglages.setY((court.getWidth() / 10) * scale);
                reglages.setTextAlignment(TextAlignment.CENTER);
                reglages.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
                reglages.setFill(SceneHandler.itemcolor);
                reglages.setText("Réglages");

                exit = new Button("Exit");
                exit.setLayoutX(((court.getWidth() / 2) * scale) - 5);
                exit.setLayoutY(((court.getHeight() / 2) * scale) + 230);
                exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                exit.setOnAction(event -> {
                        AudioBank.volume = slVolume.getValue();
                        sceneHandler.switchToMenu(getRoot());
                });

                son = new Button();
                son.setText("On");
                son.setLayoutX(1050);
                son.setLayoutY(10);
                son.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                son.setOnAction(event -> son.setText(sceneHandler.switchSonButton(son)));

                darktheme = new Button("Darktheme");
                darktheme.setLayoutX(((court.getWidth() / 2) * scale - 30));
                darktheme.setLayoutY(((court.getHeight() / 2) * scale));
                darktheme.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                darktheme.setOnAction(event -> {
                        if (!darkthemetest) {
                                SceneHandler.itemcolor = Color.WHITE;
                                darkthemetest = true;
                        } else {
                                SceneHandler.itemcolor = Color.BLACK;
                                darkthemetest = false;
                        }
                        sceneHandler.refreshSettings(getRoot());
                });

                playButton = new Button("Pause");
                playButton.setLayoutX(((court.getWidth() / 2) * scale - 50));
                playButton.setLayoutY(((court.getHeight() / 2) * scale) + 170);
                playButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
                playButton.setOnAction(event -> {
                        if (playButton.getText().equals("Pause")) {
                                AudioBank.menuSongPlayer.pause();
                                playButton.setText("Play");
                        } else {
                                AudioBank.menuSongPlayer.play();
                                playButton.setText("Pause");
                        }

                });

                slVolume = new Slider();
                slVolume.setLayoutX(((court.getWidth() / 2) * scale) + 10);
                slVolume.setLayoutY(((court.getHeight() / 2) * scale) + 170);
                slVolume.setPrefWidth(150);
                slVolume.setMaxWidth(Region.USE_PREF_SIZE);
                slVolume.setMinWidth(30);
                slVolume.setValue(AudioBank.volume);
                AudioBank.menuSongPlayer.volumeProperty().bind(slVolume.valueProperty().divide(100));

                //choisir le fond
                Image image=new Image(MenuView.class.getResourceAsStream("./playerbg.jpg"));
                ImageView backg=new ImageView(image);
                backg.setFitWidth(root.getMinWidth());
                backg.setFitHeight(root.getMinHeight());

                background = new Text();
                background.setX(((court.getWidth() / 2) * scale) - 80);
                background.setY(((court.getWidth() / 5) * scale ) + 175);
                background.setTextAlignment(TextAlignment.CENTER);
                background.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                background.setFill(SceneHandler.itemcolor);
                background.setText("Background");

                background1 = new Button();
                background1.setPrefSize(200,100);
                backg.setFitHeight(100);
                backg.setPreserveRatio(true);
                background1.setGraphic(backg);




                // Raquette Taille
                tailleRaquette = new Text();
                tailleRaquette.setX(((court.getWidth() / 2) * scale) - 80);
                tailleRaquette.setY((court.getWidth() / 5) * scale);
                tailleRaquette.setTextAlignment(TextAlignment.CENTER);
                tailleRaquette.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                tailleRaquette.setFill(SceneHandler.itemcolor);
                tailleRaquette.setText("Racket Size");

                raquetteSmall = new Button();
                raquetteSmall.setText("Small");
                raquetteSmall.setLayoutX(((court.getWidth() / 2) * scale) - 80);
                raquetteSmall.setLayoutY(250);
                raquetteSmall.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteSmall.setOnAction(event -> {
                        PlayerNumber.activateSmall();
                        /* sceneHandler.switchToGameR(getRoot(), 75.0); makePauseFalse(); */ });

                raquetteMedium = new Button();
                raquetteMedium.setText("Medium");
                raquetteMedium.setLayoutX(((court.getWidth() / 2) * scale) + 11);
                raquetteMedium.setLayoutY(250);
                raquetteMedium.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteMedium.setOnAction(event -> {
                        PlayerNumber.activateMedium();
                        /* sceneHandler.switchToGameR(getRoot(), 100.0); makePauseFalse(); */ });

                raquetteLarge = new Button();
                raquetteLarge.setText("Large");
                raquetteLarge.setLayoutX(((court.getWidth() / 2) * scale) + 120);
                raquetteLarge.setLayoutY(250);
                raquetteLarge.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteLarge.setOnAction(event -> {
                        PlayerNumber.activateLarge();
                        /*
                         * sceneHandler.switchToGameR(getRoot(), 150.0); makePauseFalse();
                         * raquetteLarge.setStyle("-fx-background-color: #00ff00");
                         */ });

                // on change la coleur du bouton qui change la taille de la raquette pour que ca
                // soit plus clair quelle taille est actuellement choisie

                if (PlayerNumber.getRacketSmall())
                        raquetteSmall.setStyle("-fx-background-color: #FDFCCB");
                else if (PlayerNumber.getRacketMedium())
                        raquetteMedium.setStyle("-fx-background-color: #FDFCCB");
                else if (PlayerNumber.getRacketLarge())
                        raquetteLarge.setStyle("-fx-background-color: #FDFCCB");

                getRoot().getChildren().addAll(reglages, exit, son, darktheme, playButton, slVolume,
                                tailleRaquette,
                                raquetteSmall,
                                raquetteMedium,
                                raquetteLarge, background, background1); // On ajoute le title et les boutons aux éléments
                // du Pane

        }

}
