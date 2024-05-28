import java.awt.Graphics2D;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public double updatePositionX(double initialX, double vel, int timeOfFlight, double angle);

        public double updatePositionY(double initialY, double vel, int timeOfFlight, double angle);
    
    }

    private double initialX;
    private double initialY;
    private double vel;
    private int timeOfFlight;
    private int duration;
    private double angle;
    private AttackPattern attackPattern;
    private int bouncesRemaining;
    private BufferedImage sprite;
    
    public static final AttackPattern LINEAR = new AttackPattern() {

        public double updatePositionX(double initialX, double vel, int timeOfFlight, double angle){

            return vel * Math.cos(angle) * timeOfFlight + initialX;

        }
        
        public double updatePositionY(double initialY, double vel, int timeOfFlight, double angle){
            
            return vel * Math.sin(angle) * timeOfFlight + initialY;
            
        }

    };

    public Projectile(double x, double y, double angle, int w, int h, AttackPattern ap) {
        super(x, y, w, h);
        this.initialX = x;
        this.initialY = y;
        this.angle = angle;
        timeOfFlight = 0;
        attackPattern = ap;
        this.vel = 5;

        try {
            sprite = ImageIO.read(new File("sprites/arrow.png"));
        } catch(IOException e){
            
        }

    }

    @Override
    public void update() {
        x = attackPattern.updatePositionX(initialX, vel, timeOfFlight, angle);
        y = attackPattern.updatePositionY(initialY, vel, timeOfFlight, angle);
        timeOfFlight++;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, drawX(), drawY(), null);
    }
    
    

}
