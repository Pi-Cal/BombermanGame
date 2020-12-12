package uet.oop.bomberman.entities.Item;

import uet.oop.bomberman.entities.Brick;

public abstract class TimeLimitedItem extends Item {
    protected Brick brick;
    protected int time;

    public TimeLimitedItem() {
        super();
        time = 15;
    }

    public TimeLimitedItem(Brick brick) {
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
