import java.awt.Graphics2D;

abstract class Tile extends GameObject {

    public Tile(double x, double y, Sprite sprite) {
        super(x, y, Room.TILE_SIZE, Room.TILE_SIZE, sprite);
    }
    public Tile(double x, double y, int scale, Sprite sprite) {
        super(x, y, Room.TILE_SIZE*scale, Room.TILE_SIZE*scale, sprite);
    }

    @Override
    public final void paint(Graphics2D g2d) {
        super.paint(g2d);
    }
}
