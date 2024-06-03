import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

abstract class Enemy extends Entity {
    
    boolean activated;

    double accuracy;
    ArrayList<GameObject> pathfindingPath; // pathfinding
    Point pathfindingCurrentIndex;

    public Enemy(double x, double y, double spd, int hp, double accuracy, Sprite s) {
        super(x, y, spd, hp, s);
        this.hp = hp;
        this.accuracy = accuracy;
        activated = false;
        pathfindingPath = new ArrayList<GameObject>();
        pathfindingCurrentIndex = new Point(-1,-1);
    }

    private void pathfind(){
        Point2D.Double v;
        
        if (pathfindingPath.size() < 1) {
            v = getVectorTo(World.p);
        } else {
            v = getVectorTo(pathfindingPath.get(0));
            vx = v.x;
            vy = v.y;

            if (vx < -spd) {
                vx = -spd;
            } else if (vx > spd) {
                vx = spd;
            }
            if (vy < -spd) {
                vy = -spd;
            } else if (vy > spd) {
                vy = spd;
            }
        }
    }

    @Override
    protected void updateAngle() {
        Point2D.Double v = getUnitVectorTo(World.p);
        angle = Math.atan(- v.y / v.x);
        double offset = (Math.PI / 4) * (1 - accuracy);
        angle += (Math.random() * offset) - (offset / 2.);
    }

    @Override
    protected void updateVelocity() {
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
            pathfind();
        }
    }

    @Override
    protected void updateDirection() {
        // do nothing
    }

    public void attack(){
        if(Math.random() < 0.5){
            weapon.angle = angle;
            weapon.shoot();
        }
    }

    @Override
    public void paint(Graphics2D g2d){
        super.paint(g2d);
        Util.drawCenteredString(g2d, String.format("(%.2f, %.2f)", vx, vy), drawCenterX(), drawY()-10);
        if(weapon != null){
            weapon.paint(g2d);
        }
    }

    public void update(){
        super.update();
        if(hp <= 0){
            activated = false;
        }
    }

}
