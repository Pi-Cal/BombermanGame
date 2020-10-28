package uet.oop.bomberman.Character;

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

}
