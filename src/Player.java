import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Player extends Entity {

    Room roomToEnter;

    String name;
    double spd; // max speed
    boolean up, down, left, right; // direction player is facing
    double dx, dy; // angle from mouse to player's center
    Point pathfindingCurrentIndex;

    boolean collidingWithDoor;

    Backpack backpack;

    public Player(Floor f, double x, double y, Sprite s) {
        super(x, y, 4, 5, s);
        spd = 5;
        pathfindingCurrentIndex = new Point(-1, -1);
        name = s.getName();
        backpack = new Backpack(10);
        backpack.addItem(new Wand(this, x, y));
        backpack.addItem(new Bow(this, x, y));
        backpack.addItem(new Sword(this, x, y));
        Fists fists = new Fists(this, x, y);
        for(int i = 0; i < 7; i++){
            backpack.addItem(fists);
        }
        activeItem = backpack.getActiveItem();
        this.r = f.entrance;
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCharacter(Sprite s){
        this.sprite = s;
    }

    public void setActiveSlot(int slot){
        backpack.setActiveSlot(slot);
    }

    public void enterNewFloor(Floor f) {
        super.enterNewFloor(f, f.entrance);
        f.entrance.activate();
        Util.centerPosition(this, f.entrance.getCenterObject());
    }

    void enter(Room r) {
        this.r = r;
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

        if (up && right) {
            this.state = "up_right";
        } else if (up && left) {
            this.state = "up_left";
        } else if (up) {
            this.state = "up";
        } else if (left) {
            this.state = "left";
        } else if (right) {
            this.state = "right";
        } else {
            this.state = "idle";
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

    void attemptToEnter() {
        if (!collidingWithDoor && roomToEnter != null && !roomToEnter.activated) {
            enter(roomToEnter);
        }

        collidingWithDoor = false;
        roomToEnter = null;
    }

	@Override
	public void paint(Graphics2D g2d) {
        super.paint(g2d);
        Util.drawCenteredString(g2d, name, drawCenterX(), drawY());
	}

    @Override
    public void interact() {

    }

    @Override
    public void update() {
        activeItem = backpack.getActiveItem();
        if(activeItem instanceof Weapon){
            weapon = (Weapon) activeItem;
            if(r != null){
                if(!r.f.weapons.contains(weapon)){
                    r.f.weapons.add(weapon);
                }
            }
        }
        super.update();
        attemptToEnter();
    }
}