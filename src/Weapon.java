import java.util.ArrayList;

abstract class Weapon extends GameObject {
    double angle;
    ArrayList <Projectile> activeProjectiles;
    ArrayList <Projectile> queuedProjectiles;

    public Weapon(double x, double y, int w, int h) {
        super(x, y, w, h);
        activeProjectiles = new ArrayList <Projectile>();
        queuedProjectiles = new ArrayList <Projectile>();
    }
    
    abstract void shoot();

    @Override
    public boolean isSolid() {
        return false;
    }
}