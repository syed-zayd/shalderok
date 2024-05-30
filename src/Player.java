import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Player extends GameObject{
    double vx, vy; // current speed
    double knockbackX, knockbackY;
    double spd; // max speed
    boolean up, down, left, right; // direction player is facing
    double dx, dy, mouseAngle; // angle from mouse to player's center

    Weapon weapon;
    Room r;

    public Player(double x, double y, Room r) {
        super(x, y, 32, 32);
        spd = 3;
        weapon = new Wand(x, y);
        this.r = r;
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
    }

    public void useWeapon(){
        if(weapon != null){
            weapon.shoot();
        }
    }

    ArrayList<Room> getRooms() {
        ArrayList<Room> rv = new ArrayList<Room>(r.f.getConnectingRooms(r));
        rv.add(r);
        return rv;
    }
    
    private void updateDirection() {
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

    private void updateVelocity() {
        knockbackX *= 0.92;
        knockbackY *= 0.92;

        // if knockback is significant, prevent moving
        if (Math.abs(knockbackX) > 1 || Math.abs(knockbackY) > 1) {
            // apply knockback
            vx = knockbackX;
            vy = knockbackY;
            return;
        }        

        if (up) {
            vy -= spd/10;
        } else if (down) {
            vy += spd/10;
        }
        if (left) {
            vx -= spd/10;
        } else if (right) {
            vx += spd/10;
        }

        if (!up && !down) {
            vy *= 0.92;
        }
        if (!left && !right) {
            vx *= 0.92;
        }

        vx = Math.min(vx, spd);
        vy = Math.min(vy, spd);
        vx = Math.max(vx, -spd);
        vy = Math.max(vy, -spd);
    }

    private void move() {
        x += vx;
        y += vy;
    }
    
    private void updateAngle() {
        dx = World.mouse.getX() - (drawCenterX()-World.camera.x);
        dy = World.mouse.getY() - (drawCenterY()-World.camera.y);
        mouseAngle = -1 * Math.atan2(dy, dx);
    }

    private void updateWeapon() {
        if(weapon != null){
            weapon.angle = mouseAngle;
            weapon.x = drawX();
            weapon.y = drawY();
            weapon.update();
        }
    }

    public void update() {
        updateDirection();
        updateVelocity();
        move();
        updateAngle();
        updateWeapon();
    }

	@Override
	public void paint(Graphics2D g2d) {
        g2d.setColor(Color.RED);
		g2d.drawRect(drawX(), drawY(), w, h);
        g2d.setColor(Color.BLACK);

        Util.drawCenteredString(g2d, String.format("vx: %.2f, vy: %.2f", vx, vy), drawCenterX(), drawY()-10);

        // draw weapon

        if(weapon != null){
            weapon.paint(g2d);
        }

	}
}