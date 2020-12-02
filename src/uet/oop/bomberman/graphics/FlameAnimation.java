package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Enemies.EnemyAbs;
import uet.oop.bomberman.notEntity.Bomb;

public class FlameAnimation extends Animation {
    protected Vector bombPosition;
    protected boolean check = false;
    protected boolean exist = true;
    protected boolean first = true;
    public FlameAnimation(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public FlameAnimation(Sprite[] frames, Vector position, Vector bombPosition) {
        this.frames = frames;
        numFrames = frames.length;
        this.position = position;
        this.bombPosition = bombPosition;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position, Vector bombPosition) {
        this.position = position;
        this.bombPosition = bombPosition;
    }

    public void check() {
        if (position.x <= 0 && position.y <= 0) {
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

    public void play(long time, GraphicsContext graphicsContext) {
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
                playAnimation(time, graphicsContext);
            } else {
                if (BombermanGame.getBricks().get(position) != null) {
                    Brick a = BombermanGame.getBricks().get(position);
                    if (!a.isExploded()) {
                        a.setExplode(true);
                    }
                    a.explode(System.nanoTime(), graphicsContext);
                }
                for (Bomb b : BombermanGame.bombs) {
                    if (b.getPosition().equals(position) && !b.getPosition().equals(bombPosition)) {
                        if (!b.isExploding()){
                            b.setExploding(true);
                            return;
                        }
                    }
                }
            }
        }
    }
}
