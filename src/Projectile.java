import java.awt.geom.Point2D;

public class Projectile extends GameObject {

    interface AttackPattern {
    
        public Point2D.Double updateVelocity(Projectile p);
    
    }

    double v;
    double vx;
    double vy;
    int timeOfFlight;
    int duration;
    private AttackPattern attackPattern;
    int bouncesRemaining;
    int damage;
    double knockback;

    public static final AttackPattern LINEAR = new AttackPattern() {
        public Point2D.Double updateVelocity(Projectile p){
            return new Point2D.Double(p.vx, p.vy);
        }
    };

    public static final AttackPattern HEAT_SEEK_LIGHT = new AttackPattern() {
        public Point2D.Double updateVelocity(Projectile p){
            Point2D.Double target = p.getUnitVectorTo(World.p);
            double vx = p.vx + (target.x - p.vx) * 0.2;
            double vy = p.vy + (target.y - p.vy) * 0.2;
            return new Point2D.Double(vx, vy);
        }
    };
    public static final AttackPattern HEAT_SEEK_HEAVY = new AttackPattern() {
        public Point2D.Double updateVelocity(Projectile p){
            Point2D.Double target = p.getUnitVectorTo(World.p);
            double vx = p.vx + (target.x - p.vx) * 0.5;
            double vy = p.vy + (target.y - p.vy) * 0.5;
            return new Point2D.Double(vx, vy);
        }
    };
    public static final AttackPattern HEAT_SEEK_FULL = new AttackPattern() {
        public Point2D.Double updateVelocity(Projectile p){
            Point2D.Double target = p.getUnitVectorTo(World.p);
            double vx = p.vx + (target.x - p.vx) * 1;
            double vy = p.vy + (target.y - p.vy) * 1;
            return new Point2D.Double(vx, vy);
        }
    };


    public Projectile(double x, double y, double v, double angle, int damage, double knockback, int duration, int bounces, AttackPattern ap, Sprite s) {
        super(x, y, 16, 16, s);
        this.timeOfFlight = 0;
        this.damage = damage;
        this.knockback = knockback;
        this.duration = duration;
        this.bouncesRemaining = bounces;
        this.attackPattern = ap;
        this.v = v;
        this.vx = v * Math.cos(angle);
        this.vy = - v * Math.sin(angle);
    }

    public Projectile(double x, double y, int w, int h, double v, double angle, int damage, double knockback, int duration, AttackPattern ap, Sprite s) {
        super(x, y, w, h, s);
        this.timeOfFlight = 0;
        this.damage = damage;
        this.knockback = knockback;
        this.duration = duration;
        this.attackPattern = ap;
        this.v = v;
        this.vx = v * Math.cos(angle);
        this.vy = - v * Math.sin(angle);
    }

    @Override
    public void update() {
        Point2D.Double newVel = attackPattern.updateVelocity(this);
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