import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day10 {
    public static long solveFirst(ArrayList<String> inputList) {
        long total = 0;
        for (var line : inputList) {
            var schematics = ButtonSchematics.parseButtonSchematics(line);
            var res = schematics.countPressesForIndicator();
            if (res == -1) {
                throw new RuntimeException("Couldn't find button press combination");
            }
            total += res;
        }
        return total;
    }

    public static long solveSecond(ArrayList<String> inputList) {
        // this is solved with python's linear solver because I couldn't find a faster
        // way to search the solution space without writing a LP solver
        return 0;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-10", "src", "input.txt");
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
