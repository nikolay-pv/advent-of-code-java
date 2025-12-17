import java.util.ArrayList;

public class Edge {
    final public Point2D start;
    final public Point2D end;
    // vector which points along the edge
    final public Point2D direction;
    // vector which points inside the figure
    final public Point2D normal;

    public Edge(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
        this.direction = end.subtract(start);
        var normal = computeNormal(start, end);
        // one of the two is always zero
        long len = Math.abs(normal.x) + Math.abs(normal.y);
        normal.x /= len;
        normal.y /= len;
        this.normal = normal;
    }

    private static Point2D computeNormal(Point2D start, Point2D end) {
        // normal pointing to the "right" of the vector
        long dx = start.y - end.y;
        long dy = end.x - start.x;
        return new Point2D(dx, dy);
    }

    public boolean isHorizontal() {
        return this.direction.x != 0;
    }

    public Point2D getIntersectionPoint(Edge o) {
        Point2D testIntersectionPoint = null;
        // using the fact that the Edges only vertical or horizontal
        if (this.isHorizontal()) { // this is horizontal, o is vertical
            testIntersectionPoint = new Point2D(o.start.x, this.start.y);
        } else {
            testIntersectionPoint = new Point2D(this.start.x, o.start.y);
        }
        if (this.contains(testIntersectionPoint) && o.contains(testIntersectionPoint)) {
            return testIntersectionPoint;
        }
        return null;
    }

    public boolean contains(Point2D o) {
        long minX = Math.min(this.start.x, this.end.x);
        long maxX = Math.max(this.start.x, this.end.x);
        long minY = Math.min(this.start.y, this.end.y);
        long maxY = Math.max(this.start.y, this.end.y);
        return minX <= o.x && o.x <= maxX && minY <= o.y && o.y <= maxY;
    }

    public boolean containsWeakly(Point2D o) {
        long minX = Math.min(this.start.x, this.end.x);
        long maxX = Math.max(this.start.x, this.end.x);
        long minY = Math.min(this.start.y, this.end.y);
        long maxY = Math.max(this.start.y, this.end.y);
        if (minX == maxX) {
            return minX == o.x && minY < o.y && o.y < maxY;
        }
        if (minY == maxY) {
            return minY == o.y && minX < o.x && o.x < maxX;
        }
        throw new RuntimeException("unreachable");
    }

    public static ArrayList<Edge> createRectFromDiagonal(Point2D start, Point2D end) {
        ArrayList<Edge> edges = new ArrayList<>();
        var p1 = new Point2D(start.x, end.y);
        var p2 = new Point2D(end.x, start.y);
        var diag = end.subtract(start);
        if (diag.x == 0 || diag.y == 0) {
            return null;
        }
        // making the rect "right" oriented, by checking if p1 or p2 is in positive from
        // the normal, that will be the 3rd point in the rect
        var normal = computeNormal(start, end);
        var p1vec = p1.subtract(start);
        if (normal.dot(p1vec) > 0L) {
            edges.add(new Edge(start, p2));
            edges.add(new Edge(p2, end));
            edges.add(new Edge(end, p1));
            edges.add(new Edge(p1, start));
        } else {
            edges.add(new Edge(start, p1));
            edges.add(new Edge(p1, end));
            edges.add(new Edge(end, p2));
            edges.add(new Edge(p2, start));
        }
        return edges;
    }

    @Override
    public String toString() {
        return this.start.toString() + " -> " + this.end.toString();
    }
}
