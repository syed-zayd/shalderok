
class Spider extends Enemy {
    public Spider(double x, double y) {
        super(x, y, 2, 10, 0.5, SpriteLoader.getSprite("spider"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
