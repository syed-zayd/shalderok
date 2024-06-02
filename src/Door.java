public class Door extends Tile {
    private boolean locked;

    public Door(double x, double y, String theme, boolean locked) {
        super(x, y, SpriteLoader.getSprite(theme + "_door"));
        this.locked = locked;
        this.state = locked ? "idle" : "unlocked";
    }

    public void lock() {
        this.state = "idle";
        locked = true;
        System.out.println("locked!");
    }

    public void unlock() {
        System.out.println("unlocked!");
        locked = false;
        this.state = "unlocked";
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isSolid() {
        return locked;
    }

    @Override
    public void interact() {

    }
}
