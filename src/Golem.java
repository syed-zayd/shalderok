class Golem extends Enemy {
    public Golem(double x, double y) {
        super(x, y, 2, 10, 3, 0.8, SpriteLoader.getSprite("golem"));
        activeItem = new LavaRockShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}