package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy2 extends EnemyAbs{

    public Enemy2(Vector position, Image img) {
        super(position, img);
        time = 0;
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
        setDirect();
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
    public void addImage() {
        images.add(Sprite.oneal_left1.getFxImage());
        images.add(Sprite.oneal_left2.getFxImage());
        images.add(Sprite.oneal_left3.getFxImage());
        images.add(Sprite.oneal_right1.getFxImage());
        images.add(Sprite.oneal_right2.getFxImage());
        images.add(Sprite.oneal_right3.getFxImage());
        images.add(Sprite.oneal_dead.getFxImage());
        images.add(Sprite.mob_dead1.getFxImage());
        images.add(Sprite.mob_dead2.getFxImage());
        images.add(Sprite.mob_dead3.getFxImage());

    }

    @Override
    public void move() {
        handleCollition();
        position.add(horizontal, vertical);
    }

    @Override
    public void handleCollition() {
        char check = '#';
        setFalse();
        if (!isMovingHorizontal() && !isMovingVertical()) {
            int checkDir = find_the_way(BombermanGame.bomberman);
            System.out.println("D: " + checkDir);
            if (checkDir < 5)  {
                dr[checkDir] = true;
            } else {
                horizontal = 0;
                vertical = 0;
                return;
            }
        }

        for (int i = 0; i < 4; i++) System.out.println(dr[i]);
        System.out.println("");

        if (dr[left]) {
            System.out.println("left");
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap - 1];
            horizontal = -4;
            vertical = 0;
        } else if (dr[right]){
            System.out.println("right");
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap][jMap + 1];
            horizontal = 4;
            vertical = 0;
        } else if (dr[up]) {
            System.out.println("up");
            jMap = (int) Math.ceil(position.x / Sprite.SCALED_SIZE);
            iMap = (int) Math.ceil(position.y / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap - 1][jMap];
            horizontal = 0;
            vertical = -4;
        } else if (dr[down]){
            System.out.println("left");
            jMap = (int)(Math.round(position.x) / Sprite.SCALED_SIZE);
            iMap = (int)(Math.round(position.y) / Sprite.SCALED_SIZE);
            check = BombermanGame.map[iMap + 1][jMap];
            horizontal = 0;
            vertical = 4;
        }
    }

    @Override
    public void update() {

    }

    public int find_the_way(Bomber bomber) {
        int u = (int) (position.y / Sprite.SCALED_SIZE);
        int v = (int) (position.x / Sprite.SCALED_SIZE);
        int m = (int) (bomber.position.y / Sprite.SCALED_SIZE);
        int n = (int) (bomber.position.x / Sprite.SCALED_SIZE);
        System.out.println(u + " " + v + " " + m + " " + n);

        int width = BombermanGame.getRealWidth();
        int height = BombermanGame.getRealHeight();
        int[] p = new int[width * height];
        int[] q = new int[width * height];
        int[] d = new int[width * height];
        boolean check_map[][] = new boolean[height][width];
        int l = 0;
        int r = 0;
        p[0] = u;
        q[0] = v;
        d[0] = -1;
        while (l <= r) {
            u = p[l];
            v = q[l];
            if (u == m && v == n) break;
            if (!check_map[u - 1][v] && check_Collision(u - 1, v)) {
                r++;
                p[r] = u - 1;
                q[r] = v;
                check_map[u - 1][v] = true;
                if (d[l] == -1) d[r] = up;
                else d[r] = d[l];
            }
            if (!check_map[u + 1][v] && check_Collision(u + 1, v)) {
                r++;
                p[r] = u + 1;
                q[r] = v;
                check_map[u + 1][v] = true;
                if (d[l] == -1) d[r] = down;
                else d[r] = d[l];
            }
            if (!check_map[u][v - 1] && check_Collision(u, v - 1)) {
                r++;
                p[r] = u;
                q[r] = v - 1;
                check_map[u][v - 1] = true;
                if (d[l] == -1) d[r] = left;
                else d[r] = d[l];
            }
            if (!check_map[u][v + 1] && check_Collision(u, v + 1)) {
                r++;
                p[r] = u;
                q[r] = v + 1;
                check_map[u][v + 1] = true;
                if (d[l] == -1) d[r] = right;
                else d[r] = d[l];
            }
            l++;
        }
        System.out.println(l + " " + r);
        if (r == 0) return 5;
        if (l > r) {
            Random direction = new Random();
            int t = direction.nextInt(r) + 1;
            return d[t];
        } else {
            return d[l];
        }
    }

    private boolean check_Collision(int i, int j) {
        char check = BombermanGame.map[i][j];
        return !(check == '#' || check == '*' || check == '0');
    }

    private boolean isMovingVertical() {
        return ((int)Math.round(position.y)) % Sprite.SCALED_SIZE != 0;
    }

    private boolean isMovingHorizontal() {
        return ((int) Math.round(position.x)) % Sprite.SCALED_SIZE != 0;
    }
}
