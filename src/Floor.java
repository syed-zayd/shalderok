import java.io.*;
import java.util.*;

public class Floor {
    private boolean[][] visited;
    private Map<Room, ArrayList<Room>> connections;
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
                r1.objs.remove(r1.gateUp);
                r1.gateUp = new Door(r1.gateUp.x, r1.gateUp.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r1.objs.add(r1.gateUp);

                r2.objs.remove(r2.gateDown);
                r2.gateDown = new Path(r2.gateDown.x, r2.gateDown.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r2.objs.add(r2.gateDown);
                break;
            case "down":
                r1.objs.remove(r1.gateDown);
                r1.gateUp = new Door(r1.gateDown.x, r1.gateDown.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r1.objs.add(r1.gateDown);

                r2.objs.remove(r2.gateUp);
                r2.gateUp = new Path(r2.gateUp.x, r2.gateUp.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r2.objs.add(r2.gateUp);
                break;
            case "left":
                r1.objs.remove(r1.gateLeft);
                r1.gateLeft = new Door(r1.gateLeft.x, r1.gateLeft.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r1.objs.add(r1.gateLeft);

                r2.objs.remove(r2.gateRight);
                r2.gateRight = new Path(r2.gateRight.x, r2.gateRight.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r2.objs.add(r2.gateRight);
                break;
            case "right":
                r1.objs.remove(r1.gateRight);
                r1.gateRight = new Door(r1.gateRight.x, r1.gateRight.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r1.objs.add(r1.gateRight);

                r2.objs.remove(r2.gateLeft);
                r2.gateLeft = new Path(r2.gateLeft.x, r2.gateLeft.y, Room.TILE_SIZE, Room.TILE_SIZE);
                r2.objs.add(r2.gateLeft);
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
        weapons = new ArrayList<Weapon>();

        entrance = new Room("entrance", 0, 0, "none", this);
        addRoom(entrance);

        Room current = entrance;
        // add 3-11 normal rooms
        for (int i = 0; i < Util.randInt(3, 3); i++) {
            current = appendRoom(current, "normal");
            if (current == null) {
                break;
            }
        }
        // add boss room
        current = appendRoom(current, "boss");
        current = appendRoom(current, "exit");
    }

    private Room appendRoom(Room current, String type) throws IOException {
        String direction = "none";
        boolean up = true, down = true, left = true, right = true;
        Room next = null;

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
                    break;
            }
            next = new Room(type, gate.x, gate.y, direction, this);
            if (next.conflicted) {
                System.out.println("[Floor Generation] Unsuccessful: Conflicting rooms");
                next = null;
            } else {
                addRoom(next);
                connect(current, next, direction);
                System.out.println("[Floor Generation] Successful: Room added");
                return next;
            }
        }

        return null;

    }

}