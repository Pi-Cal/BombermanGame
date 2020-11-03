package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;

import javax.naming.ldap.Control;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity {

    private int step = 0;
    private int iMap;
    private int jMap;
    private boolean dead = false;
    private double speed = 120.0 * 8;
    private boolean[] side = {false, false, false, false};
    private final byte left = 0, right = 1, up = 2, down = 3;

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
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }


    @Override
    public void update() {
        jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE);
        iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE);
        if (!isMovingVertical()) {
            if (BombermanGame.inputLists.equals("LEFT") && side[left]) {
                this.velocity.add(-speed, 0);
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
            if (BombermanGame.inputLists.equals("RIGHT") && side[right]) {
                this.velocity.add(speed, 0);
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
            if (BombermanGame.inputLists.equals("UP") && side[up]) {
                this.velocity.add(0, -speed);
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
            if (BombermanGame.inputLists.equals("DOWN") && side[down]) {
                this.velocity.add(0, speed);
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
        velocity.multiply( 1/120.0);
        position.add(this.getVelocity());
        step++;
    }

    public void handleCollision(Map<Vector, Entity> t) {

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



}
