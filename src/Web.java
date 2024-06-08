public class Web extends Projectile {
    
    public Web(double x, double y, double angle, int damage) {
        super(x, y, 7, angle, damage, 5, 1000, 0, Projectile.LINEAR, SpriteLoader.getSprite("web"));
    }

}
