import java.util.ArrayList;
import java.awt.Graphics2D;

abstract class Weapon extends Item {
    double angle;
    ArrayList <Projectile> activeProjectiles;
    ArrayList <Projectile> queuedProjectiles;
    int cooldownTimer;
    int projectilesShot;
    Entity owner;

    public Weapon(Entity owner, double x, double y, Sprite s) {
        super(x, y, 32, 32, s);
        this.owner = owner;
        activeProjectiles = new ArrayList <Projectile>();
        queuedProjectiles = new ArrayList <Projectile>();
        cooldownTimer = 0;
    }

    abstract void shoot();

    public void use(){
        shoot();
    }

    public boolean canShoot(){
        return cooldownTimer <= 0 && activeProjectiles.size() < 5;
    }

    @Override
    public void update(){
        ArrayList<Projectile> newProjectiles = new ArrayList<Projectile>();
        activeProjectiles.forEach((proj) -> {
            if (proj.timeOfFlight < proj.duration) {
                newProjectiles.add(proj);
                proj.update();
            }
        });
        queuedProjectiles.forEach((proj) -> {
            newProjectiles.add(proj);
        });


        activeProjectiles = newProjectiles;
        queuedProjectiles = new ArrayList<Projectile>();
        cooldownTimer--;
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