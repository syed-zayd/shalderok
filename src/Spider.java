import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class Spider extends Enemy {
    String debug = "0";

    public Spider(double x, double y) {
        super(x, y, 10, SpriteLoader.getSprite("spider"));
        weapon = new WebShooter(x, y);
    }

    @Override
    public void interact() {

    }

}
