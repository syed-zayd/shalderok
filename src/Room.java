import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Room {
    static final int TILE_SIZE = 128;

    public boolean conflicted;
    public boolean defeated;
    public boolean activated;

    public String type;
    Floor f;
    GameObject[][] grid;
    ArrayList<GameObject> objs;
    ArrayList<Enemy> enemies;
    private int cols; // width in tiles
    private int rows; // height in tiles

    public int getGateRow(String direction) {
        switch (direction) {
            case "up":
                return 0;
            case "down":
                return grid.length-1;
            case "left":
                return grid.length/2;
            case "right":
                return grid.length/2;
            default:
                return -1;
        }
    }
    public int getGateCol(String direction) {
        switch (direction) {
            case "up":
                return grid[0].length/2;
            case "down":
                return grid[0].length/2;
            case "left":
                return 0;
            case "right":
                return grid[0].length-1;
            default:
                return -1;
        }
    }
    public GameObject getGate(String direction) {
        return grid[getGateRow(direction)][getGateCol(direction)];
    }

    public GameObject getCenterObject() {
        return grid[grid.length / 2][grid[0].length / 2];
    }

    private void setEnemyPFIndex(Enemy e) {
        double left = grid[0][0].x;
        double top = grid[0][0].y;

        int i = (int) ((e.drawCenterY() - top) / TILE_SIZE);
        int j = (int) ((e.drawCenterX() - left) / TILE_SIZE);
        if (i > grid.length - 1 || i < 0 || j > grid[0].length - 1 || j < 0) {
            e.pathfindingCurrentIndex.x = -1;
            e.pathfindingCurrentIndex.y = -1;
        } else {
            e.pathfindingCurrentIndex.x = i;
            e.pathfindingCurrentIndex.y = j;
        }
    }

    private void setPlayerPFIndex() {
        double left = World.p.r.grid[0][0].x;
        double top = World.p.r.grid[0][0].y;

        int i = (int) ((World.p.drawCenterY() - top) / TILE_SIZE);
        int j = (int) ((World.p.drawCenterX() - left) / TILE_SIZE);
        if (i > World.p.r.grid.length || i < 0 || j > World.p.r.grid[0].length - 1 || j < 0) {
            World.p.pathfindingCurrentIndex.x = -1;
            World.p.pathfindingCurrentIndex.y = -1;
        } else {
            World.p.pathfindingCurrentIndex.x = i;
            World.p.pathfindingCurrentIndex.y = j;
        }
    }

    private void pathFind(Enemy e) {
        if (World.p.r != this) {
            e.pathfindingPath = new ArrayList<GameObject>();
            return;
        }

        AStar aStar = new AStar(grid);
        e.pathfindingPath = aStar.findPath(e.pathfindingCurrentIndex, World.p.pathfindingCurrentIndex);
    }

    void activate() {
        activated = true;
        if (enemies.size() != 0) {
            lock();
        } else {
            unlock();
        }

        for (Enemy enemy : enemies) {
            enemy.activated = true;
        }
    }

    void lock() {
        defeated = false;

        if (getGate("up") instanceof Door) {
            ((Door) getGate("up")).lock();
        }
        if (getGate("down") instanceof Door) {
            ((Door) getGate("down")).lock();
        }
        if (getGate("left") instanceof Door) {
            ((Door) getGate("left")).lock();
        }
        if (getGate("right") instanceof Door) {
            ((Door) getGate("right")).lock();
        }
    }

    void unlock() {
        defeated = true;

        if (getGate("up") instanceof Door) {
            ((Door) getGate("up")).unlock();
        }
        if (getGate("down") instanceof Door) {
            ((Door) getGate("down")).unlock();
        }
        if (getGate("left") instanceof Door) {
            ((Door) getGate("left")).unlock();
        }
        if (getGate("right") instanceof Door) {
            ((Door) getGate("right")).unlock();
        }

        if (type == "boss") {
            System.out.println("DEFEATED BOSS");
            int i = grid.length/2-1;
            int j = grid[0].length/2-1;
            objs.add(new Portal(grid[i][j].x, grid[i][j].y));
        }
    }

    boolean addObj(GameObject obj) {
        objs.add(obj);
        return f.isVisited(obj);
    }

    public Room(String type, double x, double y, String direction, Floor f) throws IOException {
        this.conflicted = false;
        this.type = type;
        this.objs = new ArrayList<GameObject>();
        this.enemies = new ArrayList<Enemy>();
        this.f = f;

        // load the room's file
        String filePath = "";
        if (type == "entrance") {
            filePath = "rooms/entrance.txt";
        } else if (type == "normal") {
            File[] normalRooms = new File("rooms/normal").listFiles();
            filePath = "rooms/normal/" + normalRooms[Util.randInt(0, normalRooms.length - 1)].getName();
        } else if (type == "boss") {
            File[] bossRooms = new File("rooms/boss").listFiles();
            filePath = "rooms/boss/" + bossRooms[Util.randInt(0, bossRooms.length - 1)].getName();
        } else if (type == "exit") {
            filePath = "rooms/exit.txt";
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        cols = Integer.parseInt(br.readLine());
        rows = Integer.parseInt(br.readLine());
        this.grid = new GameObject[rows][cols];

        // build the path
        int pathLength = Util.randInt(10, 10);
        switch (direction) {
            case "up":
                // build a path of walls
                for (int i = 0; i < pathLength; i++) {
                    if (addObj(new Wall(x - TILE_SIZE, y - TILE_SIZE, f.getTheme()))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x, y - TILE_SIZE, f.getTheme(), direction))) {
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y - TILE_SIZE, f.getTheme()))) {
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
                    if (addObj(new Wall(x - TILE_SIZE, y + TILE_SIZE, f.getTheme()))) { // leftwall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x, y + TILE_SIZE, f.getTheme(), direction))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y + TILE_SIZE, f.getTheme()))) { // right wall
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
                    if (addObj(new Wall(x - TILE_SIZE, y - TILE_SIZE, f.getTheme()))) { // top wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x - TILE_SIZE, y, f.getTheme(), direction))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x - TILE_SIZE, y + TILE_SIZE, f.getTheme()))) { // bottom wall
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
                    if (addObj(new Wall(x + TILE_SIZE, y - TILE_SIZE, f.getTheme()))) { // top wall
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Path(x + TILE_SIZE, y, f.getTheme(), direction))) { // middle empty
                        conflicted = true;
                        br.close();
                        return;
                    }
                    if (addObj(new Wall(x + TILE_SIZE, y + TILE_SIZE, f.getTheme()))) { // bottom wall
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
                        grid[row][col] = new Wall(x + col * TILE_SIZE, y + row * TILE_SIZE, f.getTheme());
                        break;
                    case 'P':
                        grid[row][col] = new Ground(x + col * TILE_SIZE, y + row * TILE_SIZE, f.getTheme());
                        break;
                    case 'V':
                        grid[row][col] = new Void(x + col * TILE_SIZE, y + row * TILE_SIZE, f.getTheme());
                        break;
                    case 'B':
                    case 'X':
                        grid[row][col] = new Ground(x+col*TILE_SIZE, y+row*TILE_SIZE, f.getTheme());
                        Enemy e = new Spider(0, 0);
                        e.enterNewFloor(f);
                        enemies.add(e);
                        Util.centerPosition(e, grid[row][col]);
                        break;
                    case 'E':
                    default:
                        grid[row][col] = new Ground(x + col * TILE_SIZE, y + row * TILE_SIZE, f.getTheme());
                }

                if (addObj(grid[row][col])) {
                    conflicted = true;
                    br.close();
                    return;
                }
            }
        }

        br.close();
        for (GameObject obj : objs) {
            f.visit(obj);
        }
    }

    void update() {

        for (GameObject obj : objs) {
            obj.update();
        }

        setPlayerPFIndex();
        for (Iterator<Enemy> enemiesIterator = enemies.iterator(); enemiesIterator.hasNext();) {
            Enemy e = enemiesIterator.next();
            if (e.hp <= 0) {
                f.weapons.remove(e.weapon);
                enemiesIterator.remove();
            } else if (e.activated) {
                setEnemyPFIndex(e);
                pathFind(e);
                e.update();
                e.attack();
            }
        }

        if (enemies.size() == 0 && !defeated) {
            unlock();
        }
    }
}
