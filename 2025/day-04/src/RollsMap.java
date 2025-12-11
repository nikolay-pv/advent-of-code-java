import java.util.ArrayList;
import java.util.HashSet;

public class RollsMap {
    private int width;
    private int height;
    private HashSet<Integer> map;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RollsMap(ArrayList<String> inputList) {
        this.width = inputList.getFirst().length();
        this.height = inputList.size();
        this.map = new HashSet<Integer>();
        Integer idx = 0;
        for (var line : inputList) {
            for (char c : line.toCharArray()) {
                if (c == '@') {
                    this.map.add(idx);
                }
                idx++;
            }
        }
    }

    public char getAt(int row, int col, char def) {
        if (row < 0 || col < 0 || col >= this.width || row >= this.height) {
            return def;
        }
        return this.map.contains(this.getIdx(row, col)) ? '@' : '.';
    }

    private Integer getIdx(int row, int col) {
        return Integer.valueOf(row * this.width + col);
    }

    public void removeAllAt(Indices indices) {
        for (int i = 0; i < indices.size(); i++) {
            this.map.remove(this.getIdx(indices.getRowAt(i), indices.getColAt(i)));
        }
    }

    public int countNeighbors(int row, int col, char ofValue, char assumingOutside) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (this.getAt(i, j, assumingOutside) == ofValue) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
