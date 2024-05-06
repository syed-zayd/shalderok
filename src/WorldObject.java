// abstract class for tangible stuff found in the world

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class WorldObject {
    protected double x;
    protected double y;
    protected int w;
    protected int h;
    protected BufferedImage sprite;
    private Hitbox hitbox;

    public WorldObject(String spriteFilePath, double x, double y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        try {
            this.sprite = ImageIO.read(new File(spriteFilePath));
        } catch (IOException e) {
            // e.printStackTrace();
        }

        this.hitbox = new Hitbox(x, y, w, h);
    }

    public abstract void paint(Graphics2D g2d);

    public Hitbox getHitbox() {
        return hitbox;
    }
}
