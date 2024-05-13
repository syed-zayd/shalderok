import java.awt.Graphics2D;

public class Arrow extends Projectile {

    public Arrow(double x, double y, WorldObject owner) {
        super("sprites/arrow.png", x, y, 32, 32, 1, owner);
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void paint(Graphics2D g2d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paint'");
    }

}
