
class Slime extends Enemy {
    public Slime(double x, double y) {
        super(x, y, 2, 10, 0.5, SpriteLoader.getSprite("slime"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
