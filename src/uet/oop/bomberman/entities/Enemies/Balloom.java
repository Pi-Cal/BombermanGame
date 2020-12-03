package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;

import java.util.Random;

public class Balloom extends EnemyAbs {

    public Balloom(Vector position, Image img) {
        super(position, img);
        time = 0;
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
        setDirect();
    }

    @Override
    public void update(){

    }

    @Override
    public void move() {
        handleCollition();
        position.add(vel);
        handleCollition();
    }

    @Override
    public void handleCollition() {
        char check = '#';
        if (dr[left]) {
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap - 1];
            vel.setVector(-speed,0);
        } else if (dr[right]){
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap + 1];
            vel.setVector(speed,0);
        } else if (dr[up]) {
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap - 1][jMap];
            vel.setVector(0,-speed);
        } else {
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap + 1][jMap];
            vel.setVector(0,speed);
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