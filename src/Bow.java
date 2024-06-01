public class Bow extends Weapon {

    public Bow(double x, double y) {
        super(x, y, SpriteLoader.getSprite("bow"));
    }

    @Override
    void shoot() {
        queuedProjectiles.add(new Arrow(x, y, 5, angle));
    }
    
}
