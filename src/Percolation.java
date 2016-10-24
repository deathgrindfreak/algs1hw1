import edu.princeton.cs.algs4.UF;

public class Percolation {
    private boolean[][] grid;
    private final int gridSize;
    private final UF uf;

    public Percolation(int n) {
        gridSize = n;
        grid = new boolean[n][n];
        uf = new UF(n);

        // Initialize grid squares to be completely filled (true means filled)
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = true;
    }

    public void open(int row, int col) {
        checkDimensions(row, col);
        grid[row][col] = false;
    }

    public boolean isOpen(int row, int col) {
        checkDimensions(row, col);
        return !isFull(row, col);
    }

    public boolean isFull(int row, int col) {
        checkDimensions(row, col);
        return grid[row][col];
    }

    private void checkDimensions(int row, int col) {
        if (row < 0 || col < 0)
            throw new IllegalArgumentException("Argument cannot be negative!");
        if (row > gridSize || col > gridSize)
            throw new IndexOutOfBoundsException("Cannot exceed grid size of " + gridSize + "!");
    }

    public boolean percolates() {
        return false;
    }

    public static void main(String[] args) {

    }
}
