import java.awt.Color;
import java.awt.Graphics2D;

public class Door extends GameObject {
    boolean locked;

    public Door(double x, double y, int w, int h) {
        super(x, y, w, h, null);
        locked = true;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(locked ? new Color(200, 200, 20) : Color.LIGHT_GRAY);
        g2d.fillRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);
    }

    @Override
    public boolean isSolid() {
        return locked;
    }
}
