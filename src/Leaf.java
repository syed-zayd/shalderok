public class Leaf extends Projectile {
    
    public Leaf(double x, double y, double angle, int damage) {
        super(x, y, 3, angle, damage, 30, 1000, 0, Projectile.HEAT_SEEK_HEAVY, SpriteLoader.getSprite("leaf"));
    }

}