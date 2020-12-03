package uet.oop.bomberman.entities.Enemies;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class Oneal extends EnemyAbs{

    public Oneal(Vector position, Image img) {
        super(position, img);
        time = 0;
        lastPosition = new Vector(position.x * Sprite.SCALED_SIZE, position.y * Sprite.SCALED_SIZE);
        setDirect();
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
        position.add(vel);
    }

    @Override
    public void handleCollition() {
        if (!isMovingHorizontal() && !isMovingVertical()) {
            setFalse();
            int checkDir = find_the_way();
            if (checkDir < 5)  {
                dr[checkDir] = true;
            } else {
                vel.setVector(0,0);
                return;
            }
        }

        if (dr[left]) {
            vel.setVector(-speed,0);
        } else if (dr[right]){
            vel.setVector(speed,0);
        } else if (dr[up]) {
            vel.setVector(0,-speed);
        } else if (dr[down]){
            vel.setVector(0,speed);
        }
    }

    @Override
    public void update() {

    }

    public int find_the_way() {
        int u = (int) (position.y / Sprite.SCALED_SIZE);
        int v = (int) (position.x / Sprite.SCALED_SIZE);
        int m = (int) (BombermanGame.bomberman.position.y / Sprite.SCALED_SIZE);
        int n = (int) (BombermanGame.bomberman.position.x / Sprite.SCALED_SIZE);

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
        if (r == 0) return 5;
        if (l > r) {
            Random direction = new Random();
            int t = direction.nextInt(r) + 1;
            return d[t];
        } else {
            return d[l];
        }
    }

    protected boolean check_Collision(int i, int j) {
        char check = BombermanGame.map[i][j];
        return !(check == '#' || check == '*' || check == '0');
    }


}
