public class Ent extends Enemy {

    public Ent(double x, double y) {
        super(x, y, 3, 100, 5, 1, SpriteLoader.getSprite("ent"));
        activeItem = new WebShooter(this, x, y);
        weapon = (Weapon) activeItem;
    }

    @Override
    public void interact() {
    }

}