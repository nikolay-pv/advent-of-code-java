import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Day07 {
    public static int solveFirst(ArrayList<String> inputList) {
        final int size = inputList.getFirst().length();
        // padding to not handle the bound checking down the road
        final boolean[] def = new boolean[size + 2];
        boolean[] occupied = new boolean[size + 2];
        boolean[] newOccupied = new boolean[size + 2];
        int counter = 0;
        for (var line : inputList) {
            for (int i = 1; i < size + 1; i++) {
                char c = line.charAt(i - 1); // shift back one from padded data to line idx
                switch (c) {
                    case 'S':
                        newOccupied[i] = true;
                        break;
                    case '^':
                        if (occupied[i]) {
                            newOccupied[i - 1] = true;
                            newOccupied[i] = false;
                            newOccupied[i + 1] = true;
                            counter += 1;
                        }
                        break;
                    case '.':
                        newOccupied[i] = newOccupied[i] || occupied[i];
                        break;
                    default:
                        break;
                }
            }
            occupied = newOccupied.clone();
            newOccupied = def.clone();
        }
        return counter;
    }

    public static long solveSecond(ArrayList<String> inputList) {
        final int size = inputList.getFirst().length();
        // padding to not handle the bound checking down the road
        final long[] def = new long[size + 2];
        long[] occupied = new long[size + 2];
        long[] newOccupied = new long[size + 2];
        for (var line : inputList) {
            for (int i = 1; i < size + 1; i++) {
                // shift back one from padded data to line idx
                switch (line.charAt(i - 1)) {
                    case 'S':
                        newOccupied[i] = 1;
                        break;
                    case '^':
                        if (occupied[i] > 0) {
                            newOccupied[i - 1] += occupied[i];
                            newOccupied[i] = 0;
                            newOccupied[i + 1] += occupied[i];
                        }
                        break;
                    case '.':
                        newOccupied[i] += occupied[i];
                        break;
                    default:
                        break;
                }
            }
            occupied = newOccupied.clone();
            newOccupied = def.clone();
        }
        long counter = 0;
        for (int i = 1; i < size + 1; i++) {
            counter += occupied[i];
        }
        return counter;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-07", "src", "input.txt");
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
