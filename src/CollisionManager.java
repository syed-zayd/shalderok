import java.util.ArrayList;

public class CollisionManager {
    public static void handleCollisions() {
        Player p = Player.getInstance();
        Hitbox pHitbox = p.getHitbox();
        ArrayList<WorldObject> objects = WorldManager.getInstance().objects;

        for (WorldObject obj: objects) {
            Hitbox objHitbox = obj.getHitbox();

            double cx = collisionX(pHitbox, objHitbox);
            double cy = collisionY(pHitbox, objHitbox);

            if (cx != 0 && cy != 0) {
                if (Math.abs(cx) < Math.abs(cy)) {
                    p.x += cx;
                    p.vx = 0;
                } else {
                    p.y += cy;
                    p.vy = 0;
                }
                pHitbox.align(p.x, p.y);
            }
        }
    }

    // returns how many pixels the object must be moved to resolve the collision (0 if not colliding)
    // hitbox a is the one that will be pushed, while b remains stationary
    private static double collisionX(Hitbox a, Hitbox b) {
        double pushLeft = b.getLeft()-a.getRight();
        if (pushLeft>=0) {
            return 0;
        }
        double pushRight = b.getRight() - a.getLeft();
        if (pushRight<=0) {
            return 0;
        }

        if (Math.abs(pushLeft) <= Math.abs(pushRight)) {
            return pushLeft;
        } else {
            return pushRight;
        }
    }

    // returns how many pixels the object must be moved to resolve the collision (0 if not colliding)
    // hitbox a is the one that will be pushed, while b remains stationary
    private static double collisionY(Hitbox a, Hitbox b) {
        double pushUp = b.getTop()-a.getBottom();
        if (pushUp>=0) {
            return 0;
        }
        double pushDown = b.getBottom() - a.getTop();
        if (pushDown<=0) {
            return 0;
        }

        if (Math.abs(pushUp) <= Math.abs(pushDown)) {
            return pushUp;
        } else {
            return pushDown;
        }
    }

    public static boolean checkXCollision(Hitbox a, Hitbox b) {
        return a.getLeft() < b.getRight() && a.getRight() > b.getLeft();
    }
    public static boolean checkYCollision(Hitbox a, Hitbox b) {
        return a.getTop() < b.getBottom() && a.getBottom() > b.getTop();
    }
    public static boolean checkCollision(Hitbox a, Hitbox b) {
        return checkXCollision(a, b) && checkYCollision(a, b);
    }
}