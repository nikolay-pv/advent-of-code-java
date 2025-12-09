import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day01 {
    public static Integer solve_first(ArrayList<String> inputList) {
        Integer pos = 50;
        Integer counter = 0;
        for (String line : inputList) {
            Integer sign = line.charAt(0) == 'L' ? -1 : 1;
            Integer clicks = Integer.parseInt(line, 1, line.length(), 10);
            pos = (pos + sign * clicks) % 100;
            if (pos < 0) {
                pos += 100;
            }
            if (pos == 0) {
                counter += 1;
            }
        }
        return counter;
    }

    public static int solve_second(ArrayList<String> inputList) {
        Integer pos = 50;
        Integer counter = 0;
        for (String line : inputList) {
            Integer sign = line.charAt(0) == 'L' ? -1 : 1;
            Integer clicks = Integer.parseInt(line, 1, line.length(), 10);
            Integer new_pos = pos + sign * (clicks % 100);
            counter += clicks / 100;
            counter += Math.abs(new_pos) / 100;
            if (new_pos <= 0 && pos != 0) {
                counter += 1;
            }
            pos = (new_pos % 100 + 100) % 100;
        }
        return counter;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-01", "src", "input.txt");
        try(Stream<String> lines = Files.lines(path)) {
            ArrayList<String> inputList = new ArrayList<>();
            lines.forEach(inputList::add);
            System.out.println("Solution to the first problem is: " + solve_first(inputList));
            System.out.println("Solution to the second problem is: " + solve_second(inputList));
        } catch (IOException e) {
            System.out.println("Got exception while reading the file: " + e);
        }
    }    
}
