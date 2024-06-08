
class Slime extends Enemy {
    public Slime(double x, double y) {
        super(x, y, 2, 10, 1, 0.5, SpriteLoader.getSprite("slime"));
        activeItem = new GreenSlimeShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
