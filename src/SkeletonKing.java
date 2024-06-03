public class SkeletonKing extends Enemy {

    public SkeletonKing(double x, double y) {
        super(x, y, 3, 100, 5, 1, SpriteLoader.getSprite("skeleton_king"));
        activeItem = new WebShooter(this, x, y);
        weapon = (Weapon) activeItem;
    }

    @Override
    public void interact() {
    }

}