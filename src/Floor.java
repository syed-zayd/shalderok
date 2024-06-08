import java.awt.Color;
import java.io.*;
import java.util.*;

public class Floor {
    private boolean[][] visited;
    Map<Room, ArrayList<Room>> connections;
    ArrayList<Weapon> weapons;
    public Room entrance;
    int level;
    private String lastSuccessfulDirection;

    public Enemy newEnemy() {
        switch (level) {
            case 1:
                return Math.random() < 0.5 ? new Slime(0, 0) : new Spider(0, 0);
            case 2:
                return Math.random() < 0.5 ? new BlueSlime(0, 0) : new Goblin(0, 0);
            case 3:
                return Math.random() < 0.5 ? new LavaSlime(0, 0) : new Golem(0, 0);
        }
        switch (Util.randInt(1, 3)) {
            case 1:
                return Math.random() < 0.5 ? new Slime(0, 0) : new Spider(0, 0);
            case 2:
                return Math.random() < 0.5 ? new BlueSlime(0, 0) : new Goblin(0, 0);
            case 3:
                return Math.random() < 0.5 ? new LavaSlime(0, 0) : new Golem(0, 0);
        }
        return null;
    }

    public Enemy newBoss() {
        switch (level) {
            case 1:
                return new SkeletonKing(0, 0);
            case 2:
                return new Ent(0, 0);
            case 3:
                return new Phoenix(0, 0);
            default:
                switch (Util.randInt(1, 3)) {
                    case 1:
                        return new SkeletonKing(0, 0);
                    case 2:
                        return new Ent(0, 0);
                    case 3:
                        return new Phoenix(0, 0);
                    default:
                        return null;
                }
        }
    }

    public Color getBackgroundColor() {
        switch (level) {
            case 1: // stone
                return new Color(22, 22, 26);
            case 2: // forest
                return new Color(42, 26, 13);
            case 3: // lava
                return new Color(5, 5, 11);
            default:
                return Color.BLACK;
        }
    }

    public String getTheme() {
        switch (level) {
            case 1:
                return "stone";
            case 2:
                return "forest";
            case 3:
                return "lava";
            default:
                return "stone";
        }
    }

    private Room remove(Room r) {
        System.out.println("[Floor Generation] Cannot Add Boss Room, Deleting: " + lastSuccessfulDirection);

        // seal the room before it
        Room prev = connections.get(r).get(0);
        int row = prev.getGateRow(lastSuccessfulDirection);
        int col = prev.getGateCol(lastSuccessfulDirection);
        prev.objs.remove(prev.grid[row][col]);
        prev.grid[row][col] = new Wall(prev.grid[row][col].x, prev.grid[row][col].y, getTheme());
        prev.objs.add(prev.grid[row][col]);

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

        String oppositeDirection;
        switch (direction) {
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
            default:
                oppositeDirection = "none";
        }

        GameObject gateR1 = r1.getGate(direction);
        r1.objs.remove(gateR1);
        r1.grid[r1.getGateRow(direction)][r1.getGateCol(direction)] = new Door(gateR1.x, gateR1.y, getTheme(), true);
        r1.objs.add(r1.grid[r1.getGateRow(direction)][r1.getGateCol(direction)]);

        GameObject gateR2 = r2.getGate(oppositeDirection);
        r2.objs.remove(gateR2);
        r2.grid[r2.getGateRow(oppositeDirection)][r2.getGateCol(oppositeDirection)] = new Door(gateR2.x, gateR2.y, getTheme(), false);
        r2.objs.add(r2.grid[r2.getGateRow(oppositeDirection)][r2.getGateCol(oppositeDirection)]);

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
        entrance.activated = true;

        Room current = entrance;
        Room last = current;
        int minRooms = 1;
        int maxRooms = 1;
        // add a random number of normal rooms
        for (int i = 0; i < Util.randInt(minRooms, maxRooms); i++) {
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
                if (Math.random() < 0.1 || (current == entrance && Math.random() < 0.5)) {
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

        // entrance.activate();
    }

    private Room appendRoom(Room current, String type) throws IOException {
        String direction = "none";
        boolean up = true, down = true, left = true, right = true;
        Room next = null;

        // attempt to generate a room
        while (up || down || left || right) {
            direction = Util.randDirection(up, down, left, right);
            System.out.println("[Floor Generation] Attempt: " + direction);
            GameObject gate = current.getGate(direction);
            switch (direction) {
                case "up":
                    up = false;
                    break;
                case "down":
                    down = false;
                    break;
                case "left":
                    left = false;
                    break;
                case "right":
                    right = false;
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