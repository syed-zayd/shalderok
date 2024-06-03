
class BlueSlime extends Enemy {
    public BlueSlime(double x, double y) {
        super(x, y, 2, 10, 2, 0.5, SpriteLoader.getSprite("blue_slime"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
