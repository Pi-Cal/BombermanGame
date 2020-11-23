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
    protected Vector position;


    public Animation() { }
    public Animation(Sprite[] frames, Vector position) {
        this.frames = frames;
        numFrames = frames.length;
        this.position = position;
    }

    public Animation(Sprite[] frames, double delay, Vector position) {
        this.frames = frames;
        numFrames = frames.length;
        this.delay = delay;
        this.position = position;
    }

    public Animation(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public boolean isDone() {
        return isDone;
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

    public void playAnimation(long time, GraphicsContext graphicsContext) {
        double t = time / NANO;
        graphicsContext.clearRect(position.x  * Sprite.SCALED_SIZE,
                position.y  * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            for (int i = 0; i < numFrames; i++) {
                if (t < (i + 1) * delay && t >= i * delay) {
                    graphicsContext.drawImage(frames[i].getFxImage(),
                            position.x * Sprite.SCALED_SIZE,
                            position.y * Sprite.SCALED_SIZE,
                            Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                    return;
                }
            }
            isDone = true;

    }

    public void playContinuously(long time, GraphicsContext graphicsContext) {
        double t = (time / NANO) % (delay * numFrames);
        graphicsContext.clearRect(position.x  * Sprite.SCALED_SIZE, position.y  * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        for (int i = 0; i < numFrames; i++) {
            if (t < (i + 1) * delay && t >= i * delay) {
                graphicsContext.drawImage(frames[i].getFxImage(),
                        position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE,
                        Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                return;
            }
        }
        isDone = true;

    }
}
