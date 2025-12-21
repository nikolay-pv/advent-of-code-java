import java.util.ArrayList;

public class DeviceNode {
    private String name;
    private ArrayList<DeviceNode> outlets;

    String getName() {
        return this.name;
    }

    ArrayList<DeviceNode> getOutlets() {
        return this.outlets;
    }

    DeviceNode(String name, ArrayList<DeviceNode> outlets) {
        this.name = name;
        this.outlets = outlets;
    }

    DeviceNode(String name) {
        this.name = name;
        this.outlets = new ArrayList<>();
    }

    void addOutlet(DeviceNode node) {
        this.outlets.add(node);
    }

    boolean isFirst() {
        return this.name.contentEquals("you");
    }

    boolean isLast() {
        return this.name.contentEquals("out");
    }
}
