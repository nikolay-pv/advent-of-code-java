import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day03 {
    public static int solveFirst(ArrayList<String> inputList) {
        int sum = 0;
        for (String line : inputList) {
            // assume the last two digits are the max
            char first = line.charAt(line.length() - 2);
            char second = line.charAt(line.length() - 1);

            for (int i = line.length() - 3; i >= 0; i--) {
                char c = line.charAt(i);
                if (c >= first) {
                    // found a bigger number on the left side
                    if (first > second) { // check if the right number should take the place up
                        second = first;
                    }
                    // move the left number to the left
                    first = c;
                }
            }
            int joltage = (first - '0') * 10 + (second - '0');
            sum += joltage;
        }
        return sum;
    }

    public static int solveSecond(ArrayList<String> inputList) {
        return 0;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-03", "src", "input.txt");
        try(Stream<String> lines = Files.lines(path)) {
            ArrayList<String> inputList = new ArrayList<>();
            lines.forEach(inputList::add);
            System.out.println("Solution to the first problem is: " + solveFirst(inputList));
            System.out.println("Solution to the second problem is: " + solveSecond(inputList));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }    
}
