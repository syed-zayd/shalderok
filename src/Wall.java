class Wall extends Tile {
	public Wall(double x, double y, String theme) {
		super(x, y, SpriteLoader.getSprite(theme + "_wall"));
	}

	@Override
	public void update() {
		// do nothing, its a wall
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}

    @Override
    public void interact() {

    }
}