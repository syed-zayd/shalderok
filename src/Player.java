import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class Player extends Entity {

    Room roomToEnter;

    String name;
    boolean up, down, left, right; // direction player is facing
    double dx, dy; // angle from mouse to player's center
    Point pathfindingCurrentIndex;

    boolean collidingWithDoor;

    Backpack backpack;

    public Player(Floor f, double x, double y, Sprite s) {
        super(x, y, 5, 1000, 5, s);
        pathfindingCurrentIndex = new Point(-1, -1);
        name = s.getName();
        backpack = new Backpack(10);
        backpack.addItem(new Wand(this, x, y));
        backpack.addItem(new Bow(this, x, y));
        backpack.addItem(new Sword(this, x, y));
        backpack.addItem(new HealingPotion(x, y, 5));
        backpack.addItem(new SpeedPotion(x, y, 5));
        backpack.addItem(new DamagePotion(x, y, 5));

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
        if(activeItem instanceof Weapon){
            f.weapons.add((Weapon)activeItem);
        }
        enter(f.entrance);
        Util.centerPosition(this, f.entrance.getCenterObject());

        roomToEnter = f.entrance;
    }

    void enter(Room r) {
        this.r = r;
        if (!r.activated) {
            r.activate();
            if (r.type != "entrance") {
                AudioManager.playSFX("sfx/room_enter.wav");
            }
        }

        this.spd = r.enemies.size() == 0 ? 10 : 5;
    }

    ArrayList<Room> getRooms() {
        ArrayList<Room> rv = new ArrayList<Room>(r.f.getConnectingRooms(r));
        r.f.connections.keySet().forEach((r) -> {
            if (r.activated) {
                rv.add(r);
            }
        });
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
        // if (Math.abs(knockbackX) > 1 || Math.abs(knockbackY) > 1) {
        //     // apply knockback
        //     vx = knockbackX;
        //     vy = knockbackY;
        //     return;
        // }

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
        if (!collidingWithDoor && roomToEnter != null) {
            enter(roomToEnter);
        }

        collidingWithDoor = false;
        roomToEnter = null;
    }

	@Override
	public void paint(Graphics2D g2d) {
        super.paint(g2d);
        Util.drawCenteredString(g2d, r.toString() + " " + r.f.toString(), drawCenterX(), drawY()-10);
	}

    public void paintStats(Graphics2D g2d){
        g2d.drawString("Speed: " + (int) this.spd, (int) (Main.getScreenSize().getWidth() - 100), 30);
        g2d.drawString("Damage: " + this.damage, (int) (Main.getScreenSize().getWidth() - 100), 60);
    }

    @Override
    public void interact() {

    }

    @Override
    public void update() {
        backpack.update();
        activeItem = backpack.getActiveItem();
        super.update();
        attemptToEnter();
    }
}