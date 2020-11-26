package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Item extends Entity {
    protected Brick brick;
    protected int time;

    public Item() {
        super();
        time = 15;
    }

    public Item(Brick brick) {
        super();
        this.brick = brick;
        position = brick.position;
        time = 15;
    }

    public void decTime() {
        --time;
    }

    public int getTime() {
        return time;
    }


}
