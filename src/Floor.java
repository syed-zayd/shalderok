import java.awt.Graphics2D;
import java.util.*;

public class Floor {
    boolean[][] visited = new boolean[Short.MAX_VALUE][Short.MAX_VALUE];

    private Map<Room, ArrayList<Room>> connections;
    public Room entrance;

    void addRoom(Room r) {
        connections.put(r, new ArrayList<>());
    }

    // r1 -> r2
    void connect(Room r1, Room r2, String direction) {
        connections.get(r1).add(r2);
        connections.get(r2).add(r1);

        r1.dig(direction);
        r2.digFrom(direction);
    }

    ArrayList<Room> getConnectingRooms(Room r) {
        return connections.get(r);
    }

    public Floor() {
        connections = new HashMap<>();
        int posX = 100;
        int posY = 100;

        entrance = new Room("entrance", 0, 0, "none", this);
        addRoom(entrance);

        visited[posX][posY] = true;
        Room current = entrance;
        for (int i=0; i<Util.randInt(1, 2); i++) {
            String direction = null;
            int nPosX = posX;
            int nPosY = posY;

            while (direction == null || visited[nPosX][nPosY]) {
                direction = Util.randDirection();
                nPosX = posX;
                nPosY = posY;
                if (direction == "up") {
                    nPosY--;
                } else if (direction == "down") {
                    nPosY++;
                } else if (direction == "left") {
                    nPosX--;
                } else {
                    nPosX++;
                }
            }
            System.out.println(direction);
            posX = nPosX;
            posY = nPosY;
            visited[posX][posY] = true;

            double x, y;
            Room next;
            switch (direction) {
                case "up":
                    x = current.top().x;
                    y = current.top().y;
                    break;
                case "down":
                    x = current.bottom().x;
                    y = current.bottom().y;
                    break;
                case "left":
                    x = current.left().x;
                    y = current.left().y;
                    break;
                case "right":
                default:
                    x = current.right().x;
                    y = current.right().y;
            }


            next = new Room("normal", x, y, direction, this);
            addRoom(next);
            connect(current, next, direction);

            current = next;
        }
        
        
    }

    ArrayList<Room> getRooms() {
        return new ArrayList<Room>(connections.keySet());
    }

    void visit(int x, int y) {
        x/=Room.TILE_SIZE;
        y/=Room.TILE_SIZE;
        visited[x>=0 ? x : Integer.MAX_VALUE-x][y>=0 ? y : Integer.MAX_VALUE-y] = true;
    }
}