import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day04 {
    public static int solveFirst(RollsMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map.getAt(i, j, '.') == '@' && map.countNeighbors(i, j, '@', '.') < 4) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public static int solveSecond(RollsMap map) {
        return 0;
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
