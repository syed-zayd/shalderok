import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics2D;

abstract class Weapon extends GameObject {
    double angle;
    ArrayList <Projectile> activeProjectiles;
    ArrayList <Projectile> queuedProjectiles;

    public Weapon(double x, double y, Sprite s) {
        super(x, y, 32, 32, s);
        activeProjectiles = new ArrayList <Projectile>();
        queuedProjectiles = new ArrayList <Projectile>();
        currentFrame = s.getSprite("idle", 0);
    }

    abstract void shoot();

    @Override
    public void update(){
        activeProjectiles.addAll(queuedProjectiles);
        queuedProjectiles.clear();
        Iterator<Projectile> it = activeProjectiles.iterator();
        while (it.hasNext()) {
            Projectile projectile = it.next();
            projectile.update();
            if (projectile.timeOfFlight >= projectile.duration) {
                it.remove();
            }
        }
    }

    @Override
    public void paint(Graphics2D g2d){
        super.paint(g2d);
        for(Projectile projectile: activeProjectiles){
            projectile.paint(g2d);
        }
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}