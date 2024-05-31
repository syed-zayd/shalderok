import java.awt.Graphics2D;
import java.io.*;
import java.awt.geom.Point2D;

import javax.imageio.ImageIO;

import java.awt.image.*;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public Point2D.Double updateVelocity(double vx, double vy, int timeOfFlight);
    
    }

    private double vx;
    private double vy;
    int timeOfFlight;
    int duration;
    double angle;
    private AttackPattern attackPattern;
    int bouncesRemaining = 5;
    private BufferedImage sprite;

    public static final AttackPattern LINEAR = new AttackPattern() {

        public Point2D.Double updateVelocity(double vx, double vy, int timeOfFlight){

            return new Point2D.Double(vx, vy);

        }

    };

    public Projectile(double x, double y, int w, int h, double v, double angle, int duration, AttackPattern ap) {
        super(x, y, w, h);
        this.angle = angle;
        this.timeOfFlight = 0;
        this.duration = duration;
        this.attackPattern = ap;
        this.vx = v * Math.cos(angle);
        this.vy = v * Math.sin(angle);

        try {
            sprite = ImageIO.read(new File("sprites/arrow.png"));
        } catch(IOException e){
            
        }

    }

    @Override
    public void update() {
        Point2D.Double newVel = attackPattern.updateVelocity(vx, vy, timeOfFlight);
        vx = newVel.getX();
        vy = newVel.getY();
        x += vx;
        y += vy;
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
