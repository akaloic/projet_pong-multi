package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;

public class MenuView extends View{ // Classe similaire à GameView.java avec des éléments différents

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

        root.setMinWidth(court.getWidth() * scale + 2 * getXMargin());
        root.setMinHeight(court.getHeight() * scale);

        title = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        title.setX(((court.getWidth() / 2) * scale) - 20);
        title.setY(60);
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        title.setFill(Color.BLACK);
        title.setText("Pong!");

        start = new Button("Start");
        start.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        start.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        start.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        start.setOnAction(event -> sceneHandler.switchToGame(getRoot())); // Lorsqu'on appuie sur le bouton, cela
                                                                         // enclanche la méthode switchToGame()

        Button startRobot = new Button("Player vs Bot");
        startRobot.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        startRobot.setLayoutY((court.getHeight()-280));
        startRobot.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        startRobot.setOnAction(event -> sceneHandler.switchToGameRobot(getRoot())); // Lorsqu'on appuie sur le bouton, cela
                                                                         // enclanche la méthode switchToGame()

        settings = new Button("Settings");
        settings.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        settings.setLayoutY(((court.getHeight() -190)));
        settings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        settings.setOnAction(event -> sceneHandler.switchToSettings(getRoot())); // Lorsqu'on appuie sur le bouton, cela
        // enclanche la méthode switchToGame()
        shutdown = new Button("Quitter");
        shutdown.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        shutdown.setLayoutY(((court.getHeight() - 100)));
        shutdown.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        shutdown.setOnAction(event -> System.exit(0));
        getRoot().getChildren().addAll(title, start, settings, startRobot,shutdown); // On ajoute le title

        // et les boutons aux éléments du Pane

    }
    // Test

    
}
