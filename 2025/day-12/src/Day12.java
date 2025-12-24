import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day12 {
    private static long asLong(String num) {
        return Long.parseLong(num);
    }

    public static long solveFirst(ArrayList<String> inputList) {
        long[] sizes = new long[6];
        boolean isDomainInput = false;
        int idx = 0;
        long canFitCount = 0;
        for (var line : inputList) {
            if (!isDomainInput) {
                if (line.isEmpty()) {
                    idx++;
                    if (idx == 6) {
                        isDomainInput = true;
                    }
                    continue;
                }
                long counter = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '#') {
                        counter++;
                    }
                }
                sizes[idx] += counter;
            } else {
                var tokens = line.split("[x\s]");
                if (tokens.length != 8) {
                    throw new RuntimeException("unexpected input: " + line);
                }
                long spaceSize = asLong(tokens[0]) * asLong(tokens[1]);
                long giftSize = 0;
                for (int i = 0; i < sizes.length; i++) {
                    giftSize += sizes[i] * asLong(tokens[i + 2]);
                }
                if (giftSize < spaceSize) {
                    canFitCount++;
                }
            }
        }
        return canFitCount;
    }

    public static long solveSecond(ArrayList<String> inputList) {
        return 0;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-12", "src", "input.txt");
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
