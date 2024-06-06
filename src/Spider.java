
class Spider extends Enemy {
    public Spider(double x, double y) {
        super(x, y, 2, 10, 2, 0.5, SpriteLoader.getSprite("spider"));
        activeItem = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}
