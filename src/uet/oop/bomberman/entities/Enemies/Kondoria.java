package uet.oop.bomberman.entities.Enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.notEntity.Bomb;

import java.util.Random;

public class Kondoria extends Oneal {

    private int randomBombRate = 100;

    public Kondoria(Vector position, Image img) {
        super(position, img);
    }

    @Override
    public void handleCollition() {
        if ((new Random().nextInt(randomBombRate) + 1) % randomBombRate == 0 && BombermanGame.enemyBombs.size() <= 2) {
            BombermanGame.enemyBombs.add(new Bomb(position.toNormal().round(), 1));
        }
        super.handleCollition();
    }

    @Override
    public int find_the_way() {
        int u = (int) (position.y / Sprite.SCALED_SIZE);
        int v = (int) (position.x / Sprite.SCALED_SIZE);
        int m = new Random().nextInt(BombermanGame.getRealHeight());
        int n = new Random().nextInt(BombermanGame.getRealWidth());

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

    @Override
    public void addImage() {
        images.add(Sprite.kondoria_left1.getFxImage());
        images.add(Sprite.kondoria_left2.getFxImage());
        images.add(Sprite.kondoria_left3.getFxImage());
        images.add(Sprite.kondoria_right1.getFxImage());
        images.add(Sprite.kondoria_right2.getFxImage());
        images.add(Sprite.kondoria_right3.getFxImage());
        images.add(Sprite.kondoria_dead.getFxImage());
        images.add(Sprite.mob_dead1.getFxImage());
        images.add(Sprite.mob_dead2.getFxImage());
        images.add(Sprite.mob_dead3.getFxImage());
    }
}
