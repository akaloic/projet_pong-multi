package sound;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioBank {
    public static double volume = 100;
    public static String soundpath = "file:src/main/java/sound/";
    public static AudioClip button = new AudioClip(soundpath + "ButtonPress.mp3");
    public static AudioClip hit = new AudioClip(soundpath + "Hit.mp3"); // Collision raquette sol plafond
    public static AudioClip score = new AudioClip(soundpath + "Score.mp3"); // Point marqué

    public static Media menuSong = new Media(
            new File("src/main/java/sound/menusong.wav").toURI().toString());
    public static MediaPlayer menuSongPlayer = new MediaPlayer(menuSong);

    public AudioBank() {
    };
}
