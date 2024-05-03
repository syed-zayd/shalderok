import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Armor {
    private int shield;
    private int maxShield;
    private int eva;
    private int atk;

    private BufferedImage sprite;

    public Armor(int shield, int eva, int atk, String imgPath) throws IOException {
        this.shield = shield;
        this.maxShield = shield;
        this.eva = eva;
        this.atk = atk;
        this.sprite = ImageIO.read(new File(imgPath));
    }

    public boolean isBroken() {
        return shield <= 0;
    }

    // left over damage will be returned
    public int takeDmg(int dmg) {
        shield-=dmg;
        return Math.max(dmg-shield, 0);
    }

    public void repair() {
        shield = maxShield;
    }
}