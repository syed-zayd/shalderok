import java.awt.Color;
import java.awt.Graphics2D;

public class Staircase extends GameObject {


    public Staircase(double x, double y, int w, int h) {
        super(x, y, w, h, null);
    }


    @Override
    public void interact() {
        World.nextFloor();
    }

    @Override
    public void update() {}

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);
    }

}
