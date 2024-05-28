import java.awt.Color;
import java.awt.Graphics2D;

class Wall extends GameObject {

	public Wall(double x, double y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void update() {
		// do nothing, its a wall
	}

	@Override
	public void paint(Graphics2D g2d) {
        g2d.setColor(Color.darkGray);
		g2d.drawRect((int)x, (int)y, w, h);
		g2d.fillRect((int)x, (int)y, w, h);
        g2d.setColor(Color.BLACK);
	}
    
}