import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day08 {
    public static long solveFirst(ArrayList<String> inputList) {
        ArrayList<Point> points = new ArrayList<>();
        inputList.stream().map(s -> Point.parsePoint(s)).forEach(points::add);
        AnnotatedDistance[] distances = new AnnotatedDistance[(points.size() - 1) * (points.size()) / 2];
        int idx = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            var start = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                distances[idx] = new AnnotatedDistance(i, j, start.sqrDistanceTo(points.get(j)));
                idx++;
            }
        }
        Arrays.sort(distances);

        int connectionsToMake = 1000;
        int clustersToCount = 3;
        var set = new DisjointSet(points.size());
        int counter = 0;
        for (var connection : distances) {
            if (counter == connectionsToMake) {
                break;
            }
            set.unite(connection.i, connection.j);
            counter += 1;
        }
        return set.sizeOfNLargest(clustersToCount);
    }

    public static long solveSecond(ArrayList<String> inputList) {
        ArrayList<Point> points = new ArrayList<>();
        inputList.stream().map(s -> Point.parsePoint(s)).forEach(points::add);
        AnnotatedDistance[] distances = new AnnotatedDistance[(points.size() - 1) * (points.size()) / 2];
        int idx = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            var start = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                distances[idx] = new AnnotatedDistance(i, j, start.sqrDistanceTo(points.get(j)));
                idx++;
            }
        }
        Arrays.sort(distances);

        AnnotatedDistance finalConnection = null;
        var set = new DisjointSet(points.size());
        for (var connection : distances) {
            set.unite(connection.i, connection.j);
            if (set.getDisjointSetsCount() == 1) {
                finalConnection = connection;
                break;
            }
        }
        return (long) points.get(finalConnection.i).x * points.get(finalConnection.j).x;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-08", "src", "input.txt");
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> inputList = new ArrayList<>();
            lines.forEach(inputList::add);
            System.out.println("Solution to the first problem is: " + solveFirst(inputList));
            System.out.println("Solution to the second problem is: " + solveSecond(inputList));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }
}
