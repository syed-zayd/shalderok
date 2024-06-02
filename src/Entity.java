import java.awt.geom.Point2D;

public abstract class Entity extends GameObject {
    
    public double vx, vy;
    public double knockbackX, knockbackY;
    public int hp;
    public int maxHp;
    public Weapon weapon;

    public Entity(double x, double y, int hp, Sprite s){
        super(x, y, s.getWidth(), s.getHeight(), s);
        this.vx = 0;
        this.vy = 0;
        this.hp = hp;
        this.maxHp = hp;
    }

    public Point2D.Double getUnitVectorTo(GameObject obj) {
        double dx = obj.drawCenterX()-drawCenterX();
        double dy = obj.drawCenterY()-drawCenterY();
        double magnitude = Math.sqrt(dx*dx+dy*dy);
        if (magnitude == 0) {
            return new Point2D.Double(0, 0);
        }
        return new Point2D.Double(dx/magnitude, dy/magnitude);
    }

    public void takeHit(Projectile projectile){
        this.hp -= projectile.damage;
        Point2D.Double knockbackVector = this.getUnitVectorTo(projectile);
        this.knockbackX = -10*knockbackVector.x;
        this.knockbackY = -10*knockbackVector.y;
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
    }

    public void useWeapon(){
        if(weapon != null){
            weapon.shoot();
        }
    }

    public void update(){
        if(frameIndex >= sprite.getNumFrames(state)){
            frameIndex = 0;
        }
        frameIndex++;
    }

    @Override
    public boolean isSolid(){
        return true;
    }

}
