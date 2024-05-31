import java.awt.Graphics2D;

abstract class GameObject {
    double x, y;
    int w, h;

    public GameObject(double x, double y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int drawX() {
        return (int)Math.round(x);
    }
    public int drawY() {
        return (int)Math.round(y);
    }
    public int drawCenterX() {
        return (int)Math.round((2*x+w)/2.);
    }
    public int drawCenterY() {
        return (int)Math.round((2*y+h)/2.);
    }

    public abstract void update();
    public abstract void paint(Graphics2D g2d);
    public abstract boolean isSolid();
}