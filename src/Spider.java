import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class Spider extends Enemy {
    String debug = "0";

    public Spider(double x, double y) {
        super(x, y, 30, 30, 10, SpriteLoader.getSprite("spider"));
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);

        Util.drawCenteredString(g2d, debug, drawCenterX(), drawY()-10);
    }

    @Override
    public void update() {
        Point2D.Double toPlayer = getNormalVectorToPlayer();
        double v = Math.sqrt(vx*vx+vy*vy);
        v = Math.min(v*1.01+0.001,1);

        vx=v*toPlayer.x;
        vy=v*toPlayer.y;


        // debug = String.format("%.1f, %.1f", vx, vy);

        x+=vx;
        y+=vy;
    }
}
