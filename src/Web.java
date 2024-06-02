public class Web extends Projectile {
    
    public Web(double x, double y, double v, double angle) {
        super(x, y, v, angle, 1, 0, 100, 1, Projectile.LINEAR, SpriteLoader.getSprite("web"));
    }

}
