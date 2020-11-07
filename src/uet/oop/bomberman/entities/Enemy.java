package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;

import java.util.Random;

public class Enemy extends Entity{

    private int time;
    private Vector lastPosition;
    private int vertical = 0;
    private int horizontal = 0;
    private int iMap;
    private int jMap;
    private boolean dr[] = {false, false, false, false};
    private final int left = 0, right = 1, up = 2, down = 3;

    public Enemy(Vector position, Image img) {
        super(position, img);
        time = 0;
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
        setDirect();
    }

    @Override
    public void update(){

    }

    public void update(GraphicsContext gc) {
        if (dr[0]) { setLeftAnimation(); }
        else { setRightAnimation(); }
        move();
        gc.clearRect(lastPosition.x, lastPosition.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        render(gc);
        lastPosition.setVector(position.x, position.y);
    }

    public void setLeftAnimation() {
        switch (time % 12) {
            case 0 :
                img = Sprite.balloom_left1.getFxImage();
                break;
            case 4 :
                img = Sprite.balloom_left2.getFxImage();
                break;
            case 8 :
                img = Sprite.balloom_left3.getFxImage();
                break;
        }
        time++;
    }

    public void setRightAnimation() {
        switch (time % 12) {
            case 0 :
                img = Sprite.balloom_right1.getFxImage();
                break;
            case 4 :
                img = Sprite.balloom_right2.getFxImage();
                break;
            case 8 :
                img = Sprite.balloom_right3.getFxImage();
                break;
        }
        time++;
    }

    public void move() {
        handleCollition();
        position.add(horizontal, vertical);
        handleCollition();
    }

    public void handleCollition() {
        char check = '#';
        if (dr[left]) {
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap - 1];
            horizontal = -4;
            vertical = 0;
        } else if (dr[right]){
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap + 1];
            horizontal = 4;
            vertical = 0;
        } else if (dr[up]) {
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap - 1][jMap];
            horizontal = 0;
            vertical = -4;
        } else {
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap + 1][jMap];
            horizontal = 0;
            vertical = 4;
        }
        if ( check == '*' || check == '#' ) {
            for (int i = 0; i < 4; i++) {
                if (dr[i]) {
                    setFalse();
                    int ran = new Random().nextInt(4);
                    while (ran == i) {
                        ran = new Random().nextInt(4);
                    }
                    dr[ran] = true;
                    handleCollition();
                    /*int j = (i % 2 == 0) ? i + 1 : i - 1;
                    dr[j] = true;*/
                    break;
                }
            }
        }
    }

    public void setFalse() {
        for (int i = 0; i < 4; i++) { dr[i] = false; }
    }

    public void setDirect() {
        int ran = new Random().nextInt(4);
        setFalse();
        dr[ran] = true;
    }

    public void setDirect(int d) {
        int ran = new Random().nextInt(4);
        setFalse();
        while (ran == d) {
            setFalse();
            ran = new Random().nextInt(4);
        }
        dr[ran] = true;
    }

}
