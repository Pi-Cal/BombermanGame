package uet.oop.bomberman.notEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.FlameAnimation;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomb{

    protected int length;
    protected long startTime;
    protected long explodeTime;
    protected Vector position;

    private boolean isExploding = false;
    private boolean isExploded = false;

    protected Animation unexploded = new Animation(new Sprite[]{Sprite.bomb,
            Sprite.bomb_1, Sprite.bomb_2});
    protected FlameAnimation centralFlame = new FlameAnimation(new Sprite[]{Sprite.bomb_exploded,
            Sprite.bomb_exploded1, Sprite.bomb_exploded2});
    protected FlameAnimation horizontalLeftLastFlame = new FlameAnimation((new Sprite[]{Sprite.explosion_horizontal_left_last,
            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2}));
    protected FlameAnimation horizontalRightLastFlame = new FlameAnimation((new Sprite[]{Sprite.explosion_horizontal_right_last,
            Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2}));
    protected FlameAnimation verticalTopLastFlame = new FlameAnimation((new Sprite[]{Sprite.explosion_vertical_top_last,
            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2}));
    protected FlameAnimation verticalDownLastFlame = new FlameAnimation((new Sprite[]{Sprite.explosion_vertical_down_last,
            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2}));
    protected ArrayList<FlameAnimation> verticalMiddle = new ArrayList<>();
    protected ArrayList<FlameAnimation> horizontalMiddle = new ArrayList<>();

    public Bomb(Vector p) {
        position = p;
        length = 1;
        centralFlame.setPosition(p, p);
        horizontalLeftLastFlame.setPosition(new Vector(p.x - length, p.y), p);
        horizontalRightLastFlame.setPosition(new Vector(p.x + length, p.y), p);
        verticalTopLastFlame.setPosition(new Vector(p.x, p.y - length), p);
        verticalDownLastFlame.setPosition(new Vector(p.x, p.y + length), p);
        startTime = System.nanoTime();
        explodeTime = (long) (startTime + 3 * Animation.NANO);
    }


    public Bomb(Vector p, int flameLength) {
        position = p;
        length = flameLength;
        centralFlame.setPosition(p, p);
        horizontalLeftLastFlame.setPosition(new Vector(p.x - length, p.y), p);
        horizontalRightLastFlame.setPosition(new Vector(p.x + length, p.y), p);
        verticalTopLastFlame.setPosition(new Vector(p.x, p.y - length), p);
        verticalDownLastFlame.setPosition(new Vector(p.x, p.y + length), p);
        for (int j = 1; j < length; j++) {
            verticalMiddle.add(new FlameAnimation(new Sprite[]{Sprite.explosion_vertical,
                    Sprite.explosion_vertical1, Sprite.explosion_vertical2}, new Vector( p.x, p.y - j), p));
            verticalMiddle.add(new FlameAnimation(new Sprite[]{Sprite.explosion_vertical,
                    Sprite.explosion_vertical1, Sprite.explosion_vertical2}, new Vector( p.x, p.y + j), p));
            horizontalMiddle.add(new FlameAnimation(new Sprite[]{Sprite.explosion_horizontal,
                    Sprite.explosion_horizontal1, Sprite.explosion_horizontal2}, new Vector( p.x - j, p.y), p));
            horizontalMiddle.add(new FlameAnimation(new Sprite[]{Sprite.explosion_horizontal,
                    Sprite.explosion_horizontal1, Sprite.explosion_horizontal2}, new Vector( p.x + j, p.y), p));

        }
        startTime = System.nanoTime();
        explodeTime = (long) (startTime + 3 * Animation.NANO);
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
        explodeTime = System.nanoTime();
    }

    public void update(long time, GraphicsContext gc) {
        if (!isExploded) {
            normalBomb(time, gc);
        }
    }

    public void unexplodedAnimation(long time, GraphicsContext gc) {
        time -= startTime;
        unexploded.playContinuously(time, gc, position);
    }

    public void explode1(long time, GraphicsContext gc) {
        time -= explodeTime - 10000000;
        centralFlame.play(time, gc);
        horizontalLeftLastFlame.play(time, gc);
        horizontalRightLastFlame.play(time, gc);
        verticalTopLastFlame.play(time, gc);
        verticalDownLastFlame.play(time, gc);
        long finalTime = time;
        verticalMiddle.forEach(g -> g.play(finalTime, gc));
        horizontalMiddle.forEach(g -> g.play(finalTime, gc));



    }

    public void normalBomb(long time, GraphicsContext gc) {
        if (!isExploding) {
            if (time >= explodeTime) {
                isExploding = true;
            }
            BombermanGame.map[(int) position.y][(int) position.x] = '0';
            unexplodedAnimation(time, gc);
        }
        else {
            explode1(time, gc);
            if (centralFlame.isDone()) {
                isExploded = true;
                BombermanGame.map[(int) position.y][(int) position.x] = ' ';
            }
        }
    }

}
