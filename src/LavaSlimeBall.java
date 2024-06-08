public class LavaSlimeBall extends Projectile {
    
    public LavaSlimeBall(double x, double y, double angle, int damage) {
        super(x, y, 19, angle, damage, 30, 1500, 1, Projectile.LINEAR, SpriteLoader.getSprite("lava-slime-ball"));
    }

}