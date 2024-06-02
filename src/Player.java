import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Player extends Entity {

    private final int WEAPON_RADIUS = 64;
    double spd; // max speed
    boolean up, down, left, right; // direction player is facing
    double dx, dy, mouseAngle; // angle from mouse to player's center
    Point pathfindingCurrentIndex;
    Room r;

    public Player(Floor f, double x, double y, Sprite s) {
        super(x, y, 5, s);
        spd = 3;
        weapon = new Sword(x, y);
        currentFrame = s.getSprite("idle", 0);
        pathfindingCurrentIndex = new Point(-1, -1);

        this.r = f.entrance;
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    public void updateCharacter(Sprite s){
        this.sprite = s;
    }

    void enterNewFloor(Floor f) {
        if(weapon != null){
            f.weapons.add(weapon);
        }
        this.r = f.entrance;
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    void enter(Room r) {
        if (this.r == r) {
            return;
        }
        this.r = r;
        for (Enemy e: r.enemies) {
            e.activated = true;
        }
    }

    ArrayList<Room> getRooms() {
        ArrayList<Room> rv = new ArrayList<Room>(r.f.getConnectingRooms(r));
        rv.add(r);
        return rv;
    }

    ArrayList<Enemy> activeEnemies() {
        ArrayList<Enemy> rv = new ArrayList<Enemy>();
        getRooms().forEach((r) -> rv.addAll(r.enemies));
        rv.removeIf((e) -> !e.activated);
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
        
        mouseAngle%=360;
    }

    private void updateWeapon() {
        if(weapon != null){
            weapon.angle = mouseAngle;
            double centerX = drawCenterX() + WEAPON_RADIUS * Math.cos(mouseAngle);
            double centerY = drawCenterY() - WEAPON_RADIUS * Math.sin(mouseAngle);
            weapon.x = weapon.drawXFromCenter(centerX);
            weapon.y = weapon.drawYFromCenter(centerY);
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
        super.paint(g2d);

        Util.drawCenteredString(g2d, String.format("%d, %d", pathfindingCurrentIndex.x, pathfindingCurrentIndex.y), drawCenterX(), drawY()-10);

        // draw weapon
        if(weapon != null){
            weapon.paint(g2d);
        }

	}

    @Override
    public void interact() {

    }
}