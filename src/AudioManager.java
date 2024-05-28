import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    public static void playSFX(String filename) {
        try {
            File soundFile = new File(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
