class Goblin extends Enemy {
    public Goblin(double x, double y) {
        super(x, y, 2, 10, 0.5, SpriteLoader.getSprite("goblin"));
        weapon = new WebShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}