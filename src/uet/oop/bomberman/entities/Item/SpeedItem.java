package uet.oop.bomberman.entities.Item;


import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends TimeLimitedItem {

    public SpeedItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_speed.getFxImage();
    }
}
