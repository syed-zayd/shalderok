import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class Spider extends Enemy {
    String debug = "0";

    public Spider(double x, double y) {
        super(x, y, 10, SpriteLoader.getSprite("spider"));
    }

    private void updateVelocity() {
        Point2D.Double v;
        
        if (pathfindingPath.size() < 1) {
            v = getUnitVectorTo(World.p);
        } else {
            v = getUnitVectorTo(pathfindingPath.get(0));
        }
        vx = 2*v.x;
        vy = 2*v.y;
    }

    @Override
    public void update() {
        debug = String.format("%d, %d", pathfindingCurrentIndex.x, pathfindingCurrentIndex.y);

        knockbackX *= 0.92;
        knockbackY *= 0.92;

        // if knockback is significant, prevent moving
        if (Math.abs(knockbackX) > 1 || Math.abs(knockbackY) > 1) {
            // apply knockback
            vx = knockbackX;
            vy = knockbackY;
        } else if (knockbackX != 0 || knockbackY != 0) {
            knockbackX = 0;
            knockbackY = 0;
            vx = 0;
            vy = 0;
        } else {
            updateVelocity();
        }

        x+=vx;
        y+=vy;
    }

    @Override
    public void interact() {

    }
}
