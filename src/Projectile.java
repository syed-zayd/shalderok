import java.awt.Graphics2D;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public Coordinate updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle);
    
    }

    private double originX;
    private double originY;
    private double vel;
    int timeOfFlight;
    int duration;
    double angle;
    private AttackPattern attackPattern;
    int bouncesRemaining = 10;
    private BufferedImage sprite;
    
    public void setOrigin(double ox, double oy) {
        originX = ox;
        originY = oy;
    }

    public static final AttackPattern LINEAR = new AttackPattern() {

        public Coordinate updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle){

            Coordinate dpos = new Coordinate(vel * timeOfFlight, 0);
            // dpos.reflect();
            dpos.rotate(angle);
            System.out.println("Angle: " + angle + " X Transform: " + dpos.getX() + " Y Transform: " + dpos.getY());
            
            return new Coordinate(initialX + dpos.getX(), initialY + dpos.getY());

        }

    };

    public static final AttackPattern ZIGZAG = new AttackPattern() {

        public Coordinate updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle){

            Coordinate dpos = new Coordinate(vel * timeOfFlight, 0);
            // dpos.reflect();
            dpos.rotate(angle);
            System.out.println("Angle: " + angle + " X Transform: " + dpos.getX() + " Y Transform: " + dpos.getY());
            
            return new Coordinate(initialX + dpos.getX(), initialY + dpos.getY());

        }

    };

    // public static final AttackPattern SINUSOIDAL = new AttackPattern() {

    //     public double[] updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle){

    //         double[] pos = {};
    //         return pos;

    //     }

    // };

    public Projectile(double x, double y, double angle, int w, int h, int duration, AttackPattern ap) {
        super(x, y, w, h);
        this.originX = x;
        this.originY = y;
        this.angle = angle;
        timeOfFlight = 0;
        this.duration = duration;
        attackPattern = ap;
        this.vel = 5;

        try {
            sprite = ImageIO.read(new File("sprites/arrow.png"));
        } catch(IOException e){
            
        }

    }

    @Override
    public void update() {
        Coordinate pos = attackPattern.updatePosition(originX, originY, vel, timeOfFlight, angle);
        x = pos.getX();
        y = pos.getY();
        timeOfFlight++;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, drawX(), drawY(), null);
        g2d.fillRect(drawCenterX(), drawCenterY(), 5, 5);
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }

}
