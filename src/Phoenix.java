public class Phoenix extends Enemy {

    public Phoenix(double x, double y) {
        super(x, y, 3, 100, 10, 1, SpriteLoader.getSprite("phoenix"));
        activeItem = new WebShooter(this, x, y);
        weapon = (Weapon) activeItem;
    }

    @Override
    public void interact() {
    }

}