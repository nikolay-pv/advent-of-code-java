import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day03 {
    public static long solveGeneric(ArrayList<String> inputList, int len) {
        long sum = 0;
        for (String line : inputList) {
            char[] joltageArr = line.substring(line.length() - len).toCharArray();
            for (int i = line.length() - len - 1; i >= 0; i--) {
                char c = line.charAt(i);
                if (c < joltageArr[0]) {
                    continue;
                }
                for (int j = 1; j < len; j++) {
                    if (joltageArr[0] >= joltageArr[j]) {
                        char tmp = joltageArr[0];
                        joltageArr[0] = joltageArr[j];
                        joltageArr[j] = tmp;
                    } else {
                        break;
                    }
                }
                joltageArr[0] = c;
            }
            long joltage = Long.parseLong(new String(joltageArr));
            sum += joltage;
        }
        return sum;
    }

    public static long solveFirst(ArrayList<String> inputList) {
        int len = 2;
        return solveGeneric(inputList, len);
    }

    public static long solveSecond(ArrayList<String> inputList) {
        int len = 12;
        return solveGeneric(inputList, len);
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
