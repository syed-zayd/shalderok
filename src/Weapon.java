import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Weapon {
    private String name;
    public int passiveAtk;
    public int passiveDodge;
    public int passiveSpd;
    private int rarity; // 0 common, 1 rare, 2 epic, 3 legendary
    private BufferedImage sprite;
    private int attackCooldown; // milliseconds
    private int lastUsed; // time in milliseconds of when the weapon was last fired

    public Weapon(String name, int passiveAtk, int passiveDodge, int passiveSpd, int rarity, String spriteFilePath) {
        this.name = name;
        this.passiveAtk = passiveAtk;
        this.passiveDodge = passiveDodge;
        this.passiveSpd = passiveSpd;
        this.rarity = rarity;

        this.attackCooldown = 0;
        this.lastUsed = 0;

        try {
            this.sprite = ImageIO.read(new File(spriteFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRarity() {
        return rarity;
    }
    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean canUse() {
        return System.currentTimeMillis() - lastUsed >= attackCooldown;
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(Util.rotateImage(sprite, Player.getInstance().mouseAngle), (int)Player.getInstance().x, (int)Player.getInstance().y, null);
    }

    public abstract int getAtk();
    public abstract int getDodge();
    public abstract int getSpd();
    public abstract void attack();
}