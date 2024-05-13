import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// singleton class for the player; get the player using Player.getInstance()
public class Player extends Entity {

    // stats
    private int xp;
    private int dodge;

    // equips
    private Armor armor;
    public Weapon weapon;
    private Potion[] potions;
    public double mouseAngle;

    private static final Player instance = new Player();

    private Player() {
        super("sprites/player.png", 620, 300, 50, 50, 10, 5, 1);

        this.dodge = 0;
        this.xp = 0;
        this.armor = null;
        this.weapon = null;
        this.potions = new Potion[3];

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
        if (weapon != null) {
            weapon.paint(g2d);
        }
        getHitbox().paint(g2d);

        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)hitbox.getCenterX(), (int)hitbox.getCenterY(), 10, 10);
        g2d.setColor(Color.BLACK);
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

        // decrease knockback
        knockbackX *= 0.9;
        knockbackY *= 0.9;

        // if knockback is significant, prevent moving
        if (Math.abs(knockbackX) > 1 || Math.abs(knockbackY) > 1) {
            // apply knockback
            vx = knockbackX;
            vy = knockbackY;
            return;
        }

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

    public void updateMouseAngle() {
        if (weapon == null) {
            return;
        }

        Point mouse = MouseInfo.getPointerInfo().getLocation();
        double dy = mouse.getY() - hitbox.getCenterY();
        double dx = mouse.getX() - hitbox.getCenterX();
        System.out.println(dy/dx);
    }

    public void move() {
        keysHeld();
        updateVelocity();
        updateMouseAngle();

        x += vx;
        y += vy;
        CollisionManager.handleCollisions();

        getHitbox().align(x, y);

        // System.out.println(Math.toDegrees(mouseAngle));
    }
}
