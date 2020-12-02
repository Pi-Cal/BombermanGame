package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;

import java.util.Random;

public class Enemy1 extends EnemyAbs {

    public Enemy1(Vector position, Image img) {
        super(position, img);
        time = 0;
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
        setDirect();
    }

    @Override
    public void update(){

    }

    @Override
    public void update(GraphicsContext gc) {
        if (!dead) {
            if (dr[0]) {
                setLeftAnimation();
            } else {
                setRightAnimation();
            }
            move();
        } else {
            setDeadAnimation();
        }
        gc.clearRect(lastPosition.x, lastPosition.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        render(gc);
        lastPosition.setVector(position.x, position.y);
    }

    @Override
    public void move() {
        handleCollition();
        position.add(horizontal, vertical);
        handleCollition();
    }

    @Override
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
        if ( check == '*' || check == '#' || check == '0') {
            for (int i = 0; i < 4; i++) {
                if (dr[i]) {
                    setFalse();
                    int ran = new Random().nextInt(4);
                    while (ran == i) {
                        ran = new Random().nextInt(4);
                    }
                    dr[ran] = true;
                    handleCollition();
                    break;
                }
            }
        }
    }

    @Override
    public void addImage() {
        images.add(Sprite.balloom_left1.getFxImage());
        images.add(Sprite.balloom_left2.getFxImage());
        images.add(Sprite.balloom_left3.getFxImage());
        images.add(Sprite.balloom_right1.getFxImage());
        images.add(Sprite.balloom_right2.getFxImage());
        images.add(Sprite.balloom_right3.getFxImage());
        images.add(Sprite.balloom_dead.getFxImage());
        images.add(Sprite.mob_dead1.getFxImage());
        images.add(Sprite.mob_dead2.getFxImage());
        images.add(Sprite.mob_dead3.getFxImage());
    }

}