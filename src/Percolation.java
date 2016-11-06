import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] blocked;
    private final int gridSize;
    private final WeightedQuickUnionUF uf;

    private final int topId;
    private final int bottomId;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n cannot be less than or equal to 0");

        gridSize = n;
        blocked = new boolean[n+1][n+1];
        uf = new WeightedQuickUnionUF((n+1) * (n+1) + 1);

        // Since min(id) == gridSize + 1 and max(id) == gridSize * (gridSize + 1)
        // init top/bottom ids outside of these ranges
        topId = 0;
        bottomId = (n+1) * (n+1);

        // Initialize blocked squares to be completely filled (true means filled)
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                blocked[i][j] = true;
    }

    private int id(int i, int j) {
        return j * gridSize + i;
    }

    public void open(int row, int col) {
        checkDimensions(row, col);

        // If cell is already unblocked, return
        if (isOpen(row, col))
            return;

        // Open the current cell
        blocked[row][col] = false;

        // up, right, left, right cells
        int[][] tups = new int[][] {
                {row - 1, col}, {row, col - 1},
                {row, col + 1}, {row + 1, col} };

        // Connect with top/bottom id if on the top or bottom row
        if (row == 1)
            uf.union(id(row, col), topId);
        else if (row == gridSize)
            uf.union(id(row, col), bottomId);

        // Check the surrounding cells and connect the open ones
        for (int[] t : tups) {
            if (inBounds(t[0], t[1]) && isOpen(t[0], t[1]))
                uf.union(id(row, col), id(t[0], t[1]));

            // Connect with top/bottom id if on the top or bottom row
            if (t[0] == 1)
                uf.union(id(t[0], t[1]), topId);
            else if (t[0] == gridSize)
                uf.union(id(t[0], t[1]), bottomId);
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 1 && col >= 1 && row <= gridSize && col <= gridSize;
    }

    public boolean isOpen(int row, int col) {
        checkDimensions(row, col);
        return !blocked[row][col];
    }

    public boolean isFull(int row, int col) {
        checkDimensions(row, col);
        return isOpen(row, col) && uf.connected(id(row, col), topId);
    }

    public boolean percolates() {
        return uf.connected(topId, bottomId);
    }

    private void checkDimensions(int row, int col) {
        if (row < 1 || col < 1)
            throw new IndexOutOfBoundsException("Argument cannot be negative!");
        if (row > gridSize || col > gridSize)
            throw new IndexOutOfBoundsException("Cannot exceed blocked size of " + gridSize + "!");
    }

    public static void main(String[] args) {
    }
}
