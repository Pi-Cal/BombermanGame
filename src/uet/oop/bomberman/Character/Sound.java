package uet.oop.bomberman.Character;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Sound {
    private AudioInputStream audioStream;
    private Clip clip;

    public Sound(String filename) {

        File soundFile = null;
        try {
            soundFile = new File(Sound_cdjv.class.getResource(filename).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (!soundFile.exists()) {
            System.err.println("Wave file not found: " + filename);
            return;
        }

        audioStream  = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        if (clip.getMicrosecondLength() == clip.getMicrosecondPosition()) {
            clip.setMicrosecondPosition(0);
        }
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    public void setLoop(int i) {
        clip.loop(i);
    }

    public void reset() {
        clip.setFramePosition(0);
    }

    public static Sound player_dead = new Sound("/sounds/dead.wav");
    public static Sound explore = new Sound("/sounds/explore.wav");
    public static Sound stage_theme = new Sound("/sounds/stage_theme.wav");
    public static Sound bling = new Sound("/sounds/bling.wav");
    public static Sound player_move = new Sound("/sounds/move.wav");
    public static Sound coin = new Sound("/sounds/coin.wav");
}
