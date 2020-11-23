package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.notEntity.Bomb;

import java.util.List;

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

    private void playAnimation1(long time, GraphicsContext graphicsContext) {
        double t = time / NANO;

        graphicsContext.fillRect(position.x * Sprite.SCALED_SIZE,
                position.y * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        Entity temp = new Brick(position, Sprite.grass.getFxImage());;
        if (BombermanGame.bomberman.handle_1_Collision(temp)) {
            BombermanGame.bomberman.setDead(true);
        }

        for (Enemy enemy : BombermanGame.enemies) {
            if (enemy.handle_1_Collision(temp)) { enemy.setDead(true); }
        }

        for (int i = 0; i < numFrames; i++) {
            if (t < (i + 1) * delay && t >= i * delay) {
                graphicsContext.drawImage(frames[i].getFxImage(),
                        position.x * Sprite.SCALED_SIZE,
                        position.y * Sprite.SCALED_SIZE,
                        Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
                return ;
            }
        }
        if (t > delay * numFrames + delay / 3) isDone = true;

    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position, Vector bombPosition) {
        this.position = position;
        this.bombPosition = bombPosition;
    }

    public void check() {
        if (position.y > 0 && position.x > 0) {
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
    }

    public void play(long time, GraphicsContext graphicsContext) {
        if (first) {
            check();
            first = false;
        }
        if(exist) {
            if (check) {
                playAnimation1(time, graphicsContext);
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
