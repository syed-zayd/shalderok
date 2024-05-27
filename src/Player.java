import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

class Player extends GameObject{
    double vx, vy; // current speed
    double spd; // max speed
    boolean up, down, left, right; // direction player is facing
    double dx, dy, mouseAngle; // angle from mouse to player's center

    Weapon weapon;


    public Player(double x, double y) {
        super(x, y, 32, 32);
        spd = 3;
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
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
        mouseAngle = Math.atan2(dy, dx);
    }

    private void updateWeapon() {
        if(weapon != null){
            weapon.angle = mouseAngle + (Math.PI / 2);
            weapon.x = drawCenterX() + 50*Math.cos(weapon.angle);
            weapon.y = drawCenterY() + 50*Math.sin(weapon.angle);
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

        g2d.drawString(String.format("%.1f, %.1f | Angle: %.2f", dx, dy, Math.toDegrees(mouseAngle)), 500, -50);
	}
}