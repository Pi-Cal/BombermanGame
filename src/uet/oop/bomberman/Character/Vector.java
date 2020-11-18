package uet.oop.bomberman.Character;

import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(double dX, double dY) {
        this.x += dX;
        this.y += dY;
        setVector(Math.round(x), Math.round(y));
    }

    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
        setVector(Math.round(x), Math.round(y));
    }

    public void multiply(double m) {
        this.x *= m;
        this.y *= m;
        setVector(Math.round(x), Math.round(y));
    }

    public Vector toNormal() {
        return new Vector(this.x / Sprite.SCALED_SIZE,
        this.y / Sprite.SCALED_SIZE);
    }

    public Vector round() {
        return new Vector(Math.round(x), Math.round(y));
    }

    public static List<Vector> inLine(Vector start, Vector end) {
        List<Vector> list = new ArrayList<>();
        if (start.x == end.x) {
            double a = Math.min(start.y, end.y);
            double b = Math.max(start.y, end.y);
            for (; a <= b; a++) {
                list.add(new Vector(start.x, a));
            }
        } else if (start.y == end.y) {
            double a = Math.min(start.x, end.x);
            double b = Math.max(start.x, end.x);
            for (; a <= b; a++) {
                list.add(new Vector(a, start.y));
            }
        }
        return list;
    }

    @Override
    public boolean equals(Object v) {
        if (v instanceof Vector) {
            return (this.x == ((Vector) v).x && this.y == ((Vector) v).y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
