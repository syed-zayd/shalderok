
class Slime extends Enemy {
    public Slime(double x, double y) {
        super(x, y, 2, 10, 1, 0.5, SpriteLoader.getSprite("slime"));
        activeItem = new WebShooter(this, x, y);
        weapon = (Weapon) activeItem;
    }

    @Override
    public void interact() {

    }

}
