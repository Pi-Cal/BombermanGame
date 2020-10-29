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

    private int time = 0;
    private int iMap;
    private int jMap;
    private double speed = 120.0 * 8;
    private boolean[] side = {false, false, false, false};
    private final byte left = 0, right = 1, up = 2, down = 3;

    private void setFalse() {
        for (int i = 0; i < 4; i++) {
            side[i] = false;
        }
    }

    private boolean isMoving() {
        return ((int)Math.round(position.x)) % Sprite.SCALED_SIZE != 0 ||
                ((int)Math.round(position.y)) % Sprite.SCALED_SIZE != 0;
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
        if (BombermanGame.inputLists.contains("LEFT") && side[left]) {
            this.velocity.add(-speed,0);
            switch (time % 3) {
                case 0:
                    img = Sprite.player_left.getFxImage();
                    break;
                case 1:
                    img = Sprite.player_left_1.getFxImage();
                    break;
                default:
                    img = Sprite.player_left_2.getFxImage();
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("RIGHT") && side[right]) {
            this.velocity.add(speed, 0);
            switch (time % 3) {
                case 0:
                    img = Sprite.player_right.getFxImage();
                    break;
                case 1:
                    img = Sprite.player_right_1.getFxImage();
                    break;
                default:
                    img = Sprite.player_right_2.getFxImage();
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("UP") && side[up]) {
            this.velocity.add(0, -speed);
            switch (time % 3) {
                case 0:
                    img = Sprite.player_up.getFxImage();
                    break;
                case 1:
                    img = Sprite.player_up_1.getFxImage();
                    break;
                default:
                    img = Sprite.player_up_2.getFxImage();
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("DOWN") && side[down]) {
            this.velocity.add(0, speed);
            switch (time % 3) {
                case 0:
                    img = Sprite.player_down.getFxImage();
                    break;
                case 1:
                    img = Sprite.player_down_1.getFxImage();
                    break;
                default:
                    img = Sprite.player_down_2.getFxImage();
            }
            time++;
        }

        velocity.multiply( 1/120.0);
        position.add(this.getVelocity());

    }

    public void handleCollision(Map<Vector, Entity> t) {
        System.out.println(iMap + " " + jMap);
        setFalse();
        if (isMoving()) {
            jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE) - 1;
            iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE);
        }
        if (BombermanGame.map[iMap][jMap + 1] == ' ') {
            side[right] = true;
        }


        if (isMoving()) {
            jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE) - 1;
        }
        if (BombermanGame.map[iMap + 1][jMap] == ' ') {
            side[down] = true;
        }

        if (isMoving()) {
            jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE) + 1;
        }
        if (BombermanGame.map[iMap - 1][jMap] == ' ') {
            side[up] = true;
        }

        if (isMoving()) {
            jMap = (int) Math.round(position.x / Sprite.SCALED_SIZE) + 1;
            iMap = (int) Math.round(position.y / Sprite.SCALED_SIZE);
        }
        if (BombermanGame.map[iMap][jMap - 1] == ' ') {
            side[left] = true;
        }

        

    }

}
