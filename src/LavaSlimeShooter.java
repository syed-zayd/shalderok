public class LavaSlimeShooter extends Weapon {

    public LavaSlimeShooter(Entity owner, double x, double y) {
        super(owner, x, y, null);
    }

    public void shoot() {
        if (canShoot()) {
            queuedProjectiles.add(new GreenSlimeBall(x, y, angle, owner.damage));
            cooldownTimer = 100;
        }
    }

    @Override
    public void interact() {

    }

}
