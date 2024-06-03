public class Phoenix extends Enemy {

    

    public Phoenix(double x, double y) {
        super(x, y, 3, 100, 1, SpriteLoader.getSprite("phoenix"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {
    }

}