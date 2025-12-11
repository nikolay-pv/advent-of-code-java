import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day04 {
    private static Indices countNeighbors(RollsMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        Indices removableRolls = new Indices();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map.getAt(row, col, '.') == '@' && map.countNeighbors(row, col, '@', '.') < 4) {
                    removableRolls.add(row, col);
                }
            }
        }
        return removableRolls;
    }

    public static int solveFirst(RollsMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        int count = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map.getAt(row, col, '.') == '@' && map.countNeighbors(row, col, '@', '.') < 4) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static int solveSecond(RollsMap map) {
        int count = 0;
        var indices = countNeighbors(map);
        while (indices.size() != 0) {
            count += indices.size();
            map.removeAllAt(indices);
            indices = countNeighbors(map);
        }
        return count;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-04", "src", "input.txt");
        try (Stream<String> lines = Files.lines(path)) {
            ArrayList<String> inputList = new ArrayList<>();
            lines.forEach(inputList::add);
            var map = new RollsMap(inputList);
            System.out.println("Solution to the first problem is: " + solveFirst(map));
            // test is 13
            System.out.println("Solution to the second problem is: " + solveSecond(map));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }
}
