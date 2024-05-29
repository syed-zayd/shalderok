import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Room {
    static final int TILE_SIZE = 32;

    public boolean conflicted;

    public String type;
    Floor f;
    ArrayList<GameObject> objs;
    ArrayList<GameObject> path;
    GameObject gateUp;
    GameObject gateDown;
    GameObject gateLeft;
    GameObject gateRight;
    private int cols; // width in tiles
    private int rows; // height in tiles

    Iterator<GameObject> getObjIterator() {
        return new Iterator<GameObject>() {
            private Iterator<GameObject> objsIterator = objs.iterator();
            private Iterator<GameObject> pathIterator = path.iterator();

            @Override
            public boolean hasNext() {
                return objsIterator.hasNext() || pathIterator.hasNext();
            }

            @Override
            public GameObject next() {
                if (objsIterator.hasNext()) {
                    return objsIterator.next();
                } else if (pathIterator.hasNext()) {
                    return pathIterator.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
            
        };
    }

    boolean setGateUp(GameObject obj) {
        objs.remove(gateUp);
        gateUp = obj;
        if (gateUp != null) {
            objs.add(gateUp);
            return f.isVisited(gateUp);
        }
        return false;
    }

    boolean setGateDown(GameObject obj) {
        objs.remove(gateDown);
        gateDown = obj;
        if (gateDown != null) {
            objs.add(gateDown);
            return f.isVisited(gateDown);
        }
        return false;
    }

    boolean setGateLeft(GameObject obj) {
        objs.remove(gateLeft);
        gateLeft = obj;
        if (gateLeft != null) {
            objs.add(gateLeft);
            return f.isVisited(gateLeft);
        }
        return false;
    }

    boolean setGateRight(GameObject obj) {
        objs.remove(gateRight);
        gateRight = obj;
        if (gateRight != null) {
            objs.add(gateRight);
            return f.isVisited(gateRight);
        }
        return false;
    }

    boolean addPath(GameObject obj) {
        path.add(obj);
        return f.isVisited(obj);
    }

    boolean addObj(GameObject obj) {
        objs.add(obj);
        return f.isVisited(obj);
    }

    public Room(String type, double x, double y, String direction, Floor f) throws IOException {
        this.conflicted = false;
        this.type = type;
        this.objs = new ArrayList<GameObject>();
        this.path = new ArrayList<GameObject>();
        this.f = f;

        // load the room's file
        String filePath = "";
        if (type == "entrance") {
            filePath = "rooms/entrance.txt";
        } else if (type == "normal") {
            filePath = "rooms/normal.txt";
        }
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        cols = Integer.parseInt(br.readLine());
        rows = Integer.parseInt(br.readLine());

        // build the path
        int pathLength = Util.randInt(1, 10);
        switch (direction) {
            case "up":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addPath(new Wall(x - TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addPath(new Wall(x + TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
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
                    if (addPath(new Wall(x - TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addPath(new Wall(x + TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
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
                    if (addPath(new Wall(x - TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addPath(new Wall(x - TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
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
                    if (addPath(new Wall(x + TILE_SIZE, y - TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addPath(new Wall(x + TILE_SIZE, y + TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray))) {
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
                GameObject o = null;
                switch (line.charAt(col)) {
                    case 'W':
                        o = new Wall(x + col * TILE_SIZE, y + row * TILE_SIZE, TILE_SIZE, TILE_SIZE, Color.darkGray);
                }

                // add the gates if necessary
                // this code will not work if the room has empty spaces where the gates are
                if (row == 0 && col == cols / 2) { // top
                    if (setGateUp(o)) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    ;
                } else if (row == rows - 1 && col == cols / 2) { // bottom
                    if (setGateDown(o)) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else if (row == rows / 2 && col == 0) { // left
                    if (setGateLeft(o)) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else if (row == rows / 2 && col == cols - 1) { // right
                    if (setGateRight(o)) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                } else if (o != null) {
                    if (addObj(o)) {
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
        for (GameObject obj : path) {
            f.visit(obj);
        }
    }
}