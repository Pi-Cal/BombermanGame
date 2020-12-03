package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Balloom {

    public Doll(Vector position, Image img) {
        super(position, img);
    }

    @Override
    public void handleCollition() {
        if (!isMovingHorizontal() && !isMovingVertical()) {
            speed = 2 * (new Random().nextInt(2) + 1);
        }
        super.handleCollition();
    }

    @Override
    public void addImage() {
        images.add(Sprite.doll_left1.getFxImage());
        images.add(Sprite.doll_left2.getFxImage());
        images.add(Sprite.doll_left3.getFxImage());
        images.add(Sprite.doll_right1.getFxImage());
        images.add(Sprite.doll_right2.getFxImage());
        images.add(Sprite.doll_right3.getFxImage());
        images.add(Sprite.doll_dead.getFxImage());
        images.add(Sprite.mob_dead1.getFxImage());
        images.add(Sprite.mob_dead2.getFxImage());
        images.add(Sprite.mob_dead3.getFxImage());
    }
}
