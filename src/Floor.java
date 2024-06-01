import java.awt.Color;
import java.io.*;
import java.util.*;

public class Floor {
    private boolean[][] visited;
    private Map<Room, ArrayList<Room>> connections;
    ArrayList<Weapon> weapons;
    public Room entrance;
    int level;
    private String lastSuccessfulDirection;

    private Room remove(Room r) {
        // seal the room before it
        Room prev = connections.get(r).get(0);
        switch (lastSuccessfulDirection) {
            case "up":
                System.out.println("up");
                prev.objs.remove(prev.gateUp);
                prev.grid[0][prev.grid[0].length/2] = new Wall(prev.gateUp.x, prev.gateUp.y, Room.TILE_SIZE, Room.TILE_SIZE, Color.darkGray);
                prev.gateUp = prev.grid[0][prev.grid[0].length/2];
                prev.objs.add(prev.gateUp);
                break;
            case "down":
                System.out.println("down");
                prev.objs.remove(prev.gateDown);
                prev.grid[prev.grid.length-1][prev.grid[0].length/2] = new Wall(prev.gateDown.x, prev.gateDown.y, Room.TILE_SIZE, Room.TILE_SIZE, Color.darkGray);
                prev.gateDown = prev.grid[prev.grid.length-1][prev.grid[0].length/2];
                prev.objs.add(prev.gateDown);
                break;
            case "left":
                System.out.println("left");
                prev.objs.remove(prev.gateLeft);
                prev.grid[prev.grid.length/2][0] = new Wall(prev.gateLeft.x, prev.gateLeft.y, Room.TILE_SIZE, Room.TILE_SIZE, Color.darkGray);
                prev.gateLeft = prev.grid[prev.grid.length/2][0];
                prev.objs.add(prev.gateLeft);
                break;
            case "right":
            default:
                System.out.println("right");
                prev.objs.remove(prev.gateRight);
                prev.grid[prev.grid.length/2][prev.grid[0].length-1] = new Wall(prev.gateRight.x, prev.gateRight.y, Room.TILE_SIZE, Room.TILE_SIZE, Color.darkGray);
                prev.gateRight = prev.grid[prev.grid.length/2][prev.grid[0].length-1];
                prev.objs.add(prev.gateRight);
                break;
        }

        // remove all references to the room
        connections.values().stream().forEach(e -> e.remove(r));
        // remove the room
        connections.remove(r);

        return prev;
    }

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
                r1.gateDown = new Door(r1.gateDown.x, r1.gateDown.y, Room.TILE_SIZE, Room.TILE_SIZE);
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

    public Floor(int level) throws IOException {
        this.level = level;
        visited = new boolean[0x1000][0x1000];
        connections = new HashMap<>();
        weapons = new ArrayList<Weapon>();

        entrance = new Room("entrance", 0, 0, "none", this);
        addRoom(entrance);

        Room current = entrance;
        Room last = current;
        // add 3-11 normal rooms
        for (int i = 0; i < Util.randInt(11, 11); i++) {
            current = appendRoom(current, "normal");
            if (current == null) {
                break;
            }
            last = current;
        }

        // add boss room
        current = appendRoom(current, "boss");
        while (current == null) {
            current = remove(last);
            last = current;
            current = appendRoom(current, "boss");
        }

        // add random side paths
        Set<Room> visited = new LinkedHashSet<Room>();
        Stack<Room> stack = new Stack<Room>();
        stack.push(entrance);
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                
                // chance to add a side path
                if (Math.random() < 0.1 || current == entrance) {
                    Room sidePathRoom = current;
                    for (int i=0; i<Util.randInt(5,7); i++) {
                        sidePathRoom = appendRoom(current, "normal");
                        if (sidePathRoom == null) {
                            break;
                        }
                    }
                }

                // keep going
                for (Room r: getConnectingRooms(current)) {
                    stack.push(r);
                }

            }
        }
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
                lastSuccessfulDirection = direction;
                System.out.println("[Floor Generation] Successful: Room added");
                return next;
            }
        }

        return null;

    }

}