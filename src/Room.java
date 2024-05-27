import java.awt.Graphics2D;

public class Room extends GameObject {

    public Room(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawRect((int)x, (int)y, w, h);
    }

    @Override
    public void update() {}
}
