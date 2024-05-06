import java.util.ArrayList;

public class CollisionManager {

    public static void applyMovement(Player p) {
        ArrayList<WorldObject> objects = WorldManager.getInstance().objects;

        // break the player's movement into 4 steps
        int steps = 4;
        double stepX = p.vx/steps;
        double stepY = p.vy/steps;

        // booleans representing a conflict for any of the 4 steps
        boolean[] conflictsX = new boolean[steps];
        boolean[] conflictsY = new boolean[steps];

        // loop through every object
        for (WorldObject obj: objects) {

            // check a conflict for each step
            for (int i=0; i<steps; i++) {

                // check if there's a conflict in the x direction
                if (!conflictsX[i]) {
                    if (checkX(p.getHitbox(), obj.getHitbox(), stepX*(i+1))) {

                        // if there's a conflict for the current step don't check future conflicts                        
                        for (int j=i; j<steps; j++) {
                            conflictsX[j] = true;
                        }
                    }
                }

                // check if there's a conflict in the y direction
                if (!conflictsY[i]) {
                    if (checkY(p.getHitbox(), obj.getHitbox(), stepY*(i+1))) {

                        // if there's a conflict for the current step don't check future conflicts                        
                        for (int j=i; j<steps; j++) {
                            conflictsY[j] = true;
                        }
                    }
                }
            }
        }

        // apply the movements as long as there are no conflicts
        for (int i=0; i<steps; i++) {
            if (!conflictsX[i]) {
                p.x += stepX;
            } else {
                p.vx = 0;
            }

            if (!conflictsY[i]) {
                p.y += stepY;
            } else {
                p.vy = 0;
            }
        }
    }
    
    // returns whether a displacement of dx on Hitbox a will result in a collision with Hitbox b
    private static boolean checkX(Hitbox a, Hitbox b, double dx) {
        return a.getLeft()+dx < b.getRight() && a.getRight()+dx > b.getLeft();
    }

    // returns whether a displacement of dy on Hitbox a will result in a collision with Hitbox b
    private static boolean checkY(Hitbox a, Hitbox b, double dy) {
        return a.getTop()+dy < b.getBottom() && a.getBottom()+dy > b.getTop();
    }

    
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