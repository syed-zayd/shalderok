import java.awt.geom.Point2D;

abstract class Enemy extends GameObject {
    boolean active;
    double vx, vy;
    int hp;

    public Enemy(double x, double y, int w, int h, int hp, Sprite s) {
        super(x, y, w, h, s);
        this.hp = hp;
        active = false;
    }
    Point2D.Double getNormalVectorToPlayer() {
        double dx = World.p.drawCenterX()-drawCenterX();
        double dy = World.p.drawCenterY()-drawCenterY();
        double magnitude = Math.sqrt(dx*dx+dy*dy);
        if (magnitude == 0) {
            return new Point2D.Double(0, 0);
        }
        return new Point2D.Double(dx/magnitude, dy/magnitude);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
