public class Bow extends Weapon {

    public Bow(double x, double y) {
        super(x, y, SpriteLoader.getSprite("bow"));
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
