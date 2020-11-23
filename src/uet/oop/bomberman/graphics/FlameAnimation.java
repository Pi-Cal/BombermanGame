package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.notEntity.Bomb;

import java.util.List;
import java.util.Map;

public class FlameAnimation extends Animation {
    protected Vector position;
    protected Vector bombPosition;
    protected boolean check = false;
    protected boolean exist = true;
    public FlameAnimation(Sprite[] frames) {
        this.frames = frames;
        numFrames = frames.length;
    }

    public FlameAnimation(Sprite[] frames, Vector position, Vector bombPosition) {
        this.frames = frames;
        numFrames = frames.length;
        this.position = position;
        this.bombPosition = bombPosition;
        check();
    }

    public void playAnimation(long time, GraphicsContext graphicsContext) {
        double t = time / NANO;

        graphicsContext.fillRect(position.x * Sprite.SCALED_SIZE,
                position.y * Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);

        if (BombermanGame.bomberman.handle_1_Collision(new Brick(position, Sprite.grass.getFxImage()))) {
            BombermanGame.bomberman.setDead(true);
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
        isDone = true;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position, Vector bombPosition) {
        this.position = position;
        this.bombPosition = bombPosition;
        check();
    }

    public void check() {
        if (position.y > 0 && position.x > 0) {
            List<Vector> b = Vector.inLine(bombPosition, position);
            b.remove(position);
            for (Vector a : b) {
                if (BombermanGame.map[(int) a.y][(int) a.x] == '*' ||
                        BombermanGame.map[(int) a.y][(int) a.x] == '#') {
                    exist = false;
                    return;
                }
            }

            if (BombermanGame.map[(int) position.y][(int) position.x] != '*' &&
                    BombermanGame.map[(int) position.y][(int) position.x] != '#') {
                check = true;
            }
        }
    }

    public void play(long time, GraphicsContext graphicsContext) {

        if(exist) {
            if (check) {
                playAnimation(time, graphicsContext);
                for (Bomb b : BombermanGame.bombs) {
                    if (b.getPosition().equals(position) && !b.getPosition().equals(bombPosition)) {
                        if (!b.isExploding()){
                            b.setExploding(true);
                            return;
                        }
                    }
                }
            } else {
                if (BombermanGame.getBricks().get(position.toString()) != null) {
                    Brick a = BombermanGame.getBricks().get(position.toString());
                    if (!a.isExploded()) {
                        a.setExplode(true);
                    }
                    a.explode(System.nanoTime(), graphicsContext);

                }


            }
        }
    }


}
