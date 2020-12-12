package uet.oop.bomberman.entities.Item;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    protected Brick brick;
    protected int time;
    private boolean appear = false;

    public Item() {
        super();
    }

    public Item(Brick brick) {
        super();
        this.brick = brick;
        position = brick.position;
    }

    public int getTime() {
        return time;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (appear) {
            super.render(gc);
        }
    }
}
