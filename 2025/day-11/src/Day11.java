import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

public class Day11 {

    public static long solveFirst(ArrayList<String> inputList) {
        var graph = DeviceGraph.readDeviceGraph(inputList);
        var algorithm = new GraphAlgo(graph);
        return algorithm.countPaths(graph.getNamed("you"), graph.getNamed("out"), null);
    }

    public static long solveSecond(ArrayList<String> inputList) {
        var graph = DeviceGraph.readDeviceGraph(inputList);
        var firstNode = graph.getNamed("svr");
        var midNode1 = graph.getNamed("dac");
        var midNode2 = graph.getNamed("fft");
        var lastNode = graph.getNamed("out");

        var graphAlgo = new GraphAlgo(graph);
        // sort out order
        var tmp = graphAlgo.closest(firstNode, midNode1, midNode2);
        if (tmp != midNode1) {
            // swap
            midNode2 = midNode1;
            midNode1 = tmp;
        }
        long pathsCount = graphAlgo.countReturnPaths(firstNode, midNode1, null);
        pathsCount *= graphAlgo.countPaths(midNode2, lastNode, null);

        final long midFlag1 = 0b1L;
        final long midFlag2 = 0b10L;
        final long allFlags = midFlag1 | midFlag2;
        var reachabilityState = graph.getVisitingMap(0L);
        graphAlgo.markReachable(midNode1, midFlag1, reachabilityState);
        graphAlgo.markReturnReachable(midNode2, midFlag2, reachabilityState);
        var filter = new HashSet<String>();
        for (String key : reachabilityState.keySet()) {
            if (reachabilityState.get(key).longValue() == allFlags) {
                filter.add(key);
            }
        }
        pathsCount *= graphAlgo.countPaths(midNode1, midNode2, filter);
        return pathsCount;
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
