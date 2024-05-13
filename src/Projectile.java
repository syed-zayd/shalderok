
public abstract class Projectile extends WorldObject {

    public int dmg;
    public WorldObject owner; // cannot attack the owner
    
    public Projectile(String spriteFilePath, double x, double y, int w, int h, int dmg, WorldObject owner) {
        super(spriteFilePath, x, y, w, h);
        this.dmg = dmg;
        this.owner = owner;
    }

    public abstract void move();
}