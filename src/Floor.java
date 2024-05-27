import java.util.*;

public class Floor {
    private Map<Room, ArrayList<Room>> connections;

    void addRoom(Room r) {
        connections.put(r, new ArrayList<>());
    }

    void connect(Room r1, Room r2) {
        connections.get(r1).add(r2);
        connections.get(r2).add(r1);
    }

    ArrayList<Room> getConnectingRooms(Room r) {
        return connections.get(r);
    }

    public Floor() {

        Room boss = new Room("boss");
        Room exit = new Room("exit");
        addRoom(boss);
        addRoom(exit);
        connect(boss, exit);

        Room entrance = new Room("entrance");
        addRoom(entrance);
        Room current = entrance;
        for (int i=0; i<=Util.randInt(5, 8); i++) {
            Room next = new Room("normal");
            addRoom(next);
            connect(current, next);
            current = next;
        }
        connect(current, boss);

        System.out.println(getConnectingRooms(entrance));
    }



}