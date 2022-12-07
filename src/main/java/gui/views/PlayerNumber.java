package gui.views;

import gui.SceneHandler;
import gui.View;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
	private Text text3=new Text();
	private Text text4=new Text();
	private Text text5=new Text();
	private Text text6=new Text();
	   
	//booleans pour voir laquelle taille est choisie
    private static boolean racketSmall = false;

    private static boolean racketMedium = true; //par defaut on a une raquette de taille medium

    private static boolean racketLarge = false;
	
	private boolean []AI=new boolean[4]; 
	private AnimationTimer timer=new AnimationTimer() {
		long last=0;
		public void handle(long now) {
			  if (last == 0) { // ignore the first tick, just compute the first deltaT
	                last = now;
	                return;
	            }
			  text2.setText(" "+nbre);
			  switch(nbre) {
			    case 4:text6.setText("Player4 humain/AI ?" );break;
			    case 3:text5.setText("Player3 humain/AI ?" );text6.setText(null); break;
			    case 2:text4.setText("Player2 humain/AI ?" );text5.setText(null);text6.setText(null); break;
			    case 1:text3.setText("Player1 humain/AI ?" );text4.setText(null);text5.setText(null);text6.setText(null); break;
			  }
			  
		}
	};
	public PlayerNumber(Court court,Pane root,double scale,SceneHandler scenehandler) {
		super(court,root,scale,scenehandler);
		 Image image=new Image(MenuView.class.getResourceAsStream("./playerbg.jpg"));
	        ImageView backg=new ImageView(image);
	        backg.setFitWidth(root.getMinWidth());
	        backg.setFitHeight(root.getMinHeight());
		text=new Text("Nombre de joueur : ");
		plus=new Button(" + ");
		menus=new Button(" - ");
		int spacey=60;;
		double centerX=court.getWidth()/2;
		double centerY=200;
	    
		text.setLayoutX(centerX-250);
		text.setLayoutY(centerY);
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		// button plus
		plus.setLayoutX(((centerX) * scale+100));
		plus.setLayoutY(centerY*scale+spacey);
		plus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		plus.setOnAction(event->incrementer());
		
		//button moins
		menus.setLayoutX(centerX * scale-100);
		menus.setLayoutY(centerY*scale+spacey);
		menus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		menus.setOnAction(event->decrementer());
	
		text2=new Text();
		text2.setText(" "+this.nbre);
		text2.setLayoutX(centerX*scale);
		text2.setLayoutY(centerY*scale+spacey+30);
		text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		
		spacey+=80;
		
		text3.setLayoutX(centerX-100);
		text3.setLayoutY(centerY+spacey);
		text3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		
		spacey+=20;
		text4.setLayoutX(centerX-100);
		text4.setLayoutY(centerY+spacey);
		text4.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		spacey+=20;
		text5.setLayoutX(centerX-100);
		text5.setLayoutY(centerY+spacey);
		text5.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		spacey+=20;
		text6.setLayoutX(centerX-100);
		text6.setLayoutY(centerY+spacey);
		text6.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		this.confirmer=new Button();
		confirmer.setText("Confirmer");
		confirmer.setLayoutX(court.getWidth()/2-200);
		confirmer.setLayoutY(court.getHeight()/2*scale+spacey);
		confirmer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		confirmer.setOnAction(event->getSceneHandler().switchToGame(root,nbre,racketSmall,racketMedium,racketLarge));
		
		this.annuler=new Button();
		this.annuler.setText("Annuler");
		annuler.setLayoutX(court.getWidth()/2+100);
		annuler.setLayoutY(court.getHeight()/2*scale+spacey);
		annuler.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		annuler.setOnAction(event->getSceneHandler().switchToMenu(root));
		
		getRoot().getChildren().addAll(backg,text,plus,text2,menus,confirmer,annuler,text3,text4,text5,text6);
		
	}
	private void incrementer() {
		if(this.nbre<4) {
			this.nbre++;
		}
	}
	private void decrementer() {
		if(this.nbre>1) {
			this.nbre--;
		}
	}

	public void animate() {
		timer.start();
	}
	
    //pour sauvgarder la taille de la raquette choisi si le jouer revient au start
    public static void activateSmall(){
        racketSmall = true;
        racketMedium = false;
        racketLarge = false;
    }

    public static void activateMedium(){
        racketMedium = true;
        racketSmall = false;
        racketLarge = false;
    }

    public static void activateLarge(){
        racketLarge = true;
        racketSmall = false;
        racketMedium = false;
    }

    public static boolean getRacketSmall(){
        return racketSmall;
    }

    public static boolean getRacketMedium(){
        return racketMedium;
    }

    public static boolean getRacketLarge(){
        return racketLarge;
    }
	
 
    
}
