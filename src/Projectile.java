import java.awt.geom.Point2D;

import java.awt.image.*;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public Point2D.Double updateVelocity(double vx, double vy, int timeOfFlight);
    
    }

    double vx;
    double vy;
    int timeOfFlight;
    int duration;
    double angle;
    private AttackPattern attackPattern;
    int bouncesRemaining = 1;
    int damage;

    public static final AttackPattern LINEAR = new AttackPattern() {

        public Point2D.Double updateVelocity(double vx, double vy, int timeOfFlight){

            return new Point2D.Double(vx, vy);

        }

    };

    public Projectile(double x, double y, double v, double angle, int damage, int duration, AttackPattern ap, Sprite s) {
        super(x, y, 16, 16, s);
        this.angle = angle;
        this.timeOfFlight = 0;
        this.damage = damage;
        this.duration = duration;
        this.attackPattern = ap;
        this.vx = v * Math.cos(angle);
        this.vy = - v * Math.sin(angle);
        this.currentFrame = s.getSprite("idle", 0);
    }

    public Projectile(double x, double y, int w, int h, double v, double angle, int damage, int duration, AttackPattern ap, Sprite s) {
        super(x, y, w, h, s);
        this.angle = angle;
        this.timeOfFlight = 0;
        this.damage = damage;
        this.duration = duration;
        this.attackPattern = ap;
        this.vx = v * Math.cos(angle);
        this.vy = - v * Math.sin(angle);
        this.currentFrame = s.getSprite("idle", 0);
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
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact() {

    }
}
