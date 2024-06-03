public class MeleeProjectile extends Projectile {

    public MeleeProjectile(double x, double y, double angle, int damage, int knockback) {
        super(x, y, 32, 32, 0, angle, damage, knockback, 100, Projectile.LINEAR, SpriteLoader.getSprite("melee projectile"));
    }

}
