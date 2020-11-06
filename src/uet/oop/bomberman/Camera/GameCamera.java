package uet.oop.bomberman.Camera;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Map;

public class GameCamera {
    public final int width = Math.round(BombermanGame.WIDTH * Sprite.SCALED_SIZE);
    public final int height = Math.round(BombermanGame.HEIGHT * Sprite.SCALED_SIZE);
    private double xOffset, yOffset;
    private double lastXOffset, lastYOffset;
    public GameCamera(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.lastXOffset = xOffset;
        this.lastYOffset = yOffset;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public void update(Bomber bomber) {
        int size = Sprite.SCALED_SIZE;
        xOffset = bomber.position.x + Math.round(bomber.getWidth() / 2) - Math.round(width / 2) ;
        yOffset  = bomber.position.y + Math.round(bomber.getHeight() / 2) - Math.round(height / 2);
        if (xOffset < 0) { xOffset = 0; }
        if (yOffset < 0) { yOffset = 0; }
        if (xOffset + width > Math.round(BombermanGame.getRealWidth() * size) + 16){
            xOffset = Math.floor(BombermanGame.getRealWidth() * size - width) + 16;
        }
        if (yOffset + height > Math.round(BombermanGame.getRealHeight() * size) + 48){
            yOffset = Math.floor(BombermanGame.getRealHeight() * size - height) + 48;
        }
        System.out.println(xOffset + " and " + lastXOffset);
        lastYOffset = yOffset;
        lastXOffset = xOffset;
    }

    public void setLastXOffset(double lastXOffset) {
        this.lastXOffset = lastXOffset;
    }

    public void setLastYOffset(double lastYOffset) {
        this.lastYOffset = lastYOffset;
    }

    public double getLastXOffset() {
        return lastXOffset;
    }

    public double getLastYOffset() {
        return lastYOffset;
    }
}
