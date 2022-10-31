package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Court;

public class PlayerNumber extends View {
	private int nbre=1;
	private Button plus;
	private Button menus;
	private Button confirmer;
	private Button annuler;
	private Text text;
	private Text text2;
	private SceneHandler scene2;
	private AnimationTimer timer=new AnimationTimer() {
		long last=0;
		public void handle(long now) {
			  if (last == 0) { // ignore the first tick, just compute the first deltaT
	                last = now;
	                return;
	            }
			  text2.setText(" "+nbre);
		}
	};
	public PlayerNumber(Court court,Pane root,double scale,SceneHandler scenehandler,SceneHandler scene2) {
		super(court,root,scale,scenehandler);
		
		text=new Text("Choisir le nombre de joueur ");
		plus=new Button(" + ");
		menus=new Button(" - ");
		this.scene2=scene2;
		
	//	Image image=new Image(getClass().getResourceAsStream("index.png"));
		//this.image=new ImageView(image);
		//plus.setGraphic(this.image);
		text.setLayoutX(20);
		text.setLayoutY(60);
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		plus.setLayoutX(((court.getWidth() / 2) * scale+100));
		plus.setLayoutY(((court.getHeight()/2)*scale));
		plus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		plus.setOnAction(event->incrementer());
		menus.setLayoutX(((court.getWidth() / 2) * scale-100));
		menus.setLayoutY(((court.getHeight()/2)*scale));
		menus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text2=new Text();
		text2.setText(" "+court.getNbrejoueur());
		text2.setLayoutX((court.getWidth()/2)*scale);
		text2.setLayoutY((court.getHeight()/2)*scale+30);
		text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		this.confirmer=new Button();
		confirmer.setText("Confirmer");
		confirmer.setLayoutX(100);
		confirmer.setLayoutY(court.getHeight()*scale-60);
		confirmer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		confirmer.setOnAction(event->getSceneHandler().switchToGame(root,this.scene2.getStage(),this.nbre));
		
		this.annuler=new Button();
		this.annuler.setText("Annuler");
		annuler.setLayoutX(300);
		annuler.setLayoutY(court.getHeight()*scale-60);
		annuler.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		annuler.setOnAction(event->getSceneHandler().switchToMenu(root,this.scene2.getStage()));
		getRoot().getChildren().addAll(text,plus,text2,menus,confirmer,annuler);
	}
	private void incrementer() {
		if(this.nbre<4) {
			this.nbre++;
		}
	}

	public void animate() {
		timer.start();
	}
	

}
