import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import armor.*;
import weapon.Weapon;

// singleton class for the player; get the player using Player.getInstance()
public class Player extends WorldObject {

    // stats
    private int spd;
    private int maxHp;
    private int hp;
    private int dodge;
    private int atk;
    private int xp;

    // sprite
    private BufferedImage sprite;

    // equips
    private Armor armor;
    private Weapon weapon;
    private Potion[] potions;

    // physics-based
    private double angle;
    public double vx; // velocity
    public double vy;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    private static final Player instance = new Player();

    private Player() {
        super("sprites/player.png", 620, 300, 200, 200);

        this.spd = 10;
        this.maxHp = 5;
        this.hp = 5;
        this.dodge = 0;
        this.atk = 1;
        this.xp = 0;
        this.armor = null;
        this.weapon = null;
        this.potions = new Potion[3];

        try {
            this.sprite = ImageIO.read(new File("sprites\\player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Player getInstance() {
        if (instance == null) {
            return new Player();
        }
        return instance;
    }

    public void keyPressed(KeyEvent e) {
        System.out.printf("Pressed [%s]\n", KeyEvent.getKeyText(e.getKeyCode()));
    }
    public void keyReleased(KeyEvent e) {
        System.out.printf("Released [%s]\n", KeyEvent.getKeyText(e.getKeyCode()));
    }
    public void keysHeld() {
        up = KeyHandler.isHeld(KeyEvent.VK_UP) || KeyHandler.isHeld(KeyEvent.VK_W);
        down = KeyHandler.isHeld(KeyEvent.VK_DOWN) || KeyHandler.isHeld(KeyEvent.VK_S);
        right = KeyHandler.isHeld(KeyEvent.VK_RIGHT) || KeyHandler.isHeld(KeyEvent.VK_D);
        left = KeyHandler.isHeld(KeyEvent.VK_LEFT) || KeyHandler.isHeld(KeyEvent.VK_A);
        if (up && down) {
            up = false;
            down = false;
        }
        if (right && left) {
            right = false;
            left = false;
        }
    }

    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, (int)x, (int)y, null);
        getHitbox().paint(g2d);
    }

    public int spd() {
        int effectiveSpd = spd;
        if (armor != null) {
            effectiveSpd += armor.getSpd();
        }
        if (weapon != null) {
            effectiveSpd += weapon.getSpd();
        }
        return effectiveSpd;
    }

    // updates the player's velocity
    public void updateVelocity() {
        if (up) {
            vy -= spd()/3;
        } else if (down) {
            vy += spd()/3;
        }
        if (right) {
            vx += spd()/3;
        } else if (left) {
            vx -= spd()/3;
        }

        if (! (up || down)) {
            vy *= 0.9;
        }
        if (! (right || left)) {
            vx *= 0.9;
        }

        // set maximum speed (spd)
        vx = Math.min(vx, spd());
        vy = Math.min(vy, spd());

        // set minimum speed (-spd)
        vx = Math.max(vx, -spd());
        vy = Math.max(vy, -spd());
    }

    public void move() {
        keysHeld();
        updateVelocity();

        x += vx;
        y += vy;

        getHitbox().align(x, y);

        // System.out.printf("Speed X: %f, Speed Y: %f\n", vx, vy);
    }
}
