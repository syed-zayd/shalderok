import java.io.*;
import java.util.*;

public class Floor {
    private boolean[][] visited;
    private Map<Room, ArrayList<Room>> connections;
    ArrayList<Enemy> enemies;
    ArrayList<Weapon> weapons;
    public Room entrance;

    void addRoom(Room r) {
        connections.put(r, new ArrayList<>());
    }

    // r1 ---direction---> r2
    void connect(Room r1, Room r2, String direction) {
        connections.get(r1).add(r2);
        connections.get(r2).add(r1);

        switch (direction) {
            case "up":
                r1.setGateUp(null);
                r2.setGateDown(null);
                break;
            case "down":
                r1.setGateDown(null);
                r2.setGateUp(null);
                break;
            case "left":
                r1.setGateLeft(null);
                r2.setGateRight(null);
                break;
            case "right":
                r1.setGateRight(null);
                r2.setGateLeft(null);
        }
    }

    ArrayList<Room> getConnectingRooms(Room r) {
        return connections.get(r);
    }

    ArrayList<Room> getRooms() {
        return new ArrayList<Room>(connections.keySet());
    }

    void visit(GameObject obj) {
        int x = obj.drawX() / Room.TILE_SIZE;
        int y = obj.drawY() / Room.TILE_SIZE;
        if (x < 0) {
            x += visited.length;
        }
        if (y < 0) {
            y += visited[0].length;
        }
        visited[x][y] = true;
    }

    boolean isVisited(GameObject obj) {
        int x = obj.drawX() / Room.TILE_SIZE;
        int y = obj.drawY() / Room.TILE_SIZE;
        if (x < 0) {
            x += visited.length;
        }
        if (y < 0) {
            y += visited[0].length;
        }
        return visited[x][y];
    }

    public Floor() throws IOException {
        visited = new boolean[0x1000][0x1000];
        connections = new HashMap<>();
        enemies = new ArrayList<Enemy>();
        weapons = new ArrayList<Weapon>();

        entrance = new Room("entrance", 0, 0, "non", this);
        addRoom(entrance);

        Room current = entrance;
        Room next = null;
        double x = 0;
        double y = 0;
        for (int i = 0; i < Util.randInt(3, 11); i++) {
            // keep track of directions available
            String direction = "none";
            boolean up = true, down = true, left = true, right = true;

            // attempt to generate a room
            while (up || down || left || right) {
                direction = Util.randDirection(up, down, left, right);
                System.out.println("[Floor Generation] Attempt: " + direction);
                GameObject gate = null;
                switch (direction) {
                    case "up":
                        up = false;
                        gate = current.gateUp;
                        break;
                    case "down":
                        down = false;
                        gate = current.gateDown;
                        break;
                    case "left":
                        left = false;
                        gate = current.gateLeft;
                        break;
                    case "right":
                        right = false;
                        gate = current.gateRight;
                }
                if (gate != null) {
                    x = gate.x;
                    y = gate.y;
                    next = new Room("normal", x, y, direction, this);
                    if (next.conflicted) {
                        System.out.println("[Floor Generation] Unsuccessful: Conflicting rooms");
                        next = null;
                    } else {
                        break;
                    }
                } else {
                    System.out.println("[Floor Generation] Unsuccessful: Missing gate");
                }
            }

            // if no room was able to be created (likely no space), stop
            if (next == null) {
                break;
            }

            // add the room
            addRoom(next);
            connect(current, next, direction);
            current = next;
            next = null;
            System.out.println("[Floor Generation] Successful: Room added");
        }


    }

}