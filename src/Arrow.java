public class Arrow extends Projectile {

    public Arrow(double x, double y, double v, double angle) {
        super(x, y, v, angle, 10, 0, 100, 1, Projectile.LINEAR, SpriteLoader.getSprite("arrow"));
    }
    
}
