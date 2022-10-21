package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Court;

public class MenuView { // Classe similaire à GameView.java avec des éléments différents
    // class parameters
    private final SceneHandler sceneHandler;
    private final Court court;
    private final Pane menuRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0; // pixels

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
        this.sceneHandler = sceneHandler;
        this.menuRoot = root;
        this.scale = scale;
        this.court = court;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
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
        start.setOnAction(event -> sceneHandler.switchToGame(menuRoot)); // Lorsqu'on appuie sur le bouton, cela
                                                                         // enclanche la méthode switchToGame()

        Button startRobot = new Button("Player vs Bot");
        startRobot.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        startRobot.setLayoutY((court.getHeight()-280));
        startRobot.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        startRobot.setOnAction(event -> sceneHandler.switchToGameRobot(menuRoot)); // Lorsqu'on appuie sur le bouton, cela
                                                                         // enclanche la méthode switchToGame()

        settings = new Button("Settings");
        settings.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        settings.setLayoutY(((court.getHeight() -190)));
        settings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        settings.setOnAction(event -> sceneHandler.switchToSettings(menuRoot)); // Lorsqu'on appuie sur le bouton, cela
        // enclanche la méthode switchToGame()
        shutdown = new Button("Quitter");
        shutdown.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        shutdown.setLayoutY(((court.getHeight() - 100)));
        shutdown.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        shutdown.setOnAction(event -> System.exit(0));
        

        menuRoot.getChildren().addAll(title, start, settings, startRobot,shutdown); // On ajoute le title
        // et les boutons aux éléments du Pane

    }
    // Test

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
            }
        }.start();
    }
}
