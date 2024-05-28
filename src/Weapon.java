import java.util.ArrayList;

abstract class Weapon extends GameObject {
    double angle;
    ArrayList <Projectile> projectiles;

    public Weapon(double x, double y, int w, int h) {
        super(x, y, w, h);
        projectiles = new ArrayList <Projectile>();
    }
    
    abstract void shoot();
}