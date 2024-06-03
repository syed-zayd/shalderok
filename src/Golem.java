class Golem extends Enemy {
    public Golem(double x, double y) {
        super(x, y, 2, 10, 0.5, SpriteLoader.getSprite("golem"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}