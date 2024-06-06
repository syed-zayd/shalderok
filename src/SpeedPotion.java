public class SpeedPotion extends Item {

    private int speedBoost;

    public SpeedPotion(double x, double y, int speedBoost){
        super(x, y, 32, 32, SpriteLoader.getSprite("speed potion"));
        this.speedBoost = speedBoost;
    }

    @Override
    public void use() {
        World.p.spd += speedBoost;
        numUses--;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public void update() {}

    @Override
    public void interact() {
        
    }
    
}
