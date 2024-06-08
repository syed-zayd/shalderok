public class Fireball extends Projectile {
    
    public Fireball(double x, double y, double angle, int damage) {
        super(x, y, 4, angle, damage, 10, 800, 0, Projectile.HEAT_SEEK_FULL, SpriteLoader.getSprite("fireball"));
    }

}
