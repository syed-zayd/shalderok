public class LavaSlimeShooter extends Weapon {

    public LavaSlimeShooter(Entity owner, double x, double y) {
        super(owner, x, y, 1.0, null);
    }

    public void shoot() {
        if (canShoot()) {
            queuedProjectiles.add(new GreenSlimeBall(x, y, angle, calculateDamage()));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {

    }

}
