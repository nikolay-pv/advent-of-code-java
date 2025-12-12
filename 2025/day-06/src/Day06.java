import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
import java.util.stream.Stream;

public class Day06 {
    public static long solveFirst(ArrayList<String> inputList) {
        ArrayList<Iterator<Long>> streams = new ArrayList<>();
        // skip the op stream
        for (int i = 0; i < inputList.size() - 1; i++) {
            streams.add(Arrays.stream(inputList.get(i).trim().split("\\s+")).map(s -> Long.parseLong(s)).iterator());
        }
        Iterator<String> operations = Arrays.stream(inputList.getLast().trim().split("\\s+")).iterator();
        Long total = 0L;
        while (operations.hasNext()) {
            var op = operations.next();
            Long intermediate = 0L;
            switch (op) {
                case "*":
                    intermediate = 1L;
                    for (Iterator<Long> iterator : streams) {
                        intermediate = intermediate * iterator.next();
                    }
                    break;
                case "+":
                    intermediate = 0L;
                    for (Iterator<Long> iterator : streams) {
                        intermediate = intermediate + iterator.next();
                    }
                    break;
                default:
                    throw new RuntimeException("unknown operation " + op);
            }
            total += intermediate;
        }
        return total.longValue();
    }

    public static long solveSecond(ArrayList<String> inputList) {
        int width = inputList.getFirst().length();
        int height = inputList.size();
        Long total = 0L;
        Stack<Long> numbers = new Stack<>();
        for (int i = width - 1; i >= 0; i--) {
            Long tmp = 0L;
            for (int j = height - 2, pow = 1; j >= 0; j--) {
                char c = inputList.get(j).charAt(i);
                if (c != ' ') {
                    tmp += (long) (c - '0') * pow;
                    pow *= 10;
                }
            }
            char op = inputList.getLast().charAt(i);
            switch (op) {
                case ' ':
                    if (tmp.longValue() != 0L) { // the entire column is empty
                        numbers.add(tmp);
                    }
                    break;
                case '+':
                    while (!numbers.empty()) {
                        tmp += numbers.pop();
                    }
                    total += tmp;
                    break;
                case '*':
                    if (tmp.longValue() == 0L) { // the entire column was empty
                        tmp = 1L;
                    }
                    while (!numbers.empty()) {
                        tmp *= numbers.pop();
                    }
                    total += tmp;
                    break;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-06", "src", "input.txt");
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
