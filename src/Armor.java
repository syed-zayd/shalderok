import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Armor {
    private String name;
    private int passiveAtk;
    private int passiveDodge;
    private int passiveSpd;
    private int rarity; // 0 common, 1 rare, 2 epic, 3 legendary
    private BufferedImage sprite;
    private int skillCooldown; // milliseconds
    private int skillDuration; // milliseconds
    private int lastUsed; // time in milliseconds of when the skill was last used

    public Armor(String name, int passiveAtk, int passiveDodge, int passiveSpd, int rarity, String spriteFilePath) {
        this.name = name;
        this.passiveAtk = passiveAtk;
        this.passiveDodge = passiveDodge;
        this.passiveSpd = passiveSpd;
        this.rarity = rarity;

        this.skillCooldown = -1;
        this.skillDuration = -1;
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

    public abstract int getAtk();
    public abstract int getDodge();
    public abstract int getSpd();

    public abstract void useSkill();
}