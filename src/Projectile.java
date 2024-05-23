import java.awt.Graphics2D;

public class Projectile extends GameObject {

    private int timeOfFlight;
    private int duration;
    

    public Projectile(double x, double y, int w, int h) {
        super(x, y, w, h);
        timeOfFlight = 0;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void paint(Graphics2D g2d) {
        
    }
    
    

}
