package uet.oop.bomberman.entities.Item;


import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

    public FlameItem() {
        super();
        img = Sprite.powerup_flames.getFxImage();
    }

    public FlameItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_flames.getFxImage();
    }


    @Override
    public void update() {

    }
}
