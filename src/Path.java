public class Path extends Tile {

    public Path(double x, double y, String theme, String direction) {
        super(x, y, SpriteLoader.getSprite(theme + "_path"));
        switch (direction) {
            case "up":
            case "down":
                this.state =  "vertical";
                break;
            case "left":
            case "right":
            default:
                this.state = "horizontal";
                break;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact() {

    }
}