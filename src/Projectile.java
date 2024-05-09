
public abstract class Projectile extends WorldObject {

    
    
    public Projectile(String spriteFilePath, double x, double y, int w, int h) {
        super(spriteFilePath, x, y, w, h);
    }

    public abstract void move();
}