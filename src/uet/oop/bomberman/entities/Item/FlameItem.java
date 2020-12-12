package uet.oop.bomberman.entities.Item;


import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

    public FlameItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_flames.getFxImage();
    }

}
