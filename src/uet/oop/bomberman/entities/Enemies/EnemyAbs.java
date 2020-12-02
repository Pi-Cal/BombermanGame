package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.Random;

public abstract class EnemyAbs extends Entity {

    protected int time;
    protected int timeDead = 0;
    protected Vector lastPosition;
    protected int vertical = 0;
    protected int horizontal = 0;
    protected int iMap;
    protected int jMap;
    protected boolean dr[] = {false, false, false, false};
    protected final int left = 0, right = 1, up = 2, down = 3;
    protected boolean dead = false;
    protected boolean completelyDead = false;
    protected ArrayList<Image> images;

    public EnemyAbs(Vector p, Image img) {
        super(p, img);
        images = new ArrayList<>();
        addImage();
    }

    public abstract void update(GraphicsContext gc);

    public void setLeftAnimation() {
        switch (time % 12) {
            case 0 :
                img = images.get(0);
                break;
            case 4 :
                img = images.get(1);
                break;
            case 8 :
                img = images.get(2);
                break;
        }
        time++;
    }

    public void setRightAnimation() {
        switch (time % 12) {
            case 0 :
                img = images.get(3);
                break;
            case 4 :
                img = images.get(4);
                break;
            case 8 :
                img = images.get(5);
                break;
        }
        time++;
    }

    public void setDeadAnimation() {
        if (timeDead <= 6) { img = images.get(6); }
        else if (timeDead <= 12) { img = images.get(7); }
        else if (timeDead <= 18) { img = images.get(8); }
        else {
            img = images.get(9);
            if (time >= 25) {
                completelyDead = true;
            }
        }
        timeDead++;
    }

    public void setFalse() {
        for (int i = 0; i < 4; i++) { dr[i] = false; }
    }

    public void setDirect() {
        int ran = new Random().nextInt(4);
        setFalse();
        dr[ran] = true;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isCompletelyDead() {
        return completelyDead;
    }

    public abstract void addImage();

    public abstract void move();

    public abstract void handleCollition();

}
