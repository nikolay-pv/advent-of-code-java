import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class Day05 {
    public static ArrayList<Range> deduplicate(ArrayList<Range> input) {
        Collections.sort(input);
        ArrayList<Range> res = new ArrayList<>();
        for (var r : input) {
            if (res.isEmpty()) {
                res.add(r);
                continue;
            }
            if (res.getLast().getLast() > r.getFirst()) {
                res.getLast().merge(r);
            } else {
                res.add(r);
            }
        }
        return res;
    }

    public static long solveFirst(ArrayList<String> inputList) {
        ArrayList<Range> freshRanges = new ArrayList<>();
        ArrayList<Long> available = new ArrayList<>();
        boolean collectAvailable = false;
        for (var line : inputList) {
            if (line.isEmpty()) {
                collectAvailable = true;
                continue;
            }
            if (collectAvailable) {
                available.add(Long.parseLong(line));
            } else {
                freshRanges.add(Range.parseRange(line));
            }
        }
        freshRanges = deduplicate(freshRanges);
        Collections.sort(available);

        long counter = 0;
        long minPossible = freshRanges.getFirst().getFirst();
        long maxPossible = freshRanges.getLast().getLast();
        for (Long product : available) {
            if (product.longValue() < minPossible || maxPossible < product.longValue()) {
                continue;
            }
            Long lastPlacement = null;
            for (var r : freshRanges) {
                var placement = r.place(product.longValue());
                if (placement == 0) {
                    counter += 1;
                    break;
                }
                // overshoot the last possible range
                if (lastPlacement != null && lastPlacement.longValue() != placement) {
                    break;
                }
            }
        }
        return counter;
    }

    public static long solveSecond(ArrayList<String> inputList) {
        return 0;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-05", "src", "input.txt");
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
