import java.util.ArrayList;

public class Indices {
    private ArrayList<Integer> rows;
    private ArrayList<Integer> cols;

    public Indices() {
        this.rows = new ArrayList<>();
        this.cols = new ArrayList<>();
    }

    public void add(int row, int col) {
        rows.add(Integer.valueOf(row));
        cols.add(Integer.valueOf(col));
    }

    public int getRowAt(int row) {
        return rows.get(row).intValue();
    }

    public int getColAt(int col) {
        return cols.get(col).intValue();
    }

    public int size() {
        return rows.size();
    }
}
