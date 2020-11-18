package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;

public class Animation {
    public final static double NANO = 1000000000;
    protected Sprite[] frames;
    protected double delay = 0.2;
    protected int numFrames;
    protected boolean isDone = false;


    public Animation() { }
    public Animation(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public Animation(Sprite[] frames, double delay) {
        this.frames = frames;
        numFrames = frames.length;
        this.delay = delay;
    }


    public boolean isDone() {
        return isDone;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
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

    public void playAnimation(long time, GraphicsContext graphicsContext, Vector p) {
        double t = time / NANO;
        graphicsContext.clearRect(p.x  * Sprite.SCALED_SIZE, p.y  * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            for (int i = 0; i < numFrames; i++) {
                if (t < (i + 1) * delay && t >= i * delay) {
                    graphicsContext.drawImage(frames[i].getFxImage(),
                            p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE,
                            Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                    return;
                }
            }
            isDone = true;

    }

    public void playContinuously(long time, GraphicsContext graphicsContext, Vector p) {
        double t = (time / NANO) % (delay * numFrames);
        graphicsContext.clearRect(p.x  * Sprite.SCALED_SIZE, p.y  * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < numFrames; i++) {
            if (t < (i + 1) * delay && t >= i * delay) {
                graphicsContext.drawImage(frames[i].getFxImage(),
                        p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE,
                        Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                return;
            }
        }
        isDone = true;

    }
}
