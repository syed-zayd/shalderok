public class Portal extends Tile {


    public Portal(double x, double y) {
        super(x, y, 3, SpriteLoader.getSprite("portal"));
    }


    @Override
    public void interact() {
        World.nextFloor();
    }
    
    @Override
    public void update() {}

    @Override
    public boolean isSolid() {
        return false;
    }

}
