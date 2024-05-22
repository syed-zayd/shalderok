// singleton class that tracks everything

import java.awt.Graphics2D;
import java.util.ArrayList;

public class WorldManager {
    protected ArrayList<WorldObject> objects; // includes every object in the world except the player
    private Player p;
    private static WorldManager instance;
    private WorldManager() {
        p = Player.getInstance();
        objects = new ArrayList<WorldObject>();

        objects.add(new Block(200, 200, 100, 200));
    }

    public static WorldManager getInstance() {
        if (instance == null) {
            return new WorldManager();
        }
        return instance;
    }

    public void paint(Graphics2D g2d) {
        Player.getInstance().paint(g2d);
        for (WorldObject obj: objects) {
            obj.paint(g2d);
        }
    }

    public void update() {
        Player.getInstance().move();

        CollisionManager.handleCollisions();
    }


}