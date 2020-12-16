package uet.oop.bomberman.Bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Enemies.EnemyAbs;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Flame {
    private Vector position;
    private Vector bombPosition;
    private boolean check = false;
    private boolean exist = true;
    private boolean first = true;
    private Animation ani;

    public Flame(Sprite[] frames, Vector position, Vector bombPosition) {
        ani = new Animation(frames, position);
        this.position = position;
        this.bombPosition = bombPosition;
    }

    public Vector getPosition() {
        return position;
    }

    public boolean isDone() {
        return ani.isDone();
    }
    public void check() {
        if (position.x <= 0 || position.y <= 0) {
            exist = false;
            return;
        }
        for (Vector a : Vector.inLine(bombPosition, position)) {
            if (BombermanGame.map[(int) a.y][(int) a.x] != ' ') {
                exist = false;
                return;
            }
        }

        if (BombermanGame.map[(int) position.y][(int) position.x] == ' ' || position.equals(bombPosition)) {
            check = true;
        }
    }

    public void play(long time, GraphicsContext graphicsContext, long t) {
        if (first) {
            check();
            first = false;
        }
        if(exist) {
            if (check) {
                Entity temp = new Brick(position, Sprite.grass.getFxImage());
                if (BombermanGame.bomberman.handle_1_Collision(temp)) {
                    BombermanGame.bomberman.setDead(true);
                }

                for (EnemyAbs enemy : BombermanGame.enemies) {
                    if (enemy.handle_1_Collision(temp)) { enemy.setDead(true); }
                }
                ani.playAnimation(t, graphicsContext);
            } else {
                if (BombermanGame.getBricks().get(position) != null) {
                    Brick a = BombermanGame.getBricks().get(position);
                    if (!a.isExploded()) {
                        a.setExplode(true);
                        BombermanGame.getExplodingBricks().add(a);
                        a.setExplodeTime(time);
                    }
                }
                for (Bomb b : BombermanGame.bombs) {
                    if (b.getPosition().equals(position) && !b.getPosition().equals(bombPosition)) {
                        if (!b.isExploding()){
                            b.setExploding(true);
                            b.setExplodeTime(time);
                            return;
                        }
                    }
                }
            }
        }
    }
}
