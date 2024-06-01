import java.awt.Point;

public class Node implements Comparable<Node> {
    public Point position;
    public Node parent;
    public double gCost;
    public double hCost;

    public Node(Point position, Node parent, double gCost, double hCost) {
        this.position = position;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public double getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.getFCost(), other.getFCost());
    }
}