public class Bow extends Weapon {

    public Bow(Entity owner, double x, double y) {
        super(owner, x, y, SpriteLoader.getSprite("bow"));
    }

    @Override
    void shoot() {
        if(canShoot()){
            queuedProjectiles.add(new Arrow(x, y, 5, angle));
            cooldownTimer = 50;
        }
    }
    
    @Override
    public void interact() {

    }
}
