class Goblin extends Enemy {
    public Goblin(double x, double y) {
        super(x, y, 2, 10, 2, 0.5, SpriteLoader.getSprite("goblin"));
        activeItem = new WebShooter(this, x, y);
        weapon = (Weapon) activeItem;
    }

    @Override
    public void interact() {

    }

}