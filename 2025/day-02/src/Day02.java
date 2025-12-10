import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day02 {
    public static Long solveFirstSingle(String range) {
        Integer pos = range.indexOf('-', 0);
        if (pos == -1) {
            throw new Error("invalid range passed: " + range);
        }
        Integer size = (pos + 1) / 2; // take bigger part of the string in case there are odd number of digits
        Long first = Long.parseLong(range, 0, pos, 10);
        Long last = Long.parseLong(range, pos + 1, range.length(), 10);

        // taking only parsing part is too assuming, 998 -> results in 9898, while 1010 will be the closest number
        Long startNum = (long) Math.pow(10, size - 1);
        Long multiplier = (long) Math.pow(10, size);
        Long sum = (long) 0;
        // generate symmetric numbers like 123123 and test if they are in range, then sum
        Long testNumber = startNum * multiplier + startNum;
        while (testNumber <= last) {
            if (testNumber >= first) { // skip everything which is not qualifying
                sum += testNumber;
            }
            startNum += 1;
            if (startNum >= multiplier) {
                multiplier *= 10;
            }
            testNumber = startNum * multiplier + startNum;
        }
        return sum;
    }

    public static Long solveFirst(ArrayList<String> inputList) {
        Long res = (long) 0;
        for (String input : inputList) {
            res += solveFirstSingle(input);
        }
        return res;
    }

    public static Long solveSecond(ArrayList<String> inputList) {
        Long res = (long) 0;
        return res;
    }

    public static void main(String[] args) {
        // the input was manually split to contain one entry per line
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-02", "src", "input_test.txt");
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
