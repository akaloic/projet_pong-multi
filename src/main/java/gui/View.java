package gui;

import javafx.scene.layout.Pane;
import model.Court;


public abstract class View {
    private final SceneHandler sceneHandler;
    private final Court court;
    private final Pane root;
    private final double scale, xMargin = 50.0, racketThickness = 10.0;

    public View (Court court, Pane root, double scale, SceneHandler sceneHandler) {
        this.court = court;
        this.root = root;
        this.scale = scale;
        this.sceneHandler = sceneHandler;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);
    }

    public Court getCourt() {
        return court;
    }

    public double getXMargin() {
        return xMargin;
    }

    public double getRacketThickness() {
        return racketThickness;
    }

    public Pane getRoot() {
        return root;
    }

    public double getScale() {
        return scale;
    }

    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }
}
