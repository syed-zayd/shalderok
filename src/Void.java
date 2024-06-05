public class Void extends Tile {

    public Void(double x, double y, String theme) {
        super(x, y, SpriteLoader.getSprite(theme + "_void"));
    }

    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public void update() {}

    @Override
    public void interact() {
    }
    
}
