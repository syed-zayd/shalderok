import java.awt.Graphics2D;
import java.util.ArrayList;

public class Room {
    public ArrayList<Room> connections;
    public String type;
    
    public Room(String type) {
        this.type = type;
        connections = new ArrayList<>();
    }
}