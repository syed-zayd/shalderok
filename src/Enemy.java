import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

abstract class Enemy extends GameObject {
    boolean activated;
    double vx, vy;
    double knockbackX, knockbackY;
    int hp;

    ArrayList<GameObject> pathfindingPath; // pathfinding
    Point pathfindingCurrentIndex;

    public Enemy(double x, double y, int w, int h, int hp, Sprite s) {
        super(x, y, w, h, s);
        this.hp = hp;
        activated = false;
        pathfindingPath = new ArrayList<GameObject>();
        pathfindingCurrentIndex = new Point(-1,-1);
    }

    Point2D.Double getUnitVectorTo(GameObject obj) {
        double dx = obj.drawCenterX()-drawCenterX();
        double dy = obj.drawCenterY()-drawCenterY();
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
