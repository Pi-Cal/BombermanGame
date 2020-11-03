package uet.oop.bomberman.Camera;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.Character.Vector;

public class Camera {
    public static final int width = BombermanGame.WIDTH;
    public static final int height = BombermanGame.HEIGHT;

    private Vector position;
    public Camera() {
        position = new Vector(0, 0);
    }

    public void update(Bomber bomber) {
    }
}
