import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day10 {
    private static ArrayList<Long> parse(String line) {
        var res = new ArrayList<Long>();
        for (var input : line.trim().split("\\s")) {
            long bits = 0;
            if (input.startsWith("[")) {
                long index = 1L;
                for (int i = 1; i < input.length() - 1; i++) {
                    if (input.charAt(i) == '#') {
                        bits |= index;
                    }
                    index <<= 1;
                }
            } else if (input.startsWith("(")) {
                // trim to avoid ()
                for (var idxStr : input.substring(1, input.length() - 1).split(",")) {
                    long idx = Long.parseLong(idxStr);
                    bits |= (1L << idx);
                }
            } else {
                continue;
            }
            res.add(Long.valueOf(bits));
        }
        return res;
    }

    public static long countPresses(ArrayList<Long> configuration) {
        HashMap<Long, Long> intermediate = new HashMap<>();
        final long goal = configuration.getFirst().longValue();
        // init with individual first buttons pressed
        for (int j = 1; j < configuration.size(); j++) {
            Long value = configuration.get(j);
            if (value.longValue() == goal) {
                return 1;
            }
            intermediate.put(Long.valueOf(1L << j), value);
        }
        // there is no point in toggling the same button twice because it would just
        // nullify the first press, so search for all possible press sequences which
        // will result in the `goal`
        for (int pressCount = 2; pressCount < configuration.size(); pressCount++) {
            HashMap<Long, Long> new_intermediate = new HashMap<>();
            for (var entry : intermediate.entrySet()) {
                for (int j = 1; j < configuration.size(); j++) {
                    long key = entry.getKey().longValue();
                    long value = entry.getValue().longValue();
                    if ((key & (1L << j)) == 0) {
                        key |= (1L << j);
                        if (new_intermediate.containsKey(key)) {
                            continue;
                        }
                        value ^= configuration.get(j).longValue();
                        if (value == goal) {
                            return pressCount;
                        }
                        new_intermediate.put(Long.valueOf(key), Long.valueOf(value));
                    }
                }
            }
            intermediate = new_intermediate;
        }
        return -1;
    }

    public static long solveFirst(ArrayList<String> inputList) {
        long total = 0;
        for (var line : inputList) {
            ArrayList<Long> input = parse(line);
            var res = countPresses(input);
            if (res == -1) {
                throw new RuntimeException("Couldn't find button press combination");
            }
            total += res;
        }
        return total;
    }

    public static long solveSecond(ArrayList<String> inputList) {
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
