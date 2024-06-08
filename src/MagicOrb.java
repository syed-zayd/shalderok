public class MagicOrb extends Projectile {

    public MagicOrb(double x, double y, double angle, int damage) {
        super(x, y, 5, angle, damage, 5, 200, 5, Projectile.LINEAR, SpriteLoader.getSprite("magic orb"));
    }

}
