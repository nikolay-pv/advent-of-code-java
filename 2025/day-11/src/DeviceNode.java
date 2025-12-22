import java.util.ArrayList;

public class DeviceNode {
    private String name;
    private ArrayList<DeviceNode> outlets;
    private ArrayList<DeviceNode> inlets;

    public String getName() {
        return this.name;
    }

    public ArrayList<DeviceNode> getOutlets() {
        return this.outlets;
    }

    public ArrayList<DeviceNode> getInlets() {
        return this.inlets;
    }

    public DeviceNode(String name) {
        this.name = name;
        this.outlets = new ArrayList<>();
        this.inlets = new ArrayList<>();
    }

    void addOutlet(DeviceNode node) {
        this.outlets.add(node);
        node.inlets.add(this);
    }
}
