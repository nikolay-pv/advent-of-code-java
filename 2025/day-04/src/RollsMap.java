import java.util.ArrayList;

public class RollsMap {
    private int width;
    private int height;
    private ArrayList<String> map;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RollsMap(ArrayList<String> inputList) {
        this.width = inputList.getFirst().length();
        this.height = inputList.size();
        this.map = inputList;
    }

    public char getAt(int row, int col, char def) {
        if (row < 0 || col < 0 || col >= this.width || row >= this.height) {
            return def;
        }
        return map.get(row).toCharArray()[col];
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
