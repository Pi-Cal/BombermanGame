package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;

public class Ghost2 extends Oneal {

    public Ghost2(Vector position, Image img) {
        super(position, img);
    }

    @Override
    public void update(GraphicsContext gc) {
        super.update(gc);
    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void handleCollition() {
        super.handleCollition();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public int find_the_way() {
        return super.find_the_way();
    }

    @Override
    protected boolean check_Collision(int i, int j) {
        return super.check_Collision(i, j);
    }

    @Override
    public void addImage() {
        images.add(Sprite.ghost2_left1.getFxImage());
        images.add(Sprite.ghost2_left2.getFxImage());
        images.add(Sprite.ghost2_left3.getFxImage());
        images.add(Sprite.ghost2_right1.getFxImage());
        images.add(Sprite.ghost2_right2.getFxImage());
        images.add(Sprite.ghost2_right3.getFxImage());
        images.add(Sprite.ghost2_dead.getFxImage());
        images.add(Sprite.mob_dead1.getFxImage());
        images.add(Sprite.mob_dead2.getFxImage());
        images.add(Sprite.mob_dead3.getFxImage());
    }
}
