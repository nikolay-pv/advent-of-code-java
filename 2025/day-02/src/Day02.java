import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class Day02 {
    public static long computeTestNumber(long startNum, long multiplier, int repeats) {
        long testNumber = startNum;
        for (int i = 1; i < repeats; i++) {
            testNumber = testNumber * multiplier + startNum;
        }
        return testNumber;
    }

    public static long solveFirstSingle(String range) {
        int pos = range.indexOf('-', 0);
        if (pos == -1) {
            throw new Error("invalid range passed: " + range);
        }
        int size = (pos + 1) / 2; // take bigger part of the string in case there are odd number of digits
        long first = Long.parseLong(range, 0, pos, 10);
        long last = Long.parseLong(range, pos + 1, range.length(), 10);

        // taking only parsing part is too assuming, 998 -> results in 9898, while 1010 will be the closest number
        long startNum = (long) Math.pow(10, size - 1);
        long multiplier = (long) Math.pow(10, size);
        long sum = 0;
        // generate symmetric numbers like 123123 and test if they are in range, then sum
        long testNumber = computeTestNumber(startNum, multiplier, 2);
        while (testNumber <= last) {
            if (testNumber >= first) { // skip everything which is not qualifying
                sum += testNumber;
            }
            startNum += 1;
            if (startNum >= multiplier) {
                multiplier *= 10;
            }
            testNumber = computeTestNumber(startNum, multiplier, 2);
        }
        return sum;
    }

    public static long solveFirst(ArrayList<String> inputList) {
        long res = 0;
        for (String input : inputList) {
            res += solveFirstSingle(input);
        }
        return res;
    }

    public static long solveSecondSingle(String range) {
        int pos = range.indexOf('-', 0);
        if (pos == -1) {
            throw new Error("invalid range passed: " + range);
        }
        int max_size = (pos + 1) / 2; // take bigger part of the string in case there are odd number of digits
        long first = Long.parseLong(range, 0, pos, 10);
        long last = Long.parseLong(range, pos + 1, range.length(), 10);
        int last_len = range.length() - (pos + 1);

        // the same generate and test approach, but now as the size is reduced, the
        // number of appearances increase
        // what about duplicates? 111111 can appear twice
        long sum = 0;
        HashSet<Long> generated = new HashSet<Long>();
        for (int size = max_size; size > 0; size--) {
            int max_repeats = last_len / size;
            for (int repeats = 2; repeats <= max_repeats; repeats++) {
                long startNum = (long) Math.pow(10, size - 1);
                long multiplier = (long) Math.pow(10, size);
                long testNumber = computeTestNumber(startNum, multiplier, repeats);
                while (testNumber <= last) {
                    if (testNumber >= first) { // skip everything which is not qualifying
                        if (!generated.contains(Long.valueOf(testNumber))) {
                            sum += testNumber;
                            generated.add(testNumber);
                            System.out.println("adding " + testNumber);
                        }
                    }
                    startNum += 1;
                    if (startNum >= multiplier) {
                        multiplier *= 10;
                    }
                    testNumber = computeTestNumber(startNum, multiplier, repeats);
                }
            }
        }
        return sum;
    }

    public static long solveSecond(ArrayList<String> inputList) {
        long res = 0;
        for (String input : inputList) {
            res += solveSecondSingle(input);
        }
        return res;
    }

    public static void main(String[] args) {
        // the input was manually split to contain one entry per line
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-02", "src", "input.txt");
        try(Stream<String> lines = Arrays.stream(Files.readAllLines(path).getFirst().split(","))) {
            ArrayList<String> inputList = new ArrayList<>();
            lines.forEach(inputList::add);
            System.out.println("Solution to the first problem is: " + solveFirst(inputList));
            System.out.println("Solution to the second problem is: " + solveSecond(inputList));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }    
}
