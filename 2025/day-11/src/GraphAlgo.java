import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class GraphAlgo {
    private DeviceGraph graph;

    GraphAlgo(DeviceGraph graph) {
        this.graph = graph;
    }

    public long countPaths(DeviceNode root, DeviceNode target, HashSet<String> nodesSubset) {
        HashMap<String, Boolean> visiting = new HashMap<>();
        for (var name : this.graph.getNodes().keySet()) {
            visiting.put(name, false);
        }
        return countPathsImpl(visiting, root, target, nodesSubset, 1);
    }

    public long countReturnPaths(DeviceNode first, DeviceNode last, HashSet<String> nodesSubset) {
        HashMap<String, Boolean> visiting = new HashMap<>();
        for (var name : this.graph.getNodes().keySet()) {
            visiting.put(name, false);
        }
        return countPathsImpl(visiting, last, first, nodesSubset, -1);
    }

    private static long countPathsImpl(HashMap<String, Boolean> visiting,
            DeviceNode first, DeviceNode last, HashSet<String> nodesSubset, int direction) {
        // dfs
        if (first == last) {
            return 1;
        }
        visiting.put(first.getName(), true);
        long pathsCount = 0L;
        var nextNodes = direction > 0 ? first.getOutlets() : first.getInlets();
        for (var neighbor : nextNodes) {
            if (!visiting.get(neighbor.getName())
                    && (nodesSubset == null || nodesSubset.contains(neighbor.getName()))) {
                pathsCount += countPathsImpl(visiting, neighbor, last, nodesSubset, direction);
            }
        }
        visiting.put(first.getName(), false);
        return pathsCount;
    }

    public DeviceNode closest(DeviceNode root, DeviceNode firstTarget, DeviceNode secondTarget) {
        if (firstTarget == secondTarget) {
            return firstTarget;
        }
        var visiting = this.graph.getVisitingMap(0L);
        // bfs
        Queue<DeviceNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            var node = q.poll();
            if (node == firstTarget) {
                return firstTarget;
            }
            if (node == secondTarget) {
                return secondTarget;
            }
            visiting.put(node.getName(), 1L);
            for (var nextNode : node.getOutlets()) {
                if (visiting.get(nextNode.getName()) == 0) {
                    q.add(nextNode);
                }
            }
            visiting.put(node.getName(), 2L);
        }
        return null;
    }

    public void markReachable(DeviceNode root, Long bit, HashMap<String, Long> state) {
        makeReachableImpl(root, bit, state, 1);
    }

    public void markReturnReachable(DeviceNode root, Long bit, HashMap<String, Long> state) {
        makeReachableImpl(root, bit, state, -1);
    }

    private void makeReachableImpl(DeviceNode root, Long bit,
            HashMap<String, Long> state, int direction) {
        var visiting = this.graph.getVisitingMap(0L);
        // bfs
        Queue<DeviceNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            var node = q.poll();
            state.put(node.getName(), state.get(node.getName()) | bit);
            var nextInLine = direction > 0 ? node.getOutlets() : node.getInlets();
            for (var nextNode : nextInLine) {
                if (visiting.get(nextNode.getName()) == 0) {
                    visiting.put(nextNode.getName(), 1L); // visiting
                    q.add(nextNode);
                }
            }
            visiting.put(node.getName(), 2L); // visited
        }
    }
}
