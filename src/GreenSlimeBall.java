public class GreenSlimeBall extends Projectile {
    
    public GreenSlimeBall(double x, double y, double angle, int damage) {
        super(x, y, 3, angle, damage, 10, 500, 0, Projectile.LINEAR, SpriteLoader.getSprite("green-slime-ball"));
    }

}