public class Point2D {
    public long x;
    public long y;

    public Point2D(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public static Point2D parsePoint2D(String line) {
        var pos = line.indexOf(',', 0);
        if (pos == -1) {
            throw new RuntimeException("Can't parse point from line " + line);
        }
        long x = Long.parseLong(line, 0, pos, 10);
        long y = Long.parseLong(line, pos + 1, line.length(), 10);
        return new Point2D(x, y);
    }
}
