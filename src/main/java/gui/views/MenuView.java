package gui.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;
import sound.AudioBank;

public class MenuView extends View { // Classe similaire à GameView.java avec des éléments différents

    // children of the game main node
    private final Text title;
    private final Button start;
    private final Button settings;
    private final Button shutdown;

    /**
     * @param court le "modèle" de cette vue (le titre et le bouton)
     * 
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre
     *              de pixels correspondants dans la vue
     */
    public MenuView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        super(court, root, scale, sceneHandler);
        Image image = new Image(MenuView.class.getResourceAsStream("./menu3bg.jpg"));
        ImageView backg = new ImageView(image);
        backg.setFitWidth(root.getMinWidth());
        backg.setFitHeight(root.getMinHeight());

        title = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        title.setLayoutX(((court.getWidth() / 2) * scale) - 200);
        title.setLayoutY(150);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        title.setFill(SceneHandler.itemcolor);
        title.setText("Pong!");

        start = new Button("Start");
        start.setLayoutX(((court.getWidth() / 2) * scale) - 80);

        start.setLayoutY(court.getHeight() / 2 * scale);
        start.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        start.setOnMouseClicked(event -> {
            sceneHandler.switchtoNbreJoueur(getRoot());
            AudioBank.button.play();
        }); // Lorsqu'on appuie sur le bouton,
            // cela
        start.setOnMouseEntered(event -> start.setTextFill(Color.RED));
        start.setOnMouseExited(event -> start.setTextFill(Color.BLACK));
        start.setBackground(null);

        /*
         * // enclanche la méthode switchToGame()
         * start.setLayoutY(((court.getHeight() / 2) * scale) - 60);
         * start.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,
         * 30));
         * start.setOnAction(event -> {
         * AudioBank.buttonclick.play();
         * sceneHandler.switchToGameRbis(getRoot(), racketSmall, racketMedium,
         * racketLarge);
         * makePauseFalse();
         * }); // Lorsqu'on appuie sur le bouton, cela
         * // enclanche la méthode switchToGame() + avec la bonne taille de la raquette
         * 
         * Button startRobot = new Button("Player vs Bot");
         * startRobot.setLayoutX(((court.getWidth() / 2) * scale) - 80);
         * startRobot.setLayoutY((court.getHeight() - 280));
         * startRobot.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,
         * 30));
         * startRobot.setOnAction(event -> sceneHandler.switchToGameRobot(getRoot()));
         * // Lorsqu'on appuie sur le bouton, cela
         * // enclanche la méthode switchToGame()
         */
        settings = new Button("Settings");
        settings.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        settings.setLayoutY(court.getHeight() / 2 + 100);
        settings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        settings.setOnMouseClicked(event -> {
            sceneHandler.switchToSettings(getRoot());
            AudioBank.button.play();
        }); // Lorsqu'on appuie sur le
            // bouton, cela
        settings.setOnMouseEntered(event -> settings.setTextFill(Color.RED));
        settings.setOnMouseExited(event -> settings.setTextFill(Color.BLACK));
        settings.setBackground(null);
        // enclanche la méthode switchToGame()
        shutdown = new Button("Quitter");
        shutdown.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        shutdown.setLayoutY(court.getHeight() / 2 + 200);
        shutdown.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        shutdown.setBackground(null);
        shutdown.setOnMouseEntered(event -> shutdown.setTextFill(Color.RED));
        shutdown.setOnMouseExited(event -> shutdown.setTextFill(Color.BLACK));
        shutdown.setOnMouseClicked(event -> {
            System.exit(0);
            AudioBank.button.play();
        });

        getRoot().getChildren().addAll(backg, start, settings, shutdown); // On ajoute le title

        // et les boutons aux éléments du Pane

    }

    // Test

}
