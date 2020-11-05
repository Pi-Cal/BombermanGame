package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;

public class Animation {
    private Sprite[] frames;
    private double delay = 0.5;
    private int numFrames;

    public Animation() {

    }
    public Animation(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public Animation(Sprite[] frames, double delay) {
        this.frames = frames;
        numFrames = frames.length;
        this.delay = delay;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public void setFrames(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public void playAnimation(double time, GraphicsContext graphicsContext, Vector p) {
        for (int i = 0; i < numFrames; i++) {
            if (time < (i + 1) * delay && time >= i * delay) {
                graphicsContext.fillRect(p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE,
                        Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                graphicsContext.drawImage(frames[i].getFxImage(),
                        p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE,
                        Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            }
        }
    }
}
