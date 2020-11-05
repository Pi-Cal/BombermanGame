package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

    public static final double BOMB_EXIST_TIME = 3;
    private int length;

    private Animation unexploded;
    private boolean isExplode = false;

    public Bomb(Vector p) {
        super(p);
        img = Sprite.bomb.getFxImage();
        unexploded = new Animation(new Sprite[]{Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2});
        numFrame = 3;
        length = 1;
    }

    public Bomb(Vector p, Image img) {
        super(p, img);
        unexploded = new Animation(new Sprite[]{Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2});
        numFrame = 3;
        length = 1;
    }

    public Bomb(Vector p, Image img, int flameLength) {
        super(p, img);
        unexploded = new Animation(new Sprite[]{Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2});
        length = flameLength;
        numFrame = 3;
    }

    public boolean isExplode() {
        return isExplode;
    }

    public void setExplode(boolean explode) {
        isExplode = explode;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (isExplode) return;
        super.render(gc);
    }
    public void normalAnimation(double time, GraphicsContext gc) {
        if(!isExplode) {
            unexploded.playAnimation(time % (numFrame * unexploded.getDelay()), gc, position);
        }

    }

    public void explode1(double time, GraphicsContext gc) {
        if (!isExplode) {
            return;
        }
        time -= BOMB_EXIST_TIME;
        Animation centralFlame = new Animation(new Sprite[]{Sprite.bomb_exploded,
                Sprite.bomb_exploded1, Sprite.bomb_exploded2});
        Animation horizontalMiddleFlame = new Animation(new Sprite[]{Sprite.explosion_horizontal,
                Sprite.explosion_horizontal1, Sprite.explosion_horizontal2});
        Animation verticalMiddleFlame = new Animation(new Sprite[]{Sprite.explosion_vertical,
                Sprite.explosion_vertical1, Sprite.explosion_vertical2});
        Animation horizontalLeftLastFlame = new Animation((new Sprite[]{Sprite.explosion_horizontal_left_last,
                Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2}));
        Animation horizontalRightLastFlame = new Animation((new Sprite[]{Sprite.explosion_horizontal_right_last,
                Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2}));
        Animation verticalTopLastFlame = new Animation((new Sprite[]{Sprite.explosion_vertical_top_last,
                Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2}));
        Animation verticalDownLastFlame = new Animation((new Sprite[]{Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2}));

        centralFlame.playAnimation(time, gc, position);
        horizontalLeftLastFlame.playAnimation(time, gc, new Vector(position.x - length, position.y));
        horizontalRightLastFlame.playAnimation(time, gc, new Vector(position.x + length, position.y));
        verticalTopLastFlame.playAnimation(time, gc, new Vector(position.x, position.y - length));
        verticalDownLastFlame.playAnimation(time, gc, new Vector(position.x, position.y + length));
        for (int j = 1; j < length; j++) {
            verticalMiddleFlame.playAnimation(time, gc, new Vector( position.x, position.y - j));
            verticalMiddleFlame.playAnimation(time, gc, new Vector( position.x, position.y + j));
            horizontalMiddleFlame.playAnimation(time, gc, new Vector( position.x - j, position.y));
            horizontalMiddleFlame.playAnimation(time, gc, new Vector( position.x + j, position.y));
        }

    }

    public void normalBomb(double time, GraphicsContext gc) {
        if (time < BOMB_EXIST_TIME) {
            normalAnimation(time, gc);
        }
        else {
            isExplode = true;
            explode1(time, gc);
        }


    }

}
