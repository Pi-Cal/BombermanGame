package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Character.Vector;

import java.awt.*;

public abstract class Entity {
    public Vector position = new Vector(0,0);
    protected Image img;
    private double width = Math.round(Sprite.SCALED_SIZE);
    private double height = Math.round(Sprite.SCALED_SIZE);
    protected double numFrame = 3;

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Entity(Vector p, Image img) {
        this.position.setVector(p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE);
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public Entity(Vector p) {
        this.position.setVector(p.x * Sprite.SCALED_SIZE, p.y * Sprite.SCALED_SIZE);
    }

    public void render(GraphicsContext gc) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView iv = new ImageView(img);
        Image base = iv.snapshot(params, null);

        gc.drawImage(base, position.x, position.y);
    }

    public abstract void update();

    public boolean handle_1_Collision(Entity other) {
        if (other instanceof Grass) { return false; }
        return Math.round(position.x + width) >= Math.round(other.position.x) &&
                Math.round(position.x) <= Math.round(other.position.x + width) &&
                Math.round(position.y + height) >= Math.round(other.position.y) &&
                Math.round(position.y) <= Math.round(other.position.y + height);
    }
}
