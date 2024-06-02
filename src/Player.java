import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Player extends Entity {

    String name;
    double spd; // max speed
    boolean up, down, left, right; // direction player is facing
    double dx, dy; // angle from mouse to player's center
    Point pathfindingCurrentIndex;

    public Player(Floor f, double x, double y, Sprite s) {
        super(x, y, 5, s);
        spd = 5;
        weapon = new Wand(x, y);
        pathfindingCurrentIndex = new Point(-1, -1);
        name = s.getName();

        this.r = f.entrance;
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    public void setName(String name){
        this.name = name;
    }

    public void updateCharacter(Sprite s){
        this.sprite = s;
    }

    public void enterNewFloor(Floor f) {
        super.enterNewFloor(f);
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    void enter(Room r) {
        this.r = r;
        if (r.activated == true) {
            return;
        }
        r.activate();
        if (r != World.f.entrance)
            AudioManager.playSFX("sfx/room_enter.wav");
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
    
    protected void updateDirection() {
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

    protected void updateVelocity() {
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
    
    protected void updateAngle() {
        dx = World.mouse.getX() - (drawCenterX()-World.camera.x);
        dy = World.mouse.getY() - (drawCenterY()-World.camera.y);
        angle = -1 * Math.atan2(dy, dx);
        
        angle%=360;
    }

	@Override
	public void paint(Graphics2D g2d) {
        super.paint(g2d);
        Util.drawCenteredString(g2d, name, drawCenterX(), drawY());

	}

    @Override
    public void interact() {

    }
}