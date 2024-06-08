class LavaSlime extends Enemy {
    public LavaSlime(double x, double y) {
        super(x, y, 2, 10, 2, 0.5, SpriteLoader.getSprite("lava_slime"));
        activeItem = new LavaSlimeShooter(this, x, y);
    }

    @Override
    public void interact() {

    }

}