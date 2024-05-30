import java.awt.Graphics2D;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public Coordinate updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle);
    
    }

    private double initialX;
    private double initialY;
    private double vel;
    int timeOfFlight;
    int duration;
    private double angle;
    private AttackPattern attackPattern;
    private int bouncesRemaining;
    private BufferedImage sprite;
    
    public static final AttackPattern LINEAR = new AttackPattern() {

        public Coordinate updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle){

            Coordinate dpos = new Coordinate(vel * timeOfFlight, 0);
            // dpos.reflect();
            dpos.rotate(angle);
            
            return new Coordinate(initialX + dpos.getX(), initialY + dpos.getY());

        }

    };

    // public static final AttackPattern ZIGZAG = new AttackPattern() {

    //     public double updatePositionX(double initialX, double vel, int timeOfFlight, double angle) {
            
    //         if((timeOfFlight / 100) % 2 == 0){
    //             return vel * Math.cos(angle) * timeOfFlight + initialX;
    //         }

    //     }

    //     public double updatePositionY(double initialY, double vel, int timeOfFlight, double angle) {
            


    //     }
        
    // };

    // public static final AttackPattern SINUSOIDAL = new AttackPattern() {

    //     public double[] updatePosition(double initialX, double initialY, double vel, int timeOfFlight, double angle){

    //         double[] pos = {};
    //         return pos;

    //     }

    // };

    public Projectile(double x, double y, double angle, int w, int h, int duration, AttackPattern ap) {
        super(x, y, w, h);
        this.initialX = x;
        this.initialY = y;
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
        Coordinate pos = attackPattern.updatePosition(initialX, initialY, vel, timeOfFlight, angle);
        x = pos.getX();
        y = pos.getY();
        timeOfFlight++;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, drawX(), drawY(), null);
    }
    
    

}
