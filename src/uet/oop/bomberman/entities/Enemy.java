package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;

public class Enemy extends Entity{
    private boolean horizontalMovinf = true;

    public Enemy(Vector p, Image img) {
        super(p, img);
    }

    public boolean isHorizontalMovinf() {
        return horizontalMovinf;
    }

    public void setHorizontalMovinf(boolean horizontalMovinf) {
        this.horizontalMovinf = horizontalMovinf;
    }

    @Override
    public void update(){

    }

}
