import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
    static final int TILE_SIZE = 32;

    boolean conflicted = false;
    public String type;
    private GameObject[][] grid;
    ArrayList<GameObject> objs;
    private int cols; // in tiles
    private int rows; // in tiles
    
    public GameObject top() {
        return grid[0][cols/2];
    }
    public GameObject left() {
        return grid[rows/2][0];
    }
    public GameObject bottom() {
        return grid[rows-1][cols/2];
    }
    public GameObject right() {
        return grid[rows/2][cols-1];
    }

    public void digFrom(String direction) {
        // make the connection tile null
        switch (direction) {
            case "up":
                objs.remove(grid[rows-1][cols/2]);
                grid[rows-1][cols/2] = null;
                break;
            case "down":
                objs.remove(grid[0][cols/2]);
                grid[0][cols/2] = null;
                break;
            case "left":
                objs.remove(grid[rows/2][cols-1]);
                grid[rows/2][cols-1] = null;
                break;
            case "right":
                objs.remove(grid[rows/2][0]);
                grid[rows/2][0] = null;
                break;
        }
    }

    public void dig(String direction) {
        // make the connection tile null
        switch (direction) {
            case "down":
                objs.remove(grid[rows-1][cols/2]);
                grid[rows-1][cols/2] = null;
                break;
            case "up":
                objs.remove(grid[0][cols/2]);
                grid[0][cols/2] = null;
                break;
            case "right":
                objs.remove(grid[rows/2][cols-1]);
                grid[rows/2][cols-1] = null;
                break;
            case "left":
                objs.remove(grid[rows/2][0]);
                grid[rows/2][0] = null;
                break;
        }
    }

    public Room(String type, double x, double y, String direction, Floor f) {
        this.type = type;
        this.objs = new ArrayList<GameObject>();

        // get the dimensions of the room
        if (type == "entrance") {
            cols=5;
            rows=5;
            grid = new GameObject[rows][cols];
        } else if (type == "normal") {
            cols=11;
            rows=11;
            grid = new GameObject[rows][cols];
        }

        // build the path and setup for room building
        switch (direction) {
            case "up":
                // build a path of walls
                for (int i=0; i<5; i++) {
                    // if (isConflict(x-TILE_SIZE, y-TILE_SIZE)) {
                    //     return;
                    // }

                    objs.add(new Wall(x-TILE_SIZE, y-TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    objs.add(new Wall(x+TILE_SIZE, y-TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    y-=TILE_SIZE;
                }

                // set the x and y accordingly
                x-=cols/2*TILE_SIZE;
                y-=rows*TILE_SIZE;

                break;
            case "down":
                // build a path of walls
                for (int i=0; i<5; i++) {
                    objs.add(new Wall(x-TILE_SIZE, y+TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    objs.add(new Wall(x+TILE_SIZE, y+TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    y+=TILE_SIZE;
                }

                // set the x and y accordingly
                x-=cols/2*TILE_SIZE;
                y+=TILE_SIZE;

                break;
            case "left":
                // build a path of walls
                for (int i=0; i<5; i++) {
                    objs.add(new Wall(x-TILE_SIZE, y-TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    objs.add(new Wall(x-TILE_SIZE, y+TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    x-=TILE_SIZE;
                }

                // set the x and y accordingly
                x-=cols*TILE_SIZE;
                y-=rows/2*TILE_SIZE;

                break;
            case "right":
                // build a path of walls
                for (int i=0; i<5; i++) {
                    objs.add(new Wall(x+TILE_SIZE, y-TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    objs.add(new Wall(x+TILE_SIZE, y+TILE_SIZE, TILE_SIZE, TILE_SIZE));
                    x+=TILE_SIZE;
                }

                // set the x and y accordingly
                x+=TILE_SIZE;
                y-=rows/2*TILE_SIZE;
        }

        // build the room
        try {
            BufferedReader br = new BufferedReader(new FileReader("rooms/" + type + ".txt"));
            br.readLine();

            for (int row=0; row<rows; row++) {
                String line = br.readLine();
                for (int col=0; col<cols; col++) {
                    switch (line.charAt(col)) {
                        case 'W':
                            Wall w = new Wall(x+col*TILE_SIZE, y+row*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            grid[row][col] = w;
                            objs.add(w);
                            break;
                    }
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isConflict(double x, double y) {
        return true;
    }
}