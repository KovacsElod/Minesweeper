import javax.sound.sampled.*;
import java.io.File;

public class AudioLoader {
    private Clip clip;
    public AudioLoader (String path){
        try {
            AudioInputStream audioInput= AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            clip=AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();}
    }

    public void stopAudio(){
        clip.stop();
    }
}
