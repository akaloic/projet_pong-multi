package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;

public class SettingsView { // Classe similaire à GameView.java & MenuView.java avec des éléments différents
    // class parameters
    private final SceneHandler sceneHandler;
    private final Court court;
    private final Pane settingsRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0; // pixels

    // children of the game main node
    private final Text reglages;
    private final Button exit;

    private final Button son;

    /**
     * @param court le "modèle" de cette vue (le titre et le bouton)
     * 
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera
     *              affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre
     *              de pixels correspondants dans la vue
     */
    public SettingsView(Court court, Pane root, double scale, SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;
        this.settingsRoot = root;
        this.scale = scale;
        this.court = court;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

        reglages = new Text();
        reglages.setX(((court.getWidth() / 2) * scale) - 80);
        reglages.setY((court.getWidth() / 10) * scale);
        reglages.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        reglages.setFill(Color.BLACK);
        reglages.setText("Réglages");

        exit = new Button("Exit");
        exit.setLayoutX(((court.getWidth() / 2) * scale) - 5);
        exit.setLayoutY(((court.getHeight() / 2) * scale) + 100);
        exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        exit.setOnAction(event -> sceneHandler.switchToMenu(settingsRoot));

        son = new Button();
        son.setText("On");
        son.setLayoutX(1050);
        son.setLayoutY(10);
        son.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        son.setOnAction(event -> son.setText(sceneHandler.switchSonButton(son)));

        settingsRoot.getChildren().addAll(reglages, exit, son); // On ajoute le title et les boutons aux éléments
        // du Pane

    }

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
