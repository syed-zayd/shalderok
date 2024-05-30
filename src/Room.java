import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Room {
    static final int TILE_SIZE = 64;

    public boolean conflicted;

    public String type;
    Floor f;
    GameObject[][] grid;
    ArrayList<GameObject> objs;
    GameObject gateUp;
    GameObject gateDown;
    GameObject gateLeft;
    GameObject gateRight;
    private int cols; // width in tiles
    private int rows; // height in tiles

    boolean setGateUp(GameObject obj) {
        objs.remove(gateUp);
        if (obj != null) {
            gateUp = obj;
            objs.add(gateUp);
            return f.isVisited(gateUp);
        }
        gateUp = new Empty(gateUp.x, gateUp.y, TILE_SIZE, TILE_SIZE);
        objs.add(gateUp);
        return false;
    }

    boolean setGateDown(GameObject obj) {
        objs.remove(gateDown);
        if (obj != null) {
            gateDown = obj;
            objs.add(gateDown);
            return f.isVisited(gateDown);
        }
        gateDown = new Empty(gateDown.x, gateDown.y, TILE_SIZE, TILE_SIZE);
        objs.add(gateDown);
        return false;
    }

    boolean setGateLeft(GameObject obj) {
        objs.remove(gateLeft);
        if (obj != null) {
            gateLeft = obj;
            objs.add(gateLeft);
            return f.isVisited(gateLeft);
        }
        gateLeft = new Empty(gateLeft.x, gateLeft.y, TILE_SIZE, TILE_SIZE);
        objs.add(gateLeft);
        return false;
    }

    boolean setGateRight(GameObject obj) {
        objs.remove(gateRight);
        if (obj != null) {
            gateRight = obj;
            objs.add(gateRight);
            return f.isVisited(gateRight);
        }
        gateRight = new Empty(gateRight.x, gateRight.y, TILE_SIZE, TILE_SIZE);
        objs.add(gateRight);
        return false;
    }

    boolean addObj(GameObject obj) {
        objs.add(obj);
        return f.isVisited(obj);
    }

    public Room(String type, double x, double y, String direction, Floor f) throws IOException {
        this.conflicted = false;
        this.type = type;
        this.objs = new ArrayList<GameObject>();
        this.f = f;

        // load the room's file
        String filePath = "";
        if (type == "entrance") {
            filePath = "rooms/entrance.txt";
        } else if (type == "normal") {
            File[] normalRooms = new File("rooms/normal").listFiles();
            filePath = "rooms/normal/" + normalRooms[Util.randInt(0,normalRooms.length-1)].getName();
        }
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        cols = Integer.parseInt(br.readLine());
        rows = Integer.parseInt(br.readLine());
        this.grid = new GameObject[rows][cols];

        // build the path
        int pathLength = Util.randInt(1, 10);
        switch (direction) {
            case "up":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addObj(new Wall(x-TILE_SIZE, y-TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x, y - TILE_SIZE, TILE_SIZE, TILE_SIZE))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    y -= TILE_SIZE;
                }
                // set the x and y accordingly
                x -= cols / 2 * TILE_SIZE;
                y -= rows * TILE_SIZE;
                break;
            case "down":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addObj(new Wall(x - TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // left
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x, y + TILE_SIZE, TILE_SIZE, TILE_SIZE))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // right
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    y += TILE_SIZE;
                }
                // set the x and y accordingly
                x -= cols / 2 * TILE_SIZE;
                y += TILE_SIZE;
                break;
            case "left":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addObj(new Wall(x - TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // top
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x - TILE_SIZE, y, TILE_SIZE, TILE_SIZE))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x - TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // bottom
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    x -= TILE_SIZE;
                }
                // set the x and y accordingly
                x -= cols * TILE_SIZE;
                y -= rows / 2 * TILE_SIZE;
                break;
            case "right":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addObj(new Wall(x + TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // top
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x + TILE_SIZE, y, TILE_SIZE, TILE_SIZE))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) { // bottom
                                                                                                                // wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    x += TILE_SIZE;
                }
                // set the x and y accordingly
                x += TILE_SIZE;
                y -= rows / 2 * TILE_SIZE;
        }

        // build the room
        for (int row = 0; row < rows; row++) {
            String line = br.readLine();
            for (int col = 0; col < cols; col++) {
                // determine the object to add
                switch (line.charAt(col)) {
                    case 'W':
                        grid[row][col] = new Wall(x + col * TILE_SIZE, y + row * TILE_SIZE, TILE_SIZE, TILE_SIZE,
                                Color.darkGray);
                        break;
                    case 'P':
                        grid[row][col] = new Path(x + col * TILE_SIZE, y + row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    case 'E':
                    default:
                        grid[row][col] = new Empty(x + col * TILE_SIZE, y + row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

                // add the gates if necessary
                // this code will not work if the room has empty spaces where the gates are
                if (row == 0 && col == cols / 2) { // top
                    if (setGateUp(grid[row][col])) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    ;
                } else if (row == rows - 1 && col == cols / 2) { // bottom
                    if (setGateDown(grid[row][col])) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else if (row == rows / 2 && col == 0) { // left
                    if (setGateLeft(grid[row][col])) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else if (row == rows / 2 && col == cols - 1) { // right
                    if (setGateRight(grid[row][col])) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else {
                    if (addObj(grid[row][col])) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                }
            }
        }

        br.close();
        for (GameObject obj : objs) {
            f.visit(obj);
        }
    }
}
