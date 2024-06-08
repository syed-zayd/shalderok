public class Portal extends Tile {
    boolean canInteract;

    public Portal(double x, double y) {
        super(x, y, 3, SpriteLoader.getSprite("portal"));
        canInteract = true;
    }


    @Override
    public void interact() {
        if (canInteract)
            World.nextFloor();
        canInteract = false;
    }
    
    @Override
    public void update() {}

    @Override
    public boolean isSolid() {
        return false;
    }

}
