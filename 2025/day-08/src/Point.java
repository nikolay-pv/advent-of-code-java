public class Point {
    public int x;
    public int y;
    public int z;

    public long sqrDistanceTo(Point o) {
        return (long) (o.x - this.x) * (o.x - this.x) +
                (long) (o.y - this.y) * (o.y - this.y) +
                (long) (o.z - this.z) * (o.z - this.z);
    }

    public static Point parsePoint(String line) {
        Point p = new Point();
        var components = line.split(",");
        if (components.length != 3) {
            throw new RuntimeException("unknown format to parse Point from " + line);
        }
        p.x = Integer.parseInt(components[0]);
        p.y = Integer.parseInt(components[1]);
        p.z = Integer.parseInt(components[2]);
        return p;
    }
}
