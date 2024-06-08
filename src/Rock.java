public class Rock extends Projectile {
    
    public Rock(double x, double y, double angle, int damage) {
        super(x, y, 3, angle, damage, 15, 800, 1, Projectile.LINEAR, SpriteLoader.getSprite("rock"));
    }

}