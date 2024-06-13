import java.awt.Graphics2D;

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
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public void update() {}

    @Override
    public void paintStats(Graphics2D g2d){
        g2d.drawString("Damage Potion", (int) (Main.getScreenSize().getWidth() - 120), 120);
        g2d.drawString("+" + this.damageBoost + " damage", (int) (Main.getScreenSize().getWidth() - 120), 150);
    }

    @Override
    public void interact() {
        
    }
    
}
