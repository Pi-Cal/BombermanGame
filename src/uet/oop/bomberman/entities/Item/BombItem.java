package uet.oop.bomberman.entities.Item;


import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

    public BombItem() {
        super();
        img = Sprite.powerup_bombs.getFxImage();
    }

    public BombItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_bombs.getFxImage();
    }


    @Override
    public void update() {

    }
}
