import java.awt.Graphics2D;

public class MeleeProjectile extends Projectile {

    public MeleeProjectile(double x, double y, double angle) {
        super(x, y, 32, 32, 0, angle, 10, 10, 100, Projectile.LINEAR, SpriteLoader.getSprite("melee projectile"));
    }

}
