import java.awt.Graphics2D;

public class Block extends WorldObject {

    public Block(double x, double y, int w, int h) {
        super("", x, y, w, h);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawRect((int)x, (int)y, (int)w, (int)h);
    }

}
