import java.util.ArrayList;
import java.util.Collections;

public class DisjointSet {
    private int[] parents;
    private long[] sizes;

    public DisjointSet(int n) {
        this.parents = new int[n];
        this.sizes = new long[n];
        for (int i = 0; i < n; i++) {
            this.sizes[i] = 1;
            this.parents[i] = i;
        }
    }

    public int find(int x) {
        if (parents[x] != x) {
            parents[x] = find(parents[x]);
            return parents[x];
        }
        return x;
    }

    public boolean unite(int x, int y) {
        assert (!(x < 0 || y < 0 || x >= this.sizes.length || y >= this.sizes.length));

        x = find(x);
        y = find(y);
        if (x == y) {
            return false;
        }

        // for balancing the trees
        if (this.sizes[x] < this.sizes[y]) {
            var tmp = x;
            x = y;
            y = tmp;
        }

        this.parents[y] = x;
        this.sizes[x] = this.sizes[x] + this.sizes[y];
        return true;
    }

    public long sizeOfNLargest(int n) {
        ArrayList<Long> clusterSizes = new ArrayList<>();
        for (int i = 0; i != this.parents.length; i++) {
            var parent = find(i);
            if (parent == i) {
                var size = this.sizes[i];
                if (size > 1) {
                    clusterSizes.add(Long.valueOf(size));
                }
            }
        }
        Collections.sort(clusterSizes, Collections.reverseOrder());
        long size = 1;
        n = Math.min(clusterSizes.size(), n);
        for (int i = 0; i < n; i++) {
            size *= clusterSizes.get(i).longValue();
        }
        return size;
    }
}
