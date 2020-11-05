package uet.oop.bomberman.entities;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean isExploded = false;
    private Animation explode;
    private long explodeTime;

    public Brick(Vector p, Image img) {
        super(p, img);
        numFrame = 4;
        explode = new Animation(new Sprite[]{Sprite.brick_exploded,
                Sprite.brick_exploded1, Sprite.brick_exploded2});
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExplode(boolean explode) {
        isExploded = explode;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExploded) return;
        super.render(gc);
    }

    public void explode(long time, GraphicsContext graphicsContext) {
        double t = (double) (time - explodeTime) / 1000000000;
        explode.playAnimation(t, graphicsContext, position);
    }
}
