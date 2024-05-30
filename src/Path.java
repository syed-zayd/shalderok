import java.awt.Color;
import java.awt.Graphics2D;

public class Path extends GameObject {

    public Path(double x, double y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update() {
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);
    }

}
