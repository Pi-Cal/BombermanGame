package uet.oop.bomberman.entities;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean isExploded = false;
    private Animation explode;
    private long explodeTime;

    private Item contain;

    public Brick(Vector p, Image img) {
        super(p, img);
        explode = new Animation(new Sprite[]{Sprite.brick_exploded,
                Sprite.brick_exploded1, Sprite.brick_exploded2});
    }

    public Item getContain() {
        return contain;
    }

    public void setContain(Item contain) {
        this.contain = contain;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExplode(boolean explode) {
        isExploded = explode;
        explodeTime = System.nanoTime();
    }

    @Override
    public void update() {

    }


    public void explode(long time, GraphicsContext graphicsContext) {
        time -= explodeTime;
        explode.playAnimation(time, graphicsContext, position.toNormal());
        if (explode.isDone()) {
            BombermanGame.map[(int) position.y / Sprite.SCALED_SIZE]
                    [(int) position.x / Sprite.SCALED_SIZE] = ' ';
            if (contain != null) {
                contain.render(graphicsContext);
            }
        }
    }
}
