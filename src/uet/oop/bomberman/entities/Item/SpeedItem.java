package uet.oop.bomberman.entities.Item;


import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    private double addSpeed = 1;

    public SpeedItem() {
        super();
        img = Sprite.powerup_speed.getFxImage();
    }

    public SpeedItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_speed.getFxImage();
    }


    @Override
    public void update() {

    }
}
