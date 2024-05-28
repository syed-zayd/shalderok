import java.awt.geom.*;
import java.util.*;

public class NavMesh {
    private class Polygon {
        List<Point2D> vertices = new ArrayList<>();
        List<Polygon> neighbors = new ArrayList<>();

        void addVertex(double x, double y) {
            vertices.add(new Point2D.Double(x, y));
        }
        void addNeighbor(Polygon neighbor) {
            neighbors.add(neighbor);
        }
    }

    List<Polygon> polygons = new ArrayList<>();

    void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    void connectPolygons(Polygon p1, Polygon p2) {
        p1.addNeighbor(p2);
        p2.addNeighbor(p1);
    }
}
