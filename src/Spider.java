import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

class Spider extends Enemy {
    String debug = "0";

    public Spider(double x, double y) {
        super(x, y, 30, 30, 10);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);

        Util.drawCenteredString(g2d, debug, drawCenterX(), drawY()-10);
    }

    private void updateVelocity() {
        System.out.println(pathfindingPath.size());
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
}
