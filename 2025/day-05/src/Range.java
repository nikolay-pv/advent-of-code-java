public class Range implements Comparable<Range> {
    private long first;
    private long last;

    public Range(long first, long last) {
        this.first = first;
        this.last = last;
    }

    public long getFirst() {
        return first;
    }

    public long getLast() {
        return last;
    }

    public boolean contains(long val) {
        return place(val) == 0;
    }

    public long size() {
        return last - first + 1L;
    }

    public long place(long val) {
        if (val < first) {
            return -1;
        } else if (val > last) {
            return 1;
        }
        return 0;
    }

    public void merge(Range o) {
        this.first = Math.min(this.first, o.first);
        this.last = Math.max(this.last, o.last);
    }

    public static Range parseRange(String line) throws RuntimeException {
        int pos = line.indexOf('-', 0);
        if (pos == -1) {
            throw new RuntimeException("invalid format in " + line);
        }
        long first = Long.parseLong(line, 0, pos, 10);
        long last = Long.parseLong(line, pos + 1, line.length(), 10);
        return new Range(first, last);
    }

    @Override
    public int compareTo(Range o) {
        if (this.first == o.first) {
            return Long.compare(this.last, o.last);
        }
        return Long.compare(this.first, o.first);
    }

    @Override
    public String toString() {
        return "[" + first + ", " + last + "]";
    }
}
