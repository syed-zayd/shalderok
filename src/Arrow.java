public class Arrow extends Projectile {

    public Arrow(double x, double y, double v, double angle) {
        super(x, y, v, angle, 5, 100, Projectile.LINEAR, SpriteLoader.getSprite("arrow"));
    }
    
}
