import java.awt.Color;
import java.awt.Graphics2D;

class Spider extends Enemy {
    public Spider(double x, double y) {
        super(x, y, 30, 30);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);

        Util.drawCenteredString(g2d, "debug", drawCenterX(), drawY()-10);
    }

    @Override
    public void update() {
        
    }
}
