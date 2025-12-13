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

    public static long solveSecond(ArrayList<Point2D> inputList) {
        return 0;
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
