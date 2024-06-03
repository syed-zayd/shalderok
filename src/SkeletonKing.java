public class SkeletonKing extends Enemy {

    public SkeletonKing(double x, double y) {
        super(x, y, 3, 100, 5, 1, SpriteLoader.getSprite("skeleton_king"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {
    }

}