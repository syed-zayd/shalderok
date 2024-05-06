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
public class Player {

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
    private double x;
    private double y;
    private double angle;
    private double vx; // velocity
    private double vy;
    private double ax; // acceleration
    private double ay;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    private static final Player instance = new Player();

    private Player() {
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

        this.x = 620;
        this.y = 300;
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

    // updates the player's acceleration
    public void updateAcceleration() {
        ax = 0;
        ay = 0;

        if (up) {
            ay = -spd()/3;
        } else if (down) {
            ay = spd()/3;
        }
        if (right) {
            ax = spd()/3;
        } else if (left) {
            ax = -spd()/3;
        }

        // decelerate the player when they aren't moving
        if (! (up || down) ) {
            // set the acceleration as a function of their velocity (like air resistance)
            ay = -vy/10;
        }
        // same for x direction
        if (! (right || left) ) {
            ax = -vx/10;
        }
    }

    // updates the player's velocity
    public void updateVelocity() {
        // add the acceleration
        vx += ax;
        vy += ay;

        // set maximum speed (spd)
        vx = Math.min(vx, spd());
        vy = Math.min(vy, spd());

        // set minimum speed (-spd)
        vx = Math.max(vx, -spd());
        vy = Math.max(vy, -spd());
    }

    public void move() {
        keysHeld();
        updateAcceleration();
        updateVelocity();

        x += vx;
        y += vy;

        // System.out.printf("Speed X: %f, Speed Y: %f\n", vx, vy);
    }
}
