import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private GameObject[][] grid;

    public AStar(GameObject[][] grid) {
        this.grid = grid;
    }

    public ArrayList<GameObject> findPath(Point start, Point goal) {
        if (start.x == -1 || start.y == -1 || goal.x == -1 || goal.y == -1) {
            return new ArrayList<GameObject>();
        }

        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<Point> closedList = new HashSet<>();

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            if (currentNode.position.equals(goal)) {
                return reconstructPath(currentNode);
            }

            closedList.add(currentNode.position);

            for (Point neighbor : getNeighbors(currentNode.position)) {
                if (closedList.contains(neighbor) || !isWalkable(neighbor)) {
                    continue;
                }

                double tentativeGCost = currentNode.gCost + ((currentNode.position.x != neighbor.x && currentNode.position.y != neighbor.y) ? Math.sqrt(2) : 1);

                Node neighborNode = new Node(neighbor, currentNode, tentativeGCost, heuristic(neighbor, goal));

                if (openList.stream().noneMatch(node -> node.position.equals(neighbor) && node.gCost <= tentativeGCost)) {
                    openList.add(neighborNode);
                }
            }
        }

        return new ArrayList<GameObject>(); // No path found
    }

    private ArrayList<GameObject> reconstructPath(Node node) {
        ArrayList<GameObject> path = new ArrayList<>();
        while (node != null) {
            Point index = node.position;
            path.add(grid[index.x][index.y]);
            node = node.parent;
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        // Horizontal and vertical neighbors
        if (x > 0) neighbors.add(new Point(x - 1, y));
        if (x < grid.length - 1) neighbors.add(new Point(x + 1, y));
        if (y > 0) neighbors.add(new Point(x, y - 1));
        if (y < grid[0].length - 1) neighbors.add(new Point(x, y + 1));

        // Diagonal neighbors with additional checks
        if (x > 0 && y > 0 && isWalkable(new Point(x - 1, y)) && isWalkable(new Point(x, y - 1))) {
            neighbors.add(new Point(x - 1, y - 1));
        }
        if (x > 0 && y < grid[0].length - 1 && isWalkable(new Point(x - 1, y)) && isWalkable(new Point(x, y + 1))) {
            neighbors.add(new Point(x - 1, y + 1));
        }
        if (x < grid.length - 1 && y > 0 && isWalkable(new Point(x + 1, y)) && isWalkable(new Point(x, y - 1))) {
            neighbors.add(new Point(x + 1, y - 1));
        }
        if (x < grid.length - 1 && y < grid[0].length - 1 && isWalkable(new Point(x + 1, y)) && isWalkable(new Point(x, y + 1))) {
            neighbors.add(new Point(x + 1, y + 1));
        }

        return neighbors;
    }

    private boolean isWalkable(Point point) {
        return !grid[point.x][point.y].isSolid();
    }

    private static double heuristic(Point a, Point b) {
        return Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
    }
}