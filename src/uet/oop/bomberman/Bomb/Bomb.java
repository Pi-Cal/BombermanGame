package uet.oop.bomberman.Bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Sound;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomb {

    protected int length;
    protected long startTime;
    protected long explodeTime;
    protected Vector position;

    private boolean isExploding = false;
    private boolean isExploded = false;

    protected Animation unexploded;
    protected Flame centralFlame;
    protected Flame horizontalLeftLastFlame;
    protected Flame horizontalRightLastFlame;
    protected Flame verticalTopLastFlame;
    protected Flame verticalDownLastFlame;
    protected ArrayList<Flame> verticalMiddle;
    protected ArrayList<Flame> horizontalMiddle;

    public Bomb(Vector p, int flameLength) {
        position = p;
        length = flameLength;
        unexploded = new Animation(new Sprite[]{Sprite.bomb,
                Sprite.bomb_1, Sprite.bomb_2}, p);
        setFlame(p);
        startTime = System.nanoTime();
        explodeTime = (long) (startTime + 3 * Animation.NANO);
    }

    private void setFlame(Vector p) {
        centralFlame = new Flame(new Sprite[]{Sprite.bomb_exploded,
                Sprite.bomb_exploded1, Sprite.bomb_exploded2}, p, p);
        horizontalLeftLastFlame = new Flame(new Sprite[]{Sprite.explosion_horizontal_left_last,
                Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2},
                new Vector(p.x - length, p.y), p);
        horizontalRightLastFlame = new Flame(new Sprite[]{Sprite.explosion_horizontal_right_last,
                Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2},
                new Vector(p.x + length, p.y), p);
        verticalTopLastFlame = new Flame(new Sprite[]{Sprite.explosion_vertical_top_last,
                Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2},
                new Vector(p.x, p.y - length), p);
        verticalDownLastFlame = new Flame(new Sprite[]{Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2},
                new Vector(p.x, p.y + length), p);
        verticalMiddle = new ArrayList<>();
        horizontalMiddle = new ArrayList<>();

        for (int j = 1; j < length; j++) {
            verticalMiddle.add(new Flame(new Sprite[]{Sprite.explosion_vertical,
                    Sprite.explosion_vertical1, Sprite.explosion_vertical2}, new Vector( p.x, p.y - j), p));
            verticalMiddle.add(new Flame(new Sprite[]{Sprite.explosion_vertical,
                    Sprite.explosion_vertical1, Sprite.explosion_vertical2}, new Vector( p.x, p.y + j), p));
            horizontalMiddle.add(new Flame(new Sprite[]{Sprite.explosion_horizontal,
                    Sprite.explosion_horizontal1, Sprite.explosion_horizontal2}, new Vector( p.x - j, p.y), p));
            horizontalMiddle.add(new Flame(new Sprite[]{Sprite.explosion_horizontal,
                    Sprite.explosion_horizontal1, Sprite.explosion_horizontal2}, new Vector( p.x + j, p.y), p));

        }
    }
    public boolean isExploding() {
        return isExploding;
    }


    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setExploding(boolean exploding) {
        isExploding = exploding;
    }

    public void setExplodeTime(long explodeTime) {
        this.explodeTime = explodeTime;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void update(long time, GraphicsContext gc) {
        if (!isExploded) {
            normalBomb(time, gc);
        }
    }

    public void unexplodedAnimation(long time, GraphicsContext gc) {
        long t = time - startTime;
        unexploded.playContinuously(t, gc);
    }

    public void explode(long time, GraphicsContext gc, long t) {
        centralFlame.play(time, gc, t);
        horizontalLeftLastFlame.play(time, gc, t);
        horizontalRightLastFlame.play(time, gc, t);
        verticalTopLastFlame.play(time, gc, t);
        verticalDownLastFlame.play(time, gc, t);
        verticalMiddle.forEach(g -> g.play(time, gc, t));
        horizontalMiddle.forEach(g -> g.play(time, gc, t));
        Sound.explore.start();

    }

    public void normalBomb(long time, GraphicsContext gc) {
        if (!isExploding) {
            if (time >= explodeTime) {
                isExploding = true;
            }
            BombermanGame.map[(int) position.y][(int) position.x] = '`';
            if (!BombermanGame.bomberman.handle_1_Collision(new Brick(position, Sprite.brick.getFxImage()))) {
                BombermanGame.map[(int) position.y][(int) position.x] = '0';
            }
            unexplodedAnimation(time, gc);
        } else {
            long t = time - explodeTime;
            explode(time, gc, t);
            if (centralFlame.isDone()) {
                isExploded = true;
                BombermanGame.map[(int) position.y][(int) position.x] = ' ';
            }
        }
    }

    @Override
    public boolean equals(Object v) {
        if (v instanceof Bomb) {
            return (this.position.x == ((Bomb) v).position.x && this.position.y == ((Bomb) v).position.y);
        }
        return false;
    }
}
