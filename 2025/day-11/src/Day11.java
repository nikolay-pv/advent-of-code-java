import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day11 {
    private static long dfs(HashMap<String, Integer> visiting, DeviceNode target, DeviceNode node) {
        if (node == target) {
            return 1;
        }
        var state = visiting.get(node.getName());
        if (state > 0) {
            return 0;
        }
        visiting.put(node.getName(), state + 1); // visiting
        long pathsCount = 0L;
        for (var child : node.getOutlets()) {
            pathsCount += dfs(visiting, target, child);
        }
        visiting.put(node.getName(), state); // mark back with original state
        return pathsCount;
    }

    public static long solveFirst(ArrayList<String> inputList) {
        var graph = DeviceGraph.readDeviceGraph(inputList);
        var visiting = graph.getVisitingMap();
        return dfs(visiting, graph.getLast(), graph.getFirst());
    }

    public static long solveSecond(ArrayList<String> inputList) {
        return 0;
    }

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir"), "2025", "day-11", "src", "input.txt");
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
