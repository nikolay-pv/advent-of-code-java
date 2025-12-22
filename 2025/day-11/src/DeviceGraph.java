import java.util.ArrayList;
import java.util.HashMap;

public class DeviceGraph {
    private HashMap<String, DeviceNode> nodes;

    public HashMap<String, DeviceNode> getNodes() {
        return this.nodes;
    }

    public HashMap<String, Long> getVisitingMap(Long withValue) {
        var hm = new HashMap<String, Long>();
        for (var k : this.nodes.keySet()) {
            hm.put(k, withValue);
        }
        return hm;
    }

    public DeviceNode getNamed(String name) {
        return this.nodes.get(name);
    }

    private DeviceNode getOrCreate(String name) {
        var n = this.nodes.get(name);
        if (n == null) {
            n = new DeviceNode(name);
            this.nodes.put(name, n);
        }
        return n;
    }

    public DeviceGraph() {
        this.nodes = new HashMap<>();
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
