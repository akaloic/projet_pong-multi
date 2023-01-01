package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Court;
import model.courts.CourtMulti;
import sound.AudioBank;

public class GameView extends View {

    private final Rectangle racketA, racketB, racketC, racketD;
    private boolean[] AI = new boolean[4]; // tab pour indiquer si la raquette est controlé par machine ou joueur
    private int nbreRacket;
    private final int nbrePlayer;
    private boolean[] PlayerVivant = new boolean[4];
    private final Circle ball;
    private final Text score;
    private final Text lifePlayerLeft;
    private final Text lifePlayerR;
    private final Text lifePlayerU;
    private final Text lifePlayerD;
    private Button continu;
    private Line separateur;
    private Region border;
    private Button menu;
    private boolean SystemDeVie;

    private static String fond = "./defaut.jpg";

    private final Label[] commande = new Label[20];
    private static double opacity = 1;
    private final AnimationTimer instructiontimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            opacity -= 0.002;
            commande[0].opacityProperty().set(opacity);
            commande[1].opacityProperty().set(opacity);
            commande[2].opacityProperty().set(opacity);
            commande[3].opacityProperty().set(opacity);
            commande[4].opacityProperty().set(opacity);
            commande[5].opacityProperty().set(opacity);
            commande[6].opacityProperty().set(opacity);
            commande[7].opacityProperty().set(opacity);
            commande[8].opacityProperty().set(opacity);
            commande[9].opacityProperty().set(opacity);
            commande[10].opacityProperty().set(opacity);
            if (opacity <= 0) {
                opacity = 1;
                stop();
            }
        }
    };

    private final AnimationTimer timer = new AnimationTimer() {
        long last = 0;

        @Override
        public void handle(long now) {
            if (last == 0) { // ignore the first tick, just compute the first deltaT
                last = now;
                return;
            }
            ((CourtMulti) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
            last = now;
            ((CourtMulti) getCourt()).update((now - last) * 1.0e-9); // convert nanoseconds to seconds
            last = now;
            if (!AI[0]) {
                racketA.setY(getCourt().getRacketA() * getScale());
                racketA.setX(getMargin() - getRacketThickness() + getCourt().getRacketXA());
            } else {
                racketA.setX(getMargin() - getRacketThickness());
                if (getCourt().getRacketA() * getScale() + getMargin() - 65 > 500)
                    racketA.setY(500);
                else
                    racketA.setY(getCourt().getRacketA() * getScale() + getMargin() - 65);

            }
            if (!AI[1] && nbreRacket >= 2) {
                racketB.setY(getCourt().getRacketB() * getScale());
                racketB.setX(getCourt().getWidth() * getScale() + getMargin() + getCourt().getRacketXB());
            } else {
                racketB.setX(getCourt().getWidth() * getScale() + getMargin());
                if (getCourt().getRacketB() * getScale() + getMargin() - 65 > 500)
                    racketB.setY(500);
                else
                    racketB.setY(getCourt().getRacketB() * getScale() + getMargin() - 65); // ici racketB est AI

            }

            if (racketC != null) {
                racketC.setX(getCourt().getWidth() / 2 * getScale() + getCourt().getRacketXC());
            }
            if (racketD != null) {
                racketD.setX(getCourt().getWidth() / 2 * getScale() + getCourt().getRacketXD());

            }
            if (SystemDeVie) {
                setSystemdeVie(true);

            } else {
                setSystemdeScore(true);
            }
            afficherPageVictoire();

            ball.setCenterX(getCourt().getBallX() * getScale() + getMargin());
            ball.setCenterY(getCourt().getBallY() * getScale());
            score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB()); // On ajoute le score à // pour que
            // le texte s'actualise quand un des

            lifePlayerLeft.setText("PlayerA : " + getCourt().getScoreA());
            lifePlayerR.setText("PlayerB : " + getCourt().getScoreB());
            lifePlayerU.setText("PlayerC : " + getCourt().getScoreC());
            lifePlayerD.setText("PlayerD : " + getCourt().getScoreD());

            if (getPause()) { // si le champs boolean pause est vrai
                this.stop(); // on arrete le timer pour faire une pause du scene
                this.last = 0; // comme le temps continue de s'avancer , il faut réunitialiser last en 0
                               // pour qu'il soit réinitialisé par la valeur de now pour que le jeu repart au
                               // moment que là où on arrete
                getRoot().getChildren().add(continu); // une fois le jeu arreter on fait afficher sur la scene un bouton
                                                      // qui permet de relancer le jeu
                getRoot().getChildren().add(menu);

            }

        }

    };

    public GameView(Court court, Pane root, double scale, SceneHandler sceneHandler, int nbreracket, boolean[] AI,
            boolean SystemdeVie) {
        super(court, root, scale, sceneHandler);

        Image image = new Image(MenuView.class.getResourceAsStream(fond));

        this.nbreRacket = nbreracket;
        this.nbrePlayer = nbreracket;
        this.AI = AI; // initialiser l'attribut avec le tab qui est passée par PlayerNumberView
        this.SystemDeVie = SystemdeVie;

        ImageView backg = new ImageView(image);
        backg.setFitWidth(root.getMinWidth());
        backg.setFitHeight(root.getMinHeight());
        double milieuX = court.getWidth() / 2 + getMargin();
        double milieuY = court.getHeight() / 2;
        separateur = new Line(milieuX, 45, milieuX, court.getHeight() - 25);
        separateur.getStrokeDashArray().addAll(35d, 30d);
        border = new Region();
        border.setMinHeight(court.getHeight() - getMargin());
        border.setMinWidth(court.getWidth() + 2 * super.getRacketThickness());
        border.setLayoutX(getMargin() - super.getRacketThickness());
        border.setLayoutY(getMargin());
        border.setStyle("-fx-border-style:solid;-fx-border-color:black;-fx-border-width:4");
        getRoot().getChildren().addAll(backg, border);
        score = new Text(); // On créer l'objet Text pour pouvoir l'afficher
        score.setX((court.getWidth() / 2) * scale + getMargin() / 2 - 10); // Petite modification pour mieux
                                                                           // placer le score.
        score.setY(35);
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        score.setFill(SceneHandler.itemcolor);
        score.setText(getCourt().getScoreA() + " - " + getCourt().getScoreB());
        lifePlayerLeft = new Text("PlayerA : " + getCourt().getScoreA());
        lifePlayerLeft.setLayoutY(milieuY - 20);
        lifePlayerLeft.setLayoutX(100);
        lifePlayerLeft.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        lifePlayerR = new Text("PlayerB : " + getCourt().getScoreB());
        lifePlayerR.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        lifePlayerR.setFill(Color.GREY);
        lifePlayerR.setLayoutX(getCourt().getWidth() - 100);
        lifePlayerR.setLayoutY(milieuY - 20);
        lifePlayerU = new Text("PlayerC : " + getCourt().getScoreC());
        lifePlayerU.setLayoutX(milieuX);
        lifePlayerU.setLayoutY(100);
        lifePlayerU.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        lifePlayerU.setFill(Color.BLUE);
        lifePlayerD = new Text("PlayerD : " + getCourt().getScoreD());
        lifePlayerD.setLayoutX(milieuX);
        lifePlayerD.setLayoutY(getCourt().getHeight() - 50);
        lifePlayerD.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        lifePlayerD.setFill(Color.RED);
        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(getRacketThickness());
        racketA.setFill(Color.BLACK);

        racketA.setX(getMargin() - getRacketThickness() + court.getRacketXA());
        racketA.setY(court.getRacketA() * scale);
        this.PlayerVivant[0] = true;
        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(getRacketThickness());
        racketB.setFill(Color.GREY);
        racketB.setX(court.getWidth() * scale + getMargin() + court.getRacketXB());
        racketB.setY(court.getRacketB() * scale);
        this.PlayerVivant[1] = true;
        getRoot().getChildren().addAll(racketA, racketB, separateur);
        if (nbreracket >= 3) { // creation de racket C lorsque le nobre de joueur est 3 ou plus
            racketC = new Rectangle();
            racketC.setHeight(super.getRacketThickness());
            racketC.setWidth(court.getRacketSize() * scale);
            racketC.setFill(Color.BLUE);
            racketC.setX(court.getWidth() / 2 * scale + court.getRacketXC());
            racketC.setY(getMargin());
            this.PlayerVivant[2] = true;
            getRoot().getChildren().addAll(racketC, lifePlayerU);
            getRoot().getChildren().remove(separateur);
        } else {
            racketC = null;

        }
        if (nbreracket >= 4) { // creation de racket D lorsque le nobre de joueur est 4
            racketD = new Rectangle();
            racketD.setHeight(super.getRacketThickness());
            racketD.setWidth(court.getRacketSize() * scale);
            racketD.setFill(Color.RED);
            racketD.setX(court.getWidth() / 2 * scale + court.getRacketXD());
            racketD.setY(court.getHeight() - super.getRacketThickness());
            this.PlayerVivant[3] = true;
            getRoot().getChildren().addAll(racketD, lifePlayerD);
        } else {
            racketD = null;

        }

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);
        ball.setCenterX(court.getBallX() * scale + getMargin());
        ball.setCenterY(court.getBallY() * scale);
        continu = new Button("Continue");
        continu.setLayoutX(((court.getWidth() / 2) * scale) - 80);
        continu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        continu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        continu.setOnAction(event -> {
            animate();
            AudioBank.button.play();
        });

        menu = new Button("Menu");
        menu.setLayoutX(((court.getWidth() / 2) * scale) + 120);
        menu.setLayoutY(((court.getHeight() / 2) * scale) - 60);
        menu.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        menu.setOnAction(event -> {
            pauseORcontinue();
            sceneHandler.switchToMenu(getRoot());
            AudioBank.button.play();
        });

        commande[0] = new Label(" z : Monter ");
        commande[0].setLayoutX(court.getWidth() / 2 - 350);
        commande[0].setLayoutY(200);
        commande[0].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[1] = new Label(" s : Descendre ");
        commande[1].setLayoutX(court.getWidth() / 2 - 350);
        commande[1].setLayoutY(250);
        commande[1].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[2] = new Label(" Space : Faire pause ");
        commande[2].setLayoutX(court.getWidth() / 2 - 40);
        commande[2].setLayoutY((court.getHeight() / 2) - 50);
        commande[2].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[3] = new Label(" ESC : Quitter le jeu ");
        commande[3].setLayoutX(court.getWidth() / 2 - 50);
        commande[3].setLayoutY((court.getHeight() / 2));
        commande[3].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[4] = new Label(" INSTRUCTIONS: ");
        commande[4].setLayoutX(court.getWidth() / 2 - 40);
        commande[4].setLayoutY((court.getHeight() / 2) - 100);
        commande[4].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[5] = new Label(" UP : Monter ");
        commande[5].setLayoutX(court.getWidth() / 2 + 300);
        commande[5].setLayoutY(200);
        commande[5].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[6] = new Label(" DOWN : Descendre ");
        commande[6].setLayoutX(court.getWidth() / 2 + 300);
        commande[6].setLayoutY(250);
        commande[6].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[7] = new Label(" Q : Gauche ");
        commande[7].setLayoutX(court.getWidth() / 2);
        commande[7].setLayoutY(100);
        commande[7].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[8] = new Label(" D : Droite ");
        commande[8].setLayoutX(court.getWidth() / 2);
        commande[8].setLayoutY(150);
        commande[8].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[9] = new Label(" Left : Gauche ");
        commande[9].setLayoutX(court.getWidth() / 2);
        commande[9].setLayoutY(court.getHeight() - 100);
        commande[9].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        commande[10] = new Label(" Right : Droite ");
        commande[10].setLayoutX(court.getWidth() / 2);
        commande[10].setLayoutY(court.getHeight() - 150);
        commande[10].setStyle("-fx-border-color: black; -fx-text-fill:black; -fx-font-size: 20;");
        getRoot().getChildren().addAll(ball, lifePlayerLeft, lifePlayerR, commande[0], commande[1], commande[2],
                commande[3], commande[4], commande[5], commande[6], commande[7], commande[8], commande[9], commande[10],
                score); // On ajoute le score aux éléments du Pane

    }

    public void setSystemdeVie(boolean v) {
        if (getRoot().getChildren().contains(score)) {
            getRoot().getChildren().remove(score);
        }
        if (v) {
            if (getCourt().getScoreA() == 0) {
                if (getRoot().getChildren().contains(lifePlayerLeft)) {
                    getRoot().getChildren().removeAll(lifePlayerLeft, racketA);
                    this.nbreRacket--;
                    this.PlayerVivant[0] = false;
                }
            }
            if (getCourt().getScoreB() == 0) {
                if (getRoot().getChildren().contains(lifePlayerR)) {
                    getRoot().getChildren().removeAll(lifePlayerR, racketB);
                    this.nbreRacket--;
                    this.PlayerVivant[1] = false;
                }
            }
            if (getCourt().getScoreC() == 0) {
                if (getRoot().getChildren().contains(lifePlayerU)) {
                    getRoot().getChildren().removeAll(lifePlayerU, racketC);
                    this.nbreRacket--;
                    this.PlayerVivant[2] = false;
                }
            }
            if (getCourt().getScoreD() == 0) {
                if (getRoot().getChildren().contains(lifePlayerD)) {
                    getRoot().getChildren().removeAll(lifePlayerD, racketD);
                    this.nbreRacket--;
                    this.PlayerVivant[3] = false;
                }
            }
        }
    }

    public void setSystemdeScore(boolean v) {
        if (v) {
            if (this.nbreRacket == 2) {
                if (getRoot().getChildren().contains(lifePlayerLeft)) {
                    getRoot().getChildren().removeAll(lifePlayerLeft, lifePlayerR, lifePlayerU, racketC, lifePlayerD,
                            racketD);
                }
            } else {
                if (getRoot().getChildren().contains(score)) {
                    getRoot().getChildren().remove(score);
                }
            }
        }
    }

    public void afficherPageVictoire() {
        String joueur = "";
        if (this.SystemDeVie) {
            if (nbreRacket == 1) {
                int joueurVivant = 0;
                for (int i = 0; i < 4; i++) {
                    if (PlayerVivant[i]) {
                        joueurVivant = i;
                        break;
                    }
                }
                if (!AI[joueurVivant]) {
                    char j = (char) ('A' + joueurVivant);
                    joueur += j;
                }
                this.getSceneHandler().switchToPageWin(getRoot(), joueur, AI, this.nbrePlayer, false, true, false,
                        SystemDeVie);
            }
        } else {
            int scoreA = this.getCourt().getScoreA();
            int scoreB = this.getCourt().getScoreB();
            int scoreC = this.getCourt().getScoreC();
            int scoreD = this.getCourt().getScoreD();
            if (scoreA == 10 || scoreB == 10 || scoreC == 10 || scoreD == 10) {
                if (scoreA == 10) {
                    if (!AI[0]) {
                        joueur += 'A';
                    }
                } else if (scoreB == 10) {
                    if (!AI[1]) {
                        joueur += 'B';
                    }
                } else if (scoreC == 10) {
                    if (!AI[2]) {
                        joueur += 'C';
                    }
                } else if (scoreD == 10) {
                    if (!AI[3]) {
                        joueur += 'D';
                    }
                }
                this.getSceneHandler().switchToPageWin(getRoot(), joueur, AI, this.nbrePlayer, false, true, false,
                        SystemDeVie);
            }

        }

    }

    public void animate() {
        instructiontimer.start();
        timer.start();
        this.setInstruction();
        if (getRoot().getChildren().contains(continu)) { // si on reprend / commence la partie il faut vérifier si le
                                                         // button continue existe
            getRoot().getChildren().remove(continu); // si oui , on enleve les boutons
            getRoot().getChildren().remove(menu);
            pauseORcontinue();
        }
    }

    public void setInstruction() {
        if (this.nbreRacket < 3) {
            if (getRoot().getChildren().contains(commande[7])) {
                getRoot().getChildren().removeAll(commande[7], commande[8]);
            }
        }
        if (this.nbreRacket < 4) {
            if (getRoot().getChildren().contains(commande[9])) {
                getRoot().getChildren().removeAll(commande[9], commande[10]);
            }
        }
    }

    // method pour changer le fond

    public static void setFond(String f) {
        fond = f;
    }

    public static String getFond() {
        return fond;
    }

}
