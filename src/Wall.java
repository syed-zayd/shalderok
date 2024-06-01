import java.awt.Color;
import java.awt.Graphics2D;

class Wall extends GameObject {
	Color c;

	public Wall(double x, double y, int w, int h, Color c) {
		super(x, y, w, h, null);
		this.c = c;
	}

	@Override
	public void update() {
		// do nothing, its a wall
	}

	@Override
	public void paint(Graphics2D g2d) {
        g2d.setColor(c);
		g2d.drawRect((int)x, (int)y, w, h);
		g2d.fillRect((int)x, (int)y, w, h);
        g2d.setColor(Color.BLACK);
	}
    
	@Override
	public boolean isSolid() {
		return true;
	}

    @Override
    public void interact() {

    }
}