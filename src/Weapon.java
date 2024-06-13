import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

abstract class Weapon extends Item implements Cloneable {
    double angle;
    ArrayList <Projectile> activeProjectiles;
    ArrayList <Projectile> queuedProjectiles;
    int cooldownTimer;
    int projectilesShot;
    Entity owner;
    double damageMultiplier;

    public Weapon(Entity owner, double x, double y, double damageMultiplier, Sprite s) {
        super(x, y, 32, 32, s);
        this.owner = owner;
        this.damageMultiplier = damageMultiplier;
        activeProjectiles = new ArrayList <Projectile>();
        queuedProjectiles = new ArrayList <Projectile>();
        cooldownTimer = 0;
    }

    abstract void shoot();

    public void use(){
        shoot();
    }

    public int calculateDamage(){
        return (int) (owner.damage * damageMultiplier);
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

    public void paintStats(Graphics2D g2d){
        g2d.drawString("Weapon Stats", (int) (Main.getScreenSize().getWidth() - 120), 120);
        g2d.drawString("Damage: " + this.calculateDamage(), (int) (Main.getScreenSize().getWidth() - 120), 150);
        g2d.setColor(this.cooldownTimer >= 0 ? Color.ORANGE : Color.GREEN);
        g2d.drawString("Cooldown: " + (int) (this.cooldownTimer >= 0 ? this.cooldownTimer / 10. : 0), (int) (Main.getScreenSize().getWidth() - 120), 180);
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