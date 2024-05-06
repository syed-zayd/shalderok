public abstract class Entity extends WorldObject {
    protected int spd;
    protected int maxHp;
    protected int hp;
    protected int atk;
    
    // physics-based
    protected double vx; // velocity
    protected double vy;
    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;

    protected double knockbackX;
    protected double knockbackY;

    public Entity(String spriteFilePath, double x, double y, int w, int h, int spd, int hp, int atk) {
        super(spriteFilePath, x, y, w, h);
        this.spd = spd;
        this.maxHp = hp;
        this.hp = hp;
        this.atk = atk;
    }

    public abstract void move();
    public abstract void updateVelocity();
}