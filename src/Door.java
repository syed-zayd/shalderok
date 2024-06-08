public class Door extends Tile {
    private boolean locked;

    public Door(double x, double y, String theme, boolean locked) {
        super(x, y, SpriteLoader.getSprite(theme + "_door"));
        this.locked = locked;
        this.state = locked ? "locked" : "unlocked";
    }

    public void lock() {
        this.state = "locked";
        locked = true;
    }

    public void unlock() {
        locked = false;
        this.state = "unlocked";
    }

    @Override
    public boolean isSolid() {
        return locked;
    }

    @Override
    public void update() {}

    @Override
    public void interact() {

    }
}
