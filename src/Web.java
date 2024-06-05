public class Web extends Projectile {
    
    public Web(double x, double y, double v, double angle, int damage) {
        super(x, y, v, angle, damage, 0, 1000, 10, Projectile.LINEAR, SpriteLoader.getSprite("web"));
    }

}
