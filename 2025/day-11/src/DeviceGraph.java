import java.util.ArrayList;
import java.util.HashMap;

public class DeviceGraph {
    private HashMap<String, DeviceNode> nodes;
    private DeviceNode first;
    private DeviceNode last;

    public HashMap<String, DeviceNode> getNodes() {
        return this.nodes;
    }

    public HashMap<String, Integer> getVisitingMap() {
        var hm = new HashMap<String, Integer>();
        for (var k : this.nodes.keySet()) {
            hm.put(k, 0);
        }
        return hm;
    }

    public DeviceNode getFirst() {
        return this.first;
    }

    public DeviceNode getLast() {
        return this.last;
    }

    private DeviceNode getOrCreate(String name) {
        var n = this.nodes.get(name);
        if (n == null) {
            n = new DeviceNode(name);
            this.nodes.put(name, n);
        }
        if (n.isFirst()) {
            this.first = n;
        } else if (n.isLast()) {
            this.last = n;
        }
        return n;
    }

    public DeviceGraph() {
        this.nodes = new HashMap<>();
        this.first = null;
        this.last = null;
    }

    public static DeviceGraph readDeviceGraph(ArrayList<String> inputList) {
        var dm = new DeviceGraph();
        for (var line : inputList) {
            var labels = line.split(":?\s");
            var parent = dm.getOrCreate(labels[0]);
            for (int i = 1; i != labels.length; i++) {
                var child = dm.getOrCreate(labels[i]);
                parent.addOutlet(child);
            }
        }
        return dm;
    }
}
