

public class Ground extends Tile {

    public Ground(double x, double y, String theme) {
        super(x, y, SpriteLoader.getSprite(theme + "_ground"));
    }

    @Override
    public void update() {
        // emptiness
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact() {

    }
}
