package uet.oop.bomberman.entities.Item;

import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;

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
