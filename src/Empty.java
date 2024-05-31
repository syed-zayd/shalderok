import java.awt.Color;
import java.awt.Graphics2D;

public class Empty extends GameObject {

    public Empty(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update() {
        // emptiness
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.drawRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
