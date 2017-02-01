import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    static Clip clip;
    static Clip clip1;

    public static void play(String url, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL audiourl = Sound.class.getResource(url);
        AudioInputStream as = AudioSystem.getAudioInputStream(audiourl);
        clip = AudioSystem.getClip();
        clip.open(as);
        if (loop) {
            clip.loop(-1);
        } else {
            clip.start();
        }
    }

    public static void fxPlay(String url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL audiourl = Sound.class.getResource(url);
        AudioInputStream as = AudioSystem.getAudioInputStream(audiourl);
        clip1 = AudioSystem.getClip();
        clip1.open(as);
        clip1.start();
    }

    public static void stopAudio() {
        if (clip.isActive()) {
            clip.stop();
        }
    }

    public static void resume() {
        clip.loop(-1);
    }
}