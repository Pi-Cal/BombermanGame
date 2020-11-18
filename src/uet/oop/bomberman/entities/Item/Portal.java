package uet.oop.bomberman.entities.Item;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Item {

    public Portal() {
        super();
        img = Sprite.portal.getFxImage();
    }

    public Portal(Brick brick) {
        super(brick);
        img = Sprite.portal.getFxImage();
    }


    @Override
    public void update() {

    }

}
