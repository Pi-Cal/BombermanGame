package uet.oop.bomberman.Character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Couting {
    private Vector position;
    private int timeInt;
    private int size;
    private int enemyKilledInt;

    private Label time;
    private Label enemyKilled;

    public Couting() {
        timeInt = 200;
        size = 50;
        position = new Vector(80,60);
        time = new Label(timeInt + "");
        enemyKilledInt = 0;
        enemyKilled = new Label("E:" + enemyKilledInt + "/" + BombermanGame.getEnemiesNeedingKill());

        time.setTextFill(Color.WHITE);
        time.setFont(Font.font("Consolas", size));
        time.setTranslateX(80);
        time.setTranslateY(23);

        enemyKilled.setTextFill(Color.WHITE);
        enemyKilled.setFont(Font.font("Consolas", size));
        enemyKilled.setTranslateX(300);
        enemyKilled.setTranslateY(23);

    }

    public String getTime() {
        return timeInt + "";
    }

    public int getTimeInt() { return timeInt; }

    public void setTimeInt(int timeInt) {
        this.timeInt = timeInt;
    }

    public void update() {
        time.setText(timeInt + "");
        enemyKilled.setText("E:" + enemyKilledInt + "/" + BombermanGame.getEnemiesNeedingKill());
        if (timeInt >= 0) {
            timeInt--;
        }
    }

    public Label getTimeLabel() {
        return time;
    }

    public Label getEnemyKilled() {
        return enemyKilled;
    }

    public void incEnemyKilled() {
        enemyKilledInt++;
    }
}
