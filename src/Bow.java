public class Bow extends Weapon {

    public Bow(Entity owner, double x, double y) {
        super(owner, x, y, (int) (Math.random() * 3) + 1, SpriteLoader.getSprite("bow"));
    }

    @Override
    void shoot() {
        if(canShoot()){
            queuedProjectiles.add(new Arrow(x, y, angle, calculateDamage()));
            cooldownTimer = 50;
        }
    }
    
    @Override
    public void interact() {

    }
}
