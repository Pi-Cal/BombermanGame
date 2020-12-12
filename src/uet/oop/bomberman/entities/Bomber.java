package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Item.Item;
import uet.oop.bomberman.entities.Item.TimeLimitedItem;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.notEntity.Bomb;
import uet.oop.bomberman.Character.Sound;

import java.util.ArrayList;

public class Bomber extends Entity {

    private int step = 0;
    private boolean completelyDead = false;
    private Vector lastPosition;
    private boolean dead = false;
    private double speed = 120 * 8;
    private final boolean[] side = {false, false, false, false};
    private final byte left = 0, right = 1, up = 2, down = 3;
    private final boolean[] halfSide = {false, false, false, false};
    private final byte upleft = 0, upright = 1, downright = 2, downleft = 3;
    private int timeDead = 0;
    private int maxBomb = 1;
    private int maxSpeed = 3;
    private int maxBombLength = 1;
    private ArrayList<Item> bombItems = new ArrayList<>();
    private ArrayList<Item> flameItems = new ArrayList<>();
    private ArrayList<TimeLimitedItem> speedItems = new ArrayList<>();

    public void addBomb(Item b) {
        bombItems.add(b);
    }

    public void addSpeed(TimeLimitedItem s) {
        speedItems.add(s);
    }

    public void addFlame(Item f) {
        flameItems.add(f);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }


    private void setFalse() {
        for (int i = 0; i < 4; i++) {
            side[i] = false;
            halfSide[i] = false;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    private boolean isMovingVertical() {
        return ((int)Math.round(position.y)) % Sprite.SCALED_SIZE != 0;
    }

    private boolean isMovingHorizontal() {
        return ((int) Math.round(position.x)) % Sprite.SCALED_SIZE != 0;
    }

    public boolean isCompletelyDead() {
        return completelyDead;
    }

    public Vector velocity = new Vector(0, 0);

    public Bomber(Vector position, Image img) {
        super(position, img);
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }


    public void updateTimeLimitedItem() {
        update1TypeOfItem(speedItems);
    }

    public void update1TypeOfItem(ArrayList<TimeLimitedItem> arrayList) {
        int i = 0;
        while (i<arrayList.size()) {
            TimeLimitedItem temp = arrayList.get(i);
            if (temp.getTime() == 0) { arrayList.remove(i); }
            else {
                temp.decTime();
                i++;
            }
        }
    }


    public void update(GraphicsContext gc) {
        if (!dead) {
            if (BombermanGame.input.equals("LEFT")) {
                if (side[left]) {
                    this.velocity.add(-speed, 0);
                    playMoveSound();
                }  else if (halfSide[downleft]) {
                    velocity.add(0, speed);
                    playMoveSound();
                } else if (halfSide[upleft]) {
                    velocity.add(0, -speed);
                    playMoveSound();
                }
                switch (step % 3) {
                    case 0:
                        img = Sprite.player_left.getFxImage();
                        break;
                    case 1:
                        img = Sprite.player_left_1.getFxImage();
                        break;
                    default:
                        img = Sprite.player_left_2.getFxImage();
                }
            }
            if (BombermanGame.input.equals("RIGHT")) {
                if (side[right]) {
                    this.velocity.add(speed, 0);
                    playMoveSound();
                } else if (halfSide[upright]) {
                    velocity.add(0, -speed);
                    playMoveSound();
                } else if (halfSide[downright]) {
                    velocity.add(0, speed);
                    playMoveSound();
                }
                switch (step % 3) {
                    case 0:
                        img = Sprite.player_right.getFxImage();
                        break;
                    case 1:
                        img = Sprite.player_right_1.getFxImage();
                        break;
                    default:
                        img = Sprite.player_right_2.getFxImage();
                }
            }
            if (BombermanGame.input.equals("UP")) {
                if (side[up]) {
                    this.velocity.add(0, -speed);
                    playMoveSound();
                } else if (halfSide[upleft]) {
                    velocity.add(-speed, 0);
                    playMoveSound();
                } else if (halfSide[upright]) {
                    velocity.add(speed, 0);
                    playMoveSound();
                }
                switch (step % 3) {
                    case 0:
                        img = Sprite.player_up.getFxImage();
                        break;
                    case 1:
                        img = Sprite.player_up_1.getFxImage();
                        break;
                    default:
                        img = Sprite.player_up_2.getFxImage();
                }
            }
            if (BombermanGame.input.equals("DOWN")) {
                if (side[down]) {
                    this.velocity.add(0, speed);
                    playMoveSound();
                } else if (halfSide[downleft]) {
                    velocity.add(-speed, 0);
                    playMoveSound();
                } else if (halfSide[downright]) {
                    velocity.add(speed, 0);
                    playMoveSound();
                }
                switch (step % 3) {
                    case 0:
                        img = Sprite.player_down.getFxImage();
                        break;
                    case 1:
                        img = Sprite.player_down_1.getFxImage();
                        break;
                    default:
                        img = Sprite.player_down_2.getFxImage();
                }
            }
            Vector checkPos = position.toNormal().round();
            if (BombermanGame.input.equals("SPACE") &&
                    BombermanGame.map[(int)checkPos.y][(int) checkPos.x] != '`') {
                if (BombermanGame.bombs.size() < maxBomb) {
                    BombermanGame.bombs.add(new Bomb(checkPos, maxBombLength));
                }

            }

            if(BombermanGame.input.equals("")) {
                img = Sprite.player_down.getFxImage();
            }
            velocity.multiply(1.0/120);
            position.add(this.getVelocity());
            step++;
            maxBombLength = 1 + flameItems.size();
            maxSpeed = 3 * (speedItems.size() + 1);
            maxBomb = 1 + bombItems.size();
        } else {
            deadAnimation();
            Sound.player_dead.start();
        }
        if (step >= 9) {
            step = 0;
        }
        clear(gc);
        render(gc);


    }


    public void clear(GraphicsContext gc) {
        gc.clearRect(lastPosition.x, lastPosition.y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        lastPosition.setVector(position.x, position.y);
    }

    private boolean checkMovable (int i, int j) {
        return (BombermanGame.map[i][j] == ' ' || BombermanGame.map[i][j] == '`');
    }
    public void handleCollision() {
        int iMap = (int) (position.y / Sprite.SCALED_SIZE);
        int jMap = (int) (position.x / Sprite.SCALED_SIZE);

        setFalse();
        if (!isMovingHorizontal()) {
            if (checkMovable(iMap + 1,jMap)) {
                side[down] = true;
            }
        } else {
            if (position.x % Sprite.SCALED_SIZE >= 16) {
                if (checkMovable(iMap - 1,jMap + 1)) {
                    halfSide[upright] = true;
                }
                if (checkMovable(iMap + 1,jMap + 1)) {
                    halfSide[downright] = true;
                }
            }
        }

        if (!isMovingVertical()) {
            if (checkMovable(iMap,jMap + 1)) {
                side[right] = true;
            }
        } else {
            if (position.y % Sprite.SCALED_SIZE >= 16) {
                if (checkMovable(iMap + 1,jMap - 1)) {
                    halfSide[downleft] = true;
                }
                if (checkMovable(iMap + 1,jMap + 1)) {
                    halfSide[downright] = true;
                }
            }
        }



        if (isMovingHorizontal()) {
            jMap = (int) (Math.ceil(position.x / Sprite.SCALED_SIZE));
            if (position.x % Sprite.SCALED_SIZE <= 16) {
                if (checkMovable(iMap - 1, jMap - 1)) {
                    halfSide[upleft] = true;
                }
                if (checkMovable(iMap + 1, jMap - 1)) {
                    halfSide[downleft] = true;
                }
            }
        } else if (isMovingVertical()) {
            iMap = (int) (Math.ceil(position.y / Sprite.SCALED_SIZE));
            if (position.y % Sprite.SCALED_SIZE <= 16) {
                if (checkMovable(iMap - 1, jMap - 1)) {
                    halfSide[upleft] = true;
                }
                if (checkMovable(iMap - 1, jMap + 1)) {
                    halfSide[upright] = true;
                }
            }
        }
        if (!isMovingHorizontal()) {
            if (checkMovable(iMap - 1, jMap)) {
                side[up] = true;
            }
        }
        if (!isMovingVertical()) {
            if (checkMovable(iMap, jMap - 1)) {
                side[left] = true;
            }
        }




    }


    public void deadAnimation() {
        if (timeDead <= 12) {
            this.img = Sprite.player_dead1.getFxImage();
            timeDead++;
        } else if (timeDead <= 24) {
            this.img = Sprite.player_dead2.getFxImage();
            timeDead++;
        } else {
            this.img = Sprite.player_dead3.getFxImage();
            timeDead++;
        }
        if (timeDead > 36) { completelyDead = true; }
    }

    public boolean collidedWithItem(Entity entity) {
        return  (position.equals(entity.position));
    }

    public void playMoveSound() {
        if (step % 2 == 0) {
            Sound.foot_step_2.stop();
            Sound.foot_step_2.reset();
            Sound.foot_step_1.start();
        } else {
            Sound.foot_step_1.stop();
            Sound.foot_step_1.reset();
            Sound.foot_step_2.start();
        }
    }
}