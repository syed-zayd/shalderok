import java.awt.Graphics2D;

public class Hitbox {
    double x;
    double y;
    double w;
    double h;

    public Hitbox(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void align(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getTop() {
        return y;
    }
    public double getBottom() {
        return y+h;
    }
    public double getLeft() {
        return x;
    }
    public double getRight() {
        return x+w;
    }
    public double getCenterX() {
        return (getLeft()+getRight())/2.0;
    }
    public double getCenterY() {
        return (getTop()+getBottom())/2.0;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawRect((int)x, (int)y, (int)w, (int)h);
    }
}
