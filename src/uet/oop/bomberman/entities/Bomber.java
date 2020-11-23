package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.notEntity.Bomb;

import javax.naming.ldap.Control;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity {

    private int step = 0;
    private int iMap;
    private int jMap;
    private Vector lastPosition;
    private boolean dead = false;
    private double speed = 120.0 * 8;
    private boolean[] side = {false, false, false, false};
    private final byte left = 0, right = 1, up = 2, down = 3;
    private int timeDead = 0;
    private int maxBomb = 1;
    private int maxSpeed = 3;
    private int maxBombLength = 1;
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMaxBombLength() {
        return maxBombLength;
    }

    public void setMaxBombLength(int maxBombLength) {
        this.maxBombLength = maxBombLength;
    }

    private void setFalse() {
        for (int i = 0; i < 4; i++) {
            side[i] = false;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    private boolean isMovingVertical() {
        return ((int)Math.round(position.y)) % Sprite.SCALED_SIZE != 0;
    }

    private boolean isMovingHorizontal() {
        return ((int) Math.round(position.x)) % Sprite.SCALED_SIZE != 0;
    }

    public Vector velocity = new Vector(0, 0);

    public Bomber(Vector position, Image img) {
        super(position, img);
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }


    @Override
    public void update() {}

    public void update(GraphicsContext gc) {
        if (!dead) {
            jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE);
            if (!(isMovingVertical())) {
                if (BombermanGame.input.equals("LEFT")) {
                    if (side[left]) { this.velocity.add(-speed, 0); }
                    switch (step % 3) {
                        case 0:
                            img = Sprite.player_left.getFxImage();
                            break;
                        case 1:
                            img = Sprite.player_left_1.getFxImage();
                            break;
                        default:
                            img = Sprite.player_left_2.getFxImage();
                    }
                }
                if (BombermanGame.input.equals("RIGHT")) {
                    if (side[right]) { this.velocity.add(speed, 0); }
                    switch (step % 3) {
                        case 0:
                            img = Sprite.player_right.getFxImage();
                            break;
                        case 1:
                            img = Sprite.player_right_1.getFxImage();
                            break;
                        default:
                            img = Sprite.player_right_2.getFxImage();
                    }
                }
            }
            if (!isMovingHorizontal()) {
                if (BombermanGame.input.equals("UP")) {
                    if (side[up]) { this.velocity.add(0, -speed); }
                    switch (step % 3) {
                        case 0:
                            img = Sprite.player_up.getFxImage();
                            break;
                        case 1:
                            img = Sprite.player_up_1.getFxImage();
                            break;
                        default:
                            img = Sprite.player_up_2.getFxImage();
                    }
                }
                if (BombermanGame.input.equals("DOWN")) {
                    if (side[down]) { this.velocity.add(0, speed); }
                    switch (step % 3) {
                        case 0:
                            img = Sprite.player_down.getFxImage();
                            break;
                        case 1:
                            img = Sprite.player_down_1.getFxImage();
                            break;
                        default:
                            img = Sprite.player_down_2.getFxImage();
                    }
                }
            }
            Vector checkPos = position.toNormal();
            if (BombermanGame.input.equals("SPACE") &&
                    BombermanGame.map[(int)checkPos.y][(int) checkPos.x] != '0') {
                if (BombermanGame.bombs.size() < maxBomb) {
                    BombermanGame.bombs.add(new Bomb(position.toNormal().round(), maxBombLength));
                }
            }
            velocity.multiply(1 / 120.0);
            position.add(this.getVelocity());
            step++;
        } else {
            deadAnimation();
        }
        clear(gc);
        render(gc);
    }


    public void clear(GraphicsContext gc) {
        gc.clearRect(lastPosition.x, lastPosition.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        lastPosition.setVector(position.x, position.y);
    }

    public void handleCollision() {

        if (isMovingHorizontal()) {
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
        }

        if (isMovingVertical()) {
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
        }

        setFalse();

        if (BombermanGame.map[iMap][jMap + 1] == ' ') {
            side[right] = true;
        }



        if (BombermanGame.map[iMap + 1][jMap] == ' ') {
            side[down] = true;
        }

        if (isMovingHorizontal()) {
            jMap = (int) (Math.ceil(position.x / Sprite.SCALED_SIZE));
        }
        if (isMovingVertical()) {
            iMap = (int) (Math.ceil(position.y / Sprite.SCALED_SIZE));
        }

        if (BombermanGame.map[iMap - 1][jMap] == ' ') {
            side[up] = true;
        }


        if (BombermanGame.map[iMap][jMap - 1] == ' ') {
            side[left] = true;
        }
    }

    public void deadAnimation() {
        if (timeDead <= 12) {
            this.img = Sprite.player_dead1.getFxImage();
            timeDead++;
        } else if (timeDead <= 24) {
            this.img = Sprite.player_dead2.getFxImage();
            timeDead++;
        } else {
            this.img = Sprite.player_dead2.getFxImage();
        }
    }
}
