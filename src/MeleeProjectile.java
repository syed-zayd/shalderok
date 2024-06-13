import java.awt.Color;
import java.awt.Graphics2D;

public class MeleeProjectile extends Projectile {

    public MeleeProjectile(double x, double y, double angle, int damage, int knockback) {
        super(x, y, 64, 64, 0, angle, damage, knockback, 20, Projectile.LINEAR, null);
    }

}
