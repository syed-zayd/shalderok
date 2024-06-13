import java.awt.Graphics2D;

public class HealingPotion extends Item {

    private int hpBoost;

    public HealingPotion(double x, double y, int hpBoost){
        super(x, y, 32, 32, SpriteLoader.getSprite("healing potion"));
        this.hpBoost = hpBoost;
    }

    @Override
    public void use() {
        World.p.hp = World.p.hp + hpBoost <= World.p.maxHp ? World.p.hp + hpBoost : World.p.maxHp;
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
        g2d.drawString("Healing Potion", (int) (Main.getScreenSize().getWidth() - 120), 120);
        g2d.drawString("+" + this.hpBoost + " health", (int) (Main.getScreenSize().getWidth() - 120), 150);
    }

    @Override
    public void interact() {
        
    }
    
}
