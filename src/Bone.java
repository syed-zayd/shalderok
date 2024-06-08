public class Bone extends Projectile {
    
    public Bone(double x, double y, double angle, int damage) {
        super(x, y, 25, angle, damage, 50, 1000, 0, Projectile.HEAT_SEEK_LIGHT, SpriteLoader.getSprite("bone"));
    }

}
