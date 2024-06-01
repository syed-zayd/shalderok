public class MagicOrb extends Projectile {

    public MagicOrb(double x, double y, double v, double angle) {
        super(x, y, v, angle, 10, 200, Projectile.LINEAR, SpriteLoader.getSprite("magic orb"));
    }

}
