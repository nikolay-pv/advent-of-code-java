import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day09 {
    public static long getRectArea(Point2D a, Point2D b) {
        return (Math.abs(a.x - b.x) + 1) * (Math.abs(a.y - b.y) + 1);
    }

    public static long solveFirst(ArrayList<Point2D> inputList) {
        long maxRectArea = 0;
        for (int i = 0; i < inputList.size() - 1; i++) {
            var start = inputList.get(i);
            for (int j = i + 1; j < inputList.size(); j++) {
                maxRectArea = Math.max(maxRectArea, getRectArea(start, inputList.get(j)));
            }
        }
        return maxRectArea;
    }

    private static boolean isInsidePolygon(ArrayList<Edge> polygon, ArrayList<Edge> rect) {
        for (var polygonEdge : polygon) {
            for (var rectEdge : rect) {
                // co-directional
                // not allowed to be in opposite directions (if have overlap)
                if (polygonEdge.isHorizontal() == rectEdge.isHorizontal()) {
                    if (polygonEdge.containsWeakly(rectEdge.start) || polygonEdge.containsWeakly(rectEdge.end)) {
                        if (polygonEdge.normal.compareTo(rectEdge.normal) != 0) {
                            return false;
                        }
                    }
                }
                // orthogonal
                // no polygon edge is allowed to intersect
                var intersection = rectEdge.getIntersectionPoint(polygonEdge);
                if (intersection != null) {
                    if (polygonEdge.isHorizontal() == rectEdge.isHorizontal()) {
                        continue;
                    }
                    // touching case
                    if (polygonEdge.end.compareTo(intersection) == 0) {
                        // rule out the corner
                        if (rectEdge.start.compareTo(intersection) == 0) {
                            continue;
                        }
                        // polygon edge coming from within the rect = intersects
                        if (rectEdge.normal.dot(polygonEdge.direction) < 0) {
                            return false;
                        }
                        continue;
                    }
                    if (polygonEdge.start.compareTo(intersection) == 0) {
                        // rule out the corner
                        if (rectEdge.end.compareTo(intersection) == 0) {
                            continue;
                        }
                        // polygon edge going inside the rect = intersects
                        if (rectEdge.normal.dot(polygonEdge.direction) > 0) {
                            return false;
                        }
                        continue;
                    }
                    if (rectEdge.end.compareTo(intersection) == 0 || rectEdge.start.compareTo(intersection) == 0) {
                        continue;
                    }
                    // all other intersections are bad
                    return false;
                }
            }
        }
        return true;
    }

    public static long solveSecond(ArrayList<Point2D> inputList) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 1; i < inputList.size(); i++) {
            var start = inputList.get(i - 1);
            var end = inputList.get(i);
            edges.add(new Edge(start, end));
        }
        // closing the polygon by adding last edge
        edges.add(new Edge(inputList.getLast(), inputList.getFirst()));

        long maxRectArea = 0;
        for (int i = 0; i < inputList.size() - 1; i++) {
            var start = inputList.get(i);
            for (int j = i + 1; j < inputList.size(); j++) {
                var end = inputList.get(j);
                var rect = Edge.createRectFromDiagonal(start, end);
                if (rect == null) { // line like rectangle is ignored (for now)
                    continue;
                }
                if (!isInsidePolygon(edges, rect)) {
                    continue;
                }
                maxRectArea = Math.max(maxRectArea, getRectArea(start, end));
            }
        }
        return maxRectArea;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-09", "src", "input.txt");
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<Point2D> inputList = new ArrayList<>();
            lines.forEach(s -> inputList.add(Point2D.parsePoint2D(s)));
            System.out.println("Solution to the first problem is: " + solveFirst(inputList));
            System.out.println("Solution to the second problem is: " + solveSecond(inputList));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }
}
