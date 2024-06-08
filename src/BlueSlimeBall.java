public class BlueSlimeBall extends Projectile {
    
    public BlueSlimeBall(double x, double y, double angle, int damage) {
        super(x, y, 4, angle, damage, 20, 1000, 1, Projectile.LINEAR, SpriteLoader.getSprite("blue-slime-ball"));
    }

}