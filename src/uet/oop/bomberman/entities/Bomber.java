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

public class Bomber extends Entity {

    private int time = 0;
    private double speed = 120.0 * 16;
    private boolean[] side = {false, false, false, false};
    private final byte trai = 0, phai = 1, tren = 2, duoi = 3;

    private void setFalse() {
        for (int i = 0; i < 4; i++) {
            side[i] = false;
        }
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
        if (BombermanGame.inputLists.contains("LEFT")) {
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
            if (/*position.x <= Sprite.SCALED_SIZE || */side[phai]) {
                this.velocity.add(speed,0);
                side[phai] = false;
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("RIGHT")) {
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
            if (/*position.x >= (BombermanGame.WIDTH - 2) * Sprite.SCALED_SIZE || */side[trai]) {
                this.velocity.add(-speed,0);
                side[trai] = false;
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("UP")) {
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
            if (/*position.y <= Sprite.SCALED_SIZE || */side[duoi]) {
                this.velocity.add(0,speed);
                side[duoi] = false;
            }
            time++;
        }
        if (BombermanGame.inputLists.contains("DOWN")) {
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
            if (/*position.y >= (BombermanGame.HEIGHT - 2) * Sprite.SCALED_SIZE || */side[tren]) {
                this.velocity.add(0,-speed);
                side[tren] = false;
            }
            time++;
        }

        velocity.multiply( 1/120.0);
        position.add(this.getVelocity());
    }

    public void handleCollision(List<Entity> entities) {
        for (Entity entity : entities) {
            if (handle_1_Collision(entity) && (entity instanceof Wall)) {
                setFalse();
                if (Math.round(position.x) + 0.999999 == Math.round(entity.position.x)) /*&&
                        Math.round(position.y) > Math.round(entity.position.y - getHeight()) &&
                        Math.round(position.y) < Math.round(entity.position.y + entity.getHeight() + getHeight()))*/ {
                    System.out.println("trai");
                    side[trai] = true;
                } else if (Math.round(position.x) == Math.round(entity.position.x) + 0.999999) /*&&
                        /*Math.round(position.y) > Math.round(entity.position.y - getHeight()) &&
                        Math.round(position.y) < Math.round(entity.position.y + entity.getHeight() + getHeight()))*/ {
                    System.out.println("phai");
                    side[phai] = true;
                } else if (Math.round(position.y) + 0.999999 == Math.round(entity.position.y)) /*&&
                        Math.round(position.x) > Math.round(entity.position.x - getWidth()) &&
                        Math.round(position.x) < Math.round(entity.position.x + entity.getWidth() + getWidth()))*/ {
                    System.out.println("tren");
                    side[tren] = true;
                } else if (Math.round(position.y) == Math.round(entity.position.y) + 0.999999) /*&&
                        Math.round(position.x) > Math.round(entity.position.x - getWidth()) &&
                        Math.round(position.x) < Math.round(entity.position.x + entity.getWidth() + getWidth()))*/ {
                    System.out.println("duoi");
                    side[duoi] = true;
                }
                velocity.multiply( 1/120.0);
                position.add(velocity);
                System.out.println("Va vao tuong!!" + time);
                break;
            }
        }
    }

}
