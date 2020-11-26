package uet.oop.bomberman.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Timing {
    Vector position;
    int timeInt;
    int size;

    public Timing() {
        timeInt = 200;
        size = 50;
        position = new Vector(80,60);
    }

    public String getTime() {
        return timeInt + "";
    }

    public int getTimeInt() { return timeInt; }

    public void setTimeInt(int timeInt) {
        this.timeInt = timeInt;
    }

    public void update(GraphicsContext gc) {
        gc.clearRect(0, 0, BombermanGame.WIDTH * Sprite.SCALED_SIZE, 3 * Sprite.SCALED_SIZE);
        gc.setFill(Color.rgb(179,179,179));
        gc.fillRect(0,0, Sprite.SCALED_SIZE * BombermanGame.WIDTH,Sprite.SCALED_SIZE * 3);
        gc.setFont(Font.font("Consolas", size));
        gc.setFill(Color.WHITE);
        gc.fillText(getTime(), position.x, position.y);
        if (timeInt >= 0) {
            timeInt--;
        }
    }
}
