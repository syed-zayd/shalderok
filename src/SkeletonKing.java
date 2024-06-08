public class SkeletonKing extends Enemy {

    public SkeletonKing(double x, double y) {
        super(x, y, 1, 100, 5, 1, SpriteLoader.getSprite("skeleton_king"));
        activeItem = new BoneShooter(this, x, y);
    }

    @Override
    public void interact() {
    }

}