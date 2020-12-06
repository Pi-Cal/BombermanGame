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

    public boolean isAppear() {
        return appear;
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
