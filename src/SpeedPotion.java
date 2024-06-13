import java.awt.Graphics2D;

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
    public void paintStats(Graphics2D g2d){
        g2d.drawString("Speed Potion", (int) (Main.getScreenSize().getWidth() - 120), 120);
        g2d.drawString("+" + this.speedBoost + " speed", (int) (Main.getScreenSize().getWidth() - 120), 150);
    }

    @Override
    public void interact() {
        
    }
    
}
