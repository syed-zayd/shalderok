public class Arrow extends Projectile {

    public Arrow(double x, double y, double angle, int damage) {
        super(x, y, 20, angle, damage, 0, 100, 1, Projectile.LINEAR, SpriteLoader.getSprite("arrow"));
    }
    
}
