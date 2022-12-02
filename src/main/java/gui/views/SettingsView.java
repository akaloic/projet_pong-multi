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

        private final Button background2;

        private final Button background3;

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
                exit.setLayoutX(((court.getWidth() / 2) * scale) + 475);
                exit.setLayoutY(((court.getHeight() / 2) * scale) + 320);
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
                playButton.setLayoutY(((court.getHeight() / 2) * scale) + 300);
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
                slVolume.setLayoutY(((court.getHeight() / 2) * scale) + 300);
                slVolume.setPrefWidth(150);
                slVolume.setMaxWidth(Region.USE_PREF_SIZE);
                slVolume.setMinWidth(30);
                slVolume.setValue(AudioBank.volume);
                AudioBank.menuSongPlayer.volumeProperty().bind(slVolume.valueProperty().divide(100));

                //choisir le fond
                Image image1=new Image(MenuView.class.getResourceAsStream("./onepicebg.jpg"));
                ImageView backg1=new ImageView(image1);
                backg1.setFitWidth(root.getMinWidth());
                backg1.setFitHeight(root.getMinHeight());

                Image image2=new Image(MenuView.class.getResourceAsStream("./brick.jpg"));
                ImageView backg2=new ImageView(image2);
                backg2.setFitWidth(root.getMinWidth());
                backg2.setFitHeight(root.getMinHeight());

                Image image3=new Image(MenuView.class.getResourceAsStream("./bleu.jpg"));
                ImageView backg3=new ImageView(image3);
                backg3.setFitWidth(root.getMinWidth());
                backg3.setFitHeight(root.getMinHeight());

                background = new Text();
                background.setX(((court.getWidth() / 2) * scale) - 80);
                background.setY(((court.getWidth() / 5) * scale ) + 225);
                background.setTextAlignment(TextAlignment.CENTER);
                background.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                background.setFill(SceneHandler.itemcolor);
                background.setText("Background");

                background1 = new Button();
                background1.setPrefSize(150,75);
                background1.setLayoutX(((court.getWidth() / 2) * scale) - 195);
                background1.setLayoutY(((court.getWidth() / 5) * scale ) + 255);
                backg1.setFitHeight(75);
                backg1.setPreserveRatio(true);
                background1.setGraphic(backg1);
                background1.setOnAction(event -> {GameView.setFond("./onepicebg.jpg");});

                background2 = new Button();
                background2.setPrefSize(150,75);
                background2.setLayoutX(((court.getWidth() / 2) * scale) - 35);
                background2.setLayoutY(((court.getWidth() / 5) * scale ) + 255);
                backg2.setFitHeight(75);
                backg2.setPreserveRatio(true);
                background2.setGraphic(backg2);
                background2.setOnAction(event -> {GameView.setFond("./brick.jpg");});

                background3 = new Button();
                background3.setPrefSize(150,75);
                background3.setLayoutX(((court.getWidth() / 2) * scale) + 125);
                background3.setLayoutY(((court.getWidth() / 5) * scale ) + 255);
                backg3.setFitHeight(75);
                backg3.setPreserveRatio(true);
                background3.setGraphic(backg3);
                background3.setOnAction(event -> {GameView.setFond("./bleu.jpg");});




                // Raquette Taille
                tailleRaquette = new Text();
                tailleRaquette.setX(((court.getWidth() / 2) * scale) - 80);
                tailleRaquette.setY((court.getWidth() / 5) * scale);
                tailleRaquette.setTextAlignment(TextAlignment.CENTER);
                tailleRaquette.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
                tailleRaquette.setFill(SceneHandler.itemcolor);
                tailleRaquette.setText("Racket Size");

                raquetteSmall = new Button();
                raquetteMedium = new Button();
                raquetteLarge = new Button();

                raquetteSmall.setText("Small");
                raquetteSmall.setLayoutX(((court.getWidth() / 2) * scale) - 80);
                raquetteSmall.setLayoutY(250);
                raquetteSmall.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteSmall.setOnAction(event -> {
                        PlayerNumber.activateSmall();
                        makePauseFalse(); raquetteSmall.setStyle("-fx-background-color: #FDFCCB"); raquetteMedium.setStyle(null); raquetteLarge.setStyle(null);});


                raquetteMedium.setText("Medium");
                raquetteMedium.setLayoutX(((court.getWidth() / 2) * scale) + 11);
                raquetteMedium.setLayoutY(250);
                raquetteMedium.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteMedium.setOnAction(event -> {
                        PlayerNumber.activateMedium();
                        makePauseFalse(); raquetteMedium.setStyle("-fx-background-color: #FDFCCB"); raquetteSmall.setStyle(null); raquetteLarge.setStyle(null);});


                raquetteLarge.setText("Large");
                raquetteLarge.setLayoutX(((court.getWidth() / 2) * scale) + 120);
                raquetteLarge.setLayoutY(250);
                raquetteLarge.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                raquetteLarge.setOnAction(event -> {
                        PlayerNumber.activateLarge();
                        makePauseFalse(); raquetteLarge.setStyle("-fx-background-color: #FDFCCB"); raquetteSmall.setStyle(null); raquetteMedium.setStyle(null);});


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
                                raquetteLarge, background, background1, background2, background3); // On ajoute le title et les boutons aux éléments
                // du Pane

        }

}
