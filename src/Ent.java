public class Ent extends Enemy {

    public Ent(double x, double y) {
        super(x, y, 3, 100, 5, 1, SpriteLoader.getSprite("ent"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {
    }

}