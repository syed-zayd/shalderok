import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class Entity extends GameObject {
    
    private final int WEAPON_RADIUS = 64;

    public Room r;

    public double vx, vy;
    public double knockbackX, knockbackY;
    public int hp;
    public int maxHp;
    public Item activeItem;
    public Weapon weapon;
    public double angle;

    public Entity(double x, double y, int hp, Sprite s){
        super(x, y, s.getWidth(), s.getHeight(), s);
        this.vx = 0;
        this.vy = 0;
        this.hp = hp;
        this.maxHp = hp;
    }

    public void enterNewFloor(Floor f, Room r) {
        
        if(weapon != null){
            f.weapons.add(weapon);
        }
        this.r = r;
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

    public void equip(Item item){
        this.activeItem = item;
    }

    public void useActiveItem(){
        if(activeItem != null){
            activeItem.use();
        }
    }

    protected abstract void updateAngle();
    protected abstract void updateVelocity();
    protected abstract void updateDirection();

    protected void updateWeapon(){
        if(weapon != null){
            weapon.angle = angle;
            double centerX = drawCenterX() + WEAPON_RADIUS * Math.cos(angle);
            double centerY = drawCenterY() - WEAPON_RADIUS * Math.sin(angle);
            weapon.x = weapon.drawXFromCenter(centerX);
            weapon.y = weapon.drawYFromCenter(centerY);
            weapon.update();
        }
    }
    protected void move(){
        x += vx;
        y += vy;
    }
    
    private void updateSprite(){
        frameIndex++;
        if(frameIndex >= sprite.getNumFrames(state)){
            frameIndex = 0;
        }
    }

    public void update(){
        updateSprite();
        updateDirection();
        updateVelocity();
        move();
        updateAngle();
        updateWeapon();
    }

    public void paint(Graphics2D g2d){
        super.paint(g2d);

        // draw weapon
        if(weapon != null){
            weapon.paint(g2d);
        }
    }

    @Override
    public boolean isSolid(){
        return true;
    }

}
