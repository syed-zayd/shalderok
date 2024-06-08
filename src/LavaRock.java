public class LavaRock extends Projectile {
    
    public LavaRock(double x, double y, double angle, int damage) {
        super(x, y, 4, angle, damage, 25, 600, 2, Projectile.LINEAR, SpriteLoader.getSprite("lava-rock"));
    }

}