
class Spider extends Enemy {
    String debug = "0";

    public Spider(double x, double y) {
        super(x, y, 10, 0.5, SpriteLoader.getSprite("spider"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
