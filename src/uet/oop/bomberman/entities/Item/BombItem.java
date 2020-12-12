package uet.oop.bomberman.entities.Item;


import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {

    public BombItem(Brick brick) {
        super(brick);
        img = Sprite.powerup_bombs.getFxImage();
    }


}
