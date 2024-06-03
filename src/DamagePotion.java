public class DamagePotion extends Item {

    private int damageBoost;

    public DamagePotion(double x, double y, int damageBoost){
        super(x, y, 32, 32, SpriteLoader.getSprite("damage potion"));
        this.damageBoost = damageBoost;
    }

    @Override
    public void use() {
        World.p.damage += damageBoost;
        numUses--;
    }

    @Override
    public void update() {
        
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void interact() {
        
    }
    
}
