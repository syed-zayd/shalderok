public class Fists extends Weapon {

    public Fists(Entity owner, double x, double y) {
        super(owner, x, y, null);
    }

    public void shoot() {
        if(canShoot()){
            queuedProjectiles.add(new MeleeProjectile(x, y, angle, 1, 5));
            cooldownTimer = 25;
        }
    }
    
    @Override
    public void interact() {

    }

}
