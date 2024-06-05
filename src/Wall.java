class Wall extends Tile {
	public Wall(double x, double y, String theme) {
		super(x, y, SpriteLoader.getSprite(theme + "_wall"));
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
    
    @Override
    public void update() {}

    @Override
    public void interact() {

    }
}